package com.minutes.microservices.camelmicroservicesa.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class FileRouteTimer extends RouteBuilder{

        @Override
        public void configure() throws Exception {
            // yyyy-MM-dd HH:mm:ss or yyyy-mm-dd'T'HH:mm:ss
            from("timer:my-timer?period=5s&repeatCount=10&time=2024-05-02 13:38:00")
                    .log("Hora: ${date:now:HH:mm:ss}");
        }
}