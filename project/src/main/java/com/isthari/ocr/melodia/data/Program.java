package com.isthari.ocr.melodia.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Program {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int bitrate;
	private String channels;
//	private List<Integer> channelList;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getBitrate() {
		return bitrate;
	}
	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}
	public String getChannels() {
		return channels;
	}
	public void setChannels(String channelMap) {
		this.channels = channelMap;
	}
//	public List<Integer> getChannelList() {
//		return channelList;
//	}
//	public void setChannelList(List<Integer> channelList) {
//		this.channelList = channelList;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
