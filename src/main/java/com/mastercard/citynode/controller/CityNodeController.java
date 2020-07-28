package com.mastercard.citynode.controller;

import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.citynode.service.CityNodeService;

@RestController
@RequestMapping("/connected")
public class CityNodeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CityNodeController.class);

	@Autowired
	CityNodeService citynodeService;

	@GetMapping
	public ResponseEntity<String> isConnected(
			@RequestParam(required = true, defaultValue = "") final @NotBlank String origin,
			@RequestParam(required = true, defaultValue = "") final @NotBlank String destination) {
		LOGGER.debug("isConnected is called with origin city = {} and destination city = {}", origin, destination);
		
		return new ResponseEntity<String>(citynodeService.areCitiesConnected(origin, destination), HttpStatus.OK);
	}

}
