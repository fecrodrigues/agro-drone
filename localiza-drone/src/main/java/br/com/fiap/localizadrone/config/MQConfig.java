package br.com.fiap.localizadrone.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

public class MQConfig {
    private static CachingConnectionFactory connectionFactory;

    public static CachingConnectionFactory getConnection(){

        if(connectionFactory == null){
            connectionFactory = new CachingConnectionFactory("jackal.rmq.cloudamqp.com");//TODO add hostname
            connectionFactory.setUsername("ezgarcxq");//TODO add username
            connectionFactory.setPassword("lOhbZPHOWCu8wkZ0m6p72KeDMIAC_y5U");//TODO add password
            connectionFactory.setVirtualHost("ezgarcxq");//TODO add virtualhost

            //Recommended settings
            connectionFactory.setRequestedHeartBeat(30);
            connectionFactory.setConnectionTimeout(30000);
        }

        return connectionFactory;
    }
}
