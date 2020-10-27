package br.com.fiap.localizadrone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.localizadrone.config.GeocodeConfig;
import br.com.fiap.localizadrone.config.MQConfig;
import br.com.fiap.localizadrone.model.MQResponse;


@Service
@EnableScheduling
public class MQService {
	private Logger LOOGER = LoggerFactory.getLogger(MQService.class); 
	
	private final DroneService droneService;
	private final GeocodeConfig geocodeConfig;
	
	public MQService (DroneService droneService, GeocodeConfig geocodeConfig) {
		this.droneService = droneService;
		this.geocodeConfig = geocodeConfig;
	}
	
	@Scheduled(fixedDelay = 60000)
	public void updateLocale() {
		LOOGER.info("Verificando fila a procura de novas mensagens");
		
		try {
	        RabbitTemplate template = new RabbitTemplate(MQConfig.getConnection());
	        byte[] body = template.receive("drone.locale").getBody();
	        System.out.println(new String(body));
	        
	        MQResponse mqResponse = new ObjectMapper().readValue(body, MQResponse.class);
	        
	        geocodeConfig.setLat(mqResponse.getLat());
	        geocodeConfig.setLng(mqResponse.getLng());
	        
	        droneService.getLocale();
			
	        LOOGER.info("Fila verificada com sucesso");
	    } catch (NullPointerException ex){
			LOOGER.info("Fila vazia");
	    }catch (Exception e){
			LOOGER.info("Erro ao resgatar mensagem da fila");
	    }
	}
}
