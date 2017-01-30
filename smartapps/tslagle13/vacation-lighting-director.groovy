/**
 *  Vacation Lighting Director
 *  Supports Longer interval times (up to 180 mins)
 *  Only turns off lights it turned on (vs calling to turn all off)
 * 
 *  Version  2.7 - Simplified interface.
 *  Version  2.6 - General spelling, wording, and formatting fixes.
 *                 Updated SmartApp icons.
 *  Version  2.5 - Moved scheduling over to Cron and added time as a trigger.
 *                 Cleaned up formatting and some typos.
 *                 Updated license.
 *                 Made people option optional
 *                 Added sttement to unschedule on mode change if people option is not selected
 *
 *  Version  2.4 - Added information paragraphs
 *
 *  Original source code can be found here: https://github.com/tslagle13/SmartThings/blob/master/smartapps/tslagle13/vacation-lighting-director.groovy
 *  
 *  Original Author: Tim Slagle (https://github.com/tslagle13/), Copyright 2016
 *
 *  Additional contributors:
 *      - imnotbob (https://github.com/imnotbob)
 *      - Steve Jenkins (https://github.com/stevejenkins)
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

import java.text.SimpleDateFormat

// Automatically generated. Make future change here.
definition(
    name: "Vacation Lighting Director",
    namespace: "tslagle13",
    author: "Tim Slagle",
    category: "Safety & Security",
    description: "Randomly turns lights on/off to simulate the appearance of an occupied home while you're away.",
    iconUrl: "http://icons.iconarchive.com/icons/paomedia/small-n-flat/256/calendar-clock-icon.png",
    iconX2Url: "http://icons.iconarchive.com/icons/paomedia/small-n-flat/512/calendar-clock-icon.png"
)

preferences {
    page name:"pageSetup"
    page name:"Setup"
    page name:"Options"

}

// Show setup page
def pageSetup() {

    def pageProperties = [
        name:       "pageSetup",
        title:      "About",
        nextPage:   null,
        install:    true,
        uninstall:  true
    ]

    return dynamicPage(pageProperties) {
        section(""){
        	paragraph image: "http://icons.iconarchive.com/icons/paomedia/small-n-flat/48/calendar-clock-icon.png",
			"Randomly turns lights on / off during specified times to make your home appear " +
                      "occupied while you're away."
        }
        section("Name (optional)") {
            label title:"Name this vacation setting", required:false
        }
        section("Setup & Options (required)") {
            href "Setup", title: "Setup", description: "", state:greyedOut()
            href "Options", title: "Options", description: "", state: greyedOutSettings() 
        }
    }
}

// Show "Setup" page
def Setup() {

    def newMode = [
        name:               "newMode",
        type:               "mode",
        title:              "Which Modes activate vacation lighting?",
        multiple:           true,
        required:           true
    ]
    def switches = [
        name:               "switches",
        type:               "capability.switch",
        title:              "Which lights do you want to include?",
        multiple:           true,
        required:           true
    ]

    def frequency_minutes = [
        name:               "frequency_minutes",
        type:               "number",
        title:              "Change lights after how many minutes? (5-180)",
        range:              "5..180",
        required:        true
    ]

    def number_of_active_lights = [
        name:               "number_of_active_lights",
        type:               "number",
        title:              "Turn on how many lights at one time?",
        required:        true,
    ]

    def pageName = "Setup"

    def pageProperties = [
        name:       "Setup",
        title:      "Setup",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {

        section(""){
            paragraph "Choose when to activate vacation lighting, which switches to include, how many " +
                      "lights to turn on at one time, and how often to change them."
        }
        section("Mode & time settings (required)") {
            input newMode
            href "timeIntervalInput", title: "Starting and ending when?", description: timeIntervalLabel(), refreshAfterSelection:true
         }
        section("Switch & interval settings (required)") {
        
        	input switches
        
        	input number_of_active_lights
            
            input frequency_minutes
        }
    }
}

// Show "Options" page
def Options() {

    def falseAlarmThreshold = [
        name:       "falseAlarmThreshold",
        type:       "decimal",
        title:      "Delay how many minutes? (default is 2)",
        required:        false
    ]
    def days = [
        name:       "days",
        type:       "enum",
        title:      "Run only on these days",
        multiple:   true,
        required:   true,
        options: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
    ]

    def pageName = "Options"

    def pageProperties = [
        name:       "Options",
        title:      "Options",
        nextPage:   "pageSetup"
    ]

    def people = [
        name:       "People",
        type:       "capability.presenceSensor",
        title:      "Don't activate vacation lighting if any of these people are home",
        required:        false,
        multiple:        true
    ]

    return dynamicPage(pageProperties) {

        section("Limit days of the week") {
            input days
        }
        section("Activation delay") {
            input falseAlarmThreshold
        }
        section("People who override") {
            input people
            paragraph "If you don't configure this setting, some lights could remain on when you arrive home."
            
        }
    }
}

page(name: "timeIntervalInput", title: "Starting & ending times", refreshAfterSelection:true) {
    section {
        input "startTimeType", "enum", title: "Starting at", options: [["time": "Specific time"], ["sunrise": "Sunrise"], ["sunset": "Sunset"]], defaultValue: "time", submitOnChange: true
        if (startTimeType in ["sunrise","sunset"]) {
                input "startTimeOffset", "number", title: "Offset in minutes (+/-)", range: "*..*", required: false
        }
        else {
                input "starting", "time", title: "Start time (if enabled)", required: false
        }
    }
    section {
        input "endTimeType", "enum", title: "Ending at", options: [["time": "Specific time"], ["sunrise": "Sunrise"], ["sunset": "Sunset"]], defaultValue: "time", submitOnChange: true
        if (endTimeType in ["sunrise","sunset"]) {
                input "endTimeOffset", "number", title: "Offset in minutes (+/-)", range: "*..*", required: false
        }
        else {
                input "ending", "time", title: "End time (if enabled)", required: false
        }
    }
}

def installed() {
    atomicState.Running = false
    atomicState.schedRunning = false
    atomicState.startendRunning = false
    initialize()
}

def updated() {
    unsubscribe();
    clearState(true)
    initialize()
}

def initialize(){
    if (newMode != null) {
        subscribe(location, modeChangeHandler)
    }
    schedStartEnd()
    if(people) {
        subscribe(people, "presence", modeChangeHandler)
    }
    log.debug "Installed with settings: ${settings}"
    setSched()
}

def clearState(turnOff = false) {
    if(turnOff && atomicState?.Running) {
       switches.off()
       atomicState.vacactive_switches = []
    }
    atomicState.Running = false
    atomicState.schedRunning = false
    atomicState.startendRunning = false
    atomicState.lastUpdDt = null
    unschedule()
}

def schedStartEnd() {
    if (starting != null || startTimeType != null) {
        def start = timeWindowStart()
        schedule(start, startTimeCheck)
        atomicState.startendRunning = true
    }
    if (ending != null || endTimeType != null) {
        def end = timeWindowStop()
        schedule(end, endTimeCheck)
        atomicState.startendRunning = true
    }
}

def setSched() {
    atomicState.schedRunning = true
    def maxMin = 60
    def timgcd = gcd([frequency_minutes, maxMin])
    atomicState.timegcd = timgcd
    def random = new Random()
    def random_int = random.nextInt(60)
    def random_dint = random.nextInt(timgcd.toInteger())

    def newDate = new Date()
    def curMin = newDate.format("m", location.timeZone)

    def timestr = "${random_dint}/${timgcd}"
    if(timgcd == 60) { timestr = "${curMin}" }

    log.trace "scheduled using Cron (${random_int} ${timestr} * 1/1 * ? *)"
    schedule("${random_int} ${timestr} * 1/1 * ? *", scheduleCheck)  // this runs every timgcd minutes
    def delay = (falseAlarmThreshold != null && falseAlarmThreshold != "") ? falseAlarmThreshold * 60 : 2 * 60
    runIn(delay, initCheck)
}

private gcd(a, b) {
    while (b > 0) {
        long temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}

private gcd(input = []) {
    long result = input[0];
    for(int i = 1; i < input.size; i++) result = gcd(result, input[i]);
    return result;
}

def modeChangeHandler(evt) {
    log.trace "modeChangeHandler ${evt}"
    setSched()
}

def initCheck() {
    scheduleCheck(null)
}

def startTimeCheck() {
    log.trace "startTimeCheck"
    setSched()
}

def endTimeCheck() {
    log.trace "endTimeCheck"
    scheduleCheck(null)
}

def getDtNow() {
    def now = new Date()
    return formatDt(now)
}

def formatDt(dt) {
    def tf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy")
    if(getTimeZone()) { tf.setTimeZone(getTimeZone()) }
    else {
            log.warn "SmartThings TimeZone is not found (or is not set). Please open your SmartThings location and press Save."
    }
    return tf.format(dt)
}

def GetTimeDiffSeconds(lastDate) {
    if(lastDate?.contains("dtNow")) { return 10000 }
    def now = new Date()
    def lastDt = Date.parse("E MMM dd HH:mm:ss z yyyy", lastDate)
    def start = Date.parse("E MMM dd HH:mm:ss z yyyy", formatDt(lastDt)).getTime()
    def stop = Date.parse("E MMM dd HH:mm:ss z yyyy", formatDt(now)).getTime()
    def diff = (int) (long) (stop - start) / 1000
    return diff
}

def getTimeZone() {
    def tz = null
    if (location?.timeZone) { tz = location?.timeZone }
    else { tz = TimeZone.getTimeZone(getNestTimeZone()) }
    if(!tz) { log.warn "getTimeZone: Hub or Nest TimeZone is not found ..." }
    return tz
}

def getLastUpdSec() { return !atomicState?.lastUpdDt ? 100000 : GetTimeDiffSeconds(atomicState?.lastUpdDt).toInteger() }

//Main logic to pick a random set of lights from the large set of lights to turn on and then turn the rest off

def scheduleCheck(evt) {
    if(allOk && getLastUpdSec() > ((frequency_minutes - 1) * 60) ) {
        atomicState?.lastUpdDt = getDtNow()
        log.debug("Running")
        atomicState.Running = true

        // turn off switches
        def inactive_switches = switches
        def vacactive_switches = []
        if (atomicState.Running) {
            if (atomicState?.vacactive_switches) {
                vacactive_switches = atomicState.vacactive_switches
                if (vacactive_switches?.size()) {
                    for (int i = 0; i < vacactive_switches.size() ; i++) {
                        inactive_switches[vacactive_switches[i]].off()
                        log.trace "turned off ${inactive_switches[vacactive_switches[i]]}"
                    }
                }
            }
            atomicState.vacactive_switches = []
        }

        def random = new Random()
        vacactive_switches = []
        def numlight = number_of_active_lights
        if (numlight > inactive_switches.size()) { numlight = inactive_switches.size() }
        log.trace "inactive switches: ${inactive_switches.size()} numlight: ${numlight}"
        for (int i = 0 ; i < numlight ; i++) {

            // grab a random switch to turn on
            def random_int = random.nextInt(inactive_switches.size())
            while (vacactive_switches?.contains(random_int)) {
                random_int = random.nextInt(inactive_switches.size())
            }
            vacactive_switches << random_int
        }
        for (int i = 0 ; i < vacactive_switches.size() ; i++) {
            inactive_switches[vacactive_switches[i]].on()
            log.trace "turned on ${inactive_switches[vacactive_switches[i]]}"
        }
        atomicState.vacactive_switches = vacactive_switches
        //log.trace "vacactive ${vacactive_switches} inactive ${inactive_switches}"

    } else if(people && someoneIsHome){
        //don't turn off lights if anyone is home
        if (atomicState?.schedRunning) {
            log.debug("Someone is home! Stopping vacation lighting.")
            clearState()
        }
    } else if (!modeOk || !daysOk) {
        if (atomicState?.Running || atomicState?.schedRunning) {
            log.debug("Wrong mode or day! Stopping vacation lighting.")
            clearState(true)
        }
    } else if (modeOk && daysOk && !timeOk) {
        if (atomicState?.Running || atomicState?.schedRunning) {
            log.debug("Wrong time! Stopping vacation lighting.")
            clearState(true)
        }
    }
    if (!atomicState.startendRunning) {
        schedStartEnd()
    }
    return true
}

//below is used to check restrictions
private getAllOk() {
    modeOk && daysOk && timeOk && homeIsEmpty
}


private getModeOk() {
    def result = !newMode || newMode.contains(location.mode)
    //log.trace "modeOk = $result"
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
    //log.trace "daysOk = $result"
    result
}

private getHomeIsEmpty() {
    def result = true

    if(people?.findAll { it?.currentPresence == "present" }) {
        result = false
    }

    //log.debug("homeIsEmpty: ${result}")

    return result
}

private getSomeoneIsHome() {
    def result = false

    if(people?.findAll { it?.currentPresence == "present" }) {
      result = true
    }
    //log.debug("someoneIsHome: ${result}")
    return result
}

private getTimeOk() {
    def result = true
    def start = timeWindowStart() - 30000
    def stop = timeWindowStop() - 30000
    if (start && stop && location.timeZone) {
        result = timeOfDayIsBetween(start, stop, new Date(), location.timeZone)
    }
    //log.debug "timeOk = $result"
    result
}

private timeWindowStart() {
    def result = null
    if (startTimeType == "sunrise") {
        result = location.currentState("sunriseTime")?.dateValue
        if (result && startTimeOffset) {
            result = new Date(result.time + Math.round(startTimeOffset * 60000))
        }
    }
    else if (startTimeType == "sunset") {
        result = location.currentState("sunsetTime")?.dateValue
        if (result && startTimeOffset) {
            result = new Date(result.time + Math.round(startTimeOffset * 60000))
        }
    }
    else if (starting && location.timeZone) {
        result = timeToday(starting, location.timeZone)
    }
    //log.debug "timeWindowStart = ${result}"
    result
}

private timeWindowStop() {
    def result = null
    if (endTimeType == "sunrise") {
        result = location.currentState("sunriseTime")?.dateValue
        if (result && endTimeOffset) {
            result = new Date(result.time + Math.round(endTimeOffset * 60000))
        }
    }
    else if (endTimeType == "sunset") {
        result = location.currentState("sunsetTime")?.dateValue
        if (result && endTimeOffset) {
            result = new Date(result.time + Math.round(endTimeOffset * 60000))
        }
    }
    else if (ending && location.timeZone) {
        result = timeToday(ending, location.timeZone)
    }
    //log.debug "timeWindowStop = ${result}"
    result
}

private hhmm(time, fmt = "h:mm a") {
    def t = timeToday(time, location.timeZone)
    def f = new java.text.SimpleDateFormat(fmt)
    f.setTimeZone(location.timeZone ?: timeZone(time))
    f.format(t)
}

private timeIntervalLabel() {
    def start = ""
    switch (startTimeType) {
        case "time":
            if (starting) {
                start += hhmm(starting)
            }
            break
        case "sunrise":
        case "sunset":
            start += startTimeType[0].toUpperCase() + startTimeType[1..-1]
            if (startTimeOffset) {
                start += startTimeOffset > 0 ? "+${startTimeOffset} min" : "${startTimeOffset} min"
            }
            break
    }

    def finish = ""
    switch (endTimeType) {
        case "time":
            if (ending) {
                finish += hhmm(ending)
            }
            break
        case "sunrise":
        case "sunset":
            finish += endTimeType[0].toUpperCase() + endTimeType[1..-1]
            if (endTimeOffset) {
                finish += endTimeOffset > 0 ? "+${endTimeOffset} min" : "${endTimeOffset} min"
            }
            break
        }
        start && finish ? "${start} to ${finish}" : ""
}

//sets complete/not complete for the setup section on the main dynamic page
def greyedOut(){
    def result = ""
    if (switches) {
            result = "complete"        
    }
    result
}

//sets complete/not complete for the settings section on the main dynamic page
def greyedOutSettings(){
    def result = ""
    if (people || days || falseAlarmThreshold ) {
            result = "complete"        
    }
    result
}
