/**
 *  Netatmo Monitor
 *
 *  Current Version: 2.0
 *
 *
 *
 *  Copyright 2015 Tim Slagle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *	The original licensing applies, with the following exceptions:
 *		1.	These modifications may NOT be used without freely distributing all these modifications freely
 *			and without limitation, in source form.	 The distribution may be met with a link to source code
 *			with these modifications.
 *		2.	These modifications may NOT be used, directly or indirectly, for the purpose of any type of
 *			monetary gain.	These modifications may not be used in a larger entity which is being sold,
 *			leased, or anything other than freely given.
 *		3.	To clarify 1 and 2 above, if you use these modifications, it must be a free project, and
 *			available to anyone with "no strings attached."	 (You may require a free registration on
 *			a free website or portal in order to distribute the modifications.)
 *		4.	The above listed exceptions to the original licensing do not apply to the holder of the
 *			copyright of the original work.	 The original copyright holder can use the modifications
 *			to hopefully improve their original work.  In that event, this author transfers all claim
 *			and ownership of the modifications to "SmartThings."
 *
 *	Original Copyright information:
 *
 *	Copyright 2015 SmartThings
 *
 *	Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *	in compliance with the License. You may obtain a copy of the License at:
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *	on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *	for the specific language governing permissions and limitations under the License.
 *
 */

definition(
    name: "Netatmo Monitor",
    namespace: "tslagle13",
    author: "Tim Slagle",
    description: "Monitor all netatmo values within a specific range.  Will notifiy you outside of a specific range only.",
    iconUrl: "http://withasideofthriftiness.com/wp-content/uploads/2013/05/netatmo-logo.jpeg",
    iconX2Url: "http://withasideofthriftiness.com/wp-content/uploads/2013/05/netatmo-logo.jpeg")

preferences {
  page(name: "selectNetatmo")
  page(name: "netatmoTemp")
  page(name: "netatmoHumidity")
  page(name: "netatmoCO")
  page(name: "netatmoNoise")
  page(name: "netatmoPressure")
  page(name: "timeIntervalInput", title: "Only during a certain time", nextPage: "Settings") {
		section {
			input "starting", "time", title: "Starting", required: false
			input "ending", "time", title: "Ending", required: false
            }
            }
  
  page( name:"Settings", title:"Settings", uninstall:true, install:true ) {
	section("Notifications") {
        input "sendPushMessageAll", "bool", title: "Bypass all notifications?", defaultVaule:false, required:true
        input "frequency", "decimal", title: "Minimum time between actions (defaults to every event)", description: "Minutes", required: false
  	}
    section("More options", hideable: true, hidden: true) {
			href "timeIntervalInput", title: "Only during a certain time", description: timeLabel ?: "Tap to set", state: timeLabel ? "complete" : "incomplete"
			input "days", "enum", title: "Only on certain days of the week", multiple: true, required: false,
				options: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
			input "modes", "mode", title: "Only when mode is", multiple: true, required: false
		}
	}
}


def selectNetatmo() {
    dynamicPage(name: "selectNetatmo", title: "Netatmo Monitor", nextPage:"netatmoTemp", uninstall: true) {		
		section("Netatmo") {
			input "netatmo", "capability.temperatureMeasurement", title: "Monitor this Netatmo", required: true, multiple: false,  refreshAfterSelection:true
            input "location1", "text", title: "Where is your netatmo?(E.g. Basement)", required: true
		}   	
    }
}

def netatmoTemp() {
    dynamicPage(name: "netatmoTemp", title: "Netatmo Monitor", nextPage:"netatmoHumidity", uninstall: true) {		
		section("Temperature") {
			input "netatmoTempHigh", "number", title: "What temperature is too high? (Number)", required: false
            input "sendPushMessageTempHigh", "bool", title: "Bypass high temperature notifications?", defaultValue:true, required:true
            input "netatmoTempLow", "number", title: "What temperature is too low? (Number)", required: false
            input "sendPushMessageTemp", "bool", title: "Bypass low temperature notifications?", defaultValue:true, required:true
		}	
    }
}

def netatmoHumidity() {
    dynamicPage(name: "netatmoHumidity", title: "Netatmo Monitor", nextPage:"netatmoPressure", uninstall: true) {		
		section("Humidity") {
			input "netatmoHumidityHigh", "number", title: "What humidity is too high? (Number)", required: false
            input "sendPushMessageHumidityHigh", "bool", title: "Bypass high humidity notifications?", defaultValue:true, required:true
            input "netatmoHumidityLow", "number", title: "What humidity is too low? (Number)", required: false
            input "sendPushMessageHumidity", "bool", title: "Bypass low humidity notifications?", defaultValue:true, required:true
		}	
    }
}

