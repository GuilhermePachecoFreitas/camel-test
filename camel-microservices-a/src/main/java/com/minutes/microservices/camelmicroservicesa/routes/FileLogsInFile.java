package com.minutes.microservices.camelmicroservicesa.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileLogsInFile extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:files/input")
                .log("Envio do arquivo: ${header.CamelFileName}")
                .to("file:files/output");

        from("timer:my-timer?period=5s")
                .setBody().constant("Espera acho que tem mais... ")
                .to("file:files/logs?fileName=logFile-${date:now:yyyy:MM/dd/HH_mm_ss}.log");
    }
}
