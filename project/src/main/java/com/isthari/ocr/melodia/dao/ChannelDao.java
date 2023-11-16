package com.isthari.ocr.melodia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.isthari.ocr.melodia.data.Channel;

@Component
public interface ChannelDao extends JpaRepository<Channel, Long>{

}
