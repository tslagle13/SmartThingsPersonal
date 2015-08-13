/**
 *  Travel Time Guru
 *
 *  BETA 1.8
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
 *  Updated by Joseph Quander III[Darc Ranger]
 */

definition(
    name: "Travel Time Guru v1.8",
    namespace: "DarcRanger",
    author: "Tim Slagle=[tslagle13]",
    description: "Uses the Bing Maps Maps API to calculate your time of travel to a destination with current traffic and alerts you via push, sonsos, or hue bulbs when you need to leave.",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
)

preferences {
	page(name: "mainPage", install: true, uninstall: true)
    page(name: "wayPoints")
    page(name: "triggers")
    page(name: "setupTimes")
    page(name: "notificationSettings")
    page(name: "apiKey")
    page(name: "appRestrictions")
}

def mainPage() { 
    dynamicPage(name: "mainPage") {
        section("About"){
            paragraph "This app will lookup and notify you of when you need to leave for work.  Provide it with two way points and it will automatically check traffic every 5 minutes.  As it gets closer to the the time you need to leave in order to arrive on time it will begin to alert you based on the alert thresholds you set.  (This does require a Bing Maps API key from bingmapsportal.com)"
        }
        section("Current travel time. (Touch the app button to update)") {
            paragraph travelParagraph()
        }
        section("Enable App") {
        input "activeApp", "bool", title: "Deactivate App to prevent it running, but preserving settings?", required: false, defaultValue: true
        }
        section("Setup") {
            href "apiKey", title: "Bing Maps API Key", state: greyOutApi(), description: apiDescription()
            href "wayPoints", title: "Select Way Points", state: greyOutWayPoints(), description: waypointDescription()
            href "triggers", title: "Setup App Triggers", state: greyOutTriggers(), description: triggerDescription()
            href "setupTimes", title: "Select Arrival Time", state: greyOutTimes()
            href "notificationSettings", title: "Notification Settings", state: greyOutNotifications(), description: notificationsDescription()
            href "appRestrictions", title: "App Restrictions", state: greyOutRestrictions(), description: restrictionsDesription()
        }
        section([title:"Options", mobileOnly:true]) {
            label title:"Assign a name", required:false
        }
    }   
}

def wayPoints(){
    dynamicPage(name: "wayPoints") {
        section("About"){
            paragraph "Please set your home address and work address to calculate travel time between them. Ex. 85 Challenger Road, Ridgefield Park, NJ or  Samsung Electronics"
        }
        section("Starting Way Point"){	
            input "location1a", "text", title: "Starting Location Name", required: false, defaultValue: "Home"
            input "location1", "text", title: "Starting Address", required: True
         }
         section("Ending Way Point"){	
            input "location2a", "text", title: "Destination Name", required: false, defaultValue: "Work"
            input "location2", "text", title: "Destination Address", required: True
        }	  
    }
}

def triggers(){
    dynamicPage(name: "triggers") {
        section("About"){
            paragraph "Select what events will trigger the app to start running."
        }
        section("Trigger the app to start when..."){
            input "motions", "capability.motionSensor", title: "Motion is sensed here", required: false
            input "contactOpen", "capability.contactSensor", title: "The following are opened", required: false
            input "contactClosed", "capability.contactSensor", title: "The following are closed", required: false
            input "trigger", "capability.momentary", title: "Momentary switch is the trigger?", required: false
            input "checkTime", "time", title: "When do you want to start checking travel time?", required: false
		}
    }
}

def setupTimes(){
    dynamicPage(name: "setupTimes") {
        section("About"){
            paragraph "Setup the time you want to arrive at your destination as well as when the app should start to notify you that you need to leave."
        }
    	section("Arrival Time"){
            input "mytime", "time", title: "When do you want to arrive at your destination?", required: true
        }
        section("Notify Settings"){
            input "notifyLead", "number", title: "How many minutes before your first notification to leave? (default: 15 min)", required: True, defaultValue: 15
            input "notifyLeadWarn", "number", title: "How many minutes before your second notification to leave? (default: 10 min)", required: True, defaultValue: 10
            input "notifyLeadEmergency", "number", title: "How many minutes before your last notification to leave? (default: 5 min)", required: True, defaultValue: 5
        }	  
    }
}

