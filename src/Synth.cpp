#include <Arduino.h>
#include "driver/i2s.h"
#include "Wave.h"
#include "header.h"

//------------------------------------------------------------------------------------------------------------------------
//  Global Variables/objects  

// Stochastic synth
Wave wave1;
Wave wave2;
Wave wave3;
Wave wave4;

float amp1 = 0.5;
float amp2 = 0.5;

TaskHandle_t Task1;
TaskHandle_t Task2;

//------------------------------------------------------------------------------------------------------------------------
// Control Serial

void serialControl( void * parameters ){
  // Serial stuff
  Serial.begin(115000);
  // Show info
  Serial.println("");
  Serial.println("Serial control started");
  Serial.println("**********************");
  Serial.println("");
  Serial.println("Receive unsigned int configuration line as:");
  Serial.println("#m{MinDist} #r{RandDist} #w{Walk} #i{Interpolation}");
  Serial.println("");
  Serial.println("Example for Wave#1:");
  Serial.println("1m25 1r10 1w1500 1i0");
  Serial.println("");
  Serial.println("Example for all Waves:");
  Serial.println("am25 ar10 aw1500 ai0");

  // Loop
  for(;;){
    // Serial
    if( Serial.available() )
    {
      // Read
      String inputStream = Serial.readString();
      Serial.println("Received: " + inputStream);
      // Get data control :: minimum three char
      if( inputStream.length() > 2 )
      {
        // Get Wave#
        char waveKeyChar = inputStream.charAt(0);
        int waveNumber = ((int) waveKeyChar) - ((int)'0');
        // Control
        if (
          ( waveNumber > 0 && waveNumber < 5 )
          ||
          waveKeyChar == 'a'
          )
        {
          // Remove wave key
          inputStream.remove(0, 1);
          // Get parameter
          char cKey = inputStream.charAt(0);
          inputStream.remove(0, 1);
          String sData = inputStream;
          // Temp
          int value = 10;
          sscanf(sData.c_str(), "%d", &value);
          // Switch
          switch(cKey)
          {
            case 'm':
              // MinDist
              Serial.println("MinDist: " + sData);
              // Wave#
              if (waveKeyChar == 'a')
              {
                wave1.setMinDist(value);
                wave2.setMinDist(value);
                wave3.setMinDist(value);
                wave4.setMinDist(value);
                Serial.println("Wave: ALL");
              }else{
                switch(waveNumber)
                {
                  case 1:
                    wave1.setMinDist(value);
                    Serial.println("Wave: 1");
                    break;
                  case 2:
                    wave2.setMinDist(value);
                    Serial.println("Wave: 2");
                    break;
                  case 3:
                    wave1.setMinDist(value);
                    Serial.println("Wave: 3");
                    break;
                  case 4:
                    wave1.setMinDist(value);
                    Serial.println("Wave: 4");
                    break;
                }
              }
              break;
            case 'r':
              // RandDist
              Serial.println("RandDist: " + sData);
              // Wave#
              if (waveKeyChar == 'a')
              {
                wave1.setRandDist(value);
                wave2.setRandDist(value);
                wave3.setRandDist(value);
                wave4.setRandDist(value);
                Serial.println("Wave: ALL");
              }else{
                switch(waveNumber)
                {
                  case 1:
                    wave1.setRandDist(value);
                    Serial.println("Wave: 1");
                    break;
                  case 2:
                    wave2.setRandDist(value);
                    Serial.println("Wave: 2");
                    break;
                  case 3:
                    wave1.setRandDist(value);
                    Serial.println("Wave: 3");
                    break;
                  case 4:
                    wave1.setRandDist(value);
                    Serial.println("Wave: 4");
                    break;
                }
              }
              break;
            case 'w':
              // Walk
              Serial.println("Walk: " + sData);
              // Wave#
              if (waveKeyChar == 'a')
              {
                wave1.setWalk(value);
                wave2.setWalk(value);
                wave3.setWalk(value);
                wave4.setWalk(value);
                Serial.println("Wave: ALL");
              }else{
                switch(waveNumber)
                {
                  case 1:
                    wave1.setWalk(value);
                    Serial.println("Wave: 1");
                    break;
                  case 2:
                    wave2.setWalk(value);
                    Serial.println("Wave: 2");
                    break;
                  case 3:
                    wave1.setWalk(value);
                    Serial.println("Wave: 3");
                    break;
                  case 4:
                    wave1.setWalk(value);
                    Serial.println("Wave: 4");
                    break;
                }
              }
              break;
            case 'i':
              // Interpolation
              Serial.println("Interpolation: " + sData);
              // Wave#
              if (waveKeyChar == 'a')
              {
                wave1.setInterpolation(value);
                wave2.setInterpolation(value);
                wave3.setInterpolation(value);
                wave4.setInterpolation(value);
                Serial.println("Wave: ALL");
              }else{
                switch(waveNumber)
                {
                  case 1:
                    wave1.setInterpolation(value);
                    Serial.println("Wave: 1");
                    break;
                  case 2:
                    wave2.setInterpolation(value);
                    Serial.println("Wave: 2");
                    break;
                  case 3:
                    wave1.setInterpolation(value);
                    Serial.println("Wave: 3");
                    break;
                  case 4:
                    wave1.setInterpolation(value);
                    Serial.println("Wave: 4");
                    break;
                }
              }
              break;
            default:
              // None
              break;
          }
        }
      }
    }
  }
}

//------------------------------------------------------------------------------------------------------------------------
// Core 0

void Task1code( void * parameters ){
  uint32_t sample_val_arr[1024] = { 0 };
  uint32_t left, right;

  size_t i2s_bytes_write = 0;
  
  i2s_driver_install(I2S_PORT, &i2s_config, 0, NULL);
  i2s_set_pin(I2S_PORT, &pin_config);
  
  for(;;){
    for(int i = 0; i < 1024; i++){
      left  = wave1.stoch()*amp1 + wave2.stoch()*amp2;
      right = wave3.stoch()*amp1 + wave4.stoch()*amp2;
      
      sample_val_arr[i] = (left << 16) | (right & 0xffff);
    }
    
    i2s_write(I2S_NUM_0, &sample_val_arr, sizeof(sample_val_arr), &i2s_bytes_write, 100);
   }
}

//------------------------------------------------------------------------------------------------------------------------
// Core 1

void Task2code( void * parameters ){
  if ( true )
  {
    // Serial
    serialControl(0);
  }else{
    // OSC Wifi
    //oscWifiControl(0);
  }
}
//------------------------------------------------------------------------------------------------------------------------
// Setup

void setup() {

  xTaskCreatePinnedToCore(Task1code, "Task1", 10000, NULL, 1, &Task1, 0);
  xTaskCreatePinnedToCore(Task2code, "Task2", 10000, NULL, 1, &Task2, 1);
}
//------------------------------------------------------------------------------------------------------------------------
// loop

void loop() { }