package br.com.fiap.sendemail.utils;

import br.com.fiap.sendemail.entity.GoogleMaps;
import org.springframework.beans.factory.annotation.Autowired;

public class GoogleApiUrl {

    public static String googleApiUrl (String urlLatitude, String urlLongitude, String apiKey) {
        String startUrl = "https://maps.googleapis.com/maps/api/staticmap?center=";
        String middleUrl = "&zoom=10&size=800x800&markers=color:red%7Clabel:D%7C";
        String endUrl = "&key=";
        String delimiter = ",";
        StringBuilder urlCreate = new StringBuilder();
        urlCreate.append(startUrl);
        urlCreate.append(urlLatitude);
        urlCreate.append(delimiter);
        urlCreate.append(urlLongitude);
        urlCreate.append(middleUrl);
        urlCreate.append(urlLatitude);
        urlCreate.append(delimiter);
        urlCreate.append(urlLongitude);
        urlCreate.append(endUrl);
        urlCreate.append(apiKey);
        String urlReturn = urlCreate.toString();
        return urlReturn;
    }
}
