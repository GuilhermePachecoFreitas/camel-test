package com.minutes.microservices.camelmicroservicesa.routes.a;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class MyFirstTimerRouter extends RouteBuilder {

    @Autowired
    private  getBeanTimer getBeanTimer;

    @Autowired
    private getLogProcess getLogProcess;

    @Override
    public void configure() throws Exception {
        // queue = timer
        // transformation
        // database = log
        // Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]

        from("timer:frist-timer") // null
                //.transform().constant("Minha constante")
                //.transform().constant("O horário é " + LocalDateTime.now())
                //.bean("getBeanTimer")
                .bean(getBeanTimer)
                .bean(getLogProcess)
                .to("log:frist-timer");

    }
}
@Component
class getBeanTimer {
    public String getCurrentTimer(){
        return "O horário é " + LocalDateTime.now();
    }
}

@Component
class getLogProcess {
    private final Logger logger = LoggerFactory.getLogger(getLogProcess.class);

    public void process(String message){
        logger.info("getLogProcess {}", message);
    }
}