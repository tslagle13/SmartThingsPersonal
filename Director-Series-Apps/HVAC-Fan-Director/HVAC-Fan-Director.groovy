/**
 *  HVAC Fan Director
 *
 *	Version 1.0
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
 *	Copyright 2014 SmartThings
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
    name: "HVAC Fan Director",
    namespace: "tslagle13",
    author: "Tim Slagle",
    description: "Control your HVAC fan based on a temperature difference between two temperature sensors.",
    category: "Green Living",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	preferences {
page( name:"Setup", title:"Setup", nextPage:"Settings", uninstall:false, install:tfalse ) {   
	section("Choose HVAC fan to control") {
		input "thermostat", "capability.thermostat", title: "Thermostat"
        input "honeywell", "enum", title: "Is this a Honeywell Wifi Thermostat?", metadata:[values:["Yes", "No"]], required:true
	}
	section("Choose the temp sensors you would like to monitor") {
		input "tempSensorOne", "capability.temperatureMeasurement", title: "Temp sensor 1"
        input "tempSensorTwo", "capability.temperatureMeasurement", title: "Temp sensor 2"
	}
	section("How many degrees difference will trigger the HVAC fan?"){
		input "tempDif", "number", title: "Degrees", required: true
	}
    section("Whats the maximum time the HVAC fan should run? (default: 10 minutes)"){
		input "runTime", "number", title: "Minutes", required: false
	}
    
}    
    page( name:"Settings", title:"Settings", uninstall:false, install:true ) {
    section("Don't mess with the fan if and doors/windows are open though..."){
		input "doors", "capability.contactSensor", title: "Which?", required: false, multiple: true
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
}


def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	state.thermostatFan = null
	subscribe(tempSensorOne, "temperature", tempHandler)
    subscribe(tempSensorTwo, "temperature", tempHandler)
    subscribe(app, tempHandler)
}

def tempHandler(evt){
def tempOne = tempSensorOne.latestValue("temperature") as Integer
def tempTwo = tempSensorTwo.latestValue("temperature") as Integer
def delay = (runTime != null && runTime != "") ? runTime * 60 : 10 * 60
if (allOk){

	if (tempOne - tempTwo >= tempDif && state.thermostatFan != "On"){
    	if (honeywell == "Yes"){
        	thermostat.fanOn()
            runIn(delay, "turnFanOff")
            state.thermostatFan = "On"
        }
        
        else {
			thermostat.setThermostatFanMode("on")
            runIn(delay, "turnFanOff")
            state.thermostatFan = "On"
        }
	}

		if (tempTwo - tempOne >= tempDif && state.thermostatFan != "On"){
    		if (honeywell == "Yes"){
        		thermostat.fanOn()
                runIn(delay, "turnFanOff")
                state.thermostatFan = "On"
        }
        
        else {
			thermostat.setThermostatFanMode("on")
            runIn(delay, "turnFanOff")
            state.thermostatFan = "On"
        }
	}

}
}

def turnFanOff(){
if (honeywell == "Yes"){
        		thermostat.fanAuto()
                state.thermostatFan = "Off"
        }
        
        else {
        	state.thermostatFan = "Off"
			thermostat.setThermostatFanMode("auto")
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