def notificationSettings(){
    dynamicPage(name: "notificationSettings") {
        section("About"){
            paragraph "Select the way you want to be notified when a notification is sent."
        }

	section("Alert Settings"){
            input "sendTextMessage", "bool", title: "Send a text notification?", required: false, defaultValue: "false"
            input "phone", "phone", title: "Phone Number (for SMS, optional)", required: false
            input "sendPushMessage", "bool", title: "Send a push notification?", required: false, defaultValue: "false" //, metadata:[values:["Yes","No"]], required:false
            input "sonos", "capability.musicPlayer", title:"Speak message via: (optional) ", multiple: true, required: false
            input "speechDevice", "capability.speechSynthesis",title: "Speak message via: Ubi(optional)", multiple: true, required: false, refreshAfterSelection:true
            input "volume", "enum", title: "at this volume...", required: false, options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]]
            input "resumePlaying", "bool", title: "Resume currently playing music after alert?", required: false, defaultValue: true
        } 
        section("Hues"){
            input "hues", "capability.colorControl", title: "Change the color of these bulbs for each warning... (optional)", required:false, multiple:true
            input "colorNotify", "enum", title: "Change to this color on the first warning", required: false, options: [["White":"White"],["Daylight":"Daylight"],["Blue":"Blue"],["Green":"Green"],["Yellow":"Yellow"],["Orange":"Orange"],["Purple":"Purple"],["Pink":"Pink"],["Red":"Red"]]
            input "colorWarn", "enum", title: "Change to this color on the second warning", required: false, options: [["White":"White"],["Daylight":"Daylight"],["Blue":"Blue"],["Green":"Green"],["Yellow":"Yellow"],["Orange":"Orange"],["Purple":"Purple"],["Pink":"Pink"],["Red":"Red"]]
            input "colorEmergency", "enum", title: "Change to this color on the last warning", required: false, options: [["White":"White"],["Daylight":"Daylight"],["Blue":"Blue"],["Green":"Green"],["Yellow":"Yellow"],["Orange":"Orange"],["Purple":"Purple"],["Pink":"Pink"],["Red":"Red"]]
        }	  
    }
}

def apiKey(){
    dynamicPage(name: "apiKey") {
        section("About"){
            paragraph "Here you will need to provide the Bing Maps API key you got from the bingmapsportal.  You will need to go to bingmapsportal.com and sign up for a dev account which will provide you with a secret key you can use to acccess their API"
        }
        section("Bing Maps API Key"){
            input "apiKey", "text", title: "Microsoft API Secret Key", required: true
        }	  
    }
}

def appRestrictions(){
    dynamicPage(name: "appRestrictions") {
        section("About"){
            paragraph "Select when the app will stop running. If you don't select any of these the app will continue to run indefinitely.  (Pro Tip: You want to restrict it in some way.)"
        }
        section("Only run this app when"){
            input "people", "capability.presenceSensor", title: "These people are home", required: false
            input "modes", "mode", title: "The current mode is", required: false, multiple: true
            input "days", "enum", title: "Only on certain days of the week", multiple: true, required: false,
				options: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
            href "timeIntervalInput", title: "Only during a certain time", description: timeLabel ?: "Tap to set" //,state: greyOutTimeLabel()    
        }
        
    }
}

page(name: "timeIntervalInput", title: "Only during a certain time", refreshAfterSelection:true) {
		section {
			input "starting", "time", title: "Starting", required: false, refreshAfterSelection:true
			input "ending", "time", title: "Ending", required: false, refreshAfterSelection:true
		}
}  

//schedule upon install
def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

