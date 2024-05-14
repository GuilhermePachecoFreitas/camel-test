package com.minutes.microservices.camelmicroservicesa.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class FileRouteByModifyeCreate extends RouteBuilder {

    @Override
    public void configure(){

        from("file-watch:files/input?events=MODIFY" +
                "&events=CREATE") //  DELETE CREATE MODIFY
                    .log("Evento: ${header.CamelFileEventType} Arquivo: ${header.CamelFileName}")
                    .log("Enviando o ${header.CamelFileName}")
                    .to("file:files/output");
    }

}