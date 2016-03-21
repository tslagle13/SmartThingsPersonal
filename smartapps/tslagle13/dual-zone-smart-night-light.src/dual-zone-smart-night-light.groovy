/**
 *  Dual Zone Smart Night Light
 *
 *  Current Version: 1.0
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
    name: "Dual Zone Smart Night Light",
    namespace: "tslagle13",
    author: "Tim Slagle",
    description: "Turns on lights using motion and contact sensor. Both values must be closed/not active in order for lights to turn off.",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_motion-outlet-luminance.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_motion-outlet-luminance@2x.png"
)

preferences {
	section("Control these lights..."){
		input "lights", "capability.switch", multiple: true
	}
	section("Turning on when a contact opens and there's movement..."){
		input "motionSensor", "capability.motionSensor", title: "Where?", required:true, multiple:true
        input "contactSensor", "capability.contactSensor", title: "Which?", required:true, multiple:true
	}
	section("And then off when it's light or there's been no movement for..."){
		input "delayMinutes", "number", title: "Minutes?"
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
	subscribe(motionSensor, "motion", motionHandler)
    subscribe(contactSensor, "contact", contactHandler)
    //subscribe(app, turnOffMotionAfterDelay)
}
    
def motionHandler(evt) {
	log.debug "$evt.name: $evt.value"
	if (evt.value == "active") {
		
			log.debug "turning on lights due to motion"
			lights.on()
			state.lastStatus = "on"
		
		state.motionStopTime = null
	}
	else {																					// Motion has stoped
		state.motionStopTime = now()																																// The on button was NOT pushed so...
        	if(delayMinutes) {																	// If the user set a delay then...
				runIn(delayMinutes*60, turnOffMotionAfterDelay)				// Schedule the "lights off" for later.
			} else {																			// Otherwise...
				turnOffMotionAfterDelay()														// Run the lights off now.
			
       } 
	}
}

def contactHandler(evt) {
	log.debug "$evt.name: $evt.value"
	if (evt.value == "open") {
		
			log.debug "turning on lights due to motion"
			lights.on()
			state.lastStatus = "on"
		
		state.motionStopTime = null
	}
	else {																					// Motion has stoped
		state.motionStopTime = now()
																																// The on button was NOT pushed so...
        	if(delayMinutes) {																	// If the user set a delay then...
				runIn(delayMinutes*60, turnOffMotionAfterDelay)				// Schedule the "lights off" for later.
			} else {																			// Otherwise...
				turnOffMotionAfterDelay()														// Run the lights off now.
			}
        
	}
}

def turnOffMotionAfterDelay(evt) {
	log.debug "In turnOffMotionAfterDelay"
	if (allOk){
			lights.off()
	}	
	
    else{
    runIn(delayMinutes*60, turnOffMotionAfterDelay)
    log.debug("scheduling")
    }
}

private getAllOk() {
motionOk && doorsOk
}

private getMotionOk() {
	def result = !motionSensors.latestValue("motion").contains("active")
	log.trace "motionOk = $result"
	result
}

private getDoorsOk() {
	def result = !contactSensor.latestValue("contact").contains("open")
	log.trace "doorsOk = $result"
	result
}
