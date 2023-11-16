#pragma once

#include <opus/opus.h>

#ifdef __cplusplus
extern "C" {
#endif

OpusEncoder *	media_opus_encoder_init(const int sampleRate,
								  	  	const int channels,
								        const int bitRate,
								        int * error);

void media_opus_encoder_close(OpusEncoder *encoder);

opus_int32 media_opus_encoder_encode(OpusEncoder *encoder, const opus_int16 *pcm, int frame_size, unsigned char *data, opus_int32 max_data_bytes);
opus_int32 media_opus_encoder_encode_float(OpusEncoder *encoder, const float *pcm, int frame_size, unsigned char *data, opus_int32 max_data_bytes);

#ifdef __cplusplus
}
#endif