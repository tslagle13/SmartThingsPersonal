/**
 *  Auto Home/Away with night modes
 *
 *  Copyright 2014 Tim Slagle
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
    name: "Auto away/home with night modes",
    namespace: "tslagle13",
    author: "Tim Slagle",
    description: "Monitor a set of presence sensors and change mode based on when your home is empty or occupied.  Included Night and Day modes for both an occupied and unoccupied house.",
    category: "Mode Magic",
    iconUrl: "http://icons.iconarchive.com/icons/icons8/ios7/512/Very-Basic-Home-Filled-icon.png",
    iconX2Url: "http://icons.iconarchive.com/icons/icons8/ios7/512/Very-Basic-Home-Filled-icon.png"
)

preferences {
  page(name: "selectPhrases")
  
  page( name:"Settings", title:"Settings", uninstall:true, install:true ) {
  	section("False alarm threshold (defaults to 10 min)") {
    	input "falseAlarmThreshold", "decimal", title: "Number of minutes", required: false
  	}
      section("Notifications") {
        input "sendPushMessageHome", "enum", title: "Send a push notification when home is occupied?", metadata:[values:["Yes","No"]], required:false
        input "sendPushMessageAway", "enum", title: "Send a push notification when home is empty?", metadata:[values:["Yes","No"]], required:false
  	}
    section("More options", hideable: true, hidden: true) {
			input "frequency", "decimal", title: "Minimum time between actions (defaults to every event)", description: "Minutes", required: false
			href "timeIntervalInput", title: "Only during a certain time", description: timeLabel ?: "Tap to set", state: timeLabel ? "complete" : "incomplete"
			input "days", "enum", title: "Only on certain days of the week", multiple: true, required: false,
				options: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
            input "modes", "mode", title: "Only when mode is", multiple: true, required: false
            
            
		}
		section([mobileOnly:true]) {
			label title: "Assign a name", required: false
		}
  }
  page(name: "timeIntervalInput", title: "Only during a certain time", nextPage: "More options") {
		section {
			input "starting", "time", title: "Starting", required: false
			input "ending", "time", title: "Ending", required: false
            }
            }
}

def selectPhrases() {
	def configured = (settings.awayDay && settings.awayNight && settings.homeDay && settings.awayDay)
    dynamicPage(name: "selectPhrases", title: "Configure", nextPage:"Settings", uninstall: true) {		
		section("Who?") {
			input "people", "capability.presenceSensor", title: "Monitor These Presences", required: true, multiple: true,  refreshAfterSelection:true
		}
        
		def phrases = location.helloHome?.getPhrases()*.label
		if (phrases) {
        	phrases.sort()
			section("Run This Phrase When...") {
				log.trace phrases
				input "awayDay", "enum", title: "Everyone Is Away And It's Day", required: true, options: phrases,  refreshAfterSelection:true
				input "awayNight", "enum", title: "Everyone Is Away And It's Night", required: true, options: phrases,  refreshAfterSelection:true
                input "homeDay", "enum", title: "At Least One Person Is Home And It's Day", required: true, options: phrases,  refreshAfterSelection:true
                input "homeNight", "enum", title: "At Least One Person Is Home And It's Night", required: true, options: phrases,  refreshAfterSelection:true
			}
		}
    }
}

def installed() {
  init()
  initialize()

}

def updated() {
  unsubscribe()
  initialize()
}

def uninstalled() {
unsubscribe()
}

def initialize() {
    subscribe(people, "presence", presence)
    subscribe(location, "sunrise", setSunrise)
    subscribe(location, "sunset", setSunset)
}

def setSunrise() {
  state.sunMode = "sunrise";
  changeSunMode(newMode)
}

def setSunset() {
  state.sunMode = "sunset";
  changeSunMode(newMode)
}


def changeSunMode(newMode) {
  if(allOk) {

  if(everyoneIsAway() && (state.sunMode = "sunrise")) {
    log.info("Home is Empty  Setting New Away Mode")
    def delay = (falseAlarmThreshold != null && falseAlarmThreshold != "") ? falseAlarmThreshold * 60 : 10 * 60 
    runIn(delay, "setAway")
  }

  if(everyoneIsAway() && (state.sunMode = "sunset")) {
    log.info("Home is Empty  Setting New Away Mode")
    def delay = (falseAlarmThreshold != null && falseAlarmThreshold != "") ? falseAlarmThreshold * 60 : 10 * 60 
    runIn(delay, "setAway")
  }
  
  else {
  log.info("Home is Occupied Setting New Home Mode")
  setHome()


  }
}
}

def presence(evt) {
  if(allOk) {
  if(evt.value == "not present") {
    log.debug("Checking if everyone is away")

    if(everyoneIsAway()) {
      log.info("Nobody is home, running away sequence")
      def delay = (falseAlarmThreshold != null && falseAlarmThreshold != "") ? falseAlarmThreshold * 60 : 10 * 60 
      runIn(delay, "setAway")
    }
  }

  	else {
  		log.info("Someone is home, running home sequence")
  		setHome()
    }
}
}

def setAway() {
if(allOk) {
  if(everyoneIsAway()) {
    if(state.sunMode == "sunset") {
      def message = "Performing \"${awayNight}\" for you as requested."
      log.info(message)
      sendAway(message)
      location.helloHome.execute(settings.awayNight)
    }
    
    else if(state.sunMode == "sunrise") {
      def message = "Performing \"${awayDay}\" for you as requested."
      log.info(message)
      sendAway(message)
      location.helloHome.execute(settings.awayDay)
      }
    else {
      log.debug("Mode is the same, not evaluating")
    }
  }

  else {
    log.info("Somebody returned home before we set to '${newAwayMode}'")
  }
}
}

def setHome() {
if(allOk) {
log.info("Setting Home Mode!!")
if(anyoneIsHome()) {
      if(state.sunMode == "sunset"){
      if (location.mode != "Home Night"){
      def message = "Performing \"${homeNight}\" for you as requested."
        log.info(message)
        sendHome(message)
        location.helloHome.execute(settings.homeNight)
            sendSms(phone1, message)
       }
       }
      if(state.sunMode == "sunrise"){
      if(location.mode != "Home Day"){
      def message = "Performing \"${homeDay}\" for you as requested."
        log.info(message)
        sendHome(message)
        location.helloHome.execute(settings.homeDay)
            sendSms(phone1, message)
            }
            }
    }
    
}
}

private everyoneIsAway() {
  def result = true

  if(people.findAll { it?.currentPresence == "present" }) {
    result = false
  }

  log.debug("everyoneIsAway: ${result}")

  return result
}

private anyoneIsHome() {
  def result = false

  if(people.findAll { it?.currentPresence == "present" }) {
    result = true
  }

  log.debug("anyoneIsHome: ${result}")

  return result
}

private sendHome(msg) {
  
  if(sendPushMessageHome != "No") {
    log.debug("Sending push message")
    sendPush(msg)
  }


  log.debug(msg)
}

private sendAway(msg) {
  if(sendPushMessageAway != "No") {
    log.debug("Sending push message")
    sendPush(msg)
  }
  log.debug(msg)
}



private dayString(Date date) {
	def df = new java.text.SimpleDateFormat("yyyy-MM-dd")
	if (location.timeZone) {
		df.setTimeZone(location.timeZone)
	}
	else {
		df.setTimeZone(TimeZone.getTimeZone("America/New_York"))
	}
	df.format(date)
}

private oncePerDayOk(Long lastTime) {
	def result = true
	if (oncePerDay) {
		result = lastTime ? dayString(new Date()) != dayString(new Date(lastTime)) : true
		log.trace "oncePerDayOk = $result"
	}
	result
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