//reschedule upon update
def updated() {
    unsubscribe()
    unschedule("trafficCheck")
    unschedule("checkTimeHandler")
    state.clear()
    	log.debug "Updated with settings: ${settings}"
	initialize()
}

def initialize() {
state.ending = ending
    subscribe(app, totalTravelTime)
   if(activeApp) {
	if(motions){
    	subscribe(motions, "motion.active", trafficCheck)
    }
    
    if(contactOpen){
    	subscribe(contactOpen, "contact.open", trafficCheck)
    }
    
    if(contactClosed){
    	subscribe(contactClosed, "contact.open", trafficCheck)
    }
    
    if(trigger){
    	subscribe(trigger, "momentary.pushed", trafficCheck)
    }
   if(checkTime){
    def checkHour = Date.parse("yyyy-MM-dd'T'HH:mm:ss.SSSX", checkTime).format('H', TimeZone.getTimeZone('America/New_York')) //setTimeZone(location.timeZone)
    def checkHourAP = Date.parse("yyyy-MM-dd'T'HH:mm:ss.SSSX", checkTime).format('h', TimeZone.getTimeZone('America/New_York')) //setTimeZone(location.timeZone)
    def checkMinute = Date.parse("yyyy-MM-dd'T'HH:mm:ss.SSSX", checkTime).format('mm', TimeZone.getTimeZone('America/New_York'))//TimeZone.getTimeZone('EST')) "America/New_York"

    log.debug "Check Time: " + hhmm(checkTime) 
    //log.debug "Check Hour ${checkHour}"
    //log.debug "Check Minute ${checkMinute}"
     
	schedule("0 ${checkMinute} ${checkHour} * * ?", "checkTimeHandler")
   }
} else {
	log.info "Travel App is currently off, Triggers are not Active."
}
//log.debug "initialize with settings: $settings" 
}

def checkTimeHandler(){
	if(ending<checkTime || starting>checkTime){
	log.info "Alert: The Time Trigger for the app is outside of your time restriction range.  Please update the app."
	//sendPush ( "Alert: The Time Trigger for the app is outside of your time restriction range.  Please update the app." )
    }
		if ( now() > timeToday(checkTime).time && now() < timeToday(mytime).time){
    	log.debug "Begin checking Travel Time with Traffic to ${location2a}." + " Desired Arrival Time: " + hhmm(mytime)
        trafficCheck()
    }
    else {
    	log.debug "Its not time anymore (checkTimeHandler)."
       initialize()
    }
}

