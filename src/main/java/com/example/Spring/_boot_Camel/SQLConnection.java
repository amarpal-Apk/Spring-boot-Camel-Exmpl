package com.example.Spring._boot_Camel;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;

import java.util.Map;

public class SQLConnection {
    public static void main(String[] args) throws Exception {

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/camel_apache_tutorial");
        dataSource.setUser("root");
        dataSource.setPassword("MySQL@123");

        SimpleRegistry registry = new SimpleRegistry();
        registry.put("myDataSource", (Map<Class<?>, Object>) dataSource);

        CamelContext context = new DefaultCamelContext(registry);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .to("jdbc:myDataSource")
                        .bean(new ResultHandler(), "printResult");
            }
        });

        context.start();
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        producerTemplate.sendBody("direct:start", "SELECT * FROM users");
        Thread.sleep(2000);
        context.stop();
    }
}
