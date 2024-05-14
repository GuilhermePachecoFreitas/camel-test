package com.minutes.microservices.camelmicroservicesa.routes.patterns;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.Headers;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component
public class EipPatternsRouter extends RouteBuilder {

    //1,2,3

    // null 1 => [1]
    // 1, 2 => [1,2]
    // 1,2 => [1,2,3]

    public class ArrayListAggregationStrategy implements org.apache.camel.AggregationStrategy {
        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            Object newBody = newExchange.getIn().getBody();
            ArrayList<Object> list = null;
            if (oldExchange == null) {
                list = new ArrayList<Object>();
                list.add(newBody);
                newExchange.getIn().setBody(list);
                return newExchange;
            } else {
                list = oldExchange.getIn().getBody(ArrayList.class);
                list.add(newBody);
                return oldExchange;
            }
        }
    }

    @Autowired
    SpliterComponent spliterComponent;
    @Autowired
    DynamicRouterBean dynamicRouterBean;

    @Override
    public void configure() throws Exception {
        //Pipeline = Normal way

        // Content Base = You choose what to do

        // MultiCast = Can send mensage to various endpoints at same time

//        from("timer:multicast?period=10000")
//                .multicast()
//                .to("log:something1", "log:something2");

//        from("file:files/csv")
//                .unmarshal().csv()
//                .split(body())
//                .to("activemq:split-queue");

        //Message,Message2,Message3

//          from("file:files/csv")
//                  .convertBodyTo(String.class)
//                  //.split(body(), ",")
//                  .split(method(spliterComponent))
//                  .to("activemq:split-queue");

        // Aggregation

//        from("file:files/aggregate-json")
//                .unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
//                .aggregate(simple("${body.to}"), new ArrayListAggregationStrategy() )
//                .completionSize(3)
//                //.completionTimeout(HIGHEST)
//                .to("log:aggregate-json");

        //String routingSlip = "direct:endpoint1,direct:endpoint2,direct:endpoint3";

//		from("timer:routingSlip?period=10000")
//		.transform().constant("Chegaaaaaaa")
//		.routingSlip(simple(routingSlip));

        // Dynamic Routing

        from("timer:routingSlip?period={{timePeriod}}")
                .transform().constant("Estou indo atrás de voce")
                .dynamicRouter(method(dynamicRouterBean));



        from("direct:endpoint1")
                .wireTap("log:wire-tap") //add
                .to("{{endpoint-for-logging}}");

        from("direct:endpoint2")
                .to("log:directendpoint2");

        from("direct:endpoint3")
                .to("log:directendpoint3");

    }
}

@Component
class SpliterComponent{
    public List<String> spiltInput(){
        return List.of("ABC", "DEF", "GHI");
    }
}

@Component
class DynamicRouterBean{
    Logger logger = LoggerFactory.getLogger(DynamicRouterBean.class);
    int invocations ;

    public String decideTheNextEndpoint(
            @ExchangeProperties Map<String, String> properties,
            @Headers Map<String, String> headers,
            @Body String body
    ) {
        logger.info("{Isso é} { feio } {demais }", properties, headers, body);
        invocations++;
        if(invocations%3==0)
            return "direct:endpoint1";
        if(invocations%3==1)
            return "direct:endpoint2,direct:endpoint3";
        return null;
    }
}