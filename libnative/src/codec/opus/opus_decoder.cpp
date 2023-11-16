/*
 * opus_encoder.c
 *
 *  Created on: 23/02/2013
 *      Author: jose.hernandez@isthari.com
 */


#include <stdio.h>
#include <stdlib.h>
#include "opus_decoder.h"

OpusDecoder *media_opus_decoder_init(const int sampleRate,
								  	  	const int channels,
								        int * error){
	OpusDecoder *decoder;
	decoder = opus_decoder_create(sampleRate, channels, error);

	// ERROR INICIALIZANDO TERMINAR
	if (*error != OPUS_OK){
		return NULL;
	}

	return decoder;
}

void media_opus_decoder_close(OpusDecoder *decoder){
	opus_decoder_destroy(decoder);
}

opus_int32 media_opus_decoder_decode(OpusDecoder *decoder, const unsigned char *data, opus_int32 len, opus_int16 *pcm, int frame_size, int decode_fec){
	return opus_decode(decoder, data, len, pcm, frame_size, 0);
}