def trafficCheck(evt){
if(starting && ending){
log.debug "Restrictions: App Active: ${activeApp} | people: ${people} | modes: ${modes} | days: ${days} | starting: ${hhmm(starting)} | ending: ${hhmm(ending)}"
} else if (starting && ending == null){
log.debug "Restrictions: App Active: ${activeApp} | people: ${people} | modes: ${modes} | days: ${days} | starting: ${hhmm(starting)} | ending: None Selected"
} else if (starting == null && ending ){
log.debug "Restrictions: App Active: ${activeApp} | people: ${people} | modes: ${modes} | days: ${days} | starting: None Selected | ending: ${hhmm(ending)}"
} else {
log.debug "Restrictions: App Active: ${activeApp} | people: ${people} | modes: ${modes} | days: ${days} | starting: None Selected | ending: None Selected"
}
if(checkTime){
log.debug "Triggers: App Active: ${activeApp} | motions: ${motions} | contactOpen: ${contactOpen} | contactClosed ${contactClosed} | momentary: ${trigger} | Start Checking: ${hhmm(checkTime)}"
}else {
log.debug "Triggers: App Active: ${activeApp} | motions: ${motions} | contactOpen: ${contactOpen} | contactClosed ${contactClosed} | momentary: ${trigger} | Start Checking: Not Selected"
}
log 
if(allOk){
    if(state.travelTimeTraffic){
            int timeLeft = getTimeLeft()
            //log.info "Total travel time to ${location2a} from ${location1a} with traffic is ${state.travelTimeTraffic} minutes."
        if(timeLeft != null){
        def runCheck = timeLeft - notifyLead
        	if(runCheck > notifyLead){
            log.debug "First Notify Threshold: $runCheck"
            log.debug "Recheck in  " + (runCheck - 6) + " minutes" 
            unschedule( trafficCheck )
            runIn((runCheck - 6)*60,trafficCheck)
            state.check = null
                state.notify = null
                state.notifyWarn = null
                state.notifyEmergency = null
                state.notifyNow = null
            state.trafficCheck = null
            }
            }
            if(timeLeft <= 0){
            	if(state.notifyNow != "true"){
                def timeLeftFixed = -1 * timeLeft
                state.msg = "Attention: With current traffic conditions you will be ${timeLeftFixed} minutes late for ${location2a}. Total travel time with traffic is ${state.travelTimeTraffic} minutes."
                     log.info "MSG 1[Running Late]: Attention - With current traffic conditions you will be ${timeLeftFixed} minutes late for ${location2a}. Total travel time with traffic is ${state.travelTimeTraffic} minutes."
getNotified()       
                    state.check = null
                    state.notify = null
                    state.notifyWarn = null
                    state.notifyEmergency = null
                    state.notifyNow = "true"
                    if(hues){
                        sendcolor(colorEmergency)
                    }
                    
                    if(state.trafficCheck != true){
                        runEvery5Minutes(trafficCheck)
                        state.trafficCheck = true
                    }
            	}       
            }
            else if(timeLeft <= notifyLeadEmergency){
                state.msg = "You have ${timeLeft} minutes until you need to leave ${location1a} for ${location2a}. Total travel time with traffic is ${state.travelTimeTraffic} minutes."
                    log.info "MSG 2[Notify within ${notifyLeadEmergency} Minutes]: You have ${timeLeft} minutes until you need to leave ${location1a} for ${location2a}. Total travel time with traffic is ${state.travelTimeTraffic} minutes."
					getNotified()
				if (state.notifyEmergency != "true"){
                    state.check = null
                    state.notify = null
                    state.notifyWarn = null
                    state.notifyNow = null
                    state.notifyEmergency = "true"
                    if(hues){
                        sendcolor(colorEmergency)
                    }
                    if(state.trafficCheck != true){
                        runEvery5Minutes(trafficCheck) 
                        state.trafficCheck = true
                    } 
                }
            }
            else if(timeLeft <= notifyLeadWarn){
                state.msg = "You have ${timeLeft} minutes until you need to leave ${location1a} for ${location2a}. Total travel time with traffic is ${state.travelTimeTraffic} minutes."
                    log.info "MSG 3[Notify within ${notifyLeadWarn} Minutes]: You have ${timeLeft} minutes until you need to leave ${location1a} for ${location2a}. Total travel time with traffic is ${state.travelTimeTraffic} minutes."
                    getNotified()
                if (state.notifyWarn != "true"){
                    state.check = null
                    state.notify = null
                    state.notifyNow = null
                    state.notifyWarn = "true"
                    state.notifyEmergency = null
                    if(hues){
                        sendcolor(colorWarn)
                    }
                    if(state.trafficCheck != true){
                        runEvery5Minutes(trafficCheck) 
                        state.trafficCheck = true
                    } 
                }
            }
            else if(timeLeft <= notifyLead){
                state.msg = "You have ${timeLeft} minutes until you need to leave ${location1a} for ${location2a}. Total travel time with traffic is ${state.travelTimeTraffic} minutes."
                    log.info "MSG 4[Notify within ${notifyLead} Minutes]: You have ${timeLeft} minutes until you need to leave ${location1a} for ${location2a}. Total travel time with traffic is ${state.travelTimeTraffic} minutes."
					getNotified()
				if (state.notify != "true"){                     
                    state.check = null
                    state.notify = "true"
                    state.notifyWarn = null
                    state.notifyNow = null
                    state.notifyEmergency = null
                    if(hues){
                        sendcolor(colorNotify)
                    }
                    if(state.trafficCheck != true){
                        runEvery5Minutes(trafficCheck) 
                        state.trafficCheck = true
                    } 
                }
            }
            else if((state.notify == "true" || state.notifyWarn == "true" || state.notifyEmergency == "true") && state.check != "true"){
                state.msg = "Traffic conditions seem to have improved.  You now have ${timeLeft} minutes to leave ${location1a} for ${location2a}. Total travel time with traffic is ${state.travelTimeTraffic} minutes."
					log.info "MSG 5[Improved]: Traffic conditions seem to have improved.  You now have ${timeLeft} minutes to leave ${location1a} for ${location2a}. Total travel time with traffic is ${state.travelTimeTraffic} minutes."
                	getNotified()                          
                state.check = "true"
                state.notify = null
                state.notifyWarn = null
                state.notifyNow = null
                state.notifyEmergency = null
                sendcolor(colorNormal)
                if(hues){
                    hues.off([delay:5000])
                }
                if(state.trafficCheck != true){
                    runEvery5Minutes(trafficCheck) 
                    state.trafficCheck = true
                } 
            }    
            else{
                if (state.check != "greeting"){
                state.msg = "You have ${timeLeft} minutes to leave ${location1a} for ${location2a}. Total travel time with traffic is ${state.travelTimeTraffic} minutes."
	                log.info "MSG 6[Greeting]: You have ${timeLeft} minutes to leave ${location1a} for ${location2a}. Total travel time with traffic is ${state.travelTimeTraffic} minutes."
					getNotified()                       
                state.check = "greeting"
                state.notify = null
                state.notifyWarn = null
                state.notifyEmergency = null
                state.notifyNow = null
                if(state.trafficCheck != true){
                runEvery5Minutes(trafficCheck) 
                    state.trafficCheck = true
                }    
                }
            }
        }
        else{
    		log.info "I do not have a travel time so I will look it up."
            //state.msg = "Darc, I do not have a travel time so I will need to look it up."
		//getNotified()
            log.info "Check 7:Getting Details"
            if(travelTimeTraffic == null) {
            log.info "Check 7A:Getting Travel Time"
            totalTravelTime()
            }
            
        	if(state.trafficCheck != true){ 
             log.info "Check 7B: Next Traffic Check within 5 Minutes"

            runEvery5Minutes(trafficCheck) 
				state.trafficCheck = true
        	}
        }    
	}
    else{
	log.info "Travel App is currently inactive.  Restrictions prevent it from running"
    	unschedule(trafficCheck)
    	state.clear()         
        if(activeApp == false) {

        }
    }    

}

