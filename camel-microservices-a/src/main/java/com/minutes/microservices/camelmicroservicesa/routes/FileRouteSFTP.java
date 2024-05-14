package com.minutes.microservices.camelmicroservicesa.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

// docker run --name sftp1 -v sftp/sftp1:/home/john/upload -p 2222:22 -d atmoz/sftp john:p@ssw0rd:1001

// sftp://[username@]hostname[:port]/directoryname[?options]
// sftp://john@localhost:2222/home/john/upload?username=john&password=p@ssw0rd

//@Component
public class FileRouteSFTP extends RouteBuilder {

    @Override
    public void configure() throws Exception {
                from("sftp://john@localhost:2222/upload?username=john&password=p@ssw0rd")
                .log("Arquivo ${header.CamelApache} recebido com sucesso.")
                        .log("Enviando uma copia para pasta local...")
                        .to("file:files/output")
                        .log("Arquivo ${header.CamelApache} recebido com sucesso.");
    }
}
