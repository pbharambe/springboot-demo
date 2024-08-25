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

## Actuator

To enable Actuator include this spring dependency --
```xml

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
```
Custom actuator 

---
## Caching in SpringBoot

```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
```
---
## Commit Message Style

Enforcing a consistent Git commit message style helps improve readability and maintainability in projects.

**Guidelines --**
- **Format:** A common convention is the following:
- **Header:** 50 characters or less summarizing the change.
- **Body:** Optionally, a more detailed description of the change (wrapped at 72 characters per line).
- **Create a Commit Message Hook :** The hook file will be located in your Git repository under ``.git/hooks/commit-msg``.
- After creating the ``commit-msg file``, make it executable:
```bash
  chmod +x .git/hooks/commit-msg
```
- Now, every time you commit, this hook will run, and it will enforce the commit message style. 
If the message doesn't comply, the commit will be rejected, and you'll be prompted to correct the message.

### Make the Hook Portable and Version Controlled
- **Place the Hook in Your Repository :** Store the hook script in your repository, say under ``scripts/commit-msg``.
- **Instruct Developers to Set Up the Hook :** Developers can copy or symlink this script to ``.git/hooks/commit-msg`` in their local repositories. 
Alternatively, you can automate this setup with Maven.

    **Automate with Setup**
    ```shell
        # Inside a setup script
        cp scripts/commit-msg .git/hooks/commit-msg
        chmod +x .git/hooks/commit-msg
    ```
  **Automate with Maven**
  ```xml
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-antrun-plugin</artifactId>
            <version>3.1.0</version>
            <executions>
                <execution>
                    <phase>initialize</phase>
                    <goals>
                        <goal>run</goal>
                    </goals>
                    <configuration>
                        <target>
                            <copy file="${project.basedir}/scripts/commit-msg"
                                  todir="${project.basedir}/.git/hooks/"
                                  overwrite="true"/>
                            <chmod file="${project.basedir}/.git/hooks/commit-msg"
                                   perm="755"/>
                        </target>
                    </configuration>
                </execution>
            </executions>
        </plugin>
  ```

---

---