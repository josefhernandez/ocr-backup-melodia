package com.isthari.ocr.melodia.api.codec.opus;
import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface MediaOpusEncoderLowLevel extends Library {
	
	/**
	 * Inicializa el encoder de opus
	 *  
	 * @param sampleRate
	 * @param channels
	 * @param bitRate
	 * @return Estructura del puntero de opus debe ser liberada
	 */
	public Pointer	media_opus_encoder_init(int sampleRate, int channels, int bitRate, int [] error);	
	public void media_opus_encoder_close(Pointer encoder);
	
	/**
	 * 
	 * @param encoder
	 * @param pcm
	 * @param frame_size
	 * @param data
	 * @param max_data_bytes
	 * @return
	 */
	public int media_opus_encoder_encode(Pointer encoder, byte []pcm, int frame_size, byte [] data, int max_data_bytes);
	
	public int media_opus_encoder_encode_float(Pointer encoder, float []pcm, int frame_size, byte[] data, int max_data_bytes);


}
