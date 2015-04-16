#include <Wire.h>

////////////////////////////////////////////////
//                Declarations                //
////////////////////////////////////////////////
String readString;
boolean isOn = false;
int nb = 0;

////////////////////////////////////////////////
//                 Base Code                  //
////////////////////////////////////////////////
void setup(){
	Serial.begin(9600);
	pinMode(13, OUTPUT);
}

void loop(){
	nb++;
	if(nb % 25 == 0){
		Serial.print("Coucou + ");
		Serial.println(nb);
		delay(5000);
	}
	else{
		while (Serial.available()) {
			delay(3);
			if (Serial.available() > 0) {
				char c = Serial.read();
				readString += c;
			} 
		}

		if (readString.length() >0) {
			if(isOn){
				digitalWrite(13, LOW);
				isOn = false;
			}else{
				digitalWrite(13, HIGH);
				isOn = true;
			}
			readString = "";
		}
		delay(1000);
	}
}

