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
| progreso-servicio | 8090 | Registro de progreso físico de clientes |

---

## Comunicación entre servicios

Dos pares de servicios están conectados mediante **WebClient** (Spring WebFlux):

**registro-servicio → pago-servicio**
`registro-servicio` consulta los datos de un pago al momento de recuperar un registro de socio. El endpoint `GET /api/v0/registros/conPago/{id}` devuelve el registro junto con la información del pago asociado.

**reserva-servicio → cliente-servicio**
`reserva-servicio` consulta los datos del cliente al gestionar una reserva, siguiendo el mismo patrón de comunicación.

---

## Características por servicio

| Servicio | Swagger | Security | Validaciones | HATEOAS | Tests |
|---|:---:|:---:|:---:|:---:|---|
| membrecia-servicio | ✅ | ❌ | ❌ | ✅ | Controller, Service |
| contrato-servicio | ✅ | ❌ | ❌ | ✅ | Controller, Service |
| asistencia-servicio | ✅ | ❌ | ❌ | ✅ | Controller, Service |
| pago-servicio | ✅ | ✅ HTTP Basic | ✅ | ✅ | Controller, Service |
| entrenador-servicio | ✅ | ❌ | ❌ | ✅ | Controller, Service |
| registro-servicio | ✅ | ❌ | ❌ | ✅ | Controller, Service |
| cliente-servicio | ✅ | ✅ HTTP Basic | ✅ | ✅ | Controller, Service, Model, Repository |
| reserva-servicio | ✅ | ❌ | ✅ | ✅ | Controller, Service, Model, Repository |
| actividades-deportivas-servicio | ✅ | ❌ | ✅ | ✅ | Controller, Service, Model, Repository |
| progreso-servicio | ✅ | ❌ | ⚠️ parcial | ✅ | Controller, Service |

---

## Seguridad (Spring Security)

Dos servicios implementan autenticación **HTTP Basic**:

**cliente-servicio (puerto 8087)**
- `GET /api/usuarios/**` y `POST /api/usuarios/verificar` → públicos
- `POST`, `PUT`, `DELETE /api/usuarios/**` → requieren autenticación
- Swagger UI siempre accesible sin credenciales

**pago-servicio (puerto 8084)**
- Todos los endpoints requieren autenticación, incluido Swagger UI
- Credenciales definidas vía auto-configuración de Spring Boot (`spring.security.user.*` en `application.properties`)

> Las credenciales por defecto se encuentran en el `application.properties` de cada servicio.

---

## Documentación API (Swagger / OpenAPI)

Todos los servicios exponen documentación interactiva mediante **springdoc-openapi**. Una vez iniciado cada servicio, la UI está disponible en:

```
http://localhost:{puerto}/swagger-ui/index.html
```

Por ejemplo:
- `http://localhost:8081/swagger-ui/index.html` → membrecia-servicio
- `http://localhost:8090/swagger-ui/index.html` → progreso-servicio

En `cliente-servicio`, Swagger UI está configurado como ruta pública. En `pago-servicio`, Swagger UI también requiere autenticación al igual que el resto de los endpoints.

---

## Validaciones (Bean Validation)

Los siguientes servicios aplican validaciones con `jakarta.validation` en sus modelos y controladores:

- **pago-servicio** — `socioId`, `monto`, `metodoPago` y `estadoPago` son obligatorios; maneja errores con `GlobalExceptionHandler`
- **cliente-servicio** — campos obligatorios en el modelo de cliente
- **reserva-servicio** — campos obligatorios en el modelo de reserva
- **actividades-deportivas-servicio** — campos obligatorios en el modelo de actividad
- **progreso-servicio** — `clienteId`, `peso` y `porcentajeGrasa` tienen `@NotNull` en el modelo, pero el controlador **no usa `@Valid`**, por lo que las validaciones no se activan en tiempo de ejecución.

Los errores de validación son capturados globalmente por un `GlobalExceptionHandler` en los servicios que lo implementan completamente.

---

## HATEOAS

Todos los servicios implementan **Spring HATEOAS** mediante un `Assembler` dedicado que enriquece las respuestas con enlaces hipermedia (`_links`) apuntando a los recursos relacionados.

---

## Tests

Cada servicio incluye tests unitarios con JUnit 5 y Mockito. Los servicios más completos incluyen:

| Cobertura | Servicios |
|---|---|
| Controller + Service + Model + Repository | cliente-servicio, reserva-servicio, actividades-deportivas-servicio |
| Controller + Service | membrecia-servicio, contrato-servicio, asistencia-servicio, pago-servicio, entrenador-servicio, registro-servicio, progreso-servicio |

Los tests de repositorio y modelo usan base de datos H2 en memoria (incluida como dependencia de test en los servicios correspondientes).

---

## Docker

Cada microservicio incluye un `Dockerfile` y un `docker-compose.yml` propios. El compose de cada servicio levanta dos contenedores: la base de datos MySQL y la aplicación Spring Boot.

### Estructura por servicio

```
Dockerfile            # Imagen basada en eclipse-temurin:17-jdk-alpine
docker-compose.yml    # Define el servicio MySQL + la aplicación
```

### Ejecutar un servicio con Docker

1. Compilar el JAR primero:
```bash
mvn clean package -DskipTests
```

2. Levantar con Docker Compose:
```bash
docker-compose up --build
```

El `docker-compose.yml` configura automáticamente la conexión a MySQL mediante variables de entorno (`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`) y espera a que MySQL esté saludable antes de iniciar la aplicación (`depends_on` con `healthcheck`).

---

## Estructura de cada servicio

Cada microservicio sigue la misma estructura base:

```
servicio/
├── Controller/         # Endpoints REST
├── Service/            # Lógica de negocio
├── Repository/         # Acceso a datos (JPA)
├── Model/              # Entidad de base de datos
├── Assembler/          # Ensamblador HATEOAS
├── config/
│   ├── OpenApiConfig   # Configuración Swagger
│   └── SecurityConfig  # (solo cliente-servicio y pago-servicio)
├── exception/
│   └── GlobalExceptionHandler
├── Dockerfile
├── docker-compose.yml
└── resources/
    └── application.properties
```

---

## Requisitos

- Java 17
- Maven 3.5+
- MySQL (Laragon recomendado para entorno local)
- Docker y Docker Compose (opcional, para ejecución containerizada)

---

## Ejecución local

Cada servicio requiere su propia base de datos MySQL creada previamente. Luego ejecutar en cada carpeta:

```bash
mvn spring-boot:run
```

Las tablas se crean automáticamente al iniciar gracias a `spring.jpa.hibernate.ddl-auto=update`.

---

## Dependencias principales

- Spring Boot 3.4.5
- Spring Web
- Spring Data JPA
- Spring HATEOAS
- Spring Reactive Web (WebClient)
- Spring Security (cliente-servicio, pago-servicio)
- Spring Validation (pago-servicio, cliente-servicio, reserva-servicio, actividades-deportivas-servicio, progreso-servicio)
- springdoc-openapi (Swagger UI) — todos los servicios
- MySQL Driver
- H2 (scope test)
- Lombok
