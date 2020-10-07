package com.poc.excel.poc.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.excel.poc.service.GenerateReport;
import com.poc.excel.poc.vo.DataReport;


@CrossOrigin( origins = {"http://localhost:8080","http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/download")
public class DescargaXlsx {
	@SuppressWarnings("all")
	@PostMapping("/repo")
	public ResponseEntity<Object> getCatalogValues(@RequestBody DataReport bs)  {	
		GenerateReport r = new GenerateReport();
		try {
			r.getReport(bs.getGrafica());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		File f = new File("src/main/resources/reports.xlsx");
		InputStreamResource resource = null;
		try {
			resource = new InputStreamResource(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HttpHeaders ht = new HttpHeaders();
		ht.add(HttpHeaders.CONTENT_DISPOSITION, f.getName());
		ht.add(HttpHeaders.CACHE_CONTROL,"no-cache");
		ht.add("Pragma", "no-cache");
		ht.add("Expires", "0");
		return  ResponseEntity.ok().headers(ht).contentLength(f.length()).contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource);	 
	}
	
}
