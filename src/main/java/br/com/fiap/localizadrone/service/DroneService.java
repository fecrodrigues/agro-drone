package br.com.fiap.localizadrone.service;

import java.net.URI;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.localizadrone.model.GoogleGeocodeResponse;

@Service
public class DroneService {
	
	public String getGeocode(String lat, String lng) {
		
		URI url = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/geocode/json")
				.queryParam("latlng", String.format("%s,%s", lat,lng))
				.queryParam("sensor", true)
				.queryParam("key", "AIzaSyBRFjFVJA63W85SD85Ij3bdY1mRLOB8DE0")
				.build().toUri();
				
		
		 ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {});
		
		return response.getBody();
	}
}
