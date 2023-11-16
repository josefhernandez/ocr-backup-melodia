package com.isthari.ocr.melodia.audio;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isthari.ocr.melodia.dao.DestinationDao;
import com.isthari.ocr.melodia.data.Destination;

@Component
public class RtpSender implements InitializingBean, Runnable {
	
	private static final Logger LOGGER = Logger.getLogger(RtpSender.class);
	private BlockingQueue<AudioPacket> packetsQueue = new LinkedBlockingQueue<AudioPacket>();
	
	@Autowired
	private DestinationDao destinationDao;

	private Map<Long, List<Destination>> destinations = new HashMap<Long, List<Destination>>();
	
	public void afterPropertiesSet() throws Exception {			
		List<Destination> list = this.destinationDao.findAll();
		for (Destination destination : list){
			Long programId = destination.getProgram().getId();
			List<Destination> tempDestinations = destinations.get(programId);
			if (tempDestinations == null){
				tempDestinations = new ArrayList<Destination>();
				destinations.put(programId, tempDestinations);
			}
			tempDestinations.add(destination);
		}
		
		new Thread(this, "rtpSender").start();
	}

	
	public void run() {
		try {
			Selector.open();
			DatagramChannel channel = DatagramChannel.open();			
			
			while (true){
				try {
					AudioPacket packet = packetsQueue.take();										
					byte[] data = packet.getData();					
					// OBTENER LOS DESTINOS
					Long programId = packet.getProgramId();
					List<Destination> destinations = this.destinations.get(programId);
					
					if(destinations!=null){
						for (Destination destination : destinations){
							// PAQUETE DE AUDIO
							ByteBuffer buffer = ByteBuffer.wrap(data);
							
							InetAddress ia = InetAddress.getByName(destination.getAddress());
							InetSocketAddress ina = new InetSocketAddress(ia, destination.getPort());
							channel.send(buffer, ina);								
						}														
					}
				}catch(Throwable e){
					LOGGER.error("", e);
				}
			}
		}catch(Throwable e){
			LOGGER.error("", e);
		}
	}


	public BlockingQueue<AudioPacket> getPacketsQueue() {
		return packetsQueue;
	}
	public void setPacketsQueue(BlockingQueue<AudioPacket> packetsQueue) {
		this.packetsQueue = packetsQueue;
	}
	public DestinationDao getDestinationDao() {
		return destinationDao;
	}
	public void setDestinationDao(DestinationDao destinationDao) {
		this.destinationDao = destinationDao;
	}
	
	
	
	
}
