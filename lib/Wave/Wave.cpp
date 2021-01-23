#include "Wave.h"

/* 
 * Constructor
 */

Wave::Wave(){
  
  for(int i=0; i<16; i++){
    amp[i]  = random(minAmp, maxAmp);
    distance[i] = random(minDist, minDist+randDist);
  }

  phase = 0;
  mu = 1/(float)distance[0];
  indx1 = 0; indx2 = 1; indx3 = 2; indx4 = 3;
}

/*
 * Sample Generator
 */

int16_t Wave::stoch(){

  if(phase >= distance[indx1]){
    indx1++; indx2++; indx3++; indx4++;
    phase = 0;
    //distance[indx1] = minDist + random(randDist);
    mu = 1/(float)distance[indx1];

    // breakpoint random walk
    RandWalk(walk, amp[indx2], minAmp, maxAmp);

    // distances random walk
    // RandWalk(2, distance[indx2], 10, 30);
  }

  switch (interpolation)
  {
    case 1:
      return LinearInterpolate(amp[indx1], amp[indx2], mu*phase++);
    case 2:
      return CubicInterpolate(amp[indx1], amp[indx2], amp[indx3], amp[indx4], mu*phase++);
    default: // Interpolación constante
      phase++; 
      return amp[indx1];
  }
}


/**
 * RandWalk
 */

void Wave::RandWalk(int walk, int16_t &value, int minBarrier, int maxBarrier){
    int randWalk = random(-walk, walk+1);
    if(randWalk != 0){
      if((value+randWalk) > maxBarrier || (value+randWalk) < minBarrier)
        value -= randWalk;
      else
        value += randWalk;
    }
}

/**
 * Interlopación Lineal
 */

int16_t Wave::LinearInterpolate(int16_t y1, int16_t y2, float mu){
  return (y1*(1-mu)+y2*mu);
}

/*
 * Interpolación Cubica
 */

int16_t Wave::CubicInterpolate(
   int16_t y0, int16_t y1,
   int16_t y2, int16_t y3,
   float mu)
{
   double a0,a1,a2,a3,mu2;

   mu2 = mu*mu;
   a0 = y3 - y2 - y0 + y1;
   a1 = y0 - y1 - a0;
   a2 = y2 - y0;
   a3 = y1;

   return(a0*mu*mu2+a1*mu2+a2*mu+a3);
}

/* 
 * Random
 */

long Wave::random(long big){
  if(big == 0){
    return 0;
  }

  #ifdef ESP32
    return esp_random() % big;
  #else
    return rand() % big;
  #endif
}

long Wave::random(long small, long big){
  return random(big - small) + small;
}

/**
 * Set minDist
 */
void Wave::setMinDist(unsigned int number){
	Wave::minDist = number;
}

/**
 * Set randDist
 */
void Wave::setRandDist(unsigned int number){
	Wave::randDist = number;
}

/**
 * Set walk
 */
void Wave::setWalk(unsigned int number){
	Wave::walk = number;
}

/**
 * Set interpolation
 */
void Wave::setInterpolation(unsigned int number){
	Wave::interpolation = number;
}