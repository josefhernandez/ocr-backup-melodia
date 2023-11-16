package com.isthari.ocr.melodia.api.audio.pulse;

import com.sun.jna.Library;

public interface PulseAudio extends Library {
	
	void audioInit(AudioRecordCallback callback);
	void audioInitStream(String device, int channels, boolean record);
	void audioInitRecordStream(String device, int channels);
	
}
