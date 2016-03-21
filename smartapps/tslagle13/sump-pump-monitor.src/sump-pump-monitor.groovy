/**
 *  Sump Pump Monitor
 *
 *  Current Version: 2.0
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
    name: "Sump Pump Monitor",
    namespace: "tslagle13",
    author: "Tim Slagle",
    description: "Checks to see whether your sump pump is running more than usual.  Connect a multi-sensor to the sump pump outlet pipe and it will alert you if it detects vibration more than twice in X amount of minutes.",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Which multisensor should I monitor?") {
		input "multi", "capability.accelerationSensor", title: "Which?", multiple: false, required: true
	}
    section("Danger Zone?") {
		input "frequency", "decimal", title: "What time frame should I monitor?", description: "Minutes", required: true
	}
    section("Send this message (default is ... Alert: Sump pump has ran in twice in the last X minutes.)"){
		input "messageText", "text", title: "Message Text", required: false
	}
    section("Via a push notification and/or an SMS message"){
		input "phone", "phone", title: "Phone Number (for SMS, optional)", required: false
		input "pushAndPhone", "enum", title: "Both Push and SMS?", required: false, options: ["Yes","No"]
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
	subscribe(multi, "acceleration.active", checkFrequency)
}

def checkFrequency(evt){
log.debug("running check sump")
def lastTime = state[frequencyKeyAccelration(evt)]

if (lastTime == null) {
	state[frequencyKeyAccelration(evt)] = now()
}

else if (now() - lastTime >= frequency * 60000) {
	state[frequencyKeyAccelration(evt)] = now()
}


else if (now() - lastTime <= frequency * 60000) {
log.debug("Last time valid")
def timePassed = now() - lastTime
def timePassedFix = timePassed / 60000
def timePassedRound = Math.round(timePassedFix.toDouble()) + (unit ?: "")
state[frequencyKeyAccelration(evt)] = now()
def msg = messageText ?: "Alert: Sump pump has ran in twice in the last ${timePassedRound} minutes."

	if (!phone || pushAndPhone != "No") {
		log.debug "sending push"
		sendPush(msg)
	}
	if (phone) {
		log.debug "sending SMS"
		sendSms(phone, msg)
	}
}
}


private frequencyKeyAccelration(evt) {
	"lastActionTimeStamp"
}
