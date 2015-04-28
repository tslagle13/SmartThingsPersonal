/**
 *  Thermostat Mode Director
 *
 *  Version 2.1.1
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
 *  Copyright 2014 SmartThings
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
	name: "Thermostat Mode Director",
	namespace: "tslagle13",
	author: "Tim Slagle",
	description: "Changes mode of your thermostat based on the temperature range of a specified temperature sensor and shuts off the thermostat if any windows/doors are open.",
	category: "Green Living",
	iconUrl: "http://icons.iconarchive.com/icons/icons8/windows-8/512/Science-Temperature-icon.png",
	iconX2Url: "http://icons.iconarchive.com/icons/icons8/windows-8/512/Science-Temperature-icon.png"
)

preferences {
	page( name:"Sensor", title:"Setup", nextPage:"directorSettings", uninstall:true, install:false ) {
    	section("About 'Thermostat Mode Director'"){
        	paragraph "Changes mode of your thermostat based on the temperature range of a specified temperature sensor and shuts off the thermostat if any windows/doors are open."
        }
		section("Which temperature sensor will control your thermostat?"){
			input "sensor", "capability.temperatureMeasurement", title: "Sensor", multiple:false
		}
	}
	page( name:"directorSettings", title:"Director Settings", nextPage:"ThermostatandDoors", uninstall:true, install:false ) {
    	section(""){
        	paragraph "Here you will setup the upper and lower thresholds for the temperature sensor that will send commands to your thermostat."
        }
		section("When the temperature falls below this tempurature set mode to..."){
			input "setLow", "decimal", title: "Low temp?"
			input "cold", "enum", title: "Mode?", metadata:[values:["auto", "heat", "cool", "off"]], required:false
		}
        section("When the temperature goes above this tempurature set mode to..."){
			input "setHigh", "decimal", title: "High temp?"
			input "hot", "enum", title: "Mode?", metadata:[values:["auto", "heat", "cool", "off"]], required:false
		}
        section("When temperature is between the previous temperatures, change mode to..."){
			input "neutral", "enum", title: "Mode?", metadata:[values:["auto", "heat", "cool", "off"]], required:false
		}
	}
	page( name:"ThermostatandDoors", title:"Thermostat and Doors", nextPage:"ThermostatBoost", uninstall:true, install:false ) {
		section(""){
        	paragraph "If any of the doors selected here are open the thermostat will automatically be turned off and this app will be 'disabled' until all the doors are closed. (This is optional)"
        }
        section("Choose thermostat...") {
			input "thermostat", "capability.thermostat", multiple: true
		}
        section("If these doors/windows are open turn off thermostat regardless of outdoor temperature") {
			input "doors", "capability.contactSensor", multiple: true, required:false
		}
		section("Wait this long before turning the thermostat off (defaults to 1 minute)") {
			input "turnOffDelay", "decimal", title: "Number of minutes", required: false
		}
	}
    page( name:"ThermostatBoost", title:"Thermostat Boost", nextPage:"Settings", uninstall:true, install:false ) {
    	section(""){
        	paragraph "Here you can setup the ability to 'boost' your thermostat.  In the event that your thermostat is 'off'" +
            " and you need to heat or cool your your home for a little bit you can 'touch' the app in the 'My Apps' section to boost your thermostat."
        }
		section("Choose a thermostats to boost") {
   			input "thermostat1", "capability.thermostat", multiple: true
        }
        section("If thermostat is off switch to which mode?") {
    		input "turnOnTherm", "enum", metadata: [values: ["cool", "heat"]], required: false
  		}
        section("Set the thermostat to the following temps") {
    		input "coolingTemp", "decimal", title: "Cooling temp?", required: false
    		input "heatingTemp", "decimal", title: "Heating temp?", required: false
  		}
  		section("For how long?") {
    		input "turnOffDelay2", "decimal", defaultValue:30
  		}
	}
   	
	
	page( name:"Settings", title:"Settings", uninstall:false, install:true ) {
		section( "Notifications" ) {
			input "sendPushMessage", "enum", title: "Send a push notification?", metadata:[values:["Yes","No"]], required:true, defaultValue: "Yes"
			input "phoneNumber", "phone", title: "Send SMS notifications to?", required: false
		}
		section("Settings") {
			label title: "Assign a name", required: false
		}
		section(title: "More options", hidden: hideOptionsSection(), hideable: true) {
			def timeLabel = timeIntervalLabel()

			input "days", "enum", title: "Only on certain days of the week", multiple: true, required: false,
				options: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]

			input "modes", "mode", title: "Only when mode is", multiple: true, required: false
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
	subscribe(sensor, "temperature", temperatureHandler)
	if(doors){
		subscribe(doors, "contact", temperatureHandler)
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
            	if ((state.lastStatus == "two") || (state.lastStatus == "three") || (state.lastStatus == null) || (state.lastStatus == "off")){
					//log.info "Setting thermostat mode to ${cold}"
					def msg = "I changed your thermostat mode to ${cold} because temperature is below ${setLow}"
					thermostat?."${cold}"()
                    sendMessage(msg)
                    }
				state.lastStatus = "one"
			}
			if (currentTemp > setHigh) {
            	if ((state.lastStatus == "one") || (state.lastStatus == "three") || (state.lastStatus == null) || (state.lastStatus == "off")){
					//log.info "Setting thermostat mode to ${hot}"
					def msg = "I changed your thermostat mode to ${hot} because temperature is above ${setHigh}"
					thermostat?."${hot}"()
					sendMessage(msg)
				}
				state.lastStatus = "two"
			}
			if (currentTemp > setLow && currentTemp < setHigh) {
            	if ((state.lastStatus == "two") || (state.lastStatus == "one") || (state.lastStatus == null) || (state.lastStatus == "off")){
					//log.info "Setting thermostat mode to ${neutral}"
					def msg = "I changed your thermostat mode to ${neutral} because temperature is neutral"
					thermostat?."${neutral}"()
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
	state.lastStatusDisabled = state.lastStatus
	state.lastStatus = "disabled"
	def currentCoolSetpoint = thermostat1.latestValue("coolingSetpoint") as String
    def currentHeatSetpoint = thermostat1.latestValue("heatingSetpoint") as String
    def currentMode = thermostat1.latestValue("thermostatMode") as String
	def mode = turnOnTherm
    state.currentCoolSetpoint1 = currentCoolSetpoint
    state.currentHeatSetpoint1 = currentHeatSetpoint
    state.currentMode1 = currentMode
    
    	thermostat1."${mode}"()
    	thermostat1.setCoolingSetpoint(coolingTemp)
    	thermostat1.setHeatingSetpoint(heatingTemp)
        
    thermoShutOffTrigger()
    log.debug("current coolingsetpoint is ${state.currentCoolSetpoint1}")
    log.debug("current heatingsetpoint is ${state.currentHeatSetpoint1}")
    log.debug("current mode is ${state.currentMode1}")
    //thermostats.setCoolingSetpoint(currentCoolSetpoint)
}    
}

def thermoShutOffTrigger() {
    log.info("Starting timer to turn off thermostat")
    def delay = (turnOffDelay2 != null && turnOffDelay2 != "") ? turnOffDelay2 * 60 : 60 
    state.turnOffTime = now()
	log.debug ("Turn off delay is ${delay}")
    runIn(delay, "thermoShutOff")
  }

def thermoShutOff(){
	def coolSetpoint = state.currentCoolSetpoint1
    def heatSetpoint = state.currentHeatSetpoint1
	def mode = state.currentMode1
    def coolSetpoint1 = coolSetpoint.replaceAll("\\]", "").replaceAll("\\[", "")
    def heatSetpoint1 = heatSetpoint.replaceAll("\\]", "").replaceAll("\\[", "")
    def mode1 = mode.replaceAll("\\]", "").replaceAll("\\[", "")
    
	state.lastStatus = state.lastStatusDisabled
	log.info("Returning thermostat back to normal")
	thermostat1.setCoolingSetpoint("${coolSetpoint1}")
    thermostat1.setHeatingSetpoint("${heatSetpoint1}")
    thermostat1."${mode1}"()
}

def doorCheck(){
	if (!doorsOk){
		//log.debug("doors still open turning off ${thermostat}")
		def msg = "I changed your thermostat mode to off because some doors are open"
		thermostat?.off()

		if (state.lastStatus != "off"){
			sendMessage(msg)
		}
		state.lastStatus = "off"
	}

	else{
		temperatureHandler()
		//log.debug("doors are actually closed checking stuff again")
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

private hideOptionsSection() {
	(starting || ending || days || modes) ? false : true
}

private timeIntervalLabel() {
	(starting && ending) ? hhmm(starting) + "-" + hhmm(ending, "h:mm a z") : ""
}
