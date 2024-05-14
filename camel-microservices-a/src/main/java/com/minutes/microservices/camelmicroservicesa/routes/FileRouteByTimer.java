package com.minutes.microservices.camelmicroservicesa.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class FileRouteByTimer extends RouteBuilder {

    @Override
    public void configure(){
        // yyyy-MM-dd HH:mm:ss ou yyyy-mm-dd'T'HH:mm:ss = 24h

        from("timer:my-timer?time= 2024-05-06 17:09:00")
                .pollEnrich("file:files/input")
                .log("Movendo arquivo ${header.CamelFileName}")
                .to("file:files/output");
    }
}