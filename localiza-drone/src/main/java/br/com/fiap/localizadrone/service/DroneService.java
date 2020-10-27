package br.com.fiap.localizadrone.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.fiap.localizadrone.config.GeocodeConfig;

@Service
public class DroneService {
	
	@Value("${google.src}")
	private String googleSource;
	
	@Value("${google.key}")
	private String googleKey;
	
	private final TemplateEngine templateEngine;
	private final GeocodeConfig geocodeConfig;
	
	public DroneService(TemplateEngine templateEngine, GeocodeConfig geocodeConfig) {
		this.templateEngine = templateEngine;
		this.geocodeConfig = geocodeConfig;
	}
	
	public String getLocale() {
		String source = String.format(googleSource, googleKey);
		String googleScript = String.format("<script src= %s defer></script>", source);
		
		Context context = new Context();
		context.setVariable("googleScript", googleScript);
		context.setVariable("lat", geocodeConfig.getLat());
		context.setVariable("lng", geocodeConfig.getLng());
		
		String mapa = templateEngine.process("Mapa", context);
		
		return mapa;
	}
}
