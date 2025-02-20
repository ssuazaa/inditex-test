# Inditex Test Project

## 📌 **Descripción**

Este documento detalla como fue construido el proyecto y también como ejecutarlo.

## 🛠 **Construcción**

El proyecto fue construido siguiendo una arquitectura hexagonal con las siguientes herramientas:

- **Lenguaje de programación:** Java 21
- **Framework:** Spring Boot 3.4.2
- **Herramienta de compilación:** Gradle 8.12.1

Algunas Librerías de terceros usadas:

- MapStruct 1.6.3
- OpenAPI 7.11.0
- SpringDoc 2.8.5

## 🗄️ Base de Datos

La base de datos usada fue H2, al momento de iniciarse la aplicación se crearan los siguientes
archivos en la raiz del proyecto:

- testdb.mv.db
- testdb.trace.db

La cadena de conexión es la siguiente:

   ```
    jdbc:h2:file:./testdb;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE
   ```

## 🚀 **Ejecución con Gradle**

1. **Sistemas Linux o MacOS:**

   ```bash
   ./gradlew bootRun
   ```

2. **Sistemas Windows:**

   ```bash
   gradlew.bat bootRun
   ```

## 🚀 **Ejecución con Docker**

1. **Construcción de la imagen:**

   ```bash
   docker build -t inditex-test:1.0.0 .
   ```

2. **Crear contenedor:**

   ```bash
   docker run --rm -p 8080:8080 --name inditex-test inditex-test:1.0.0
   ```

## 🧪 Tests

El módulo de prices cuenta con dos directorios unit (test unitarios) integration (test de
integración)

1. **Sistemas Linux o MacOS:**

    ```bash
    ./gradlew clean test
    ```

2. **Sistemas Windows:**

    ```bash
    gradlew.bat clean test
    ```

## 📚 **Recursos**

* **OpenAPI:** http://localhost:8080/openapi.yaml
* **Swagger:** http://localhost:8080/swagger-ui/index.html
