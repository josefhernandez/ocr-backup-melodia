package com.isthari.ocr.melodia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.isthari.ocr.melodia.data.Program;

@Component
public interface ProgramDao extends JpaRepository<Program, Long>{
	
	public Program findById(Long id);

}
