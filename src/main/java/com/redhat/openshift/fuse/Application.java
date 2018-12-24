package com.redhat.openshift.fuse;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@SpringBootApplication
//@ImportResource("classpath:amq.xml")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Component
    class HelloAMQ extends RouteBuilder {

        @Override
        public void configure() {
            restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json);

            rest()
                //.post("/send").to("direct:sendMessage")
                .get("/hello").to("direct:hello");

            //from("direct:sendMessage").log("sendMessage").to("amq://hello");

            from("direct:hello").setBody(constant("Hello AMQ! (FEATURE/1)"));

            //from("amq://hello").log(body().toString());
        }
    }

    class Message implements Serializable {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
