package com.isthari.ocr.melodia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.isthari.ocr.melodia.data.Destination;

@Component
public interface DestinationDao extends JpaRepository<Destination, Long>{

}
