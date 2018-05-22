package com.lab.sample.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lab.sample.model.CalculateRequest;
import com.lab.sample.model.CalculateResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CalculateController {

	@PostMapping("/api/calculation")
	public CalculateResponse calculate(@RequestBody CalculateRequest request) {
		log.info("Received calculation request");
		return null;
	}

}
