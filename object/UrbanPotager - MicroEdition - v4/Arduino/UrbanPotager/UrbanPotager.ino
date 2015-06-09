/***************************************************************
------- Urban Potager - Micro Edition v4 ------- 
Author : Julien Morin (UrbanaPlant)
Licence : CC-BY-SA-NC
Tutorial : www.urbanpotager.com
Last Update : 2015-04-30
Last Update By : Tatiana
***************************************************************/

//////////////////////////////////////////////////
//                  Includes                    //
//////////////////////////////////////////////////
/***** Own Lib *****/
#include "chardef.h"

/***** Downloaded Lib *****/
#include <LiquidCrystal_I2C.h>
#include <SPI.h>
#include <RTClib.h>
#include "DHT.h"

/***** Arduino's Lib *****/
#include <Wire.h>

bool isShield 	= false;


//////////////////////////////////////////////////////
//                  Declarations                    //
//////////////////////////////////////////////////////

/***** You can adjuste the pins if you want *****/
int tempPin		= isShield ? 13 : 7;
int lightPin	= isShield ? 0 : 1;
int pinLight	= isShield ? 10 : 8;
int pinPump		= isShield ? 9 : 9;

/***** You can adjuste this values if you want *****/
unsigned long 	delaySensorReading	= 5;		// Sensor will be check each delaySensorReading seconds
unsigned long 	durationWatering	= 20;
unsigned long 	delaySendUpdate		= 30;
unsigned long 	delayWatering		= 1200 ;	// 1200 = every 20 mn (in seconds)
boolean			canUseLight			= false;
boolean			canUsePump			= false;
int 			startLightHour		= 8;		// Start light at <hour> (8 => led on at 7:00AM)
int 			endLightHour		= 21;		// Heure de FIN lumière (22 => led of 09:00PM)
int 			startLightMin		= 0;		// Start light at <hour> (8 => led on at 7:00AM)
int 			endLightMin			= 0;		// Heure de FIN lumière (22 => led of 09:00PM)
int 			lightMinValue		= 90;

/*****                    Screen                      *****/
/***** GND -> GND / VCC -> 5V / SDA -> A4 / SCL -> A5 *****/
LiquidCrystal_I2C lcd(0x27,16,2);
byte celcius[8]		= CELCIUS_ARRAY; 
byte light[8]		= LIGHT_ARRAY;
byte humidity[8]	= HUMIDITY_ARRAY;
byte temp[8]		= TEMP_ARRAY;
byte water1[8]		= WATER_ARRAY;
byte water2[8]		= WATER2_ARRAY;

/***** Temperature *****/
#define DHTTYPE DHT11
DHT dht(tempPin, DHTTYPE);

/***** Time and hour *****/
RTC_DS1307 RTC;
#if defined(__SAM3X8E__)
	#undef __FlashStringHelper::F(string_literal)
	#define F(string_literal) string_literal
#endif

/***** Variables *****/
//Those values will be change in the loop
unsigned long 	lastWatering		= 0;
long 			longLightReading	= 0;
long 			longTempVal			= 0;
long 			longHumiVal			= 0;
unsigned long 	lastSensorReading	= 0;
unsigned long 	lastUpdate			= 0;
String 			detailledStatus1;	// Status ligne 1 (max 16 char)
String 			detailledStatus2;	// Status ligne 2 (max 16 char)
String 			lightVal;			//This is the value of Light sensor in String
String 			tempVal;			//This is the value of Temperature sensor in String
String 			humiVal;			//This is the value of Humidity sensor in String
long 			lightPercent		= 0;
unsigned long 	currentTime;
long 			time; 
String 			myDate;
String 			myTime;
String 			readString;
boolean			isManage 			= false;
boolean			isCleaning 			= false;
int 			nextWateringSeconds	= 0;


////////////////////////////////////////////////
//                 Base Code                  //
////////////////////////////////////////////////
void setup() {

	//Begin Serial
	Serial.begin(9600);

	//Setup Light
	pinMode(pinLight, OUTPUT); 
	digitalWrite(pinLight,HIGH); // Turn OFF (default)

	//Setup Pump
	pinMode(pinPump, OUTPUT); 
	digitalWrite(pinPump,HIGH); // Turn OFF = HIGH (default)

	//Setup LCD
	initLCD();

	//Setup RTC Module
	Wire.begin();
	RTC.begin();
	// WARNING! Un-comment the next line if the program is launch for the first time. It will init the RTC Module with the actual hour.
	//RTC.adjust(DateTime(__DATE__, __TIME__));
	if (! RTC.isrunning()) {
		// Serial.println("RTC is NOT running!");
		RTC.adjust(DateTime(__DATE__, __TIME__));
	}
}

