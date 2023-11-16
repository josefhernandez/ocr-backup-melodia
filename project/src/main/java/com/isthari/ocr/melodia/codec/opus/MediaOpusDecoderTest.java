package com.isthari.ocr.melodia.codec.opus;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isthari.ocr.melodia.api.codec.opus.MediaOpusDecoderLowLevel;

import com.sun.jna.Pointer;

//@Component
public class MediaOpusDecoderTest implements InitializingBean, Runnable {
	
	private static final Log log = LogFactory.getLog(MediaOpusDecoderTest.class);
	
	@Autowired
	private MediaOpusDecoderLowLevel lowLevel;
	private Pointer codec;

	@Override
	public void afterPropertiesSet() throws Exception {
		codec = lowLevel.media_opus_decoder_init(48000, 2, new int[2]); 
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			DatagramSocket socket = new DatagramSocket(5001);
			byte[] data = new byte [9999];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			
			while(true) {
				socket.receive(packet);
				int len = packet.getLength();
				log.info("data "+len);
				
				byte[] pcm = new byte [999999];
				int samples = lowLevel.media_opus_decoder_decode(codec, data, len, pcm, 3000, 0);
				log.info("samples "+samples);
			}
		} catch(Throwable e) {
			log.error("", e);
		}
	}

}
