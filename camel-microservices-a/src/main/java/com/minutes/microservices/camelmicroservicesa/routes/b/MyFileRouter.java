package com.minutes.microservices.camelmicroservicesa.routes.b;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

// @Component
public class MyFileRouter extends RouteBuilder{

    @Autowired
    private DeciderBean deciderBean;

    @Override
    public void configure() throws Exception {
        // Pipeline

        from("file:files/input")
        //  .pipeline()
            .routeId("Rota de arquivos-input")
            .transform().body(String.class)
            .choice() //Content Based Rounting
                .when( simple("${file:ext} ends with 'xml' "))
                    .log("XML FILE")
            //.when( simple("${body} contains 'USD' "))
            .when(method(deciderBean))
            .log("NOT XML FILE, BUT with USD")
                .otherwise()
                    .log("Not an XML file")
            .end()
            //.to("direct:log-file-values")
            .to("file:files/output");

            from("direct:log-file-values")
                    .log("${messageHistory} ${file:absolute.path}")
                    .log("${file:name} ${file:name.ext} ${file:name.noext} ${file:onlyname}")
                    .log("${file:onlyname.noext} ${file:parent} ${file:path} ${file:absolute}")
                    .log("${routeId} ${camelId} ${body}");
    }
}

@Component
class DeciderBean{

    Logger logger = LoggerFactory.getLogger(DeciderBean.class);

    public boolean isThisCodition(@Body String body, @Headers Map<String,String> headers, @ExchangeProperties  Map<String,String> exchangeProperties){
        logger.info("DeciderBean {} Headers {} Properties {}", body, headers, exchangeProperties);
        return true;
    }

}