def getNotified(){
def msg = state.msg
log.info "Push/SMS Message: $msg"
                    if(sonos){
                       /* if(resumePlaying){
                            loadText(msg)
                            sonos.playTrackAndResume(state.sound.uri, state.sound.duration, volume)
                        }
                        else{*/
                        	sonos.each(){		// Sonos Speaks Message
        				log.debug ("${it.displayName} | - | Sending speak(PlayText).")
                            sonos.playText(msg)
                            }
                        //}    
                   }
                    
                    if(speechDevice){
                    //Talk(msg, speechDevice, evt) 
                    speechDevice.each(){		// Ubi Speaks Message
        			log.debug ("${it.displayName} | - | Sending speak(Speak).")
		    		it.speak(msg)
                    }
                    }
            
             		if(sendPushMessage){
                 	sendPush(msg  + " : " + app.label)
            		}      
              		 else {
					log.debug "No Push"
                   	}
                      
             		if (phone !=null  && sendTextMessage) {
                 	log.debug "sending SMS"
				  	sendSms(phone, msg)
                     }
     				  else {
				 	log.debug "No SMS"
                   }
}


private getTimeLeft(){
	def location1Fixed = location1.replaceAll(" ", "%20")
	def location2Fixed = location2.replaceAll(" ", "%20")
	def result = ""
	try {
			httpGet("http://dev.virtualearth.net/REST/v1/Routes?wayPoint.1=${location1Fixed}&waypoint.2=${location2Fixed}&key=${apiKey}") { resp ->
            	resp.headers.each {
        			log.debug "${it.name} : ${it.value}"
            
    		}
    		log.debug "response contentType: ${resp.contentType}"
        	def totalTime = resp.data.resourceSets.resources.travelDurationTraffic as String
        	def totalTimeFixed = totalTime.replaceAll("\\[", "").replaceAll("\\]","") as Double
        	def travelTimeMinutes = (totalTimeFixed / 60) as Double
        	def travelTimeMinutesRounded = travelTimeMinutes.round(0)
			state.travelTimeTraffic = travelTimeMinutesRounded as Integer
        	log.info "Travel time with traffic = ${state.travelTimeTraffic}"
     	}
   	}
    
    catch (e) {
		log.error "HTTP Error: ${e}"
	}
  
	def getTime = timeToday(mytime)    
	def timeTillArrival = getTime.time - now()
	def timeTillArrivalMinutes = (timeTillArrival / 60000) as Double
	def timeTillArrivalMinutesRounded = timeTillArrivalMinutes.round() as Double
	log.info "Time until Arrival/Event = ${timeTillArrivalMinutesRounded} | Expected Arrival: ${hhmm(mytime)}"
	def timeLeft = timeTillArrivalMinutesRounded - state.travelTimeTraffic
	log.info "Time Left = ${timeLeft}"
	result = timeLeft
    state.timeLeft = timeLeft
    state.timeTillArrivalMinutesRounded = timeTillArrivalMinutesRounded-5

result 
}

