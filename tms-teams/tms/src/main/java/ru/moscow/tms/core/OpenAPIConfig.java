package ru.moscow.tms.core;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class OpenAPIConfig {
    @Value("${ru_moscow_tms.openapi.dev-url}")
    private String devUrl;

    @Value("${ru_moscow_tms.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("DEV TMS server system");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("PROD TMS server system");

        Contact contact = new Contact();
        contact.setEmail("kv.russia@gmail.com");
        contact.setName("Koss Volkov");
        contact.setUrl("https://go-koss.ru");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("TMS API")
                .version("v0.0.1")
                .contact(contact)
                .description("This API exposes test management system.").termsOfService("https://go-koss.ru")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
