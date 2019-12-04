package com.mock.services;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.github.tomakehurst.wiremock.WireMockServer;

@EnableDiscoveryClient
public class WireMockStart {

	public static void main(String[] args) {
		// WireMockServer wireMockServer = new WireMockServer(9098);

		WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
		wireMockServer.start();
		
		System.out.println("Wiremock server started:" + wireMockServer.port());

		// configureFor(9098);

		// get stub
		wireMockServer.stubFor(get(urlEqualTo("/getinfo"))
				.willReturn(aResponse().withBody("{\r\n" + "	\"id\":123,\r\n" + "	\"name\":\"Shahjade Alam\",\r\n"
						+ "	\"company\":\"HCL tech\",\r\n" + "	\"address\":\"Noida\"\r\n"
						+ "	\"phoneNumbers\":[9988664422, 1234567890],\r\n" + "	\"role\":\"Developer\"\r\n" + "}")));

		// post stub
		wireMockServer.stubFor(post(urlEqualTo("/login")).withRequestBody(containing("username"))
				.withRequestBody(containing("password")).willReturn(aResponse().withBody("Welcome to my world")));

		/*
		 * stubFor(get(urlEqualTo("/testwm2")).willReturn(aResponse().withBody(
		 * "{age:90}")));
		 * 
		 * 
		 * stubFor(post(urlEqualTo("/testwm3")).withRequestBody(containing("designation"
		 * )) .willReturn(aResponse().withBody("{designation:SSE}")));
		 * 
		 */

	}

}
