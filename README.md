# SpringBoot Demo


## Swagger API details

[Springdoc-openapi (swagger)](https://springdoc.org/#swagger-ui-properties)

To enable Swagger OpenAPI add dependency 
```xml
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.5.0</version>
    </dependency>
```

OpenAPI descriptions will be available at the path /v3/api-docs by default: ``http://localhost:8080/v3/api-docs/``

To use a custom path, we can indicate in the application.properties file:
``springdoc.api-docs.path=/api-docs``. Now api description will be access as ``http://localhost:8080/api-docs``.

Swagger UI will be available by default: ``http://localhost:8080/swagger-ui.html``
To customize the path of our API documentation. Modify our application.properties to include: ``springdoc.swagger-ui.path=/swagger-ui-custom.html``
So now our API documentation will be available at ``http://localhost:8080/swagger-ui-custom.html``

---