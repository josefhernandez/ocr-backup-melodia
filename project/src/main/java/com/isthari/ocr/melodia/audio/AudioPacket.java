package com.isthari.ocr.melodia.audio;
public class AudioPacket {
	
	private Long programId;
	private byte[] data;
			
	public Long getProgramId() {
		return programId;
	}
	public void setProgramId(Long programId) {
		this.programId = programId;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	
}
