/**
 *  Thermostat Mode Director
 *
 *	Version 1.1
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
	page( name:"Sensor", title:"Use the following temperature sensor...", nextPage:"Cold", uninstall:true, install:false ) {
    	section("Sensor"){
			input "sensor", "capability.temperatureMeasurement", title: "Sensor", multiple:false
		}
    }   
    page( name:"Cold", title:"Cold", nextPage:"Hot", uninstall:true, install:false ) {
    	section("When the temperature falls below this tempurature set mode to..."){
			input "setLow", "decimal", title: "Low temp?"
            input "cold", "enum", title: "Mode?", metadata:[values:["auto", "heat", "cool", "off"]], required:false
		}
    }
    page( name:"Hot", title:"Hot", nextPage:"Neutral", uninstall:true, install:false ) {
    	section("When the temperature goes above this tempurature set mode to..."){
			input "setHigh", "decimal", title: "High temp?"
            input "hot", "enum", title: "Mode?", metadata:[values:["auto", "heat", "cool", "off"]], required:false
		}
    }    
    page( name:"Neutral", title:"Neutral", nextPage:"Thermostat", uninstall:true, install:false ) {
    	section("When temperature is between the previous temperatures, change mode to..."){
            input "neutral", "enum", title: "Mode?", metadata:[values:["auto", "heat", "cool", "off"]], required:false
		}    
    }
    page( name:"Thermostat", title:"Thermostat", nextPage:"Doors", uninstall:true, install:false ) {
    		section("Choose thermostat...") {
			input "thermostat", "capability.thermostat", multiple: true
		}
    }
    page( name:"Doors", title:"Doors", nextPage:"Settings", uninstall:true, install:false ) {
    		section("If these doors/windows are open turn off thermostat regardless of outdoor temperature") {
				input "doors", "capability.contactSensor", multiple: true, required:false
            }    
            section("Wait this long before turning the thermostat off (defaults to 1 minute)") {
    			input "turnOffDelay", "decimal", title: "Number of minutes", required: false
			}
    }
    page( name:"Settings", title:"Settings", uninstall:false, install:true ) {
    	section( "Notifications" ) {
			input "sendPushMessage", "enum", title: "Send a push notification?", metadata:[values:["Yes","No"]], required:false
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
	subscribe(app, temperatureHandler)
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
            //log.info "Setting thermostat mode to ${cold}"
        	def msg = "I changed your thermostat mode to ${cold}"
			thermostat."${cold}"()
        	if (state.lastStatus != "${cold}"){
        		if ( sendPushMessage != "No" ) {
        			sendMessage(msg)
        		}
        	}
        
    def lastStatus = state.lastStatus
    state.lastStatus = "${cold}" 
	}
    if (currentTemp > setHigh) {
    		//log.info "Setting thermostat mode to ${hot}"
        	def msg = "I changed your thermostat mode to ${hot}"
			thermostat."${hot}"()
        	if (state.lastStatus != "${hot}"){
        		if ( sendPushMessage != "No" ) {
        			sendMessage(msg)
        		}
       		}
               
	 def lastStatus = state.lastStatus
     state.lastStatus = "${hot}" 
	 }
     if (currentTemp > setLow && currentTemp < setHigh) {
    		//log.info "Setting thermostat mode to ${neutral}"
        	def msg = "I changed your thermostat mode to ${neutral}"
			thermostat."${neutral}"()
        	if (state.lastStatus != "${neutral}"){
        		if ( sendPushMessage != "No" ) {
        		sendMessage(msg)
        		}
        	}
            
        def lastStatus = state.lastStatus
        state.lastStatus = "${neutral}" 
		}
     }
     else{
     	def delay = (turnOffDelay != null && turnOffDelay != "") ? turnOffDelay * 60 : 60
     	log.debug("Detected open doors.  Checking door states again")
     	runIn(delay, "doorCheck")
     }
     }
     
}

def doorCheck(){
	if (!doorsOk){
		//log.debug("doors still open turning off ${thermostat}")
		thermostat.off()
	}

	else{
    	temperatureHandler()
		//log.debug("doors are actually closed checking stuff again")
	}
}


private sendMessage(msg){
if (state.lastStatus != "${hot}"){
	sendPush(msg)
}    

if (state.lastStatus != "${cold}"){
	sendPush(msg)
}

if (state.lastStatus != "${neutral}"){
	sendPush(msg)
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

