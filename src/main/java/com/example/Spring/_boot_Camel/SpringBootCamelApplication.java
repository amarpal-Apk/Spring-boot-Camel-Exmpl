package com.example.Spring._boot_Camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCamelApplication extends RouteBuilder {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCamelApplication.class, args);
    }

    @Override
    public void configure() throws Exception {
        System.out.println("Started....");
       //moveAllFiles();
      //moveSpecificFile("myFile");
        moveSpecificFileWithBody("Java");
        System.out.println("Finished....");
    }

    public void moveAllFiles() {
        from("file:E:/SpringBootCamp/Spring-boot-Camel/Folder1?noop=true").to("file:E:/SpringBootCamp/Spring-boot-Camel/Folder2");
    }

    public void moveSpecificFile(String type) {
        from("file:E:/SpringBootCamp/Spring-boot-Camel/Folder1?noop=true").filter(header(Exchange.FILE_NAME).startsWith(type)).to("file:E:/SpringBootCamp/Spring-boot-Camel/Folder2");
    }

    public void moveSpecificFileWithBody(String content) {
        from("file:E:/SpringBootCamp/Spring-boot-Camel/Folder1?noop=true").filter(body().contains(content)).to("file:E:/SpringBootCamp/Spring-boot-Camel/Folder2");
    }
}
