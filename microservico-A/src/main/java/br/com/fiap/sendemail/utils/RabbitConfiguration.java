package br.com.fiap.sendemail.utils;


import br.com.fiap.sendemail.entity.RabbitMq;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

public class RabbitConfiguration {
    private static CachingConnectionFactory connectionFactory;

    public static CachingConnectionFactory getConnection() {
        if (connectionFactory == null) {
            connectionFactory = new CachingConnectionFactory("jackal.rmq.cloudamqp.com");
            connectionFactory.setUsername("mwjkmatq");
            connectionFactory.setPassword("fDcDrC2TnZ5Ll6OFb-Bbwt4AdnOVQ5Yr");
            connectionFactory.setVirtualHost("mwjkmatq");
            connectionFactory.setRequestedHeartBeat(30);
            connectionFactory.setConnectionTimeout(30_000);

        }
        return connectionFactory;
    }

}
