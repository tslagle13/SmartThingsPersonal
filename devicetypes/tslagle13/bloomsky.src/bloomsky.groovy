/**
 *  Bloomsky
 *
 *  This DTH requires the BloomSky (Connect) app (https://github.com/tslagle13/SmartThingsPersonal/blob/master/smartapps/tslagle13/bloomsky-connect.src/bloomsky-connect.groovy)
 *
 *  Version: 1.0.6 - fixed code halting issue with temp conversion introduced by 1.0.5 and fixed last updated display issue
 *  
 *  Version: 1.0.5 - fixed celcius support, i had removed by accident - @thrash99er
 *
 *  Version: 1.0.4 - reincluded lx after bloomsky api change
 *
 *	Version: 1.0.3 - Removed Lux value. Dynamic Lux value will no longer be supported in this device type.
 * 	
 *  Version: 1.0.2 - Added new tile to display last time the data was retrieved - lastUpdated  @thrash99er
 * 
 *	Version: 1.0.1 - Fixed issue where DTH would not update at night. 
 *
 *	Version: 1.0 - Feature!: This device now has a device manager.
 *                      - Uninstall the current bloomsky device from ST. Copy this code overtop the current DTH
 *						  and install the connect app. The Connect app will create a new device for you.
 *				 - Logging level taken from parent app
 *				 - API Key transmitted from parent app in secure manner
 *				 - Only updates DTH when current value changes. Keeps history clean and removes needless send events 
 *
 *	Version: 0.5 - Celcius support thanks to @terafin
 *				 - Specific device refresh thanks to @thrash99er
 *               
 * 
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
 */
