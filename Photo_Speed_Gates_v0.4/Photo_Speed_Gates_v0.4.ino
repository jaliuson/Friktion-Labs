/* Version 0.4
 *  
 *  This maintains the same input reading structure as 0.3 
 *  This vesion will be the first to interface with the java program 
 *  Serial output will be used to check data and errors
 *  Java program is still in development and can only print apeed values
 */


#include <LiquidCrystal.h>

LiquidCrystal lcd(3,2,4,5,6,7); //contrast pin (3) -> 5k resistor -> GND
const int laserPin1 = 12;       //photoresistor 1 to A0
const int laserPin2 = 8;       //photoresistor 2 to A1
double objectLength;             //length of object being passed
boolean clearContinue;   //will be used to wait for user to reset values
double tStamps[4];
double t1;             //duration of laser detector on high
  double t2;
  double dt1;                      //Time between reads
  double dt2;
  double dt;       
  double v1;                    //will store speed values
  double v2;                    
  double acc;                   //calculated acceleration

void setup() {
  pinMode(8,INPUT);
  lcd.begin(16,2);
  Serial.begin(9600);
  objectLength = 0.05;//meters          //@TODO - take input instead of hardcode
  lcd.print("Status:");
  lcd.setCursor(0,1);
  if(digitalRead(laserPin1) == HIGH && digitalRead(laserPin2) == HIGH){
    //Serial.println("readys");
    lcd.print("Ready");
    delay(1000);
  }
  else{
    lcd.print("Not Ready");
    //Serial.println("readys");
  }
}

void loop() {
  clearContinue = false;        //reset continue check
  //Serial.println(digitalRead(laserPin1));
  //Serial.println(digitalRead(laserPin2));
  //Serial.println("1");
  if(digitalRead(laserPin1) == 0){
    tStamps[0] = micros();
    while(digitalRead(laserPin1) == 0){}
    tStamps[1] = micros();
    while(digitalRead(laserPin2) == 1){}
    tStamps[2] = micros();
    while(digitalRead(laserPin2) == 0){}
    tStamps[3] = micros();
    
    dt = (double)tStamps[2]-tStamps[0];
    dt /= (double)1000000.0;
    t1 = (double)tStamps[1] - tStamps[0];
    t1 /= (double)1000000.0;
    t2 = (double)tStamps[3] - tStamps[2];
    t2 /= (double)1000000.0;
    v1 = (double)objectLength/t1;
    v2 = (double)objectLength/t2;
    acc = (double)(v2-v1)/dt;

    Serial.println(v1);
    Serial.println(v2);

    lcd.print("V1: ");
    lcd.print(v1);
    lcd.print(" m/s");
    lcd.setCursor(0,1);
    lcd.print("V2: ");
    lcd.print(v2);
    lcd.print(" m/s");
    delay(2000);
    lcd.clear();
    lcd.print("Acc: ");
    lcd.print(acc);
    delay(1000);
  }
  lcd.clear();
}
