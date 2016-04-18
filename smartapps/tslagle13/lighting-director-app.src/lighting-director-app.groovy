/**
 *  Lighting Director
 *
 *  Copyright 2015 Tim Slagle & Michael Struck
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
    name: "Lighting Director App",
    namespace: "tslagle13",
    author: "Tim Slagle & Michael Struck",
    description: "Control up to 4 sets (scenarios) of lights based on motion, door contacts and lux levels.",
    category: "Convenience",
    iconUrl: "https://raw.githubusercontent.com/MichaelStruck/SmartThings/master/Other-SmartApps/Lighting-Director/LightingDirector.png",
    iconX2Url: "https://raw.githubusercontent.com/MichaelStruck/SmartThings/master/Other-SmartApps/Lighting-Director/LightingDirector@2x.png",
    iconX3Url: "https://raw.githubusercontent.com/MichaelStruck/SmartThings/master/Other-SmartApps/Lighting-Director/LightingDirector@2x.png")

preferences {
    page name:"pageSetupScenarioA"
}

// Show "pageSetupScenarioA" page
def pageSetupScenarioA() {

    def inputLightsA = [
        name:       "A_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersA = [
        name:       "A_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionA = [
        name:       "A_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
	def inputAccelerationA = [
		name:       "A_acceleration",
		type:       "capability.accelerationSensor",
		title:      "Or using these acceleration sensors...",
		multiple:   true,
		required:   false
	]
    def inputContactA = [
        name:       "A_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputTriggerOnceA = [
    	name:       "A_triggerOnce",
        type:       "bool",
        title:      "Trigger only once per day...",
        defaultValue:false
    ]
    
    def inputSwitchDisableA = [
    	name:       "A_switchDisable",
        type:       "bool",
        title:      "Stop triggering if physical switches/dimmers are turned off...",
        defaultValue:false
    ]
    
    def inputLockA = [
        name:       "A_lock",
        type:       "capability.lock",
        title:      "Or using these locks...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeA = [
        name:       "A_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputDayA = [
        name:       "A_day",
        type:       "enum",
        options: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"],
        title:      "Only on certain days of the week...",
        multiple:   true,
        required:   false
    ]
    
    
    def inputLevelA = [
        name:       "A_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOnLuxA = [
        name:       "A_turnOnLux",
        type:       "number",
        title:      "Only run this scenario if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsA = [
        name:       "A_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffA = [
        name:       "A_turnOff",
        type:       "number",
        title:      "Turn off this scenario after motion stops or doors close/lock (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def pageProperties = [
        name:       "pageSetupScenarioA",
        install:	true,
        uninstall:	true,
        submitOnChange: true
    ]

    return dynamicPage(pageProperties) {
section([title:"Options", mobileOnly:true]) {
            label title:"Name your lighting director", required:false
}

section("Devices included in the scenario") {
            input inputMotionA
			input inputAccelerationA
            input inputContactA
            input inputLockA
            input inputLightsA
            input inputDimmersA
            }

section("Scenario settings") {
            input inputLevelA
            input inputTurnOnLuxA
            input inputLuxSensorsA
            input inputTurnOffA
            }
            
section("Scenario restrictions") {            
            input inputTriggerOnceA
            input inputSwitchDisableA
            href "timeIntervalInputA", title: "Only during a certain time...", description: getTimeLabel(A_timeStart, A_timeEnd), state: greyedOutTime(A_timeStart, A_timeEnd), refreshAfterSelection:true
            input inputDayA
            input inputModeA
            }

section("Help") {
            paragraph helpText()
            }
    }
    
}

def installed() {
    initialize()
}

def updated() {
    unschedule()
    unsubscribe()
    initialize()
}

def initialize() {
app.updateLabel(inputScenarioNameA)

midNightReset()

if(A_motion) {
	subscribe(settings.A_motion, "motion", onEventA)
}

if(A_acceleration) {
	subscribe(settings.A_acceleration, "acceleration", onEventA)
}

if(A_contact) {
	subscribe(settings.A_contact, "contact", onEventA)
}

if(A_lock) {
	subscribe(settings.A_lock, "lock", onEventA)
}

if(A_switchDisable) {
	subscribe(A_switches, "switch.off", onPressA)
    subscribe(A_dimmers, "switch.off", onPressA)
}

if(A_mode) {
    subscribe(location, onEventA)
}
}

def onEventA(evt) {

if ((!A_triggerOnce || (A_triggerOnce && !state.A_triggered)) && (!A_switchDisable || (A_switchDisable && !state.A_triggered))) {
if ((!A_mode || A_mode.contains(location.mode)) && getTimeOk (A_timeStart, A_timeEnd) && getDayOk(A_day)) {
if ((!A_luxSensors) || (A_luxSensors.latestValue("illuminance") <= A_turnOnLux)){
def A_levelOn = A_level as Integer

if (getInputOk(A_motion, A_contact, A_lock, A_acceleration)) {
        	log.debug("Motion, Door Open or Unlock Detected Running '${ScenarioNameA}'")
            settings.A_dimmers?.setLevel(A_levelOn)
            settings.A_switches?.on()
            if (A_triggerOnce){
            	state.A_triggered = true
                if (!A_turnOff) {
					runOnce (getMidnight(), midNightReset)
                }
            }
        	if (state.A_timerStart){
            	unschedule(delayTurnOffA)
            	state.A_timerStart = false
        	}
}
else {
    	if (settings.A_turnOff) {
		runIn(A_turnOff * 60, "delayTurnOffA")
        state.A_timerStart = true
        }
        else {
        settings.A_switches?.off()
		settings.A_dimmers?.setLevel(0)
        	if (state.A_triggered) {
    			runOnce (getMidnight(), midNightReset)
    		}
        }
}
}
}
else{
log.debug("Motion, Contact or Unlock detected outside of mode or time/day restriction.  Not running scenario.")
}
}
}

def delayTurnOffA(){
	settings.A_switches?.off()
	settings.A_dimmers?.setLevel(0)
	state.A_timerStart = false
	if (state.A_triggered) {
    	runOnce (getMidnight(), midNightReset)
    }

}

def onPressA(evt) {
if ((!A_mode || A_mode.contains(location.mode)) && getTimeOk (A_timeStart, A_timeEnd) && getDayOk(A_day)) {
if ((!A_luxSensors) || (A_luxSensors.latestValue("illuminance") <= A_turnOnLux)){
if ((!A_triggerOnce || (A_triggerOnce && !state.A_triggered)) && (!A_switchDisable || (A_switchDisable && !state.A_triggered))) {    
    if (evt.physical){
    	state.A_triggered = true
        unschedule(delayTurnOffA)
        runOnce (getMidnight(), midNightReset)
        log.debug "Physical switch in '${ScenarioNameA}' pressed. Triggers for this scenario disabled."
	}
}
}}}



//Common Methods

def midNightReset() {
	state.A_triggered = false
    state.B_triggered = false
    state.C_triggered = false
    state.D_triggered = false
}

private def helpText() {
	def text =
		"Select motion sensors, acceleration sensors, contact sensors or locks to control a set of lights. " +
        "Each scenario can control dimmers and switches but can also be " +
        "restricted to modes or between certain times and turned off after " +
        "motion stops, doors close or lock. Scenarios can also be limited to  " +
        "running once or to stop running if the physical switches are turned off."
	text
}

def greyOut(scenario){
	def result = ""
    if (scenario) {
    	result = "complete"	
    }
    result
}

def greyedOutTime(start, end){
	def result = ""
    if (start || end) {
    	result = "complete"	
    }
    result
}

def getTitle(scenario) {
	def title = "Empty"
	if (scenario) {
		title = scenario
    }
	title
}

def getDesc(scenario) {
	def desc = "Tap to create a scenario"
	if (scenario) {
		desc = "Tap to edit scenario"
    }
	desc	
}

def getMidnight() {
	def midnightToday = timeToday("2000-01-01T23:59:59.999-0000", location.timeZone)
	midnightToday
}

private getInputOk(motion, contact, lock, acceleration) {

def motionDetected = false
def accelerationDetected = false
def contactDetected = false
def unlockDetected = false
def result = false

if (motion) {
	if (motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (acceleration) {
	if (acceleration.latestValue("acceleration").contains("active")) {
		accelerationDetected = true
	}
}

if (contact) {
	if (contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (lock) {
	if (lock.latestValue("lock").contains("unlocked")) {
		unlockDetected = true
	}
}

result = motionDetected || contactDetected || unlockDetected || accelerationDetected
result

}

private getTimeOk(starting, ending) {
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

def getTimeLabel(start, end){
	def timeLabel = "Tap to set"
	
    if(start && end){
    	timeLabel = "Between" + " " + hhmm(start) + " "  + "and" + " " +  hhmm(end)
    }
    else if (start) {
		timeLabel = "Start at" + " " + hhmm(start)
    }
    else if(end){
    timeLabel = "End at" + hhmm(end)
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

private getDayOk(dayList) {
	def result = true
    if (dayList) {
		def df = new java.text.SimpleDateFormat("EEEE")
		if (location.timeZone) {
			df.setTimeZone(location.timeZone)
		}
		else {
			df.setTimeZone(TimeZone.getTimeZone("America/New_York"))
		}
		def day = df.format(new Date())
		result = dayList.contains(day)
	}
    result
}


page(name: "timeIntervalInputA", title: "Only during a certain time", refreshAfterSelection:true) {
		section {
			input "A_timeStart", "time", title: "Starting", required: false, refreshAfterSelection:true
			input "A_timeEnd", "time", title: "Ending", required: false, refreshAfterSelection:true
		}
        }  
page(name: "timeIntervalInputB", title: "Only during a certain time", refreshAfterSelection:true) {
		section {
			input "B_timeStart", "time", title: "Starting", required: false, refreshAfterSelection:true
			input "B_timeEnd", "time", title: "Ending", required: false, refreshAfterSelection:true
		}
        }  
page(name: "timeIntervalInputC", title: "Only during a certain time", refreshAfterSelection:true) {
		section {
			input "C_timeStart", "time", title: "Starting", required: false, refreshAfterSelection:true
			input "C_timeEnd", "time", title: "Ending", required: false, refreshAfterSelection:true
		}
        }         
page(name: "timeIntervalInputD", title: "Only during a certain time", refreshAfterSelection:true) {
		section {
			input "D_timeStart", "time", title: "Starting", required: false, refreshAfterSelection:true
			input "D_timeEnd", "time", title: "Ending", required: false, refreshAfterSelection:true
		}
        }