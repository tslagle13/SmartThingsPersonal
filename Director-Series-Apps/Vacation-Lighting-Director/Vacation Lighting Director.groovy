/**
 *  Vacation Lighting Director
 *
 *  Version 1.0
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
    name: "Vacation Lighting Director",
    namespace: "tslagle13",
    author: "Tim Slagle",
    description: "Randomly turn on/off lights to simulate the apperance of a occupied home while you are away.",
    iconUrl: "http://icons.iconarchive.com/icons/custom-icon-design/mono-general-2/512/settings-icon.png",
    iconX2Url: "http://icons.iconarchive.com/icons/custom-icon-design/mono-general-2/512/settings-icon.png"
)

preferences {
 page(name: "Setup", title: "", nextPage:"Settings") { 
 section("Which mode change triggers the simulator? (This app will only run in selected mode(s))") {
		input "newMode", "mode", title: "Which?", multiple: true, required: false, refreshAfterSelection:true
	}
  section("Light switches to turn on/off"){
    input "switches", "capability.switch", title: "Switches", multiple: true, required: true, refreshAfterSelection:true
  }
  section("How often to cycle the lights"){
    input "frequency_minutes", "number", title: "Minutes?"
  }
  section("Number of active lights at any given time"){
    input "number_of_active_lights", "number", title: "Number of active lights"
  }
  section("People") {
			input "people", "capability.presenceSensor", title: "If these people are home do not change light status", required: false, multiple: true,  refreshAfterSelection:true
		}
  }
 page(name: "Settings", title: "", install:true, uninstall:true, refreshAfterSelection:true) { 
 section("Delay to start simulator... (defaults to 2 min)") {
    	input "falseAlarmThreshold", "decimal", title: "Number of minutes", required: false
  	}

   section([mobileOnly:true]) {
			label title: "Assign a name", required: false
		} 
        section("More options", refreshAfterSelection:true) {
			href "timeIntervalInput", title: "Only during a certain time", description: timeLabel ?: "Tap to set", state: timeLabel ? "complete" : "incomplete", refreshAfterSelection:true
			input "days", "enum", title: "Only on certain days of the week", multiple: true, required: false,
				options: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
		}
  }
page(name: "timeIntervalInput", title: "Only during a certain time", refreshAfterSelection:true) {
		section {
			input "starting", "time", title: "Starting (both are required)", required: false, refreshAfterSelection:true
			input "ending", "time", title: "Ending (both are required)", required: false, refreshAfterSelection:true
		}
        }  

}

def installed() {
initialize()
}

def updated() {
  unsubscribe();
  unschedule();
  initialize()
}

def initialize(){

	if (newMode != null) {
		subscribe(location, modeChangeHandler)
    }
}

def modeChangeHandler(evt) {
	log.debug "Mode change to: ${evt.value}"
    // Have to handle when they select one mode or multiple
    if (newMode.any{ it == evt.value } || newMode == evt.value) {
		def delay = (falseAlarmThreshold != null && falseAlarmThreshold != "") ? falseAlarmThreshold * 60 : 2 * 60 
    	runIn(delay, scheduleCheck)
    }
    else if(people){
    //don't turn off lights if anyone is home
		if(anyoneIsHome()){
		log.debug("Stopping Check for Light")
    	}
        else{
    log.debug("Stopping Check for Light and turning off all lights")
	switches.off()
    }
}    
}


// We want to turn off all the lights
// Then we want to take a random set of lights and turn those on
// Then run it again when the frequency demands it
def scheduleCheck(evt) {
if(allOk){
log.debug("Running")
  // turn off all the switches
  switches.off()

  // grab a random switch
  def random = new Random()
  def inactive_switches = switches
  for (int i = 0 ; i < number_of_active_lights ; i++) {
    // if there are no inactive switches to turn on then let's break
    if (inactive_switches.size() == 0){
      break
    }

    // grab a random switch and turn it on
    def random_int = random.nextInt(inactive_switches.size())
    inactive_switches[random_int].on()

    // then remove that switch from the pool off switches that can be turned on
    inactive_switches.remove(random_int)
  }

  // re-run again when the frequency demands it
  runIn(frequency_minutes * 60, scheduleCheck)
}
//Check to see if mode is ok but not time/day.  If mode is still ok, check again after frequency period.
else if (modeOk) {
	log.debug("mode OK.  Running again")
	runIn(frequency_minutes * 60, scheduleCheck)
    switches.off()
}
//if none is ok turn off frequency check and turn off lights.
else if(people){
    //don't turn off lights if anyone is home
		if(anyoneIsHome()){
		log.debug("Stopping Check for Light")
    	}
        else{
    log.debug("Stopping Check for Light and turning off all lights")
	switches.off()
    }
}
}    


//below is used to check restrictions
private getAllOk() {
	modeOk && daysOk && timeOk
}


private getModeOk() {
	def result = !newMode || newMode.contains(location.mode)
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

private anyoneIsHome() {
  def result = false

  if(people.findAll { it?.currentPresence == "present" }) {
    result = true
  }

  log.debug("anyoneIsHome: ${result}")

  return result
}