def totalTravelTime(evt){
	getTimeLeft()    
if(activeApp) {
    if(sendPushMessage || sendTextMessage){
	//sendPush 
    state.msg = ("With traffic, the estimated travel time is ${state.travelTimeTraffic} minutes from ${location1a} to ${location2a}.")
    getNotified()
    }
	log.info ("With traffic, the estimated travel time is ${state.travelTimeTraffic} minutes from ${location1a} to ${location2a}.")
  }
if(activeApp != true) {
    log.info "Travel App is currently off, please switch on if you want the app running normally."
 	}
}

def sendcolor(color) {
	log.debug "Sendcolor = $color"
    def hueColor = 0
    def saturation = 100

	switch(color) {
		case "White":
			hueColor = 52
			saturation = 19
			break;
		case "Daylight":
			hueColor = 53
			saturation = 91
			break;
		case "Soft White":
			hueColor = 23
			saturation = 56
			break;
		case "Warm White":
			hueColor = 20
			saturation = 80 //83
			break;
		case "Blue":
			hueColor = 70
			break;
		case "Green":
			hueColor = 39
			break;
		case "Yellow":
			hueColor = 25
			break;
		case "Orange":
			hueColor = 10
			break;
		case "Purple":
			hueColor = 75
			break;
		case "Pink":
			hueColor = 83
			break;
		case "Red":
			hueColor = 100
			break;
	}

	state.previous = [:]

	hues.each {
		state.previous[it.id] = [
			"switch": it.currentValue("switch"),
			"level" : it.currentValue("level"),
			"hue": it.currentValue("hue"),
			"saturation": it.currentValue("saturation")
           
		]
	}
	
	log.debug "current values = $state.previous"
    
    
    
  	def lightLevel = 60
    if (brightnessLevel != null) {
    	lightLevel = brightnessLevel 
    }
     
	def newValue = [hue: hueColor, saturation: saturation, level: lightLevel]  
	log.debug "new value = $newValue"

	hues*.setColor(newValue)
}



