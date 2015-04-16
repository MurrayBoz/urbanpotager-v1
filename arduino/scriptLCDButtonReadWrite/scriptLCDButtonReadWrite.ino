#include "U8glib.h"

////////////////////////////////////////////////
//                Declarations                //
////////////////////////////////////////////////
String readString;
int nb = 0;
const int buttonPin = 2;
boolean statebtn = true;
U8GLIB_SSD1306_128X64 u8g(U8G_I2C_OPT_NO_ACK);

////////////////////////////////////////////////
//                 Base Code                  //
////////////////////////////////////////////////
void setup(void) {
	if ( u8g.getMode() == U8G_MODE_R3G3B2 ) {
		u8g.setColorIndex(255); // white
	}
	else if ( u8g.getMode() == U8G_MODE_GRAY2BIT ) {
		u8g.setColorIndex(3); // max intensity
	}
	else if ( u8g.getMode() == U8G_MODE_BW ) {
		u8g.setColorIndex(1); // pixel on
	}
	else if ( u8g.getMode() == U8G_MODE_HICOLOR ) {
		u8g.setHiColorByRGB(255,255,255);
	}

	Serial.begin(9600);
	pinMode(13, OUTPUT);
	pinMode(buttonPin, INPUT); 
}

void loop(void) {
	if (digitalRead(buttonPin) == HIGH) {  
		if(statebtn){  
			digitalWrite(13, HIGH);
			Serial.print("Coucou: ");
			Serial.println(nb++);
			statebtn = false;
		}
	} 
	else {
		statebtn = true;
		digitalWrite(13, LOW); 
	}

	while (Serial.available()) {
		delay(3);
		if (Serial.available() > 0) {
			char c = Serial.read();
			readString += c;
		} 
	}

	if (readString.length() >0) {
		char c = readString.charAt(0);
		if(c == '!'){
			u8g.firstPage();
			do {
				draw(readString == "!on");
			} while( u8g.nextPage() );
		}
	}

	delay(50);
}

////////////////////////////////////////////////
//                 FUNCTIONS                  //
////////////////////////////////////////////////
void draw(boolean isOn) {
	// graphic commands to redraw the complete screen should be placed here
	u8g.setFont(u8g_font_unifont);
	//u8g.setFont(u8g_font_osb21);
	if(isOn)
		u8g.drawStr( 0, 22, "C'est ok");
	else
		u8g.drawStr( 0, 22, "C'est pas ok");
}