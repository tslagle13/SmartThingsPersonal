/**
 *  Lighting Director
 *
 *  Version - 1.3
 *  Version - 1.30.1 Modification by Michael Struck - Fixed syntax of help text and titles of scenarios, along with a new icon
 *  Version - 1.40.0 Modification by Michael Struck - Code optimization and added door contact sensor capability		
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
 *	Copyright 2014 SmartThings
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
    name: "Lighting Director",
    namespace: "tslagle13",
    author: "Tim Slagle & Michael Struck",
    description: "Control up to 15 sets (scenes) of lights based on motion, door contacts and lux levels.",
    category: "Convenience",
    iconUrl: "https://raw.githubusercontent.com/MichaelStruck/SmartThings/master/Other-SmartApps/Lighting-Director/LightingDirector.png",
    iconX2Url: "https://raw.githubusercontent.com/MichaelStruck/SmartThings/master/Other-SmartApps/Lighting-Director/LightingDirector@2x.png",
    iconX3Url: "https://raw.githubusercontent.com/MichaelStruck/SmartThings/master/Other-SmartApps/Lighting-Director/LightingDirector@2x.png")



preferences {
    page name:"pageSetup"
    page name:"pageSetupScenarioA"
    page name:"pageSetupScenarioB"
    page name:"pageSetupScenarioC"
    page name:"pageSetupScenarioD"
    page name:"pageSetupScenarioE"
    page name:"pageSetupScenarioF"
    page name:"pageSetupScenarioG"
    page name:"pageSetupScenarioH"
    page name:"pageSetupScenarioI"
    page name:"pageSetupScenarioJ"
    page name:"pageSetupScenarioK"
    page name:"pageSetupScenarioL"
    page name:"pageSetupScenarioM"
    page name:"pageSetupScenarioN"
    page name:"pageSetupScenarioO"
    
}

// Show setup page
def pageSetup() {
if (settings.ScenarioNameA == null) {
        settings.ScenarioNameA = "Empty"
        settings.EditNameA = "Tap to create a scene"
    }
    else {
    settings.EditNameA = "Tap to edit scene"
    }


if (settings.ScenarioNameB == null) {
        settings.ScenarioNameB = "Empty"
    settings.EditNameB = "Tap to create a scene"
    }
    else {
    settings.EditNameB = "Tap to edit scene"
    }
    

if (settings.ScenarioNameC == null) {
        settings.ScenarioNameC = "Empty"
    settings.EditNameC = "Tap to create a scene"
    }
    else {
    settings.EditNameC = "Tap to edit scene"
    }


if (settings.ScenarioNameD == null) {
        settings.ScenarioNameD = "Empty"
    settings.EditNameD = "Tap to create a scene"
    }
    else {
    settings.EditNameD = "Tap to edit scene"
    }   


if (settings.ScenarioNameE == null) {
        settings.ScenarioNameE = "Empty"
    settings.EditNameE = "Tap to create a scene"
    }
    else {
    settings.EditNameE = "Tap to edit scene"
    }


if (settings.ScenarioNameF == null) {
        settings.ScenarioNameF = "Empty"
    settings.EditNameF = "Tap to create a scene"
    }
    else {
    settings.EditNameF = "Tap to edit scene"
    }    


if (settings.ScenarioNameG == null) {
        settings.ScenarioNameG = "Empty"
    settings.EditNameG = "Tap to create a scene"
    }
    else {
    settings.EditNameG = "Tap to edit scene"
    }    


if (settings.ScenarioNameH == null) {
        settings.ScenarioNameH = "Empty"
    settings.EditNameH = "Tap to create a scene"
    }
    else {
    settings.EditNameH = "Tap to edit scene"
    }    


if (settings.ScenarioNameI == null) {
        settings.ScenarioNameI = "Empty"
    settings.EditNameI = "Tap to create a scene"
    }
    else {
    settings.EditNameI = "Tap to edit scene"
    }    


if (settings.ScenarioNameJ == null) {
        settings.ScenarioNameJ = "Empty"
    settings.EditNameJ = "Tap to create a scene"
    }
    else {
    settings.EditNameJ = "Tap to edit scene"
    }   


if (settings.ScenarioNameK == null) {
        settings.ScenarioNameK = "Empty"
    settings.EditNameK = "Tap to create a scene"
    }
    else {
    settings.EditNameK = "Tap to edit scene"
    }    


if (settings.ScenarioNameL == null) {
        settings.ScenarioNameL = "Empty"
    settings.EditNameL = "Tap to create a scene"
    }
    else {
    settings.EditNameL = "Tap to edit scene"
    }    


if (settings.ScenarioNameM == null) {
        settings.ScenarioNameM = "Empty"
    settings.EditNameM = "Tap to create a scene"
    }
    else {
    settings.EditNameM = "Tap to edit scene"
    }   


if (settings.ScenarioNameN == null) {
        settings.ScenarioNameN = "Empty"
    settings.EditNameN = "Tap to create a scene"
    }
    else {
    settings.EditNameN = "Tap to edit scene"
    }


if (settings.ScenarioNameO == null) {
        settings.ScenarioNameO = "Empty" 
    settings.EditNameO = "Tap to create a scene"
    }
    else {
    settings.EditNameO = "Tap to edit scene"
    }

	

    
    def pageProperties = [
        name:       "pageSetup",
        title:      "Status",
        nextPage:   null,
        install:    true,
        uninstall:  true
    ]


    return dynamicPage(pageProperties) {
        section("Setup Menu") {
            href "pageSetupScenarioA", title:"${settings.ScenarioNameA}", description:"${settings.EditNameA}", state: greyOut(settings.ScenarioNameA)
            href "pageSetupScenarioB", title:"${settings.ScenarioNameB}", description:"${settings.EditNameB}", state: greyOut(settings.ScenarioNameB)
            href "pageSetupScenarioC", title:"${settings.ScenarioNameC}", description:"${settings.EditNameC}", state: greyOut(settings.ScenarioNameC)
            href "pageSetupScenarioD", title:"${settings.ScenarioNameD}", description:"${settings.EditNameD}", state: greyOut(settings.ScenarioNameD)
            href "pageSetupScenarioE", title:"${settings.ScenarioNameE}", description:"${settings.EditNameE}", state: greyOut(settings.ScenarioNameE)
            href "pageSetupScenarioF", title:"${settings.ScenarioNameF}", description:"${settings.EditNameF}", state: greyOut(settings.ScenarioNameF)
            href "pageSetupScenarioG", title:"${settings.ScenarioNameG}", description:"${settings.EditNameG}", state: greyOut(settings.ScenarioNameG)
            href "pageSetupScenarioH", title:"${settings.ScenarioNameH}", description:"${settings.EditNameH}", state: greyOut(settings.ScenarioNameH)
            href "pageSetupScenarioI", title:"${settings.ScenarioNameI}", description:"${settings.EditNameI}", state: greyOut(settings.ScenarioNameI)
            href "pageSetupScenarioJ", title:"${settings.ScenarioNameJ}", description:"${settings.EditNameJ}", state: greyOut(settings.ScenarioNameJ)
            href "pageSetupScenarioK", title:"${settings.ScenarioNameK}", description:"${settings.EditNameK}", state: greyOut(settings.ScenarioNameK)
            href "pageSetupScenarioL", title:"${settings.ScenarioNameL}", description:"${settings.EditNameL}", state: greyOut(settings.ScenarioNameL)
            href "pageSetupScenarioM", title:"${settings.ScenarioNameM}", description:"${settings.EditNameM}", state: greyOut(settings.ScenarioNameM)
            href "pageSetupScenarioN", title:"${settings.ScenarioNameN}", description:"${settings.EditNameN}", state: greyOut(settings.ScenarioNameN)
            href "pageSetupScenarioO", title:"${settings.ScenarioNameO}", description:"${settings.EditNameO}", state: greyOut(settings.ScenarioNameO)
            }
        section([title:"Options", mobileOnly:true]) {
            label title:"Assign a name", required:false
        }
    }
}

// Show "pageSetupScenarioA" page
def pageSetupScenarioA() {

    def inputLightsA = [
        name:       "A_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersA = [
        name:       "A_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionA = [
        name:       "A_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactA = [
        name:       "A_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeA = [
        name:       "A_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelA = [
        name:       "A_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOnLuxA = [
        name:       "A_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsA = [
        name:       "A_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffA = [
        name:       "A_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameA = [
        name:       "ScenarioNameA",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def pageName = ""
    if (settings.ScenarioNameA) {
        	pageName = settings.ScenarioNameA
   		}
    def pageProperties = [
        name:       "pageSetupScenarioA",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
section("Name your scene") {
            input inputScenarioNameA
        }

section("Devices included in the scene") {
            input inputMotionA
            input inputContactA
            input inputLightsA
            input inputDimmersA
            }

section("Scene settings") {
            input inputLevelA
            input inputTurnOnLuxA
            input inputLuxSensorsA
            input inputTurnOffA
            input inputModeA
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def pageSetupScenarioB() {

    def inputLightsB = [
        name:       "B_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersB = [
        name:       "B_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]
    
    def inputTurnOnLuxB = [
        name:       "B_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsB = [
        name:       "B_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]

    def inputMotionB = [
        name:       "B_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactB = [
        name:       "B_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeB = [
        name:       "B_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelB = [
        name:       "B_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffB = [
        name:       "B_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameB = [
        name:       "ScenarioNameB",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def pageName = ""
    if (settings.ScenarioNameB) {
        	pageName = settings.ScenarioNameB
   		}
    def pageProperties = [
        name:       "pageSetupScenarioB",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
section("Name your scene") {
            input inputScenarioNameB
        }

section("Devices included in the scene") {
            input inputMotionB
			input inputContactB
            input inputLightsB
            input inputDimmersB
            }

section("Scene settings") {
            input inputLevelB
            input inputTurnOnLuxB
            input inputLuxSensorsB
            input inputTurnOffB
            input inputModeB
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def pageSetupScenarioC() {

    def inputLightsC = [
        name:       "C_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersC = [
        name:       "C_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionC = [
        name:       "C_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactC = [
        name:       "C_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeC = [
        name:       "C_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelC = [
        name:       "C_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffC = [
        name:       "C_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameC = [
        name:       "ScenarioNameC",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def inputTurnOnLuxC = [
        name:       "C_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsC = [
        name:       "C_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
    
    def pageName = ""
    if (settings.ScenarioNameC) {
        	pageName = settings.ScenarioNameC
   		}
    def pageProperties = [
        name:       "pageSetupScenarioC",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
        section("Name your scene") {
            input inputScenarioNameC
        }

section("Devices included in the scene") {
            input inputMotionC
            input inputContactC
            input inputLightsC
            input inputDimmersC
            }

section("Scene settings") {
            input inputLevelC
            input inputTurnOnLuxC
            input inputLuxSensorsC
            input inputTurnOffC
            input inputModeC
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def pageSetupScenarioD() {

    def inputLightsD = [
        name:       "D_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersD = [
        name:       "D_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionD = [
        name:       "D_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactD = [
        name:       "D_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeD = [
        name:       "D_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelD = [
        name:       "D_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffD = [
        name:       "D_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameD = [
        name:       "ScenarioNameD",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def inputTurnOnLuxD = [
        name:       "D_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsD = [
        name:       "D_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
    
    def pageName = ""
    if (settings.ScenarioNameD) {
        	pageName = settings.ScenarioNameD
   		}
    def pageProperties = [
        name:       "pageSetupScenarioD",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
        section("Name your scene") {
            input inputScenarioNameD
        }

section("Devices included in the scene") {
            input inputMotionD
          	input inputContactD
            input inputLightsD
            input inputDimmersD
            }

section("Scene settings") {
            input inputLevelD
            input inputTurnOnLuxD
            input inputLuxSensorsD
            input inputTurnOffD
            input inputModeD
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def pageSetupScenarioE() {

    def inputLightsE = [
        name:       "E_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersE = [
        name:       "E_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionE = [
        name:       "E_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactE = [
        name:       "E_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeE = [
        name:       "E_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelE = [
        name:       "E_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffE = [
        name:       "E_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameE = [
        name:       "ScenarioNameE",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def inputTurnOnLuxE = [
        name:       "E_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsE = [
        name:       "E_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
    
    def pageName = ""
    if (settings.ScenarioNameE) {
        	pageName = settings.ScenarioNameE
   		}
    def pageProperties = [
        name:       "pageSetupScenarioE",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
        section("Name your scene") {
            input inputScenarioNameE
        }

section("Devices included in the scene") {
            input inputMotionE
            input inputContactE
            input inputLightsE
            input inputDimmersE
            }

section("Scene settings") {
            input inputLevelE
            input inputTurnOnLuxE
            input inputLuxSensorsE
            input inputTurnOffE
            input inputModeE
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def pageSetupScenarioF() {

    def inputLightsF = [
        name:       "F_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersF = [
        name:       "F_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionF = [
        name:       "F_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactF = [
        name:       "F_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeF = [
        name:       "F_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelF = [
        name:       "F_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffF = [
        name:       "F_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameF = [
        name:       "ScenarioNameF",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def inputTurnOnLuxF = [
        name:       "F_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsF = [
        name:       "F_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
    
    def pageName = ""
    if (settings.ScenarioNameF) {
        	pageName = settings.ScenarioNameF
   		}
    def pageProperties = [
        name:       "pageSetupScenarioF",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
        section("Name your scene") {
            input inputScenarioNameF
        }

section("Devices included in the scene") {
            input inputMotionF
            input inputContactF
            input inputLightsF
            input inputDimmersF
            }

section("Scene settings") {
            input inputLevelF
            input inputTurnOnLuxF
            input inputLuxSensorsF
            input inputTurnOffF
            input inputModeF
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def pageSetupScenarioG() {

    def inputLightsG = [
        name:       "G_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersG = [
        name:       "G_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionG = [
        name:       "G_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactG = [
        name:       "G_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeG = [
        name:       "G_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelG = [
        name:       "G_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffG = [
        name:       "G_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameG = [
        name:       "ScenarioNameG",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def inputTurnOnLuxG = [
        name:       "G_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsG = [
        name:       "G_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
    
    def pageName = ""
    if (settings.ScenarioNameG) {
        	pageName = settings.ScenarioNameG
   		}
    def pageProperties = [
        name:       "pageSetupScenarioG",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
        section("Name your scene") {
            input inputScenarioNameG
        }

section("Devices included in the scene") {
            input inputMotionG
            input inputContactG
            input inputLightsG
            input inputDimmersG
            }

section("Scene settings") {
            input inputLevelG
            input inputTurnOnLuxG
            input inputLuxSensorsG
            input inputTurnOffG
            input inputModeG
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def pageSetupScenarioH() {

    def inputLightsH = [
        name:       "H_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersH = [
        name:       "H_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionH = [
        name:       "H_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactH = [
        name:       "H_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeH = [
        name:       "H_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelH = [
        name:       "H_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffH = [
        name:       "H_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameH = [
        name:       "ScenarioNameH",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def inputTurnOnLuxH = [
        name:       "H_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsH = [
        name:       "H_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
    
    def pageName = ""
    if (settings.ScenarioNameH) {
        	pageName = settings.ScenarioNameH
   		}
    def pageProperties = [
        name:       "pageSetupScenarioH",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
        section("Name your scene") {
            input inputScenarioNameH
        }

section("Devices included in the scene") {
            input inputMotionH
            input inputContactH
            input inputLightsH
            input inputDimmersH
            }

section("Scene settings") {
            input inputLevelH
            input inputTurnOnLuxH
            input inputLuxSensorsH
            input inputTurnOffH
            input inputModeH
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def pageSetupScenarioI() {

    def inputLightsI = [
        name:       "I_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersI = [
        name:       "I_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionI = [
        name:       "I_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactI = [
        name:       "I_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeI = [
        name:       "I_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelI = [
        name:       "I_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffI = [
        name:       "I_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameI = [
        name:       "ScenarioNameI",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def inputTurnOnLuxI = [
        name:       "I_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsI = [
        name:       "I_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
    
    def pageName = ""
    if (settings.ScenarioNameI) {
        	pageName = settings.ScenarioNameI
   		}
    def pageProperties = [
        name:       "pageSetupScenarioI",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
        section("Name your scene") {
            input inputScenarioNameI
        }

section("Devices included in the scene") {
            input inputMotionI
            input inputContactI
            input inputLightsI
            input inputDimmersI
            }

section("Scene settings") {
            input inputLevelI
            input inputTurnOnLuxI
            input inputLuxSensorsI
            input inputTurnOffI
            input inputModeI
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def pageSetupScenarioJ() {

    def inputLightsJ = [
        name:       "J_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersJ = [
        name:       "J_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionJ = [
        name:       "J_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactJ = [
        name:       "J_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeJ = [
        name:       "J_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelJ = [
        name:       "J_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffJ = [
        name:       "J_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameJ = [
        name:       "ScenarioNameJ",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def inputTurnOnLuxJ = [
        name:       "J_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsJ = [
        name:       "J_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
    
    def pageName = ""
    if (settings.ScenarioNameJ) {
        	pageName = settings.ScenarioNameJ
   		}
    def pageProperties = [
        name:       "pageSetupScenarioJ",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
        section("Name your scene") {
            input inputScenarioNameJ
        }

section("Devices included in the scene") {
            input inputMotionJ
            input inputContactJ
            input inputLightsJ
            input inputDimmersJ
            }

section("Scene settings") {
            input inputLevelJ
            input inputTurnOnLuxJ
            input inputLuxSensorsJ
            input inputTurnOffJ
            input inputModeJ
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def pageSetupScenarioK() {

    def inputLightsK = [
        name:       "K_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersK = [
        name:       "K_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionK = [
        name:       "K_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactK = [
        name:       "K_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeK = [
        name:       "K_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelK = [
        name:       "K_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffK = [
        name:       "K_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameK = [
        name:       "ScenarioNameK",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def inputTurnOnLuxK = [
        name:       "K_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsK = [
        name:       "K_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
    
    def pageName = ""
    if (settings.ScenarioNameK) {
        	pageName = settings.ScenarioNameK
   		}
    def pageProperties = [
        name:       "pageSetupScenarioK",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
        section("Name your scene") {
            input inputScenarioNameK
        }

section("Devices included in the scene") {
            input inputMotionK
            input inputContactK
            input inputLightsK
            input inputDimmersK
            }

section("Scene settings") {
            input inputLevelK
            input inputTurnOnLuxK
            input inputLuxSensorsK
            input inputTurnOffK
            input inputModeK
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def pageSetupScenarioL() {

    def inputLightsL = [
        name:       "L_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersL = [
        name:       "L_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionL = [
        name:       "L_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactL = [
        name:       "L_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeL = [
        name:       "L_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelL = [
        name:       "L_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffL = [
        name:       "L_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameL = [
        name:       "ScenarioNameL",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def inputTurnOnLuxL = [
        name:       "L_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsL = [
        name:       "L_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
    
    def pageName = ""
    if (settings.ScenarioNameL) {
        	pageName = settings.ScenarioNameL
   		}
    def pageProperties = [
        name:       "pageSetupScenarioL",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
        section("Name your scene") {
            input inputScenarioNameL
        }

section("Devices included in the scene") {
            input inputMotionL
            input inputContactL
            input inputLightsL
            input inputDimmersL
            }

section("Scene settings") {
            input inputLevelL
            input inputTurnOnLuxL
            input inputLuxSensorsL
            input inputTurnOffL
            input inputModeL
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def pageSetupScenarioM() {

    def inputLightsM = [
        name:       "M_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersM = [
        name:       "M_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionM = [
        name:       "M_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactM = [
        name:       "M_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeM = [
        name:       "M_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelM = [
        name:       "M_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffM = [
        name:       "M_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameM = [
        name:       "ScenarioNameM",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def inputTurnOnLuxM = [
        name:       "M_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsM = [
        name:       "M_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
    
    def pageName = ""
    if (settings.ScenarioNameM) {
        	pageName = settings.ScenarioNameM
   		}
    def pageProperties = [
        name:       "pageSetupScenarioM",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
        section("Name your scene") {
            input inputScenarioNameM
        }

section("Devices included in the scene") {
            input inputMotionM
            input inputContactM
            input inputLightsM
            input inputDimmersM
            }

section("Scene settings") {
            input inputLevelM
            input inputTurnOnLuxM
            input inputLuxSensorsM
            input inputTurnOffM
            input inputModeM
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def pageSetupScenarioN() {

    def inputLightsN = [
        name:       "N_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersN = [
        name:       "N_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionN = [
        name:       "N_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactN = [
        name:       "N_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeN = [
        name:       "N_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelN = [
        name:       "N_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffN = [
        name:       "N_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameN = [
        name:       "ScenarioNameN",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def inputTurnOnLuxN = [
        name:       "N_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsN = [
        name:       "N_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
 	
    def pageName =""
    if (settings.ScenarioNameN) {
        	pageName = settings.ScenarioNameN
   		}
    def pageProperties = [
        name:       "pageSetupScenarioN",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
        section("Name your scene") {
            input inputScenarioNameN
        }

section("Devices included in the scene") {
            input inputMotionN
            input inputContactN
            input inputLightsN
            input inputDimmersN
            }

section("Scene settings") {
            input inputLevelN
            input inputTurnOnLuxN
            input inputLuxSensorsN
            input inputTurnOffN
            input inputModeN
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def pageSetupScenarioO() {

    def inputLightsO = [
        name:       "O_switches",
        type:       "capability.switch",
        title:      "Control the following switches...",
        multiple:   true,
        required:   false
    ]
    def inputDimmersO = [
        name:       "O_dimmers",
        type:       "capability.switchLevel",
        title:      "Dim the following...",
        multiple:   true,
        required:   false
    ]

    def inputMotionO = [
        name:       "O_motion",
        type:       "capability.motionSensor",
        title:      "Using these motion sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputContactO = [
        name:       "O_contact",
        type:       "capability.contactSensor",
        title:      "Or using these contact sensors...",
        multiple:   true,
        required:   false
    ]
    
    def inputModeO = [
        name:       "O_mode",
        type:       "mode",
        title:      "Only during the following modes...",
        multiple:   true,
        required:   false
    ]
    
    def inputLevelO = [
        name:       "O_level",
        type:       "enum",
        options: [[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[100:"100%"]],
        title:      "Set dimmers to this level",
        multiple:   false,
        required:   false
    ]
    
    def inputTurnOffO = [
        name:       "O_turnOff",
        type:       "number",
        title:      "Turn off this scene after motion stops or doors close (minutes)...",
        multiple:   false,
        required:   false
    ]
    
    def inputScenarioNameO = [
        name:       "ScenarioNameO",
        type:       "text",
        title:      "Scenario Name",
        multiple:   false,
        required:   false,
        defaultValue: empty
    ]
    
    def inputTurnOnLuxO = [
        name:       "O_turnOnLux",
        type:       "number",
        title:      "Only run this scene if lux is below...",
        multiple:   false,
        required:   false
    ]
    
    def inputLuxSensorsO = [
        name:       "O_luxSensors",
        type:       "capability.illuminanceMeasurement",
        title:      "On these lux sensors",
        multiple:   false,
        required:   false
    ]
    
    def pageName = ""
    if (settings.ScenarioNameO) {
        	pageName = settings.ScenarioNameO
   		}
    def pageProperties = [
        name:       "pageSetupScenarioO",
        title:      "${pageName}",
        nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {
        section("Name your scene") {
            input inputScenarioNameO
        }

section("Devices included in the scene") {
            input inputMotionO
            input inputContactO
            input inputLightsO
            input inputDimmersO
            }

section("Scene settings") {
            input inputLevelO
            input inputTurnOnLuxO
            input inputLuxSensorsO
            input inputTurnOffO
            input inputModeO
            }

section("Help") {
            paragraph helpText()
            }
    }
}

def installed() {

    initialize()
}

def updated() {

    unschedule()
    unsubscribe()
    initialize()
}

def initialize() {
if(settings.A_motion) {
	subscribe(settings.A_motion, "motion", onEventA)
}

if(settings.A_contact) {
	subscribe(settings.A_contact, "contact", onEventA)
}

if(settings.B_motion) {
	subscribe(settings.B_motion, "motion", onEventB)
}

if(settings.B_contact) {
	subscribe(settings.B_contact, "contact", onEventB)
}

if(settings.C_motion) {
	subscribe(settings.C_motion, "motion", onEventC)
}

if(settings.C_contact) {
	subscribe(settings.C_contact, "contact", onEventC)
}

if(settings.D_motion) {
	subscribe(settings.D_motion, "motion", onEventD)
}

if(settings.D_contact) {
	subscribe(settings.D_contact, "contact", onEventD)
}

if(settings.E_motion) {
	subscribe(settings.E_motion, "motion", onEventE)
}

if(settings.E_contact) {
	subscribe(settings.E_contact, "contact", onEventE)
}

if(settings.F_motion) {
	subscribe(settings.F_motion, "motion", onEventF)
}

if(settings.F_contact) {
	subscribe(settings.F_contact, "contact", onEventF)
}

if(settings.G_motion) {
	subscribe(settings.G_motion, "motion", onEventG)
}

if(settings.G_contact) {
	subscribe(settings.G_contact, "contact", onEventG)
}

if(settings.H_motion) {
	subscribe(settings.H_motion, "motion", onEventH)
}

if(settings.H_contact) {
	subscribe(settings.H_contact, "contact", onEventH)
}

if(settings.I_motion) {
	subscribe(settings.I_motion, "motion", onEventI)
}

if(settings.I_contact) {
	subscribe(settings.I_contact, "contact", onEventI)
}

if(settings.J_motion) {
	subscribe(settings.J_motion, "motion", onEventJ)
}

if(settings.J_contact) {
	subscribe(settings.J_contact, "contact", onEventJ)
}

if(settings.K_motion) {
	subscribe(settings.K_motion, "motion", onEventK)
}

if(settings.K_contact) {
	subscribe(settings.K_contact, "contact", onEventK)
}

if(settings.L_motion) {
	subscribe(settings.L_motion, "motion", onEventL)
}

if(settings.L_contact) {
	subscribe(settings.L_contact, "contact", onEventL)
}

if(settings.M_motion) {
	subscribe(settings.M_motion, "motion", onEventM)
}

if(settings.M_contact) {
	subscribe(settings.M_contact, "contact", onEventM)
}

if(settings.N_motion) {
	subscribe(settings.N_motion, "motion", onEventN)
}

if(settings.N_contact) {
	subscribe(settings.N_contact, "contact", onEventN)
}

if(settings.O_motion) {
	subscribe(settings.O_motion, "motion", onEventO)
}

if(settings.O_contact) {
	subscribe(settings.O_contact, "contact", onEventO)
}

}

def onEventA(evt) {

if ((settings.A_mode == null) || (settings.A_mode.contains(location.mode))){
if ((settings.A_luxSensors == null) || (settings.A_luxSensors.latestValue("illuminance") <= A_turnOnLux)){
def A_levelOn = settings.A_level as Integer
def delayA = settings.A_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.A_motion) {
	if (settings.A_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.A_contact) {
	if (settings.A_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameA}'")
		settings.A_dimmers?.setLevel(A_levelOn)
		settings.A_switches?.on()
        unschedule(delayTurnOffA)
}
else {
    	if (settings.A_turnOff) {
		runIn(delayA, "delayTurnOffA")
        }
        
        else {
        settings.A_switches?.off()
		settings.A_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion or Contact open detected outside of mode restriction.  Not running mode.")
}
}

def delayTurnOffA(){
	settings.A_switches?.off()
	settings.A_dimmers?.setLevel(0)
}

def onEventB(evt) {

if ((settings.B_mode == null) || (settings.B_mode.contains(location.mode))){
if ((settings.B_luxSensors == null) || (settings.B_luxSensors.latestValue("illuminance") <= B_turnOnLux)){
def B_levelOn = settings.B_level as Integer
def delayB = settings.B_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.B_motion) {
	if (settings.B_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.B_contact) {
	if (settings.B_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameB}'")
		settings.B_dimmers?.setLevel(B_levelOn)
		settings.B_switches?.on()
        unschedule(delayTurnOffB)
}
else {
    	if (settings.B_turnOff) {
		runIn(delayB, "delayTurnOffB")
        }
        
        else {
        settings.B_switches?.off()
		settings.B_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion or Contact open detected outside of mode restriction.  Not running mode.")
}
}

def delayTurnOffB(){
	settings.B_switches?.off()
	settings.B_dimmers?.setLevel(0)
}


def onEventC(evt) {


if ((settings.C_mode == null) || (settings.C_mode.contains(location.mode))){
if ((settings.C_luxSensors == null) || (settings.C_luxSensors.latestValue("illuminance") <= C_turnOnLux)){
def C_levelOn = settings.C_level as Integer
def delayC = settings.C_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.C_motion) {
	if (settings.C_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.C_contact) {
	if (settings.C_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameC}'")
		settings.C_dimmers?.setLevel(C_levelOn)
		settings.C_switches?.on()
        unschedule(delayTurnOffC)
}
else {
    	if (settings.C_turnOff) {
		runIn(delayC, "delayTurnOffC")
        }
        
        else {
        settings.C_switches?.off()
		settings.C_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion or Contact open detected outside of mode restriction.  Not running mode.")
}
}

def delayTurnOffC(){
	settings.C_switches?.off()
	settings.C_dimmers?.setLevel(0)
}


def onEventD(evt) {

if ((settings.D_mode == null) || (settings.D_mode.contains(location.mode))){
if ((settings.D_luxSensors == null) || (settings.D_luxSensors.latestValue("illuminance") <= D_turnOnLux)){
def D_levelOn = settings.D_level as Integer
def delayD = settings.D_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.D_motion) {
	if (settings.D_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.D_contact) {
	if (settings.D_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameD}'")
		settings.D_dimmers?.setLevel(D_levelOn)
		settings.D_switches?.on()
        unschedule(delayTurnOffD)
}
else {
    	if (settings.D_turnOff) {
		runIn(delayD, "delayTurnOffD")
        }
        
        else {
        settings.D_switches?.off()
		settings.D_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion or Contact open detected outside of mode restriction.  Not running mode.")
}
}

def delayTurnOffD(){
	settings.D_switches?.off()
	settings.D_dimmers?.setLevel(0)
}


def onEventE(evt) {

if ((settings.E_mode == null) || (settings.E_mode.contains(location.mode))){
if ((settings.E_luxSensors == null) || (settings.E_luxSensors.latestValue("illuminance") <= E_turnOnLux)){
def E_levelOn = settings.E_level as Integer
def delayE = settings.E_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.E_motion) {
	if (settings.E_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.E_contact) {
	if (settings.E_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameE}'")
		settings.E_dimmers?.setLevel(E_levelOn)
		settings.E_switches?.on()
        unschedule(delayTurnOffE)
}
else {
    	if (settings.E_turnOff) {
		runIn(delayE, "delayTurnOffE")
        }
        
        else {
        settings.E_switches?.off()
		settings.E_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion or Contact open detected outside of mode restriction.  Not running mode.")
}
}

def delayTurnOffE(){
	settings.E_switches?.off()
	settings.E_dimmers?.setLevel(0)
}

def onEventF(evt) {

if ((settings.F_mode == null) || (settings.F_mode.contains(location.mode))){
if ((settings.F_luxSensors == null) || (settings.F_luxSensors.latestValue("illuminance") <= F_turnOnLux)){
def F_levelOn = settings.F_level as Integer
def delayF = settings.F_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.F_motion) {
	if (settings.F_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.F_contact) {
	if (settings.F_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameF}'")
		settings.F_dimmers?.setLevel(F_levelOn)
		settings.F_switches?.on()
        unschedule(delayTurnOffF)
}
else {
    	if (settings.F_turnOff) {
		runIn(delayF, "delayTurnOffF")
        }
        
        else {
        settings.F_switches?.off()
		settings.F_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion or Contact open detected outside of mode restriction.  Not running mode.")
}
}

def delayTurnOffF(){
	settings.F_switches?.off()
	settings.F_dimmers?.setLevel(0)
}


def onEventG(evt) {

if ((settings.G_mode == null) || (settings.G_mode.contains(location.mode))){
if ((settings.G_luxSensors == null) || (settings.G_luxSensors.latestValue("illuminance") <= G_turnOnLux)){
def G_levelOn = settings.G_level as Integer
def delayG = settings.G_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.G_motion) {
	if (settings.G_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.G_contact) {
	if (settings.G_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameG}'")
		settings.G_dimmers?.setLevel(G_levelOn)
		settings.G_switches?.on()
        unschedule(delayTurnOffG)
}
else {
    	if (settings.G_turnOff) {
		runIn(delayG, "delayTurnOffG")
        }
        
        else {
        settings.G_switches?.off()
		settings.G_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion or Contact open detected outside of mode restriction.  Not running mode.")
}
}

def delayTurnOffG(){
	settings.G_switches?.off()
	settings.G_dimmers?.setLevel(0)
}

def onEventH(evt) {

if ((settings.H_mode == null) || (settings.H_mode.contains(location.mode))){
if ((settings.H_luxSensors == null) || (settings.H_luxSensors.latestValue("illuminance") <= H_turnOnLux)){
def H_levelOn = settings.H_level as Integer
def delayH = settings.H_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.H_motion) {
	if (settings.H_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.H_contact) {
	if (settings.H_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameH}'")
		settings.H_dimmers?.setLevel(H_levelOn)
		settings.H_switches?.on()
        unschedule(delayTurnOffH)
}
else {
    	if (settings.H_turnOff) {
		runIn(delayH, "delayTurnOffH")
        }
        
        else {
        settings.H_switches?.off()
		settings.H_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion or Contact open detected outside of mode restriction.  Not running mode.")
}
}

def delayTurnOffH(){
	settings.H_switches?.off()
	settings.H_dimmers?.setLevel(0)
}

def onEventI(evt) {

if ((settings.I_mode == null) || (settings.I_mode.contains(location.mode))){
if ((settings.I_luxSensors == null) || (settings.I_luxSensors.latestValue("illuminance") <= I_turnOnLux)){
def I_levelOn = settings.I_level as Integer
def delayI = settings.I_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.I_motion) {
	if (settings.I_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.I_contact) {
	if (settings.I_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameI}'")
		settings.I_dimmers?.setLevel(I_levelOn)
		settings.I_switches?.on()
        unschedule(delayTurnOffI)
}
else {
    	if (settings.I_turnOff) {
		runIn(delayI, "delayTurnOffI")
        }
        
        else {
        settings.I_switches?.off()
		settings.I_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion or Contact open detected outside of mode restriction.  Not running mode.")
}
}

def delayTurnOffI(){
	settings.I_switches?.off()
	settings.I_dimmers?.setLevel(0)
}

def onEventJ(evt) {

if ((settings.J_mode == null) || (settings.J_mode.contains(location.mode))){
if ((settings.J_luxSensors == null) || (settings.J_luxSensors.latestValue("illuminance") <= J_turnOnLux)){
def J_levelOn = settings.J_level as Integer
def delayJ = settings.J_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.J_motion) {
	if (settings.J_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.J_contact) {
	if (settings.J_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameJ}'")
		settings.J_dimmers?.setLevel(J_levelOn)
		settings.J_switches?.on()
        unschedule(delayTurnOffJ)
}
else {
    	if (settings.J_turnOff) {
		runIn(delayJ, "delayTurnOffJ")
        }
        
        else {
        settings.J_switches?.off()
		settings.J_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion or Contact open detected outside of mode restriction.  Not running mode.")
}
}

def delayTurnOffJ(){
	settings.J_switches?.off()
	settings.J_dimmers?.setLevel(0)
}

def onEventK(evt) {

if ((settings.K_mode == null) || (settings.K_mode.contains(location.mode))){
if ((settings.K_luxSensors == null) || (settings.K_luxSensors.latestValue("illuminance") <= K_turnOnLux)){
def K_levelOn = settings.K_level as Integer
def delayK = settings.K_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.K_motion) {
	if (settings.O_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.K_contact) {
	if (settings.K_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameK}'")
		settings.K_dimmers?.setLevel(K_levelOn)
		settings.K_switches?.on()
        unschedule(delayTurnOffK)
}
else {
    	if (settings.K_turnOff) {
		runIn(delayK, "delayTurnOffK")
        }
        
        else {
        settings.K_switches?.off()
		settings.K_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion or Contact open detected outside of mode restriction.  Not running mode.")
}
}

def delayTurnOffK(){
	settings.K_switches?.off()
	settings.K_dimmers?.setLevel(0)
}

def onEventL(evt) {

if ((settings.L_mode == null) || (settings.L_mode.contains(location.mode))){
if ((settings.L_luxSensors == null) || (settings.L_luxSensors.latestValue("illuminance") <= L_turnOnLux)){
def L_levelOn = settings.L_level as Integer
def delayL = settings.L_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.L_motion) {
	if (settings.O_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.L_contact) {
	if (settings.L_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameL}'")
		settings.L_dimmers?.setLevel(L_levelOn)
		settings.L_switches?.on()
        unschedule(delayTurnOffL)
}
else {
    	if (settings.L_turnOff) {
		runIn(delayL, "delayTurnOffL")
        }
        
        else {
        settings.L_switches?.off()
		settings.L_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion or Contact open detected outside of mode restriction.  Not running mode.")
}
}

def delayTurnOffL(){
	settings.L_switches?.off()
	settings.L_dimmers?.setLevel(0)
}

def onEventM(evt) {

if ((settings.M_mode == null) || (settings.M_mode.contains(location.mode))){
if ((settings.M_luxSensors == null) || (settings.M_luxSensors.latestValue("illuminance") <= M_turnOnLux)){
def M_levelOn = settings.M_level as Integer
def delayM = settings.M_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.M_motion) {
	if (settings.M_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.M_contact) {
	if (settings.M_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameM}'")
		settings.M_dimmers?.setLevel(M_levelOn)
		settings.M_switches?.on()
        unschedule(delayTurnOffM)
}
else {
    	if (settings.M_turnOff) {
		runIn(delayM, "delayTurnOffM")
        }
        
        else {
        settings.M_switches?.off()
		settings.M_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion detected outside of mode restriction.  Not running mode.")
}
}

def delayTurnOffM(){
	settings.M_switches?.off()
	settings.M_dimmers?.setLevel(0)
}

def onEventN(evt) {

if ((settings.N_mode == null) || (settings.N_mode.contains(location.mode))){
if ((settings.N_luxSensors == null) || (settings.N_luxSensors.latestValue("illuminance") <= N_turnOnLux)){
def N_levelOn = settings.N_level as Integer
def delayN = settings.N_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.N_motion) {
	if (settings.N_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.N_contact) {
	if (settings.N_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameN}'")
		settings.N_dimmers?.setLevel(N_levelOn)
		settings.N_switches?.on()
        unschedule(delayTurnOffN)
}
else {
    	if (settings.N_turnOff) {
		runIn(delayN, "delayTurnOffN")
        }
        
        else {
        settings.N_switches?.off()
		settings.N_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion or Contact open detected outside of mode restriction.  Not running mode.")
}
}

def delayTurnOffN(){
	settings.N_switches?.off()
	settings.N_dimmers?.setLevel(0)
}


def onEventO(evt) {

if ((settings.O_mode == null) || (settings.O_mode.contains(location.mode))){
if ((settings.O_luxSensors == null) || (settings.O_luxSensors.latestValue("illuminance") <= O_turnOnLux)){
def O_levelOn = settings.O_level as Integer
def delayO = settings.O_turnOff * 60
def motionDetected = false
def contactDetected = false

if (settings.O_motion) {
	if (settings.O_motion.latestValue("motion").contains("active")) {
		motionDetected = true
	}
}

if (settings.O_contact) {
	if (settings.O_contact.latestValue("contact").contains("open")) {
		contactDetected = true
	}
}

if (motionDetected || contactDetected) {
		log.debug("Motion or Open Contact Detected Running '${settings.ScenarioNameO}'")
		settings.O_dimmers?.setLevel(O_levelOn)
		settings.O_switches?.on()
        unschedule(delayTurnOffO)
}
else {
    	if (settings.O_turnOff) {
		runIn(delayO, "delayTurnOffO")
        }
        
        else {
        settings.O_switches?.off()
		settings.O_dimmers?.setLevel(0)
        }
	
}
}
}
else{
log.debug("Motion or Contact open detected outside of mode restriction.  Not running mode.")
}
}


def delayTurnOffO(){
	settings.O_switches?.off()
	settings.O_dimmers?.setLevel(0)
}

private def helpText() {
	def text =
    	"Select a motion sensor or contact sensors to control a set of lights. " +
        "Each scenario can control dimmers and switches but can " +
        "also be restricted to mode and turned off after motion stops or doors close."
	text
}

def greyOut(title){
	def result = "complete"
    if (title == "Empty") {
    	result = ""
    }
    result
}
