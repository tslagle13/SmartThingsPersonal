/**
 *  Bloomsky
 *
 *  This DTH requires the BloomSky (Connect) app (https://github.com/tslagle13/SmartThingsPersonal/blob/master/smartapps/tslagle13/bloomsky-connect.src/bloomsky-connect.groovy)
 *
 *  Version: 2.0.2 - Fixed DST issue and display time in AM/PM
 *
 *	Version: 2.0.1 - Fixed issue with pollster
 * 
 *  Version: 2.0 - Perfomance improvements. The DTH now updates almsot twice as fast!
 *				 - Updated the "Rain" tile to a "Water Sensor" tile. You can use bloomsky in any water detection app now!
 *				 - Removed the "It's Night Time" tile. It was sort of meaningless. 
 * 
 *  Version: 1.0.7 - Fixed trailing decimcals for android.
 *
 *	Version: 1.0.6 - fixed code halting issue with temp conversion introduced by 1.0.5 and fixed last updated display issue
 *  
 *  Version: 1.0.5 - fixed celcius support, i had removed by accident - @thrash99er
 *
 *  Version: 1.0.4 - reincluded lx after bloomsky api change
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
        capability "Polling"
		capability "Relative Humidity Measurement"
		capability "Sensor"
		capability "Temperature Measurement"
		capability "Ultraviolet Index"
        capability "Water Sensor"
        
        attribute "pressure", "number"
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
        standardTile("water", "device.water", width: 2, height: 2) {
			state "dry", icon:"st.alarm.water.dry", backgroundColor:"#ffffff"
			state "wet", icon:"st.alarm.water.wet", backgroundColor:"#53a7c0"
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
			state "default", label:'${currentValue}% Battery'
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
		details(["cameraDetails", "temperature", "uv",  "water", "humidity", "pressure","light", "battery", "lastUpdated", "refresh"])}	
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

//call Bloomsky API and update device
private def callAPI() {
	log.debug "Refreshing Bloomsky Device - ${device.label}"
        try {
            httpGet([
        			uri: "https://api.bloomsky.com",
        			path: "/api/skydata/",
        			requestContentType: "application/json",
        			headers: ["Authorization": parent.getAPIKey()]
				]) { resp ->
            	
                // Get the Device Info of the Correct Bloom Sky
                def individualBloomSky = resp.getData().findAll{ it.DeviceID == device.deviceNetworkId }
                // If you don't have Device ID specific get the first bloom sky only
                if(device.deviceNetworkId) {
                    individualBloomSky = resp.getData().findAll{ it.DeviceID == device.deviceNetworkId }
                }
                else {
                    individualBloomSky = resp.data[0]
                }
                def data = [:]
                data << individualBloomSky.Data //put bloomsky data into hash map
                //itterate through hashmap pairs to update device. Used case statement because it was twice as fast.
                data.each {oldkey, datum->
                	def key = oldkey.toLowerCase() //bloomsky returns camel cased keys. Put to lowercase so dth can update correctly
                	switch(datum) {
                    	case { key == "voltage"}: //update "battery"
                        	sendEvent(name:"battery", value: getBattery(datum))
                            //log.debug "${key}:${datum}"
                          break;
                        case { key == "ts"}: //update last update from bloomsky
                        	def date = (datum * 1000L)
                            def df = new java.text.SimpleDateFormat("MMM dd hh:mm a")
                            df.setTimeZone(location.timeZone)
                        	def lastUpdated = df.format(date)
                			sendEvent(name: "lastUpdated", value: lastUpdated)
                            //log.debug "${key}:${datum}"
                          break;
                        case { key == "rain" && datum == false}: //check if it is raining or not
                        	sendEvent(name: "water", value: "dry")
                            //log.debug "${key}:${datum}"
                          break;
                        case { key == "rain" && datum == true}:
                        	sendEvent(name: "water", value: "wet")
                            //log.debug "${key}:${datum}"
                          break;
                        case { key == "luminance"}: //update illuminance
                        	sendEvent(name: "illuminance", value: datum)
                            //log.debug "${key}:${datum}"
                          break;
                        case { key == "uvindex"}: //bloomsky does UV index! how cool is that!?
                        	sendEvent(name: "ultravioletIndex", value: datum)
                            //log.debug "${key}:${datum}"
                          break;
                        case { it instanceof Integer && key != "imagets"}: //check for integers. Two data points are integers so we save some expense doing this.
                        	sendEvent(name:"${key}", value: datum)
                            //log.debug "${key}:${datum}"
                          break;
                        case { key == "temperature"}: //temperature needs to be converted to celcuis in some cases so we break it out on its own
                        	def temp = getTemperature(datum).toDouble().trunc(1)
                        	sendEvent(name:"${key}", value: temp)
                            //log.debug "${key}:${temp}"
                          break;
                    	case { it instanceof BigDecimal && key != "imagets"}: //rest of the data points are big decimals. Another expense saver.
                        	def newDatum = datum.toFloat().trunc(1)
                        	sendEvent(name:"${key}", value: newDatum)
                            //log.debug "${key}:${newDatum}"
                          break;
                        case { key == "imageurl"}: //lastly update the image from bloomsky to the DTH
                        	httpGet(datum) { it -> 
                            	storeImage(getPictureName(), it.data)
                    		}
                            //log.debug "${key}:${datum}"
                      	  break;
                        default: 
                        	//log.debug "${key} is not being used"
                          break;
                	}
            	}
        	}
        }catch (Exception e) { //log exception gracefully
			log.debug "Error: $e"
		}
        data.clear()
}

//create unique name for picture storage
private getPictureName() {
  def pictureUuid = java.util.UUID.randomUUID().toString().replaceAll('-', '')
  "image" + "_$pictureUuid" + ".jpg"
}

def getBattery(v) {
	def result
    switch (v) {
    	case 2500..2700:
        	result = 100
          break;
        case 2450..2499:
        	result = 90
          break; 
        case 2400..2449:
        	result = 80
          break; 
        case 2350..2399:
        	result = 70
          break; 
        case 2300..2349:
        	result = 60
          break; 
        case 2250..2299:
        	result = 50
          break; 
        case 2200..2249:
        	result = 40
          break; 
        case 2150..2199:
        	result = 30
          break; 
        case 2100..2149:
        	result = 20
          break; 
        case 2050..2099:
        	result = 10
          break; 
        case 1999..2049:
        	result = 0
          break; 
        default: 
          result = "Check bloomsky device"
          break;
    }
    return result
}
