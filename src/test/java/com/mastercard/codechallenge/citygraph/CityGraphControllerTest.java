package com.mastercard.codechallenge.citygraph;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.mastercard.citynode.controller.CityNodeController;
import com.mastercard.citynode.service.CityNodeService;

@WebMvcTest(CityNodeController.class)
@ComponentScan("com.mastercard.codechallenge.citygraph")
@AutoConfigureMockMvc
public class CityGraphControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	CityNodeService CityGraphService;

	@Test
	public void isConnectedTest_whenTwoCitiesAreDirectlyConnected() throws Exception {
		this.mockMvc
				.perform(
						MockMvcRequestBuilders
						.get("/connected")
						.param("origin", "New York")
						.param("destination", "Boston")
						.contentType(MediaType.APPLICATION_JSON)
						)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("yes")));
	}

	@Test
	public void isConnectedTest_whenTwoCitiesAreIndirectlyConnected() throws Exception {
		this.mockMvc
				.perform(
						MockMvcRequestBuilders
						.get("/connected")
						.param("origin", "Philadelphia")
						.param("destination", "Boston")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk()
						)
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("yes")));
	}

	@Test
	public void isConnectedTest_whenTwoCitiesAreNotConnected_butCitiesArePresent() throws Exception {
		this.mockMvc
				.perform(
						MockMvcRequestBuilders
						.get("/connected").param("origin", "Philadelphia")
						.param("destination", "Albany")
						.contentType(MediaType.APPLICATION_JSON)
						)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("no")));
	}

	@Test
	public void isConnectedTest_whenTwoCitiesAreNotConnected_andCitiesAreNotPresent() throws Exception {
		this.mockMvc
				.perform(
						MockMvcRequestBuilders
						.get("/connected")
						.param("origin", "London")
						.param("destination", "Sydney")
						.contentType(MediaType.APPLICATION_JSON)
						)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("no")));
	}

}
