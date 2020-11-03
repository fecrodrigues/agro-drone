package br.com.fiap.sendemail.utils;

import br.com.fiap.sendemail.entity.EmailContent;

public class EmailBody {
    public static String emailBody (EmailContent newBody, String issue){
        String bodyReturn = (
                String.format("<h1> Drone ID: %s %s", newBody.getDrone_id() , "</h1>") +
                String.format("<h2> Issue Reported: %s %s", issue, "</h2>" ) +
                String.format("<p> Umidade Relativa do Ar: %s%s", newBody.getUmidade() , "%</p>") +
                String.format("<p> Temperatura: %s%s", newBody.getTemperatura() , "°</p>") +
                String.format("<p> Latitude: %s %s", newBody.getLatitude() , "</p>") +
                String.format("<p> Longitude: %s %s", newBody.getLongitude() , "</p>"));
        return bodyReturn;
    }
}
