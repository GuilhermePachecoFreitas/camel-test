package com.minutes.microservices.camelmicroservicesa.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.springframework.stereotype.Component;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import java.security.*;
import java.security.cert.CertificateException;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

//@Component
public class ActiveMqSenderRounter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // getContext().setTracing(true);
        //  errorHandler(deadLetterChannel("activemq:dead-letter-queue"));

        //timer

        from("timer:active-mq-timer?period=10000")
                .transform().constant("Mensagem recebida")
                .marshal(createEncryptor())
                .log("${body}")
                .to("activemq:my-activemq-queue");

        //JSON
//        from("file:/home/guilhermefreitas/Documentos/Testes/Spring/files/json")
//                .log("${body}")
//                .to("activemq:my-activemq-queue");
        //XML
//        from("file:files/xml")
//                .log("${body}")
//                .to("activemq:my-activemq-xml-queue");
    }

    private CryptoDataFormat createEncryptor() throws KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        ClassLoader classLoader = getClass().getClassLoader();
        keyStore.load(classLoader.getResourceAsStream("myDesKey.jceks"), "someKeystorePassword".toCharArray());
        Key sharedKey = keyStore.getKey("myDesKey", "someKeyPassword".toCharArray());

        CryptoDataFormat sharedKeyCrypto = new CryptoDataFormat("DES", sharedKey);
        return sharedKeyCrypto;
    }
}
