#include <stdlib.h>
#ifdef ESP32
#include "esp_system.h"
#endif

#define minAmp -30000
#define maxAmp 30000

class Wave{

 private:
  int16_t amp[16];
  int16_t distance[16];
  unsigned int phase;
  float mu;
  unsigned int indx1 : 4;
  unsigned int indx2 : 4;
  unsigned int indx3 : 4;
  unsigned int indx4 : 4;

  unsigned int minDist = 45;
  unsigned int randDist = 0;
  unsigned int walk = 1500;
  unsigned int interpolation = 1;

  int16_t LinearInterpolate(int16_t y1, int16_t y2, float mu);
  int16_t CubicInterpolate(int16_t y0, int16_t y1, int16_t y2, int16_t y3, float mu);
  long random(long big);
  long random(long small, long big);
  void RandWalk(int walk, int16_t &value, int minBarrier, int maxBarrier);

 public:
  Wave();
  int16_t stoch();
  void setMinDist(unsigned int number);
  void setRandDist(unsigned int number);
  void setWalk(unsigned int number);
  void setInterpolation(unsigned int number);
};