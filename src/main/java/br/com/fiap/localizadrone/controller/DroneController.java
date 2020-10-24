package br.com.fiap.localizadrone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.localizadrone.model.GoogleGeocodeResponse;
import br.com.fiap.localizadrone.service.DroneService;

@RestController
@RequestMapping("/drone")
public class DroneController {
	
	@Autowired
	private DroneService droneService;
	
	@GetMapping(value = "/geocode", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getGeocode(String lat, String lng) {
		return droneService.getGeocode(lat, lng).toString();
	}
}
