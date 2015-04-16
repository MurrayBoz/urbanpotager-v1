/***************************************************************
------- Urban Potager - Micro Edition v3 ------- 
Author : Julien Morin (UrbanaPlant)
Licence : CC-BY
Tutorial : www.urbanpotager.com
Last Update : 29-01-2015
***************************************************************/

#include <LiquidCrystal.h>
LiquidCrystal lcd(12,11,5,4,3,2);
int lightPin = 1;   
long longLightReading=0;
long longTempVal=0;
long longHumiVal=0;
String detailledStatus1;    // Status ligne 1 (max 16 caractères)
String detailledStatus2;    // Status ligne 2 (max 16 caractères)
String lightVal;
String tempVal;
String humiVal;
long lightPercent;

unsigned long lastSensorReading=0;
unsigned long delaySensorReading = 5 ; // every 5 s

unsigned long lastWatering=0;
unsigned long durationWatering = 20;// 20 seconds (in seconds)
unsigned long delayWatering = 1200 ; // 1200 = every 20 mn (in seconds)
unsigned long currentTime;
boolean bLightON=false;
boolean bPumpON=false;

int startLight = 7;       // Heure de DEBUT lumière (8 = allumage à 8:00)
int endLight = 23;        // Heure de FIN lumière (22 = extinction à 22:00)
int lightMinValue = 90;                

/****** RTC Libraries & Variables ******************/
#include <SPI.h>
#include <RTClib.h>          // RTC library
#include <Wire.h>            // linked to RTC
RTC_DS1307 RTC;
#if defined(__SAM3X8E__)
    #undef __FlashStringHelper::F(string_literal)
    #define F(string_literal) string_literal
#endif
long time; 
String myDate;
String myTime;
  
#include <DHT22.h>   
int tempPin=7;      
DHT22 myDHT22(tempPin);

int pinLight = 8;  
int pinPump = 9;  

byte celcius[8] = {
  B01000,
  B10100,
  B01000,
  B00011,
  B00100,
  B00100,
  B00011,
};  

byte light[8] = {
  B00000,
  B10101,
  B01110,
  B11111,
  B01110,
  B10101,
  B00000,
  B00000,
};  

byte humidity[8] = {
  B00100,
  B01110,
  B11111,
  B01000,
  B00010,
  B01000,
  B00010,
  B00000,
};

byte temp[8] = {
  B00100,
  B01010,
  B01010,
  B01110,
  B11111,
  B11111,
  B01110,
};


byte water1[8] = {
  B00000,
  B00100,
  B01110,
  B01110,
  B11111,
  B11101,
  B11111,
  B01110,
};

byte water2[8] = {
  B00100,
  B01110,
  B01110,
  B11111,
  B11101,
  B11111,
  B01110,
  B00000,
};

/*byte water3[8] = {
  B11111,
  B11111,
  B01110,
  B00000,
  B00000,
  B00000,
  B00000,
  B00000,
};*/

/*byte urbanpotager1[8] = {
  B10001,
  B01010,
  B10101,
  B10101,
  B10101,
  B10001,
  B01110,
  B00000,
};*/

void setup() {
  Serial.begin(9600);
  lcd.begin(16, 2);
  lcd.createChar(0, celcius);
  lcd.createChar(1, light);
  lcd.createChar(2, humidity);
  lcd.createChar(3, temp);
  lcd.createChar(4, water1);
  lcd.createChar(5, water2);
  //lcd.createChar(6, urbanpotager1);

  pinMode(pinLight, OUTPUT); 
  digitalWrite(pinLight,HIGH); // Turn OFF (default)
  pinMode(pinPump, OUTPUT); 
  digitalWrite(pinPump,HIGH); // Turn OFF = HIGH (default)
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
  Wire.begin();
  RTC.begin();
  //RTC.adjust(DateTime(__DATE__, __TIME__));
  if (! RTC.isrunning()) {
    Serial.println("RTC is NOT running!");
    RTC.adjust(DateTime(__DATE__, __TIME__));
  }
}

