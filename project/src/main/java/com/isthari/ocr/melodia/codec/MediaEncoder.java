package com.isthari.ocr.melodia.codec;

public interface MediaEncoder {
	public void init(int sampleRate, int channels, int bitRate);
	public byte[] encode(byte[] inputBuffer);
	public void close();
}
