package com.isthari.ocr.melodia.api.codec.opus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sun.jna.Native;

@Configuration
public class MediaOpusFactory {

	@Value("${pulseaudio.path}/libnative.so")
	private String path;
	
	@Bean
	public MediaOpusDecoderLowLevel mediaOpusDecoderLowLevel() {
		return Native.load(path, MediaOpusDecoderLowLevel.class);
	}
	
	@Bean
	public MediaOpusEncoderLowLevel mediaOpusEncoderLowLevel() {
		return Native.load(path, MediaOpusEncoderLowLevel.class);
	}
	
}