/* Song selection isn't working for some reason.  Will revisit.
private songOptions() {

	// Make sure current selection is in the set

	def options = new LinkedHashSet()
	if (state.selectedSong?.station) {
		options << state.selectedSong.station
	}
	else if (state.selectedSong?.description) {
		// TODO - Remove eventually? 'description' for backward compatibility
		options << state.selectedSong.description
	}

	// Query for recent tracks
	def states = sonos.statesSince("trackData", new Date(0), [max:30])
	def dataMaps = states.collect{it.jsonValue}
	options.addAll(dataMaps.collect{it.station})

	log.trace "${options.size()} songs in list"
	options.take(20) as List
}

private saveSelectedSong() {
	try {
		def thisSong = song
		log.info "Looking for $thisSong"
		def songs = sonos.statesSince("trackData", new Date(0), [max:30]).collect{it.jsonValue}
		log.info "Searching ${songs.size()} records"

		def data = songs.find {s -> s.station == thisSong}
		log.info "Found ${data?.station}"
		if (data) {
			state.selectedSong = data
			log.debug "Selected song = $state.selectedSong"
		}
		else if (song == state.selectedSong?.station) {
			log.debug "Selected existing entry '$song', which is no longer in the last 20 list"
		}
		else {
			log.warn "Selected song '$song' not found"
		}
	}
	catch (Throwable t) {
		log.error t
	}
}
*/
private loadText(msg) {  // not fully working yet.
		log.debug "msg = ${msg}"
		state.sound = textToSpeech(msg, true)
		log.trace (" Message send[S]: ${msg}")
}
// Incorportated "Speak" command under getNotified()
/*private Talk(msg, speechDevice, evt){
	    speechDevice.each(){
        log.debug ("${it.displayName} | - | Sending speak().")
        log.trace (" Message send[U]: ${msg}")
		    it.speak(msg)
}
}*/

def greyOutApi(){
	def result = ""
    if (apiKey) {
    	result = "complete"	
    }
    result
}

def greyOutWayPoints(){
	def result = ""
    if (location1 && location2) {
    	result = "complete"	
    }
    result
}

def greyOutTriggers(){
	def result = ""
    if (motions || contactClosed || contactOpen || trigger || checkTime) {
    	result = "complete"	
    }
    result
}

def greyOutTimes(){
	def result = ""
    if (mytime && notifyLead && notifyLeadWarn && notifyLeadEmergency) {
    	result = "complete"	
    }
    result
}

def greyOutNotifications(){
	def result = ""
    if (sendPushMessage || sendTextMessage || sonos || hues || speechDevice) {
    	result = "complete"	
    }
    result
}

def greyOutRestrictions(){
	def result = ""
    if (modes || people) {
    	result = "complete"	
    }
    result
}

def greyOutTimeLabel(){
	def result = ""
    if(starting || ending){
    	result = true
    }
    result
}

def triggerDescription(){
	def result = ""
    result = "Motion: ${motions}"  + "\n" + "Contact Open: ${contactOpen}"  + "\n" + "Contact Closed: ${contactClosed}" + "\n" + "Momentary: ${trigger}" + "\n"+ "${getTriggerLabel()}" + "\n" 
}

def waypointDescription(){
	def result = ""
    if(location1 && location2){
    	result = "Calculate times between"  + "\n" + "${location1a}" + "\n"  + "${location1}"  + "\n" + "and ${location2a}" + "\n" + "${location2}."
    }
    else if(location1){
    	result = "Destination address not set"
    }
    else if(location2){
    	result = "Starting address not set"
    }
    else{
    	result = "Tap to set"
    }
    result
}

def notificationsDescription(){
	def result = ""
    result = 	"Send Text: ${sendTextMessage}" + "\n" +"SMS #: ${phone}" + "\n" + "Send Push: ${sendPushMessage}" + "\n" + "Voice: ${speechDevice}" + "\n" + "Sonos: ${sonos}"  + "\n" + "Hues: ${hues}" + "\n"

}

