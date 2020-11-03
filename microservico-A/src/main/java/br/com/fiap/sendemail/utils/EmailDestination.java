package br.com.fiap.sendemail.utils;

public class EmailDestination {
    public static String emailDestination (String emailQueue, String emailDefault) {
        if (emailQueue != null && !emailQueue.isEmpty()) {
            return emailQueue;
        } else {
            return  emailDefault;
        }
    }
}
