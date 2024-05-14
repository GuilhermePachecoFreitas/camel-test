package com.minutes.microservices.camelmicroservicesa.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

//@Component
public class FileRouteByFilter extends RouteBuilder {

    @Override
    public void configure(){
        from("file:files/input")
               .filter(header(Exchange.FILE_NAME).startsWith("10"))
                .log("Processando o arquivo ${header.CamelFileName}")
                .to("file:files/output");

    }

}