package com.isthari.ocr.melodia.audio;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.isthari.ocr.melodia.api.audio.pulse.PulseAudio;
import com.isthari.ocr.melodia.api.codec.opus.MediaOpusEncoderLowLevel;
import com.isthari.ocr.melodia.dao.ProgramDao;
import com.isthari.ocr.melodia.data.Program;

/**
 * Inicializa un thread por cada programEncoder en el sistema
 * @author jhernan
 *
 */
@Component
public class Encoder implements InitializingBean {
	
	private static final Log log = LogFactory.getLog(Encoder.class);
	
	@Value("${programId}")
	private long programId;
	
	@Autowired
	private MediaOpusEncoderLowLevel lowLevel;
	@Autowired
	private ProgramDao programDao;	
	@Autowired
	private RtpSender rtpSender;
	@Autowired
	private PulseAudio pulseAudio;
	
	private Map<Long, ProgramEncoder> encoders = new HashMap<Long, ProgramEncoder>();

	public void afterPropertiesSet() throws Exception {
		
		Program program = this.programDao.findById(programId);
		log.info("starting program: "+program.getName());
		ProgramEncoder encoder = new ProgramEncoder();
		encoder.setProgram(program);
		encoder.setPulseAudio(pulseAudio);
		encoder.setRtpSender(this.rtpSender);
		encoder.setLowLevel(lowLevel);
		encoder.afterPropertiesSet();			
		
		this.encoders.put(program.getId(), encoder);		
	}		
	
	public ProgramDao getProgramDao() {
		return programDao;
	}
	public void setProgramDao(ProgramDao programDao) {
		this.programDao = programDao;
	}	
	public RtpSender getRtpSender() {
		return rtpSender;
	}
	public void setRtpSender(RtpSender rtpSender) {
		this.rtpSender = rtpSender;
	}
}
