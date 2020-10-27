package br.com.fiap.localizadrone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.localizadrone.service.DroneService;

@RestController
@RequestMapping("/drone")
public class DroneController {
	
	private final DroneService droneService;
	
	public DroneController(DroneService droneService) {
		this.droneService = droneService;
	}
	
	@GetMapping(value = "/locale")
	public String getLocale() {
		return droneService.getLocale();
	}
}
