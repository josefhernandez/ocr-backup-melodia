package com.isthari.ocr.melodia.codec.opus;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.isthari.ocr.melodia.api.codec.opus.MediaOpusEncoderLowLevel;
import com.isthari.ocr.melodia.codec.MediaEncoder;
import com.sun.jna.Pointer;

public class MediaOpusEncoder implements MediaEncoder {
	
	private static final Log log = LogFactory.getLog(MediaOpusEncoder.class);
			
	private MediaOpusEncoderLowLevel lowLevel;
	private Pointer encoder;
	private boolean finalized=false;
		
	private int channels;
	private OutputStream output;
	
	public MediaOpusEncoder(MediaOpusEncoderLowLevel lowLevel){
		this.lowLevel = lowLevel;
		try {
			 output = new FileOutputStream("/home/jhernan/Desktop/borrable/audio_lite.pcm");
		} catch(Throwable e) {
			
		}
	}
	
	public void init(int sampleRate, int channels, int bitRate){		
		this.channels = channels;
		
		int [] error = new int[1];
		this.encoder = this.lowLevel.media_opus_encoder_init(sampleRate, channels, bitRate, error);		
		log.info("opus init: "+error[0]);
	}
	
	public byte[] encode(byte[] inputBuffer){
		try {
			output.write(inputBuffer);
		} catch(Throwable e) {
			
		}
		int numSamples = inputBuffer.length;
		numSamples = numSamples >> 1; // 2 BYTES
		if (channels==2){
			numSamples = numSamples >> 1;
		}
		
		byte []tempOutput = new byte[10000];		
		int size = this.lowLevel.media_opus_encoder_encode(encoder, inputBuffer, numSamples, tempOutput, tempOutput.length);
		log.trace(size);
		
		return Arrays.copyOf(tempOutput, size);		
	}
	
	public byte[] encodeFloat(float[] inputBuffer){
		int numSamples = inputBuffer.length;
//		numSamples = numSamples >> 1; // 2 BYTES
		if (channels==2){
			numSamples = numSamples >> 1;
		}
		
		byte []tempOutput = new byte[5000];		
		int size = this.lowLevel.media_opus_encoder_encode_float(encoder, inputBuffer, numSamples, tempOutput, tempOutput.length);
		log.info(size);
		
		return Arrays.copyOf(tempOutput, size);
	}
	
	public void close(){
		try {
			this.finalize();
		} catch (Throwable e) {
			log.error("", e);
		}
	}

	@Override
	protected synchronized void finalize() throws Throwable {
		if (!this.finalized){
			this.finalized = true;
			this.lowLevel.media_opus_encoder_close(this.encoder);
		}
	}
	
	
}
