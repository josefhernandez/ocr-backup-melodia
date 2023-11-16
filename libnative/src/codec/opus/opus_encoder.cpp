/*
 * opus_encoder.c
 *
 *  Created on: 23/02/2013
 *      Author: jose.hernandez@isthari.com
 */


#include <stdio.h>
#include <stdlib.h>
#include "opus_encoder.h"


OpusEncoder *	media_opus_encoder_init(const int sampleRate,
								  	  	const int channels,
								        const int bitRate,
								        int * error){
	OpusEncoder *encoder;
	encoder = opus_encoder_create(sampleRate, channels, OPUS_APPLICATION_RESTRICTED_LOWDELAY, error);

	// ERROR INICIALIZANDO TERMINAR
	if (*error != OPUS_OK){
		return NULL;
	}

	// FIJAR EL BITRATE
	// TODO comprobar el resultado
	opus_encoder_ctl(encoder, OPUS_SET_BITRATE(bitRate));
	//opus_encoder_ctl(encoder, OPUS_SET_COMPLEXITY(1));

	return encoder;
}

void media_opus_encoder_close(OpusEncoder *encoder){
	opus_encoder_destroy(encoder);
}

opus_int32 media_opus_encoder_encode(OpusEncoder *encoder, const opus_int16 *pcm, int frame_size, unsigned char *data, opus_int32 max_data_bytes){
	return opus_encode(encoder, pcm, frame_size, data, max_data_bytes);
}


opus_int32 media_opus_encoder_encode_float(OpusEncoder *encoder, const float *pcm, int frame_size, unsigned char *data, opus_int32 max_data_bytes){
	//return opus_encode(encoder, pcm, frame_size, data, max_data_bytes);
    return 0;
}

