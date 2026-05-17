# Sistema Gimnasio

Sistema de gestión de gimnasio construido con microservicios independientes en Spring Boot y MySQL.

---

## Microservicios

| Servicio | Puerto | Descripción |
|---|---|---|
| membrecia-servicio | 8081 | Gestión de membrecías |
| contrato-servicio | 8082 | Gestión de contratos |
| asistencia-servicio | 8083 | Control de asistencia |
| pago-servicio | 8084 | Gestión de pagos de socios |
| entrenador-servicio | 8085 | Gestión de entrenadores |
| registro-servicio | 8086 | Registro de socios → consume pago-servicio |
| cliente-servicio | 8087 | Gestión de clientes |
| reserva-servicio | 8088 | Reservas de actividades → consume cliente-servicio |
| actividades-deportivas-servicio | 8089 | Gestión de actividades deportivas |



---

## Comunicación entre servicios

Dos pares de servicios están conectados mediante **WebClient** (Spring WebFlux):

**pago-servicio → registro-servicio**
`registro-servicio` consulta los datos de un pago al momento de recuperar un registro de socio. El endpoint `GET /registros/conPago/{id}` devuelve el registro junto con la información del pago asociado.

**cliente-servicio → reserva-servicio**
`reserva-servicio` consulta los datos del cliente al gestionar una reserva, siguiendo el mismo patrón de comunicación.

---

## Estructura de cada servicio

Cada microservicio sigue la misma estructura:

```
servicio/
-- Controller/    # Endpoints REST
-- Service/       # Lógica de negocio
-- Repository/    # Acceso a datos (JPA)
-- Model/         # Entidad de base de datos
-- resources/
    -- application.properties
```

---

## Requisitos

- Java 17
- Maven 3.5.14
- MySQL (Laragon recomendado para entorno local)

---

## Ejecución

Cada servicio requiere su propia base de datos MySQL creada previamente. Luego ejecutar en cada carpeta:

```bash
mvn spring-boot:run
```

Las tablas se crean automáticamente al iniciar gracias a `spring.jpa.hibernate.ddl-auto=update`.

---

## Dependencias principales

- Spring Boot 3.5
- Spring Web
- Spring Data JPA
- Spring Reactive Web (WebClient)
- MySQL Driver
- Lombok
- Validation
