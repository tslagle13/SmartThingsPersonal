/**
 *  Virtual Temp Controller
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
    name: "Virtual Temp Controller",
    namespace: "tslagle13",
    author: "Tim Slagle/Barry Burke",
    category: "Green Living",
    description: "Turns on a switch when temp gets too high, back off when it reaches the target again.",
    iconUrl: "http://icons.iconarchive.com/icons/icons8/windows-8/512/Science-Temperature-icon.png",
    iconX2Url: "http://icons.iconarchive.com/icons/icons8/windows-8/512/Science-Temperature-icon.png"
)

preferences {
    section("Humidity") {
        input "tempSensor", "capability.temperatureMeasurement", title: "Which Sensor?"
        input "desiredTemperature", "number", title: "Desired Temperature?"
        input "tempSwitch", "capability.switch", title: "Which Switch?"
    }
}

def installed() {
    initialize()
}

def updated() {
    unsubscribe()
    initialize()
}

def initialize() {
    subscribe(tempSensor, "temperature", tempHandler)
    log.debug "Initialized... current humidity is ${humiditySensor.latestValue("humidity")}%, max humidity is ${desiredHumidity*1.05}, dehumidifier is ${dehumidifierSwitch.latestValue( "switch" )}"
    tempSwitch.poll()             // Update power display
}


def tempHandler(evt) {
    log.debug "temp: $evt.value, $evt"

    if (Double.parseDouble(evt.value.replace("%", "")) <= desiredTemperature) {
        if ( tempSwitch.latestValue( "switch" ) != "off" ) {
            log.debug "Turning ${tempSwitch} on"
            tempSwitch.off()
        }
    }
    else if (Double.parseDouble(evt.value.replace("%", "")) > desiredTemperature ) {
        if ( tempSwitch.latestValue( "switch" ) != "on" ) {
            log.debug "Turning ${tempSwitch} on"
            tempSwitch.on()
        }  
    }
    else {
        log.debug "Current temp is ${evt.value}"
    }
    tempSwitch.poll()                // every time the temp changes, poll the switch for power updates
}
