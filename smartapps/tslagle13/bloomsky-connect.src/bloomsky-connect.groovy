/**
 *  BloomSky (Connect)
 *
 *	Version - 0.2 
 *		- remove logging option. Just log all the time. It's all good.
 *  
 *  Version - 0.1
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
 */
 
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
	section("Title") {
    	input "apiKey", "password", title: "API Key", Required: true
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
	getBloomskyIds()
    schedule("0 0/10 * 1/1 * ? *", "refreshBloomsky")
}

// use API key to find device IDs 
def getBloomskyIds(evt) {
	def pollParams = [
        uri: "http://thirdpartyapi.appspot.com",
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
	if (state.deviceCollection) {
    	state.deviceCollection.each{
        	def childDevices = getChildDevices()
            //if child device doens't exist, create it
        	if (!(childDevices.name).toString(/*convert to string for testing*/).contains("${it}")) {
            	addChildDevice("tslagle13", "Bloomsky", it, null, [label:"Bloomsky" + it, name:"${it}"]) //create child device with name and label so name remains protected
                    log.debug "Created Child Device Bloomsky${it}"
            }
            //if child device does exist log that it does
            else if ((childDevices.name).toString(/*convert to string for testing*/).contains("${it}")) {
            		log.debug "Child device Bloomsky${it} already exists"
            }
        }
        removeOldDevices(getChildDevices().name - state.deviceCollection) //find child devices that exist in ST that do not exist in the bloomsky account. 
    }
    refreshBloomsky()
}

def removeOldDevices(devices) {
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
	state.lastTime = now()
	def devices = getChildDevices()
    devices.each{
    	it.callAPI()
    }
    log.debug "Refreshing bloomsky devices"
}