def apiDescription(){
	def result = ""
    def length = apiKey.length()
    if (length == 64){
    	result = "Valid API Key"
	}
    else if (length != 0){
    	result = "Invalid API key"
    }
    else{
    	result = "Tap to enter API key"
    }    
}

def restrictionsDesription(){
	def result = ""
    def daysRest = ""
    def modesBracket = ""
    def peopleBracket = ""
    def timeRest = ""
    def modesFixed = "No Mode Restrictions"
    def daysFixed = "No Day Restrictions"
    def peopleFixed = "No Presence Restrictions"
    if (days){
    	daysRest = "$days"
        daysFixed = daysRest.replaceAll("\\[", "").replaceAll("\\]","")        
    }
    if(modes != null){
    	modesBracket = "${modes}"
        modesFixed = modesBracket.replaceAll("\\[", "").replaceAll("\\]","")
    }
    if(starting || ending){
    	timeRest = getTimeLabel()
    }
    if(people){
    	peopleBracket = "$people"
        peopleFixed = peopleBracket.replaceAll("\\[", "").replaceAll("\\]","")
    }
    result = "Days: ${daysFixed}" + "\n" + "Modes: ${modesFixed}" + "\n" + "Time: ${getTimeLabel()}" + "\n" + "Presence: ${peopleFixed}"
}

def travelParagraph(){
	def timeTravel = state.travelTimeTraffic as Integer
	def result = "Total travel time to ${location2a} with traffic is $timeTravel minutes."
    return result
}

private getAllOk() {
log.trace "activeApp = $activeApp"
	modeOk && daysOk && timeOk && peopleOk && activeApp
}

private getModeOk(){
	def result = false
     if (modes != "All Modes"){
		result = true
    }
	else (modes.contains(location.mode)){
    	result = true
    }
    log.trace "modeOk: $result"
    return result	
}

private getPeopleOk() {
  def result = false
  if(people.findAll { it?.currentPresence == "present" }) {
result = true
 }
 if (people == null){
	result = true
    }
    
  log.trace("anyoneIsHome: ${result}")

  return result

}
private getTimeOk() {
	def result = true
if (state.timeLeft <=0 && state.timeLeft != null){
    //log.debug timeLeft
    result = false
    log.debug app.label + "[${state.timeLeft} minutes]: You should have left by now. You have about $state.timeTillArrivalMinutesRounded Minutes to get to ${location2a}."
    sendPush (app.label + "[${state.timeLeft} minutes]: You should have left by now. You have about $state.timeTillArrivalMinutesRounded Minutes to get to ${location2a}.")
    }
	 else if (starting && ending) {
		def currTime = now()
		def start = timeToday(starting).time
		def stop = timeToday(ending).time
		result = start < stop ? currTime >= start && currTime <= stop : currTime <= stop || currTime >= start
	}
    else if (starting){
        def currTime = now()
		def start = timeToday(starting).time
    	result = currTime >= start
    }
    else if (ending){
        def currTime = now()
		def stop = timeToday(ending).time
    	result = currTime <= stop
    }
	log.trace "timeOk = $result"
	result
}

private getTimeLabel(){
	def timeLabel = "No Time restrictions"
	
    if(starting && ending){
    	timeLabel = "Between " + hhmm(starting) + " and " + hhmm(ending)
    }
    else if (starting) {
		timeLabel = "Start at: " + hhmm(starting)
    }
    else if(ending){
    timeLabel = "End at: " + hhmm(ending)
    }
	timeLabel	
}

private getTriggerLabel(){
	def triggerLabel = "Start Checking: Not Set"
	
    if (checkTime) {
		triggerLabel = "Start Checking: " + hhmm(checkTime)
    }
	triggerLabel	
}

private hhmm(time, fmt = "h:mm a")
{
	def t= timeToday(time, location.timeZone)
  	def f = new java.text.SimpleDateFormat(fmt)
	f.setTimeZone(location.timeZone ?: timeZone(time))
	f.format(t)
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
    log.trace "daysOk = $result"
    result
}
