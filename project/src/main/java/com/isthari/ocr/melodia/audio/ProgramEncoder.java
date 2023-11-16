package com.isthari.ocr.melodia.audio;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;

import com.isthari.ocr.melodia.api.audio.pulse.AudioRecordCallback;
import com.isthari.ocr.melodia.api.audio.pulse.PulseAudio;
import com.isthari.ocr.melodia.api.codec.opus.MediaOpusEncoderLowLevel;
import com.isthari.ocr.melodia.codec.opus.MediaOpusEncoder;
import com.isthari.ocr.melodia.data.Program;
import com.sun.jna.Pointer;

public class ProgramEncoder implements InitializingBean, Runnable, AudioRecordCallback {
	
	private static final int SAMPLERATE = 48000; 	
	private static final Log log = LogFactory.getLog(ProgramEncoder.class);
			
	private MediaOpusEncoderLowLevel lowLevel;
	private Program program;	
	private RtpSender rtpSender;		
	private MediaOpusEncoder encoder;	
	private PulseAudio pulseAudio;
	
	public void afterPropertiesSet() throws Exception {
		if (this.program==null){
			throw new BeanInitializationException("program is null");
		}		
		if (this.rtpSender==null){
			throw new BeanInitializationException("rtpSender is null");
		}
		
		if (this.lowLevel==null) {
			throw new BeanInitializationException("lowLevel is null");
		}
		
		encoder = new MediaOpusEncoder(lowLevel);
		encoder.init(SAMPLERATE, 1, program.getBitrate());
		
		new Thread(this, "programEncoder-"+program.getId()).start();
	}
	
	private long delta = -1;
	
	@Override
	public void onAudioRecord(String device, Pointer data, int channels, int samples) {
		log.info("on data channels: "+channels + " samples "+samples);
		byte[]pcm = data.getByteArray(0, channels*samples*2);
//		pcm = new byte[2*2880*1];
//		byte[] pcm = new byte[5760];
		byte[]opus = encoder.encode(pcm);
//		log.info(pcm.length+" "+opus.length);
		
		AudioPacket packet = new AudioPacket();
		packet.setData(opus);
		packet.setProgramId(this.program.getId());
		rtpSender.getPacketsQueue().add(packet);
		
		long temp = System.currentTimeMillis();
		log.info("delta "+ (temp-delta));
		delta = temp;
	}
		
	public void onAudioRecord(String device, byte[] pcm, int channels, int samples) {
		byte[]opus = encoder.encode(pcm);
		log.info(pcm.length+" "+opus.length);
		
		AudioPacket packet = new AudioPacket();
		packet.setData(opus);
		packet.setProgramId(this.program.getId());
		rtpSender.getPacketsQueue().add(packet);
		
		long temp = System.currentTimeMillis();
		log.info("delta "+ (temp-delta));
		delta = temp;
	}

	public void run() {
//		try {
//			int channels = 1;
//			AudioFormat format = new AudioFormat(48000, 16, channels, true, true);
//			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
//			TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(info);
//			targetLine.open(format);
//			targetLine.start();
//						
//			byte[] data = new byte[2880*channels*2];
//			while (true) {
//				int read = targetLine.read(data, 0, data.length);
//				log.info(read);
//				onAudioRecord("", data, channels, 2880);
//			}
//		} catch (Exception e) {
//			log.error("", e);
//		}
				
		pulseAudio.audioInitStream(program.getName(), 1, true);
		pulseAudio.audioInit(this);
		pulseAudio.audioInitRecordStream(program.getChannels(), 1);
		
		// EUropa FM
		//pulseAudio.audioInitRecordStream("alsa_input.pci-0000_04_00.0.analog-stereo", 1);
		
		// melodia
//		pulseAudio.audioInitRecordStream(program.getChannels(), 2);
	}		

	public RtpSender getRtpSender() {
		return rtpSender;
	}

	public void setRtpSender(RtpSender rtpSender) {
		this.rtpSender = rtpSender;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public PulseAudio getPulseAudio() {
		return pulseAudio;
	}

	public void setPulseAudio(PulseAudio pulseAudio) {
		this.pulseAudio = pulseAudio;
	}

	public MediaOpusEncoderLowLevel getLowLevel() {
		return lowLevel;
	}

	public void setLowLevel(MediaOpusEncoderLowLevel lowLevel) {
		this.lowLevel = lowLevel;
	}	
	
	
	
}
