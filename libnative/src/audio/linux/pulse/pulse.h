#pragma once

#include <map>
#include <string>

#include "ExporterMacro.h"

#ifdef __cplusplus
extern "C" {
#endif

typedef void (*AudioRecordCallback) (const char *device, void *data, int channels, int samples);

EXPORTER void audioInit(AudioRecordCallback callback);
pa_simple* audioInitStream(const char *device, int channels, bool record);

// record
EXPORTER void audioInitRecordStream(const char *device, int channels);

// playback
EXPORTER void audioInitPlaybackStream(const char *device, int channels);
EXPORTER void audioPlay(const char *device, void *data, int channels, int samples);

#ifdef __cplusplus
}
#endif

class AudioManager {
public:
	AudioRecordCallback callback_;
	std::map<std::string, pa_simple *> recordStreams_;
	std::map<std::string, pa_simple *> playbackStreams_;
};
