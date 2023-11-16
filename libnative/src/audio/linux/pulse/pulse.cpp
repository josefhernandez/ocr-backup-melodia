#include <pulse/simple.h>
#include "Logger.h"
#include "pulse.h"

#include <iostream>
#include <sys/time.h>

AudioManager *audioManager = NULL;
long t0 ;
void audioInit(AudioRecordCallback callback) {
	ILOG_INFO ("Audio init native "<<callback);
	audioManager = new AudioManager();
	audioManager->callback_=callback;
	t0 = 0;
}

pa_simple* audioInitStream(const char *device, int channels, bool record) {
	ILOG_INFO("Audio init stream " << device);
	pa_simple *s;
	pa_sample_spec ss;

	ss.format = PA_SAMPLE_S16LE;
	ss.channels = channels;
	ss.rate = 48000;

	ILOG_INFO("Configure channel map "<< device);
	pa_channel_map *map = new pa_channel_map();
	map = pa_channel_map_init_auto(map, channels, PA_CHANNEL_MAP_ALSA);
	map->channels = channels;

	pa_stream_direction_t direction;
	if (record) {
		direction = PA_STREAM_RECORD;
	} else {
		direction = PA_STREAM_PLAYBACK;
	}
	ILOG_INFO("create client " << device);
	s = pa_simple_new(NULL,               // Use the default server.
					  device,           // Our application's name.
					  direction,
					  device,               // Use the default device.
					  device,            // Description of our stream.
					  &ss,                // Our sample format.
					  map,               // Use default channel map
					  NULL,               // Use default buffering attributes.
					  NULL               // Ignore error code.
					  );
	return s;
}

void audioInitRecordStream(const char *device, int channels) {
	pa_simple* s = audioInitStream(device, channels, true);
	audioManager->recordStreams_ [std::string(device)] = s;

	// int samples = 1440; 
	int samples = 2880;
	int size = 2 * channels * samples;
	int error;
	void *data = malloc(size);
	struct timeval te;
	long t1 = -1;
	while (true) {
		int result = pa_simple_read(s, data, size, &error);
		gettimeofday(&te, NULL);
		t1 = (te.tv_sec*1000LL + te.tv_usec/1000);

		if ( (t1-t0) > 10) {
/*		std::cout << "data read " 
			<< result 
			<< " "
			<< t1
			<< std::endl;*/
			audioManager->callback_(device, data, channels, samples);
		}
		t0 = t1;
	}
}

void audioInitPlaybackStream(const char *device, int channels) {
	pa_simple* s = audioInitStream(device, channels, false);
	audioManager->playbackStreams_[std::string(device)] = s;
}

void audioPlay(const char* device, void* data, int channels, int samples) {
	pa_simple* s = audioManager->playbackStreams_[std::string(device)];
	int error = 0;
	int size = 2 * channels * samples;
	pa_simple_write(s, data, size, &error);	
}