void loop() {
	DateTime now = RTC.now();
	time = now.unixtime();

	// correct date if necessary
	myTime = (now.hour() < 10) ? "0" : "";
	myTime += String(now.hour())+":";
	myTime += (now.minute() < 10) ?"0" : "";
	myTime += String(now.minute());

	//Get Light value
	long backupLight = lightPercent;
	longLightReading = analogRead(lightPin);
	lightPercent = isShield ? map(longLightReading, 0, 1023, 100, 0) : map(longLightReading, 0, 1023, 0, 100);
	lightVal = doubleToString(lightPercent, 0);

	//If difference between the two values are more than 20
	//We have too inform phone
	if(abs(backupLight - lightPercent) > 12){
		Serial.println("slanotlig" + lightVal);
	}

	if (time > (lastSensorReading + delaySensorReading)) {
		checkSensorsValues();  
		lastSensorReading = time;
	} 

	if (time > (lastUpdate + delaySendUpdate)) {
		updateInformations();  
	} 

	while (Serial.available()) {
		delay(3);
		if (Serial.available() > 0) {
			char c = Serial.read();
			readString += c;
		} 
	}

	if(!isManage){
		manageDayLight();
		manageWatering();
		manageLCDDisplay();
	}else{
		lcd.setCursor(0, 0);
		lcd.print("Maintenance mode");

		lcd.setCursor(0, 1);
		lcd.print(isCleaning ? "Cleaning on     " : "Cleaning off    ");
	}

	if (readString.length() >0) {

		if (readString.substring(0,3) == "mas") {
			String command = readString.substring(3);
			String instruction = command.substring(0,3);
			String action = command.substring(3);
			if (instruction == "man"){
				if (action == "of"){
					lcd.clear();
					isManage = false;
				}
				else{
					isManage = true;
					digitalWrite(pinPump, HIGH);
					digitalWrite(pinLight, HIGH);
				}       
			}else if(instruction == "set"){
				String subCommand = action.substring(0,3);  
				String datas = action.substring(3);
				lcd.clear();
				lcd.setCursor(0,0);

				if(subCommand == "set"){
					endLightHour = datas.substring(0,2).toInt();
					endLightMin = datas.substring(3,5).toInt();
					lcd.print("Change Sunset to");
					lcd.setCursor(0,1);
					lcd.print("     " + datas + "      ");
				}else if(subCommand == "ris"){
					startLightHour = datas.substring(0,2).toInt();
					startLightMin = datas.substring(3,5).toInt();
					lcd.print("Change Sunrise");
					lcd.setCursor(0,1);
					lcd.print("to : " + datas + "      ");
				}else if(subCommand == "max"){
					lcd.print("Work in progress");
				}else if(subCommand == "min"){
					lcd.print("Work in progress");
				}else if(subCommand == "wat"){
					int slashIndex = datas.indexOf('/');
					delayWatering = datas.substring(0, slashIndex).toInt() * 60;
					durationWatering = datas.substring(slashIndex+1).toInt();
					String s = "Each ";
					s += delayWatering/60;
					s += " min";
					lcd.print(s);
					lcd.setCursor(0,1);
					s = "During ";
					s += durationWatering;
					s += " min";
					lcd.print(s);
				}
				delay(3000);
				lcd.clear();
			}else if (instruction == "upd"){
				updateInformations();
			}else if(isManage){
				String actionMain = command.substring(0,3);
				if (actionMain == "pum"){
					digitalWrite(pinPump, (action == "of") ? HIGH : LOW);
				}else if(actionMain == "lig"){
					digitalWrite(pinLight, (action == "of") ? HIGH : LOW);
				}else if(actionMain == "cle"){
					isCleaning = (action == "on");
					if(isCleaning){
						digitalWrite(pinPump, HIGH);
						digitalWrite(pinLight, HIGH);
					}
				}
			}
		}
		readString = "";
	}
	delay(1000);
	
}

///////////////////////////////////////////////
//                 Fuctions                  //
///////////////////////////////////////////////

/**
 *  This function is used to attribute the  
 *  correct data to each sensors variables.
 */
void checkSensorsValues() { 
	//Get Temperature and humidity values
	longTempVal = dht.readTemperature();
	longHumiVal = dht.readHumidity();

	// Values to String
	tempVal = doubleToString(longTempVal, 0);
	humiVal = doubleToString(longHumiVal, 0);  
}

/**
 *  This function is used to make led On if time
 *  is between startLight and endLight
 */