void loop() {
  DateTime now = RTC.now();
  time = now.unixtime(); // seconds  
  //if (now.day() < 10) { myDate ="0"+ String(now.day())+"/";} else {myDate = String(now.day())+"/";}             // correct date if necessary
  //if (now.month() < 10) { myDate =myDate+"0"+ String(now.month())+"/";} else {myDate = myDate+String(now.month())+"/";}             // correct date if necessary
  //myDate = myDate+String(now.year());
  
  if (now.hour() < 10) { myTime ="0"+ String(now.hour())+":";} else {myTime = String(now.hour())+":";}             // correct date if necessary
  if (now.minute() < 10) { myTime =myTime+"0"+ String(now.minute());} else {myTime = myTime+String(now.minute());}             // correct date if necessary

  if (time > (lastSensorReading + delaySensorReading)) {
    getSensorsValues();  
    lastSensorReading = time;
  } 
  manageDayLight();            // Based on Settings & RTC, swith High / Low lights 
  manageWatering();   
  manageLCDDisplay();
  //delay(5000) ;
}

/**********************************************************************
             Light / Temp / Humidity
**********************************************************************/
void getSensorsValues() { 
  // Display Temperature in C

  longLightReading = analogRead(lightPin);
  
  DHT22_ERROR_t errorCode;
  errorCode = myDHT22.readData();

  switch(errorCode)
  {
    case DHT_ERROR_NONE:
      longTempVal = myDHT22.getTemperatureC();
      longHumiVal = myDHT22.getHumidity();
      break;
      
    case DHT_ERROR_CHECKSUM:
      //Serial.print("check sum error ");
      break;
      
    case DHT_BUS_HUNG:
      //Serial.println("BUS Hung ");
      break;
      
    case DHT_ERROR_NOT_PRESENT:
      //Serial.println("Not Present ");
      break;
      
    case DHT_ERROR_ACK_TOO_LONG:
      //Serial.println("ACK time out ");
      break;
      
    case DHT_ERROR_SYNC_TIMEOUT:
      //Serial.println("Sync Timeout ");
      break;
      
    case DHT_ERROR_DATA_TIMEOUT:
      //Serial.println("Data Timeout ");
      break;
      
    case DHT_ERROR_TOOQUICK:
      //Serial.println("Polled to quick ");
      break;
  }
  lightPercent = map(longLightReading, 0, 1023, 0, 100);
  Serial.print("longLightReading = ");
  Serial.println(longLightReading);
  lightVal = doubleToString(lightPercent, 0);
  tempVal = doubleToString(longTempVal, 0);
  humiVal = doubleToString(longHumiVal, 0);  
}

/****** LIGHTING SYSTEM ******/
void manageDayLight() { 
 DateTime now = RTC.now();
 int myHour = now.hour(); 
 if ((myHour >= startLight ) && (myHour < endLight)) { 
   if (lightPercent>=90) {
      digitalWrite(pinLight,HIGH); // Turn OFF = HIGH (default)
      bLightON=false;
    } else {
      digitalWrite(pinLight,LOW); // Turn ON
      bLightON=true;
    }
 } else {
   digitalWrite(pinLight,HIGH); // Turn OFF = HIGH (default)
   bLightON=false;
 }
}

/****** WATERING SYSTEM ******/
void manageWatering() {  
  if (bPumpON==true) {
      Serial.print("Pump ON (lastWatering=");
      Serial.print(lastWatering);
      Serial.print(" / time=");
      Serial.print(time);
      Serial.print(" / durationWatering=");
      Serial.print(durationWatering);
      Serial.println(")");
    if (time > (lastWatering + durationWatering)) {
      digitalWrite(pinPump,HIGH); // Turn OFF = HIGH (default)
      bPumpON=false;
      Serial.print("Pump OFF (lastWatering=");
      Serial.print(lastWatering);
      Serial.print(" / time=");
      Serial.print(time);
      Serial.println(")");
      lastWatering = time;
    }
  } else {
    if (time > (lastWatering + delayWatering)) {
      digitalWrite(pinPump,LOW); // Turn ON
      Serial.print("Pump ON (lastWatering=");
      Serial.print(lastWatering);
      Serial.print(" / time=");
      Serial.print(time);
      Serial.println(")");
      lastWatering =time;
      bPumpON=true;
    }
  }
}
  
/****** LCD DISPLAY SYSTEM ******/  
void manageLCDDisplay() {
  if (bPumpON == true) {
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
    int nextWatering = (lastWatering+delayWatering) - time;
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

/****** TOOLS FUNCTIONS ******/  
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
