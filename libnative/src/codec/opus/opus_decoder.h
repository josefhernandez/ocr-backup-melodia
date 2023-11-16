#pragma once

#include <opus/opus.h>

#ifdef __cplusplus
extern "C" {
#endif

OpusDecoder *media_opus_decoder_init(const int sampleRate,
								  	  	const int channels,
								        int * error);
void media_opus_decoder_close(OpusDecoder *decoder);
opus_int32 media_opus_decoder_decode(OpusDecoder *decoder, const unsigned char *data, opus_int32 len, opus_int16 *pcm, int frame_size, int decode_fec);

#ifdef __cplusplus
}
#endif