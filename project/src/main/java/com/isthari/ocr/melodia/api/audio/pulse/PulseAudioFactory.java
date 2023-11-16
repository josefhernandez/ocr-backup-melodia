package com.isthari.ocr.melodia.api.audio.pulse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sun.jna.Native;

@Configuration
public class PulseAudioFactory {

	@Value("${pulseaudio.path}/libnative.so")
	private String path; // = "/home/jhernan/Desktop/git/isthari/broadcast/hq-reporter/hq-test-v2-native/build/libnative.so";
	
	@Bean
	public PulseAudio pulseAudio() {
		return Native.load(path, PulseAudio.class);
	}
	
}
