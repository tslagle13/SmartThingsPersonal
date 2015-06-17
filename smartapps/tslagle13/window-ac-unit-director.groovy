/**
 *  Window AC Unit Director
 *
 *  Version 1.0
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
 *  The original licensing applies, with the following exceptions:
 *      1.  These modifications may NOT be used without freely distributing all these modifications freely
 *          and without limitation, in source form.  The distribution may be met with a link to source code
 *          with these modifications.
 *      2.  These modifications may NOT be used, directly or indirectly, for the purpose of any type of
 *          monetary gain.  These modifications may not be used in a larger entity which is being sold,
 *          leased, or anything other than freely given.
 *      3.  To clarify 1 and 2 above, if you use these modifications, it must be a free project, and
 *          available to anyone with "no strings attached."  (You may require a free registration on
 *          a free website or portal in order to distribute the modifications.)
 *      4.  The above listed exceptions to the original licensing do not apply to the holder of the
 *          copyright of the original work.  The original copyright holder can use the modifications
 *          to hopefully improve their original work.  In that event, this author transfers all claim
 *          and ownership of the modifications to "SmartThings."
 *
 *  Original Copyright information:
 *
 *  Copyright 2015 SmartThings
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

// Automatically generated. Make future change here.
definition(
	name: "Window AC Unit Director",
	namespace: "tslagle13",
	author: "Tim Slagle",
	description: "Changes a window AC unit off or on based on the temperature range of a specified temperature sensor and shuts off the unit if any windows/doors are open.",
	category: "Green Living",
	iconUrl: "http://icons.iconarchive.com/icons/icons8/windows-8/512/Science-Temperature-icon.png",
	iconX2Url: "http://icons.iconarchive.com/icons/icons8/windows-8/512/Science-Temperature-icon.png"
)

preferences {
    page name:"pageSetup"
    page name:"directorSettings"
    page name:"ThermostatandDoors"
    page name:"ThermostatBoost"
    page name:"Settings"

}

// Show setup page
def pageSetup() {

    def pageProperties = [
        name:       "pageSetup",
        title:      "Status",
        nextPage:   null,
        install:    true,
        uninstall:  true
    ]

	return dynamicPage(pageProperties) {
    	section("About 'Thermostat Mode Director'"){
        	paragraph "Changes mode of your thermostat based on the temperature range of a specified temperature sensor and shuts off the thermostat if any windows/doors are open."
        }
        section("Setup Menu") {
            href "directorSettings", title: "Director Settings", description: "", state:greyedOut()
            href "ACUnitandDoors", title: "Thermostat and Doors", description: "", state: greyedOutTherm()
            href "ThermostatBoost", title: "Thermostat Boost", description: "", state: greyedOutTherm1()
            href "Settings", title: "Settings", description: "", state: greyedOutSettings()
            }
        section([title:"Options", mobileOnly:true]) {
            label title:"Assign a name", required:false
        }
    }
}

// Show "Setup" page
def directorSettings() {

    def sensor = [
        name:       "sensor",
        type:       "capability.temperatureMeasurement",
        title:      "Which?",
        multiple:   false,
        required:   true
    ]
    def setLow = [
        name:       "setLow",
        type:       "decimal",
        title:      "Low temp?",
        required:   true
    ]
    
    def cold = [
        name:       "cold",
        type:       "enum",
        title:		"Mode?",
        metadata:   [values:["off", "on"]]
    ]
    
    def setHigh = [
        name:       "setHigh",
        type:       "decimal",
        title:      "High temp?",
        required:   true
    ]
    
    def hot = [
        name:       "hot",
        type:       "enum",
        title:		"Mode?",
        metadata:   [values:["off", "on"]]
    ]
    
    def neutral = [
        name:       "neutral",
        type:       "enum",
        title:		"Mode?",
        metadata:   [values:["off", "on"]]
    ]
    
    def pageName = "Setup"
    
    def pageProperties = [
        name:       "directorSettings",
        title:      "Setup",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {

		section("Which temperature sensor will control your thermostat?"){
			input sensor
		}
        section(""){
        	paragraph "Here you will setup the upper and lower thresholds for the temperature sensor that will send commands to your thermostat."
        }
		section("When the temperature falls below this tempurature set switch to..."){
			input setLow
			input cold
		}
        section("When the temperature goes above this tempurature set switch to..."){
			input setHigh
			input hot
		}
        section("When temperature is between the previous temperatures set switch to..."){
			input neutral
		}
    }
    
}

def ACUnitandDoors() {

    def ACUnit = [
        name:       "ACUnit",
        type:       "capability.switch",
        title:      "Which?",
        multiple:   true,
        required:   true
    ]
    def doors = [
        name:       "doors",
        type:       "capability.contactSensor",
        title:      "Low temp?",
        multiple:	true,
        required:   false
    ]
    
    def turnOffDelay = [
        name:       "turnOffDelay",
        type:       "decimal",
        title:		"Number of minutes",
        required:	false
    ]
    
    def pageName = "Thermostat and Doors"
    
    def pageProperties = [
        name:       "ThermostatandDoors",
        title:      "Thermostat and Doors",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {

		section(""){
        	paragraph "If any of the doors selected here are open the thermostat will automatically be turned off and this app will be 'disabled' until all the doors are closed. (This is optional)"
        }
        section("Choose thermostat...") {
			input ACUnit
		}
        section("If these doors/windows are open turn off thermostat regardless of outdoor temperature") {
			input doors
		}
		section("Wait this long before turning the thermostat off (defaults to 1 minute)") {
			input turnOffDelay
		}
    }
    
}

def ThermostatBoost() {

    def ACUnie1 = [
        name:       "ACUnit1",
        type:       "capability.switch",
        title:      "Which?",
        multiple:   true,
        required:   true
    ]
    def turnOnTherm = [
        name: 		"turnOnTherm", 
        type:		"enum", 
        metadata: 	[values: ["on", "off"]], 
        required: 	false
    ]
    
    def modes1 = [
        name:		"modes1", 
        type:		"mode", 
        title: 		"Put thermostat into boost mode when mode is...", 
        multiple: 	true, 
        required: 	false
    ]
    
    def turnOffDelay2 = [
        name:       "turnOffDelay2",
        type:       "decimal",
        title:		"Number of minutes",
        required:	false,
        defaultValue:30
    ]
    
    def pageName = "Thermostat Boost"
    
    def pageProperties = [
        name:       "ThermostatBoost",
        title:      "Thermostat Boost",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {

		section(""){
        	paragraph "Here you can setup the ability to 'boost' your thermostat.  In the event that your thermostat is 'off'" +
            " and you need to heat or cool your your home for a little bit you can 'touch' the app in the 'My Apps' section to boost your thermostat."
        }
		section("Choose a thermostats to boost") {
   			input ACUnit1
        }
        section("Turn the AC Unit switch...") {
    		input turnOnTherm
  		}
  		section("For how long?") {
    		input turnOffDelay2
  		}
        section("In addtion to 'app touch' the following modes will also boost the thermostat") {
   			input modes1
        }
    }
    
}

// Show "Setup" page
def Settings() {

    def sendPushMessage = [
        name: 		"sendPushMessage",
        type: 		"enum", 
        title: 		"Send a push notification?", 
        metadata:	[values:["Yes","No"]], 
        required:	true, 
        defaultValue: "Yes"
    ]
    
    def phoneNumber = [
        name: 		"phoneNumber", 
        type:		"phone", 
        title: 		"Send SMS notifications to?", 
        required: 	false
    ]
    
    def days = [
        name:       "days",
        type:       "enum",
        title:      "Only on certain days of the week",
        multiple:   true,
        required:   false,
        options: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
    ]
    
    def modes = [
        name:		"modes", 
        type:		"mode", 
        title: 		"Only when mode is", 
        multiple: 	true, 
        required: 	false
    ]
    
    def pageName = "Settings"
    
    def pageProperties = [
        name:       "Settings",
        title:      "Settings",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {


		section( "Notifications" ) {
			input sendPushMessage
			input phoneNumber
		}
		section(title: "More options", hideable: true) {
			href "timeIntervalInput", title: "Only during a certain time", description: getTimeLabel(starting, ending), state: greyedOutTime(starting, ending), refreshAfterSelection:true
			input days
			input modes
		}    
    }
    
}

def installed(){
	init()
}

def updated(){
	unsubscribe()
	init()
}

def init(){
	state.lastStatus = null
	subscribe(app, appTouch)
    runIn(60, "temperatureHandler")
    subscribe(sensor, "temperature", temperatureHandler)
    if(modes1){
    	subscribe(location, modeBoostChange)
    }
	if(doors){
		subscribe(doors, "contact.open", temperatureHandler)
        subscribe(doors, "contact.closed", doorCheck)
	}
}

def temperatureHandler(evt) {
	if(modeOk && daysOk && timeOk) {
		if(setLow > setHigh){
			def temp = setLow
			setLow = setHigh
			setHigh = temp
		}
		if (doorsOk) {
			def currentTemp = sensor.latestValue("temperature")
			if (currentTemp < setLow) {
            	if (state.lastStatus == "two" || state.lastStatus == "three" || state.lastStatus == null){
					//log.info "Setting thermostat mode to ${cold}"
					def msg = "I turn your AC unit ${cold} because temperature is below ${setLow}"
					ACUnit?."${cold}"()
                    sendMessage(msg)
                    }
				state.lastStatus = "one"
			}
			if (currentTemp > setHigh) {
            	if (state.lastStatus == "one" || state.lastStatus == "three" || state.lastStatus == null){
					//log.info "Setting thermostat mode to ${hot}"
					def msg = "I turn your AC unit ${hot} because temperature is above ${setHigh}"
					ACUnit."${hot}"()
					sendMessage(msg)
				}
				state.lastStatus = "two"
			}
			if (currentTemp > setLow && currentTemp < setHigh) {
            	if (state.lastStatus == "two" || state.lastStatus == "one" || state.lastStatus == null){
					//log.info "Setting thermostat mode to ${neutral}"
					def msg = "I turn your AC unit ${neutral} because temperature is neutral"
					ACUnit."${neutral}"()
					sendMessage(msg)
				}
				state.lastStatus = "three"
			}
		}
        else{
			def delay = (turnOffDelay != null && turnOffDelay != "") ? turnOffDelay * 60 : 60
			log.debug("Detected open doors.  Checking door states again")
			runIn(delay, "doorCheck")
		}
	}
}

def appTouch(evt) {
if(thermostat1){
	state.lastStatus = "disabled"
	def currentState = ACUnit.latestValue("switch") as String
	def mode = turnOnTherm
    state.currentState1 = currentState
    
    	ACUnit1."${mode}"()
        
    thermoShutOffTrigger()
    //log.debug("current coolingsetpoint is ${state.currentCoolSetpoint1}")
    //log.debug("current heatingsetpoint is ${state.currentHeatSetpoint1}")
    //log.debug("current mode is ${state.currentMode1}")
}    
}

def modeBoostChange(evt) {
	if(thermostat1 && modes1.contains(location.mode)){
		state.lastStatus = "disabled"
	def currentState = ACUnit.latestValue("switch") as String
	def mode = turnOnTherm
    state.currentState1 = currentState
    
    	ACUnit1."${mode}"()
	}
	else{
		thermoShutOff()
    }    
}

def thermoShutOffTrigger() {
    //log.info("Starting timer to turn off thermostat")
    def delay = (turnOffDelay2 != null && turnOffDelay2 != "") ? turnOffDelay2 * 60 : 60 
    state.turnOffTime = now()
	log.debug ("Turn off delay is ${delay}")
    runIn(delay, "thermoShutOff")
  }

def thermoShutOff(){
	if(state.lastStatus == "disabled"){
		def mode = state.currentState1
    	def mode1 = mode.replaceAll("\\]", "").replaceAll("\\[", "")
    
		state.lastStatus = null
    	ACUnit1."${mode1}"()
    	temperatureHandler()
    }
}

def doorCheck(evt){
	if (!doorsOk){
		log.debug("doors still open turning off ${thermostat}")
		def msg = "I switched your AC Unit off because some doors are open"
		
        if (state.lastStatus != "off"){
        	ACUnit.off()
			sendMessage(msg)
		}
		state.lastStatus = "off"
	}

	else{
    	if (state.lastStatus == "off"){
			state.lastStatus = null
        }
        temperatureHandler()
	}
}

private sendMessage(msg){
	if (sendPushMessage == "Yes") {
		sendPush(msg)
	}
	if (phoneNumber != null) {
		sendSms(phoneNumber, msg)
	}
}

private getAllOk() {
	modeOk && daysOk && timeOk && doorsOk
}

private getModeOk() {
	def result = !modes || modes.contains(location.mode)
	log.trace "modeOk = $result"
	result
}

private getDoorsOk() {
	def result = !doors || !doors.latestValue("contact").contains("open")
	log.trace "doorsOk = $result"
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
    
    else if (starting){
    	result = currTime >= start
    }
    else if (ending){
    	result = currTime <= stop
    }
    
	log.trace "timeOk = $result"
	result
}

def getTimeLabel(starting, ending){

	def timeLabel = "Tap to set"
	
    if(starting && ending){
    	timeLabel = "Between" + " " + hhmm(starting) + " "  + "and" + " " +  hhmm(ending)
    }
    else if (starting) {
		timeLabel = "Start at" + " " + hhmm(starting)
    }
    else if(ending){
    timeLabel = "End at" + hhmm(ending)
    }
	timeLabel
}

private hhmm(time, fmt = "h:mm a")
{
	def t = timeToday(time, location.timeZone)
	def f = new java.text.SimpleDateFormat(fmt)
	f.setTimeZone(location.timeZone ?: timeZone(time))
	f.format(t)
}
def greyedOut(){
	def result = ""
    if (sensor) {
    	result = "complete"	
    }
    result
}

def greyedOutTherm(){
	def result = ""
    if (thermostat) {
    	result = "complete"	
    }
    result
}

def greyedOutTherm1(){
	def result = ""
    if (thermostat1) {
    	result = "complete"	
    }
    result
}

def greyedOutSettings(){
	def result = ""
    if (starting || ending || days || modes || sendPushMessage) {
    	result = "complete"	
    }
    result
}

def greyedOutTime(starting, ending){
	def result = ""
    if (starting || ending) {
    	result = "complete"	
    }
    result
}

private anyoneIsHome() {
  def result = false

  if(people.findAll { it?.currentPresence == "present" }) {
    result = true
  }

  log.debug("anyoneIsHome: ${result}")

  return result
}

page(name: "timeIntervalInput", title: "Only during a certain time", refreshAfterSelection:true) {
		section {
			input "starting", "time", title: "Starting (both are required)", required: false 
			input "ending", "time", title: "Ending (both are required)", required: false 
		}
        }