def netatmoPressure() {
    dynamicPage(name: "netatmoPressure", title: "Netatmo Monitor", nextPage:"netatmoCO", uninstall: true) {		
		section("Pressure") {
			input "netatmoPressueHigh", "number", title: "What air pressure level is too high? (Number)", required: false
            input "sendPushMessagePressureHigh", "bool", title: "Bypass high pressure notifications?", defaultValue:true, required:true
            input "netatmoPressueLow", "number", title: "What air pressure level is too low? (Number)", required: false
            input "sendPushMessagePressure", "bool", title: "Bypass low pressure notifications?", defaultValue:true, required:true
		}	
    }
}

def netatmoCO() {
    dynamicPage(name: "netatmoCO", title: "Netatmo Monitor", nextPage:"netatmoNoise", uninstall: true) {		
		section("CO2") {
			input "netatmoCOHigh", "number", title: "What CO2 level is too high? (Number)", required: false
            input "sendPushMessageCO", "bool", title: "Bypass CO2 notifications?", defaultValue:true, required:true
		}	
    }
}

def netatmoNoise() {
    dynamicPage(name: "netatmoNoise", title: "Netatmo Monitor", nextPage:"Settings", uninstall: true) {		
		section("Noise") {
			input "netatmoNoiseHigh", "number", title: "What Noise level is too high? (Number)", required: false
            input "sendPushMessageNoise", "bool", title: "Bypass noise notifications?", defaultValue:true, required:true
		}	
    }
}

def installed(){
	go()
}

def updated(){
	unsubscribe()
    go()
}

def go(){
subscribe(netatmo, "temperature", notifyTemp)
subscribe(netatmo, "humidity", notifyHumidity)
subscribe(netatmo, "noise", notifyNoise)
subscribe(netatmo, "carbonDioxide", notifyCO)
subscribe(netatmo, "pressure", notifyPressure)
//subscribe(app, appTouch)  //Used for testing
}

def notifyTemp(evt) {
if (allOk){
if ( sendPushMessageAll != "On" ) {
if (frequency) {
	def lastTimeTemp = state[frequencyKeyTemp(evt)]
	if (lastTimeTemp == null || now() - lastTimeTemp >= frequency * 60000) {
    if (netatmo.latestValue("temperature") > netatmoTempHigh){
    def msg = "Netatmo detected a temperature above ${netatmoTempHigh} in ${location1}.  Current temp is ${netatmo.latestValue("temperature")}"
    	if ( sendPushMessageTempHigh != "On" ) {
        	log.info(msg)
        	sendPush(msg)
            state[frequencyKeyTemp(evt)] = now()
    	}
    } 
    
    if (netatmo.latestValue("temperature") < netatmoTempLow){
    def msg = "Netatmo detected a temperature under ${netatmoTempLow} in ${location1}. Current temp is ${netatmo.latestValue("temperature")}"
    	if ( sendPushMessageTemp != "On" ) {
        	log.info(msg)
        	sendPush(msg)
            state[frequencyKeyTemp(evt)] = now()
    	}
    } 
}
}
}
}
}

def notifyHumidity(evt){
if (allOk){
if ( sendPushMessageAll != "On" ) {
if (frequency) {
	def lastTimeHumidity = state[frequencyKeyHumidity(evt)]
	if (lastTimeHumidity == null || now() - lastTimeHumidity >= frequency * 60000) {
if (netatmo.latestValue("humidity") < netatmoHumidityLow){
    def msg = "Netatmo detected a humidity under ${netatmoHumidityLow} in ${location1}. Current humidity is ${netatmo.latestValue("humidity")}"
    	if ( sendPushMessageHumidityHigh != "On" ) {
        	log.info(msg)
        	sendPush(msg)
            state[frequencyKeyHumidity(evt)] = now()
    	}
    }
    
    if (netatmo.latestValue("humidity") > netatmoHumidityHigh){
    def msg = "Netatmo detected a humidity over ${netatmoHumidityHigh} in ${location1}. Current humidity is ${netatmo.latestValue("humidity")}"
    	if ( sendPushMessageHumidity != "On" ) {
        	log.info(msg)
        	sendPush(msg)
            state[frequencyKeyHumidity(evt)] = now()
    	}
    }
    
}
}
}
}
}

def notifyNoise(evt){
if (allOk){
if ( sendPushMessageAll != "On" ) {
if (frequency) {
	def lastTimeNoise = state[frequencyKeyNoise(evt)]
	if (lastTimeNoise == null || now() - lastTimeNoise >= frequency * 60000) {
if (netatmo.latestValue("noise") > netatmoNoiseHigh){
    def msg = "Netatmo detected a noise level over ${netatmoNoiseHigh} in ${location1}. Current noise level is ${netatmo.latestValue("noise")}"
    	if ( sendPushMessageNoise != "On" ) {
        	log.info(msg)
        	sendPush(msg)
            state[frequencyKeyNoise(evt)] = now()
    	}
    }
}
}
}
}
}

