package com.minutes.microservices.camelmicroservicesa.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

// @Component
public class FileWatch extends RouteBuilder {

    private  String path = "/home/guilhermefreitas/Documentos/Testes/Spring/files/input";

    @Override
    public void configure() throws Exception {
        from("file-watch:" + path
                  )
                 // + "?recursive=false")
                //
                 //+ "?events=CREATE") // MODIFY DELETE CREATE
                .log("Evento: ${header.CamelFileEventType} Arquivo: ${header.CamelFileName}");
    }
}
