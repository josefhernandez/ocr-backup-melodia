package com.isthari.ocr.melodia.api.codec.opus;
import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface MediaOpusDecoderLowLevel extends Library {
	
	/**
	 * Inicializa el encoder de opus
	 *  
	 * @param sampleRate
	 * @param channels
	 * @param bitRate
	 * @return Estructura del puntero de opus debe ser liberada
	 */
	public Pointer	media_opus_decoder_init(int sampleRate, int channels, int [] error);	
	public void media_opus_decoder_close(Pointer decoder);
	
	/**
	 * 
	 * @param encoder
	 * @param pcm
	 * @param frame_size
	 * @param data
	 * @param max_data_bytes
	 * @return
	 */
	public int media_opus_decoder_decode(Pointer decoder, byte []data, int len, byte [] pcm, int frameSize, int fec);

}
