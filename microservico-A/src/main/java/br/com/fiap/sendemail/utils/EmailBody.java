package br.com.fiap.sendemail.utils;

import br.com.fiap.sendemail.entity.EmailContent;

public class EmailBody {
    public static String emailBody (EmailContent newBody){
        String bodyReturn = (
                String.format("<h1> Drone ID: %s %s", newBody.getDroneID() , "</h1>") +
                String.format("<p> Humidade: %s %s", newBody.getHumidade() , "</p>") +
                String.format("<p> Temperatura: %s %s", newBody.getTemperatura() , "</p>") +
                String.format("<p> Latitude: %s %s", newBody.getLatitude() , "</p>") +
                String.format("<p> Longitude: %s %s", newBody.getLongitude() , "</p>"));
        return bodyReturn;
    }
}
