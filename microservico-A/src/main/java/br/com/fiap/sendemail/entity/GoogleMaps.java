package br.com.fiap.sendemail.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api")
public class GoogleMaps {
    public String getGooglemaps() {
        return googlemaps;
    }

    public void setGooglemaps(String googlemaps) {
        this.googlemaps = googlemaps;
    }

    private String googlemaps;
}
