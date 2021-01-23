#include "driver/i2s.h"

//------------------------------------------------------------------------------------------------------------------------
// I2S configuration structures
const i2s_port_t I2S_PORT = I2S_NUM_0;

const i2s_config_t i2s_config = {
    .mode = i2s_mode_t(I2S_MODE_MASTER | I2S_MODE_TX),
    .sample_rate = 44100,                         
    .bits_per_sample = I2S_BITS_PER_SAMPLE_16BIT,
    .channel_format = I2S_CHANNEL_FMT_RIGHT_LEFT,
    .communication_format = i2s_comm_format_t(I2S_COMM_FORMAT_I2S),
    .intr_alloc_flags = ESP_INTR_FLAG_LEVEL3, //_LEVEL1,     // Interrupt level 1
    .dma_buf_count = 8,                        // number of buffers
    .dma_buf_len = 64                          // 64 samples per buffer (minimum)
};

const i2s_pin_config_t pin_config = {
    .bck_io_num = 26,   // Serial Clock (SCK)
    .ws_io_num = 25,    // Word Select (WS)
    .data_out_num = 33, // not used (only for speakers)
    .data_in_num = I2S_PIN_NO_CHANGE   // Serial Data (SD)
};
