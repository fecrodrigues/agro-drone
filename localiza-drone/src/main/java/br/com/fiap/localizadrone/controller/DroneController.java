package br.com.fiap.localizadrone.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.localizadrone.service.DroneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Drone")
@RestController
@RequestMapping("/drone")
public class DroneController {
	
	private final DroneService droneService;
	
	public DroneController(DroneService droneService) {
		this.droneService = droneService;
	}

	@ApiOperation("Exibe a localização do drone no mapa informada na última mensagem enviada para a fila drone.locationInfo")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna a exibição do drone no mapa")
	})
	@GetMapping(value = "/locale")
	@CrossOrigin(origins = "http://localhost:3000")
	public String getLocale() {
		return droneService.getLocale();
	}
}