void manageDayLight() { 
	DateTime now = RTC.now();
	int myHour = now.hour(); 
	int myMin = now.minute();
	if ((myHour >= startLightHour ) && (myHour < endLightHour + 1)) { 
		if((myHour == startLightHour && myMin < startLightMin) || (myHour == endLightHour && myMin >= endLightMin)){
			digitalWrite(pinLight,HIGH); // Turn OFF = HIGH (default)
			canUseLight=false;
		}else{
			canUseLight = !(lightPercent>=90);
			digitalWrite(pinLight, canUseLight ? LOW : HIGH);
		}
		
	} else {
		digitalWrite(pinLight,HIGH); // Turn OFF = HIGH (default)
		canUseLight=false;
	}
}

void updateInformations(){
	Serial.print("slaupd/");
	Serial.print(tempVal);
	Serial.print("/");
	Serial.print(humiVal);
	Serial.print("/");
	Serial.print(lightVal);
	Serial.print("/");
	Serial.print(nextWateringSeconds);
	Serial.print("/");
	Serial.print(digitalRead(pinLight) == LOW ? "ON" : "OFF" );
	Serial.println("");
	lastUpdate = time;
}

/**
 *  This function is used to make 
 *  pump work each delayWatering
 */
void manageWatering() {  
	if (canUsePump==true) {
		if (time > (lastWatering + durationWatering)) {
			digitalWrite(pinPump,HIGH); // Turn OFF = HIGH (default)
			canUsePump=false;
			lastWatering = time;
		}
	} else {
		if (time > (lastWatering + delayWatering)) {
			digitalWrite(pinPump,LOW); // Turn ON
			lastWatering =time;
			canUsePump=true;
		}
	}
}
	
/**
 *  This function is used to show 
 *  all variables in the screen
 */
void manageLCDDisplay() {
	if (canUsePump == true) {
		lcd.setCursor(0, 0);
		lcd.print(" ");
		lcd.write(byte(5));  // Water
		lcd.print("  Watering  ");
		lcd.write(byte(5));  // Water
		lcd.print(" ");

		lcd.setCursor(0, 1);
		lcd.write(byte(5));  // Water
		lcd.print(" in progress  ");
		lcd.write(byte(5));  // Water 
	} else {
		//lcd.setCursor(0, 0);
		//lcd.print("===== "+myTime+" ====");
		lcd.setCursor(0, 0);
		lcd.print(myTime+"    (");
		lcd.write(byte(5));  // Water char
		nextWateringSeconds = (lastWatering+delayWatering) - time;
		int nextWatering = nextWateringSeconds;
		if (nextWatering >= 3600) {
			nextWatering = nextWatering/3600;
			lcd.print(String(nextWatering)+"h)    ");
		} else {
			if (nextWatering >= 60) {
				nextWatering = nextWatering/60;
				lcd.print(String(nextWatering)+"mn)        ");
			} else {
				lcd.print(String(nextWatering)+"s)         ");      
			}
		}
		lcd.setCursor(0, 1);
		lcd.write(byte(3));  // Temp char
		lcd.setCursor(1, 1);
		lcd.print(tempVal);
		lcd.setCursor(3, 1);
		lcd.write(byte(0));  // Celcius char
		lcd.setCursor(4, 1);
		lcd.print("  ");
		lcd.setCursor(6, 1);
		lcd.write(byte(2));  // Humidity char
		lcd.setCursor(7, 1);
		lcd.print(humiVal+"%  ");
		lcd.setCursor(12, 1);
		lcd.write(byte(1));  // Light char
		lcd.setCursor(13, 1);
		lcd.print(lightVal+"%");
	}
}

/**
 *  This function is used to init the LCD in setup function
 */
 void initLCD(){
	lcd.init();
	lcd.backlight();
	lcd.createChar(0, celcius);
	lcd.createChar(1, light);
	lcd.createChar(2, humidity);
	lcd.createChar(3, temp);
	lcd.createChar(4, water1);
	lcd.createChar(5, water2);
	lcd.home();
	lcd.setCursor(0, 0);
	lcd.print("  UrbanPotager  ");
	delay(1000);
	lcd.setCursor(0, 1);
	lcd.print("   ");
	lcd.write(byte(3));
	delay(1000);
	lcd.print("  ");
	lcd.write(byte(2));
	delay(1000);
	lcd.print("  ");
	lcd.write(byte(1));
	delay(1000);
	lcd.print("  ");
	lcd.write(byte(5));
	delay(3000) ;
	lcd.clear();
 }

////////////////////////////////////////////
//                 Tools                  //
//////////////////////////////////////////// 
String doubleToString(double input,int decimalPlaces)
{
	if(decimalPlaces!=0){
		String string = String((int)(input*pow(10,decimalPlaces)));
		if(abs(input)<1)
		{
			if(input>0)
				string = "0"+string;
			else if(input<0)
				string = string.substring(0,1)+"0"+string.substring(1);
		}
		return string.substring(0,string.length()-decimalPlaces)+"."+string.substring(string.length()-decimalPlaces);
	}
	else 
	{
		return String((int)input);
	}
}










