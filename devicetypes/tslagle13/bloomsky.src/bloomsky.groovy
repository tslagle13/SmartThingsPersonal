/**
 *  Bloomsky
 *
 *  Version: 0.5 - Fixed long decimal values on temp and pressure. 
 *
 *	Version: 0.4 - Convert cd/m2 to lux properly
 *				 - Make humidity display properly with humidity text
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
        
	}
    preferences {
		input "apiKey", "text", title: "API Key", required: true
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
			state "pressure", label:'${currentValue} mbar', unit:""
		}
        valueTile("battery", "device.battery", decoration: "flat", width: 2, height: 2) {
			state "default", label:'${currentValue} mV Battery'
		}
        valueTile("night", "device.day", decoration: "flat", width: 2, height: 2) {
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
		details(["cameraDetails", "temperature", "uv", "humidity", "pressure", "light", "rain", "night", "battery", "refresh"])}
	
}    

def poll() {
	callAPI()
}

def refresh() {
	callAPI()
}

def callAPI() {
    def pollParams = [
        uri: "http://thirdpartyapi.appspot.com",
        path: "/api/skydata/",
        requestContentType: "application/json",
        headers: ["Authorization": apiKey]
		]
        httpGet(pollParams) { resp ->
        	if (resp.data.Data.Temperature) {
        		def T =  resp.data.Data.Temperature.toString()
            	def temp = ((T.replaceAll("\\[", "").replaceAll("\\]","")).take(5))
                sendEvent(name: "temperature", value: temp, unit: "F")
                log.debug "Temp:" + temp
            }
            if (resp.data.Data.Humidity) {
            	def H =  resp.data.Data.Humidity.toString()
            	def humidity = H.replaceAll("\\[", "").replaceAll("\\]","")
                sendEvent(name: "humidity", value: humidity, unit: "%")
                log.debug "Humidity:" + humidity
            }
            if (resp.data.Data.Luminance) {
            	def L =  resp.data.Data.Luminance.toString()
            	def luminance = ((L.replaceAll("\\[", "").replaceAll("\\]","")) as int).intdiv(3600)
                sendEvent(name: "illuminance", value: luminance)
                log.debug "Luminance:" + luminance
            }
            if (resp.data.Data.Rain) {
            	def R =  resp.data.Data.Rain.toString()
            	def rain = R.replaceAll("\\[", "").replaceAll("\\]","")
                if (rain == "false"){
                	sendEvent(name: "rain", value: "Not Raining")
                }
                else {
                	sendEvent(name: "rain", value: "Raining")
                }
                log.debug "Rain:" + rain
            }
            if (resp.data.Data.Pressure) {
            	def P =  resp.data.Data.Pressure.toString()
            	def pressure = (P.replaceAll("\\[", "").replaceAll("\\]","").take(4))
                sendEvent(name: "pressure", value: pressure, unit: "inHg")
                log.debug "Pressure:" + pressure
            }
            if (resp.data.Data.UVIndex) {
            	def U =  resp.data.Data.UVIndex.toString()
            	def uvIndex = U.replaceAll("\\[", "").replaceAll("\\]","")
                sendEvent(name: "ultravioletIndex", value: uvIndex)
                log.debug "uvIndex:" + uvIndex
            }
            if (resp.data.Data.Night) {
            	def N =  resp.data.Data.Night.toString()
            	def night = N.replaceAll("\\[", "").replaceAll("\\]","")
                if (night == "false") {
                	sendEvent(name: "day", value: "It's day time")
                }
                else {
                	sendEvent(name: "day", value: "It's night time")
                }
                log.debug "Night:" + night
            }
            if (resp.data.Data.Voltage) {
            	def V =  resp.data.Data.Voltage.toString()
            	def voltage = V.replaceAll("\\[", "").replaceAll("\\]","")
                sendEvent(name: "battery", value: voltage)
                log.debug "voltage:" + voltage
            }
            if (resp.data.Data.ImageURL) {
            	def I =  resp.data.Data.ImageURL.toString()
            	def image = I.replaceAll("\\[", "").replaceAll("\\]","").toString()
                httpGet(image) { it -> 
        			storeImage(getPictureName(), it.data)
        		}
                log.debug "image:" + image
            }
        }
        

}

private getPictureName() {
  def pictureUuid = java.util.UUID.randomUUID().toString().replaceAll('-', '')
  "image" + "_$pictureUuid" + ".jpg"
}