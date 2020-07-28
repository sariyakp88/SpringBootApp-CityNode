package com.mastercard.citynode.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class CityNodeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CityNodeService.class);

	@Value("${cityFileLocation}")
	private String connectedCityFilePath;

	private Path_Between_Nodes path_Between_Nodes;

	@PostConstruct
	public void init() {

		List<String> connectedCityPairs = new ArrayList<>();

		try {
			connectedCityPairs = Files
					.readAllLines(ResourceUtils.getFile("classpath:" + connectedCityFilePath).toPath());
		} catch (IOException e) {
			LOGGER.error("File of Connected City Pairs Was not found: {} && an empty list is loaded",
					this.connectedCityFilePath);
			LOGGER.error(e.toString());
		}
	
		this.path_Between_Nodes = new Path_Between_Nodes();
		
		for (String connectedCityPair : connectedCityPairs) {
			if (!connectedCityPair.isEmpty()) {
				String[] cities = connectedCityPair.split(", ");
				if (cities.length == 2) {
					this.path_Between_Nodes.addEdge(cities[0], cities[1]);
				} else {
					throw new IllegalArgumentException();
				}
			}

		}
	}

	public String areCitiesConnected(String cityA, String cityB) {
		try {
			LinkedList<String> visited = new LinkedList();
	        visited.add(cityA);
	        this.path_Between_Nodes.initIndexes(cityA, cityB);
	        this.path_Between_Nodes.breadthFirst(path_Between_Nodes, visited);
	        
			if (this.path_Between_Nodes.isConnected(cityA, cityB)) {
				return "yes";
			}
		} catch (Exception e) {
			LOGGER.error("Either argument was not valid or unknown exception occured");
		}
		return "no";
	}

}
