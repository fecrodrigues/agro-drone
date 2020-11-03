package br.com.fiap.sendemail.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mail.smtp")
public class SmtpConfig {
    private String auth;
    private String host;
    private String port;
    private String starttlsEnable;
    private String emailDestination;
    private String attachPath;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getStarttlsEnable() {
        return starttlsEnable;
    }

    public void setStarttlsEnable(String starttlsEnable) {
        this.starttlsEnable = starttlsEnable;
    }

    public String getEmailDestination() {
        return emailDestination;
    }

    public void setEmailDestination(String emailDestination) {
        this.emailDestination = emailDestination;
    }

    public String getAttachPath() {
        return attachPath;
    }

    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath;
    }
}
