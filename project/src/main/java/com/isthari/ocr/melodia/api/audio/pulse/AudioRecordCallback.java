package com.isthari.ocr.melodia.api.audio.pulse;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface AudioRecordCallback extends Callback {

	void onAudioRecord(String device, Pointer data, int channels, int samples);
	
}