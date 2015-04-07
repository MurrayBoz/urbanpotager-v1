char NOMBRE[10]  = "MyPotager";    // le nom du module
char BPS         = '4';           // 1=1200 , 2=2400, 3=4800, 4=9600, 5=19200, 6=38400, 7=57600, 8=115200
char PASS[10]    = "1234";   // le mot de passe        

void setup()
{
   Serial.begin(9600);
   pinMode(13,OUTPUT);
   digitalWrite(13,HIGH);
   delay(10000);
   digitalWrite(13,LOW);
   
   Serial.print("AT"); 
   delay(1000);

   Serial.print("AT+NAME"); 
   Serial.print(NOMBRE);
   delay(1000);

   Serial.print("AT+BAUD"); 
   Serial.print(BPS); 
   delay(1000);

   Serial.print("AT+PIN");
   Serial.print(PASS); 
   delay(1000);    
}

void loop()
{
   digitalWrite(13, !digitalRead(13));
   delay(500);
}