def notifyCO(evt){
if (allOk){
if ( sendPushMessageAll != "On" ) {
if (frequency) {
	def lastTimeCO = state[frequencyKeyCO(evt)]
	if (lastTimeCO == null || now() - lastTimeCO >= frequency * 60000) {
if (netatmo.latestValue("carbonDioxide") > netatmoCOHigh){
        def msg = "I detected a CO2 level over ${netatmoCOHigh} for ${netatmo}.  Current CO2 level is ${netatmo.latestValue("carbonDioxide")}"
    	if ( sendPushMessageCO != "On" ) {
        	log.info(msg)
        	sendPush(msg)
            state[frequencyKeyCO(evt)] = now()
    	}
    } 
}
}
}
}
}


def notifyPressure(evt){
if (allOk){
if ( sendPushMessageAll != "On" ) {
if (frequency) {
	def lastTimePressure = state[frequencyKeyPressure(evt)]
	if (lastTimePressure == null || now() - lastTimeTemp >= frequency * 60000) {
if (netatmo.latestValue("pressure") > netatmoPressureHigh){
    def msg = "Netatmo detected a pressure level over ${netatmoPressureHigh} in ${location1}. Current pressure is ${netatmo.latestValue("pressure")}"
    	if ( sendPushMessagePressureHigh != "On" ) {
        	log.info(msg)
        	sendPush(msg)
            state[frequencyKeyPressure(evt)] = now()
    	}
    }
    
    if (netatmo.latestValue("pressure") < netatmoPressureLow){
    def msg = "Netatmo detected a pressure level under ${netatmoPressureLow} in ${location1}. Current pressure is ${netatmo.latestValue("pressure")}"
    	if ( sendPushMessagePressure != "On" ) {
        	log.info(msg)
        	sendPush(msg)
            state[frequencyKeyPressure(evt)] = now()
    	}
    }
}
}
}
}
}


//def appTouch(evt){      //Used for testing
//log.info("test")

//if (frequency) {
//	def lastTimeTemp = state[frequencyKeyTemp(evt)]
//	if (lastTimeTemp == null || now() - lastTimeTemp >= frequency * 60000) {
//    if (netatmo.latestValue("temperature") > netatmoTempHigh){
//    def msg = "Netatmo detected a temperature above ${netatmoTempHigh} in ${location1}."
//    sendNotificationEvent(msg)
//    	if ( sendPushMessageTemp != "Yes" ) {
//        	log.info(msg)
        	
//            state[frequencyKeyTemp(evt)] = now()
//    	}
//    } 
//    
//    if (netatmo.latestValue("temperature") < netatmoTempLow){
//    def msg = "Netatmo detected a temperature under ${netatmoTempLow} in ${location1}."
//    sendNotificationEvent(msg)
//    	if ( sendPushMessageTemp != "Yes" ) {
//        	log.info(msg)
//        	
//            state[frequencyKeyTemp(evt)] = now()
//    	}
//    } 
//}
//}
//}


private frequencyKeyTemp(evt) {
	"lastActionTimeStamp"
}

private frequencyKeyHumidity(evt) {
	"lastActionTimeStamp"
}

private frequencyKeyPressure(evt) {
	"lastActionTimeStamp"
}

private frequencyKeyCO(evt) {
	"lastActionTimeStamp"
}

private frequencyKeyNoise(evt) {
	"lastActionTimeStamp"
}



private getAllOk() {
	modeOk && daysOk && timeOk
}

private getModeOk() {
	def result = !modes || modes.contains(location.mode)
	log.trace "modeOk = $result"
	result
}

private getDaysOk() {
	def result = true
	if (days) {
		def df = new java.text.SimpleDateFormat("EEEE")
		if (location.timeZone) {
			df.setTimeZone(location.timeZone)
		}
		else {
			df.setTimeZone(TimeZone.getTimeZone("America/New_York"))
		}
		def day = df.format(new Date())
		result = days.contains(day)
	}
	log.trace "daysOk = $result"
	result
}

private getTimeOk() {
	def result = true
	if (starting && ending) {
		def currTime = now()
		def start = timeToday(starting).time
		def stop = timeToday(ending).time
		result = start < stop ? currTime >= start && currTime <= stop : currTime <= stop || currTime >= start
	}
	log.trace "timeOk = $result"
	result
}

private hhmm(time, fmt = "h:mm a")
{
	def t = timeToday(time, location.timeZone)
	def f = new java.text.SimpleDateFormat(fmt)
	f.setTimeZone(location.timeZone ?: timeZone(time))
	f.format(t)
}

private getTimeLabel()
{
	(starting && ending) ? hhmm(starting) + "-" + hhmm(ending, "h:mm a z") : ""
}
// TODO - End Centralize
