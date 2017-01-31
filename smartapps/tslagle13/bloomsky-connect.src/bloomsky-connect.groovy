/**
*  BloomSky (Connect)
*
*  Version History:  
*
*  1.0 - 01/25/2017 - Storm Integration Testing
*  0.2 - remove logging option. Just log all the time. It's all good.
*  0.1 - Initial Version
*
*  Copyright 2016 Tim Slagle
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
*  Instructions: 
*   - Copy this app into your IDE using the "From Code" Option in the IDE and publish it
*   - If you have a bloomsky device installed currently, remove it and remove the DTH code from your account. 
*		- If this is your first time installing a bloomsky device you can skip this step completely.
*   - Copy the new DTH code into your account and publish it (https://github.com/tslagle13/SmartThingsPersonal/blob/master/devicetypes/tslagle13/bloomsky.src/bloomsky.groovy)
*   - Install the "BloomSky (Connect)" app through the SmartThings Mobile App
**/
 
import groovy.json.*

definition(
    name: "BloomSky (Connect)",
    namespace: "tslagle13",
    author: "Tim Slagle",
    description: "Used to spin up device types for BloomSky weather stations. ",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    singleInstance: true)

preferences {
	page(name: "setupPage")
}

def getVersion() {
	return "1.0" 
}

def setupPage() {
    dynamicPage(name: "setupPage", install: true, uninstall: true) {
        section("BloomSky API Settings") {
            input "apiKey", "password", title: "API Key", Required: true
        }
        section("Options:", hideable: true, hidden: false) {
            input(name: "refreshTime", title: "Refresh Time (Minutes: 1 - 60)", type: "number", range: "01..60", defaultValue: 10, required: true)
            input(name: "detailDebug", type:"bool", title: "Enable Debug logs", defaultValue: false, submitOnChange: true)
            paragraph "Give a name to this SmartApp (Optional)"
            input
            label(title: "Assign a name", required: false)
        }
        section ("Version " + "${getVersion()}") { }
    }
}

def installed() {
    log.info "--- Installed with settings: ${settings}"
	initialize()
}

def updated() {
    log.info "--- Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}

def initialize() {
    log.info "---  BloomSky (Connect) - Version: ${getVersion()}"

	getBloomskyIds()

    // Schedule Refresh
    int refreshMin = settings.refreshTime ? settings.refreshTime : 10
    String refreshSchedule = "0 0/" + refreshMin.toString() + " * 1/1 * ? *"
    schedule(refreshSchedule, "refreshBloomsky")
}

// use API key to find device IDs 
def getBloomskyIds(evt) {
    if (settings.detailDebug) log.debug "Getting BloomSky Devices..."

	def pollParams = [
        uri: "https://api.bloomsky.com",
        path: "/api/skydata/",
        requestContentType: "application/json",
        headers: ["Authorization": apiKey]
		]
        try {
            httpGet(pollParams) { resp ->
                state.deviceCollection = resp.getData().collectAll{it.DeviceID} //get all device IDs on bloomsky account
            } 
        } catch (Exception e) {
        	log.debug "Error: $e"   
		}    
    createBloomskyDevices()
}

def createBloomskyDevices() {
    if (settings.detailDebug) log.debug "Creating BloomSky Devices..."
	if (state.deviceCollection) {
    	state.deviceCollection.each{
        	def childDevices = getChildDevices()
            //if child device doens't exist, create it
        	if (!(childDevices.name).toString(/*convert to string for testing*/).contains("${it}")) {
            	addChildDevice("tslagle13", "Bloomsky", it, null, [label:"Bloomsky " + it, name:"${it}"]) //create child device with name and label so name remains protected
                log.info "Created Child Device - Bloomsky ${it}"
            }
            //if child device does exist log that it does
            else if ((childDevices.name).toString(/*convert to string for testing*/).contains("${it}")) {
                log.info "Child device - Bloomsky ${it} already exists"
            }
        }
        removeOldDevices(getChildDevices().name - state.deviceCollection) //find child devices that exist in ST that do not exist in the bloomsky account. 
    }
    refreshBloomsky()
}

def removeOldDevices(devices) {
    if (settings.detailDebug) log.debug "Removing Old BloomSky Devices..."
    devices.each {
    	def device = getChildDevice("${it}") //find device ID with DNI of non bloomsky device
        deleteChildDevice(device.deviceNetworkId)
        	log.debug "Removed Child Device '${device.name}' because it no longer exists in your bloomsky account."   
    }
}

//provide API Key to child devices in more secure manner
private getAPIKey() {
	def key = apiKey as String 
    return key
}

//refresh for child devices
def refreshBloomsky(evt) {
    log.info "--- Refresh Devices"
	state.lastTime = now()
	def devices = getChildDevices()
    devices.each{
        if (settings.detailDebug) log.debug "Calling Refresh on BloomSky device: ${it.id}"
    	it.callAPI()
    }
}
