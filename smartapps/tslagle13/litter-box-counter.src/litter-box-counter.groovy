/**
 *  Litter Box Counter
 *
 *  Copyright 2016 Tim Slagle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Litter Box Counter",
    namespace: "tslagle13",
    author: "Tim Slagle",
    description: "Count how many times your cat uses the litter box and alert you if they fall under the average.",
    category: "Pets",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Which contact sensor will detect your cat?") {
		input "catContact", "capability.accelerationSensor", title: "Contact", multiple: false, required: true
	}
    section("How many days should be used to calculate the average?") {
		input "daysAverage", "number", title: "Days", required: true, defaultValue: 3
	} 
    section("How far below the average should you be alerted?") {
		input "underAverage", "number", title: "Defecit", required: true, defaultValue: 2
	}
    section("Where wold you like to be notified?") {
    	input("recipients", "contact", title: "Send notifications to", required: true) {
        	input "push", "bool", title: "Send an push notification?", required:false
    		input "phone", "phone", title: "Send an SMS to this number?", required:false
    	}
    }
    section("Send a message with a daily usage count?") {
		input "sendDailyUsage", "bool", required: false
	}
    section("When should we check if usage is under average and send daily usage count?") {
		input "dailyCheck", "time", title: "Select a time", required: true
	}
    section("What is your cats name?") {
		input "catName", "text", title: "name", required: true
	}
}

def installed() {
	state.averageList = []
	initialize()
}

def updated() {
	unsubscribe()
	initialize()
}

def initialize() {
	state.lastTime = 0
    subscribe(catContact, "acceleration.active", contactEvent)
    schedule(dailyCheck, "checkDailyCount")
}

//handle contact event
def contactEvent(evt) {
    if (((now() - state.lastTime) > 300000) || (state.lastTime == 0)) {
    	usageCounter()
        state.lastTime = now()
        log.debug "Cat is using the litter box"
    }
}

//build list and calculate average
def usageCounter() {
	def removeList = []
	log.debug "calculateAverage()"
    state.averageList.push(now())
    state.averageList.each{ it ->
    	if ((now() - it) > (86400000 * daysAverage)){
        	removeList.push(it)
        }
    }
    def average = state.averageList
    def newAverage = average - removeList
    state.averageList = newAverage
    log.debug state.averageList
    
    state.average = (state.averageList.size().div(daysAverage)).toDouble().round(1)
    log.debug state.average
}


//check if daily count is under average
def checkDailyCount() {
	if (((now() - state.lastTimeDaily) > 60000) || (state.lastTimeDaily == 0)) {
		def dailyCount = 0
		log.debug "checkDailyCount()"
    	state.averageList.each{ it ->
    		if ((now() - it) <= 86400000) {
    	    	dailyCount = dailyCount + 1
    	    }
    	}    
    	def deficit = state.average - dailyCount
    	if (deficit >= underAverage) {
        	state.lastTimeDaily = now()
    		def alertMessage = "${catName} has only used the litter box ${dailyCount} times today. That is ${deficit} times below average"
    		if (recipients) {
    	    		sendNotificationToContacts(alertMessage, recipients)
    	    	}
    	    else if (!recipients) {
    	    	if (push) {
    	    		sendPush(alertMessage)
    	    	}    
    	    	if (phone){
    	    		sendSms(phone, alertMessage)
    	   		}
    	    }
		}
    	if (sendDailyUsage ) {
        	state.lastTimeDaily = now()
    		def dailyMessage = "${catName} has used the litter box ${dailyCount} times today."
    		if (recipients) {
    	    		sendNotificationToContacts(dailyMessage, recipients)
    	    	}
    	    else if (!recipients) {
    	    	if (push) {
    	    		sendPush(dailyMessage)
    	    	}    
    	    	if (phone){
    	    		sendSms(phone, dailyMessage)
    	   		}
    	    }
    	}
    }
}    