metadata {
	definition (name: "Bloomsky", namespace: "tslagle13", author: "Tim Slagle") {
		capability "Battery"
		capability "Illuminance Measurement"
		capability "Refresh"
		capability "Relative Humidity Measurement"
		capability "Sensor"
		capability "Temperature Measurement"
		capability "Ultraviolet Index"
        
        attribute "day", "string"
        attribute "pressure", "number"
        attribute "rain", "string"
        attribute "lastUpdated", "string"
        
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles(scale:2) {
		carouselTile("cameraDetails", "device.image", width: 4, height: 4) { }
        standardTile("refresh", "device.weather", decoration: "flat", width: 2, height: 2) {
			state "default", label: "", action: "refresh", icon:"st.secondary.refresh"
		}
        valueTile("light", "device.illuminance", decoration: "flat", width: 2, height: 2) {
			state "default", label:'${currentValue} Lux'
		}
        valueTile("rain", "device.rain", decoration: "flat", width: 2, height: 2) {
			state "default", label:'${currentValue}'
		}
        valueTile("uv", "device.ultravioletIndex", width: 2, height: 2) {
			state "default", label:'${currentValue} UV',
				backgroundColors:[
					[value: 1, color: "#289500"],
					[value: 3, color: "#F7e400"],
					[value: 6, color: "#F85900"],
					[value: 8, color: "#d80010"],
					[value: 11, color: "#6B49C8"]
				]
		}
        valueTile("humidity", "device.humidity", inactiveLabel: false, width: 2, height: 2) {
			state "humidity", label:'${currentValue}% humidity', unit:""
		}
        valueTile("pressure", "device.pressure", inactiveLabel: false, width: 2, height: 2) {
			state "pressure", label:'${currentValue} inHg', unit:""
		}
        valueTile("battery", "device.battery", decoration: "flat", width: 2, height: 2) {
			state "default", label:'${currentValue} mV Battery'
		}
        valueTile("night", "device.day", decoration: "flat", width: 2, height: 2) {
			state "default", label:'${currentValue}'
		}
         valueTile("lastUpdated", "device.lastUpdated", decoration: "flat", width: 2, height: 2) {
			state "default", label:'${currentValue}'
		}
		valueTile("temperature", "device.temperature", width: 2, height: 2) {
			state "default", label:'${currentValue}°',
				backgroundColors:[
					[value: 31, color: "#153591"],
					[value: 44, color: "#1e9cbb"],
					[value: 59, color: "#90d2a7"],
					[value: 74, color: "#44b621"],
					[value: 84, color: "#f1d801"],
					[value: 95, color: "#d04e00"],
					[value: 96, color: "#bc2323"]
				]
		}
        main(["temperature"])
		details(["cameraDetails", "temperature", "uv", "humidity", "pressure","light",  "rain", "night", "battery", "lastUpdated", "refresh"])}	
}    

def poll() {
	callAPI()
}

def refresh() {
	callAPI()
}

//convert temp to celcius if needed
def getTemperature(value) {
    def cmdScale = getTemperatureScale()
    return convertTemperatureIfNeeded(value.toFloat(), "F", 4)
}

def callAPI() {
	log.debug "Refreshing Bloomsky Device - ${device.label}"
	def logging = parent.loggingOn()
    def pollParams = [
        uri: "http://thirdpartyapi.appspot.com",
        path: "/api/skydata/",
        requestContentType: "application/json",
        headers: ["Authorization": parent.getAPIKey()]
		]
        try {
            httpGet(pollParams) { resp ->
                // Get the Device Info of the Correct Bloom Sky
                def individualBloomSky
                // If you don't have Device ID specific get the first bloom sky only
                if(device.deviceNetworkId) {
                    individualBloomSky = resp.getData().findAll{ it.DeviceID == device.deviceNetworkId }
                }
                else {
                    individualBloomSky = resp.data[0]
                }
                
              
                // def timeString = new Date(ts + location.timeZone.rawOffset ).format("yyyy-MM-dd HH:mm")
                // log.debug "TSS " + timeString
				
                if (logging) {
                    log.debug ("This BloomSky: " + individualBloomSky)
                }
               
				//if statements crawl through json to send events for each data point
                if (individualBloomSky.Data.Temperature) {
                    def T =  individualBloomSky.Data.Temperature.toString()
                    def temp = ((T.replaceAll("\\[", "").replaceAll("\\]","")).take(5))
                    temp = (getTemperature(temp)).take(5)
                    if (temp != state.currentTemp) {
                        sendEvent(name: "temperature", value: temp, unit: "F")
                        state.currentTemp = temp  
                    }
                    if (logging) {
                        log.debug "Temp:" + temp
                    }
                }
                if (individualBloomSky.Data.Humidity) {
                    def H =  individualBloomSky.Data.Humidity.toString()
                    def humidity = H.replaceAll("\\[", "").replaceAll("\\]","")
                    if (humidity != state.currentHumidity) {
                        sendEvent(name: "humidity", value: humidity, unit: "%")
                        state.currentHumidity = humidity
                    } 
                    if (logging) {
                            log.debug "Humidity:" + humidity
                    }
                }
                if (individualBloomSky.Data.Luminance) {
                    def L =  individualBloomSky.Data.Luminance.toString()
                    def luminance = L.replaceAll("\\[", "").replaceAll("\\]","")
                    if (luminance != state.currentLuminance) {
                        sendEvent(name: "illuminance", value: luminance)
                        state.currentLuminance = luminance
                    }
                    if (logging) {
                            log.debug "Luminance:" + luminance
                    }
                }
                if (individualBloomSky.Data.Pressure) {
                    def P =  individualBloomSky.Data.Pressure.toString()
                    def pressure = (P.replaceAll("\\[", "").replaceAll("\\]","").take(4))
                    if (pressure != state.currentPressure) {
                        sendEvent(name: "pressure", value: pressure, unit: "inHg")
                        state.currentPressure = pressure
                    }
                    if (logging) {
                            log.debug "Pressure:" + pressure
                    }
                }
                if (individualBloomSky.Data.UVIndex) {
                    def U =  individualBloomSky.Data.UVIndex.toString()
                    def uvIndex = U.replaceAll("\\[", "").replaceAll("\\]","")
                    if (uvIndex != state.currentIlluminance) {
                        sendEvent(name: "ultravioletIndex", value: uvIndex)
                        state.currentIlluminance = uvIndex
                    }
                    if (logging) {
                            log.debug "uvIndex:" + uvIndex
                    }
                }
                if (individualBloomSky.Data.Voltage) {
                    def V =  individualBloomSky.Data.Voltage.toString()
                    def voltage = V.replaceAll("\\[", "").replaceAll("\\]","")
                    if (voltage != state.currentBattery) {
                        sendEvent(name: "battery", value: voltage)
                        state.currentBattery = voltage
                    }
                    if (logging) {
                            log.debug "voltage:" + voltage
                    }
                }
                if (individualBloomSky.Data.ImageURL) {
                    def I =  individualBloomSky.Data.ImageURL.toString()
                    def image = I.replaceAll("\\[", "").replaceAll("\\]","").toString()
                        httpGet(image) { it -> 
                            storeImage(getPictureName(), it.data)
                    }
                    if (logging) {
                            log.debug "image:" + image
                    }
                }
                
                def newTS =  individualBloomSky.Data.TS.toString()
                def lastUpdated = Long.parseLong(((newTS.replaceAll("\\[", "").replaceAll("\\]",""))))
                def lastUpdateTick = (lastUpdated * 1000L) + location.timeZone.rawOffset
                def finalUpdated = new java.text.SimpleDateFormat("MMM dd HH:mm").format(lastUpdateTick)
               
                sendEvent(name: "lastUpdated", value: finalUpdated)
                state.lastUpdated = finalUpdated
                
                
                //for some reason the rain and night booleans evaluate as false no matter what, took out if statement for the time being
                def R =  individualBloomSky.Data.Rain.toString()
                def rain = R.replaceAll("\\[", "").replaceAll("\\]","")
                if (rain == "false"){
                	if (state.rain != "false") {
                    	sendEvent(name: "rain", value: "Not Raining")
                        state.rain = "false"
                    }
                    if (logging) {
                			log.debug "Rain:" + rain
                	} 
                }
                else {
                	if (state.rain != "true") {
                    	sendEvent(name: "rain", value: "Raining")
                    	state.rain = "true"
                	}
                    if (logging) {
                			log.debug "Rain:" + rain
               		} 
                }    
                def N =  individualBloomSky.Data.Night.toString()
                def night = N.replaceAll("\\[", "").replaceAll("\\]","")
                if (night == "false") {
                	if (state.night != "false") {
                    	sendEvent(name: "day", value: "It's day time")
                    	state.night = "false"
                    }
                    if (logging) {
              			log.debug "Night:" + night
              		}
                }
                else {
                	if (state.night != "true") {
                    	sendEvent(name: "day", value: "It's night time")
                        state.night = "true"
                        if (logging) {
                			log.debug "Night:" + night
                		} 
                    }
                    if (logging) {
              			log.debug "Night:" + night
              		}
                }
        		 
        }
        }catch (Exception e) { //log exception gracefully
			log.debug "Error: $e"
		}   
        
        

}

//create unique name for picture storage
private getPictureName() {
  def pictureUuid = java.util.UUID.randomUUID().toString().replaceAll('-', '')
  "image" + "_$pictureUuid" + ".jpg"
}