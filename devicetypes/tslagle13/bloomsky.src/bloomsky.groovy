/**
 *  Bloomsky
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
        
        
	}
    preferences {
		input "apiKey", "text", title: "API Key", required: true
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles(scale:1) {
		carouselTile("cameraDetails", "device.image", width: 3, height: 2) { }
        standardTile("refresh", "device.weather", decoration: "flat") {
			state "default", label: "", action: "refresh", icon:"st.secondary.refresh"
		}
        valueTile("illuminance", "device.illuminance", decoration: "flat", width: 2) {
			state "default", label:'${currentValue} Luminance'
		}
        valueTile("rain", "device.rain", decoration: "flat") {
			state "default", label:'${currentValue}'
		}
        valueTile("uv", "device.ultraviolet", decoration: "flat", width: 2) {
			state "default", label:'${currentValue} UV Index'
		}
        valueTile("humidity", "device.humidity", decoration: "flat", width:2) {
			state "default", label:'${currentValue} Humidity'
		}
        valueTile("pressure", "device.pressure", decoration: "flat", width: 2) {
			state "default", label:'${currentValue} inHg Pressure'
		}
        valueTile("battery", "device.battery", decoration: "flat", width: 2) {
			state "default", label:'Device Battery ${currentValue} V'
		}
        valueTile("night", "device.night", decoration: "flat",) {
			state "default", label:'${currentValue}'
		}
		valueTile("temperature", "device.temperature") {
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
		details(["humidity","temperature", "illuminance", "rain", "uv", "pressure", "battery", "night", "refresh"])}
	
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
            	def temp = T.replaceAll("\\[", "").replaceAll("\\]","")
                sendEvent(name: "temperature", value: temp, unit: "F")
                log.debug "Temp:" + temp
            }
            if (resp.data.Data.Humidity) {
            	def H =  resp.data.Data.Humidity.toString()
            	def humidity = H.replaceAll("\\[", "").replaceAll("\\]","")
                sendEvent(name: "humidity", value: humidity + "%")
                log.debug "Humidity:" + humidity
            }
            if (resp.data.Data.Luminance) {
            	def L =  resp.data.Data.Luminance.toString()
            	def luminance = L.replaceAll("\\[", "").replaceAll("\\]","")
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
            	def pressure = P.replaceAll("\\[", "").replaceAll("\\]","")
                sendEvent(name: "pressure", value: pressure, unit: "inHg")
                log.debug "Pressure:" + pressure
            }
            if (resp.data.Data.UVIndex) {
            	def U =  resp.data.Data.UVIndex.toString()
            	def uvIndex = U.replaceAll("\\[", "").replaceAll("\\]","")
                sendEvent(name: "ultraviolet", value: uvIndex)
                log.debug "uvIndex:" + uvIndex
            }
            if (resp.data.Data.Night) {
            	def N =  resp.data.Data.Night.toString()
            	def night = N.replaceAll("\\[", "").replaceAll("\\]","")
                if (night == "false") {
                	sendEvent(name: "night", value: "It's day time")
                }
                else {
                	sendEvent(name: "night", value: "It's night time")
                }
                log.debug "Night:" + night
            }
            if (resp.data.Data.Voltage) {
            	def V =  resp.data.Data.Voltage.toString()
            	def voltage = V.replaceAll("\\[", "").replaceAll("\\]","")
                sendEvent(name: "battery", value: voltage)
                log.debug "voltage:" + voltage
            }
        }
}
