# Sistema de Gestión de Alumnos

## Descripción del proyecto

API REST desarrollada con Spring Boot para la gestión de alumnos. Implementa operaciones CRUD completas con autenticación mediante Spring Security, validación de datos y documentación automática con Swagger/OpenAPI.


## Instrucciones de instalación

1. Clonar el repositorio:
git clone <https://github.com/inesgarcia-nervion/Spring-Boot.git>
cd Proyecto

2. Compilar el proyecto:
mvnw clean install

3. Ejecutar la aplicación:
mvnw spring-boot:run

4. La aplicación estará disponible en: http://localhost:8082


## Configuración de base de datos

El proyecto utiliza H2, una base de datos en memoria.

Consola H2:
- URL: http://localhost:8082/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: (dejar vacío)

Configuración en application.properties:

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=


Nota: La base de datos se reinicia al detener la aplicación.



## Credenciales de prueba

Usuario: admin
Contraseña: 1234



## Ejemplos de uso de la API

Documentación interactiva disponible en: http://localhost:8082/swagger-ui/index.html

Crear un alumno:

POST http://localhost:8082/alumnos
Authorization: Basic admin:1234
Content-Type: application/json

{
  "nombre": "Inés García",
  "email": "ines.garcia@gmail.com"
}



Listar todos los alumnos:

GET http://localhost:8082/alumnos
Authorization: Basic admin:1234

Obtener un alumno por ID:
GET http://localhost:8082/alumnos/1
Authorization: Basic admin:1234



Actualizar un alumno:

PUT http://localhost:8082/alumnos/1
Authorization: Basic admin:1234
Content-Type: application/json

{
  "nombre": "Nuevo Nombre",
  "email": "nuevo@gmail.com"
}


Eliminar un alumno:

DELETE http://localhost:8082/alumnos/1
Authorization: Basic admin:1234