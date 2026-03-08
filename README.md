# ForoHub API 🗨️

API REST desarrollada con **Spring Boot 3** para la gestión de tópicos de un foro. Permite crear, listar, detallar, actualizar y eliminar tópicos, con autenticación y autorización mediante **JWT**.

---

## 📋 Funcionalidades

- ✅ Crear un nuevo tópico
- ✅ Listar todos los tópicos activos (paginado)
- ✅ Ver el detalle de un tópico específico
- ✅ Actualizar un tópico existente
- ✅ Eliminar un tópico (borrado lógico)
- ✅ Autenticación con JWT
- ✅ Validaciones según reglas de negocio
- ✅ Persistencia con MySQL y migraciones con Flyway

---

## 🛠️ Tecnologías

- Java 17
- Spring Boot 3.3.10
- Spring Security
- Spring Data JPA
- Flyway Migration
- MySQL
- Lombok
- Auth0 Java JWT 4.5.0
- Maven

---

## 📁 Estructura del proyecto

```
src/main/java/com/forohub/api/
├── controller/
│   ├── AutenticacionController.java
│   └── TopicoController.java
├── domain/
│   ├── autor/
│   │   ├── Autor.java
│   │   └── AutorRepository.java
│   ├── curso/
│   │   ├── Curso.java
│   │   └── CursoRepository.java
│   ├── topico/
│   │   ├── Topico.java
│   │   ├── TopicoRepository.java
│   │   ├── StatusTopico.java
│   │   ├── DatosRegistroTopico.java
│   │   ├── DatosActualizacionTopico.java
│   │   ├── DatosDetalleTopico.java
│   │   └── DatosListaTopico.java
│   └── usuario/
│       ├── Usuario.java
│       ├── UsuarioRepository.java
│       ├── AutenticacionService.java
│       └── DatosAutenticacion.java
└── infra/
    ├── exceptions/
    │   └── GestorDeErrores.java
    └── security/
        ├── SecurityConfigurations.java
        ├── SecurityFilter.java
        ├── TokenService.java
        └── DatosTokenJWT.java

src/main/resources/
├── application.properties
└── db/migration/
    ├── V1__create-table-autores.sql
    ├── V2__create-table-cursos.sql
    ├── V3__create-table-topicos.sql
    └── V4__create-table-usuarios.sql
```

---

## 🗄️ Base de datos

El schema utilizado es `forohub`. Las tablas son generadas automáticamente por Flyway al iniciar la aplicación.

**Diagrama de tablas:**

```
autores       cursos        topicos                  usuarios
----------    ----------    --------------------     ----------
id (PK)       id (PK)       id (PK)                  id (PK)
nombre        nombre        titulo                   login
email         categoria     mensaje                  contrasena
                            fecha_creacion
                            status
                            autor_id (FK → autores)
                            curso_id (FK → cursos)
```

---

## ⚙️ Configuración

### Requisitos previos

- Java 17+
- MySQL 8+
- Maven 3.8+

### Pasos para ejecutar

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/forohub.git
   cd forohub
   ```

2. Crea el schema en MySQL:
   ```sql
   CREATE DATABASE forohub;
   ```

3. Configura tus credenciales en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost/forohub
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contrasena
   ```

4. (Opcional) Define tu secreto JWT como variable de entorno:
   ```bash
   export JWT_SECRET=tu_secreto_seguro
   ```

5. Ejecuta el proyecto:
   ```bash
   ./mvnw spring-boot:run
   ```

Flyway aplicará las migraciones automáticamente al iniciar.

---

## 🔐 Autenticación

Todos los endpoints (excepto `/login`) requieren un token JWT válido en el header de la petición.

### Obtener token

**POST** `/login`

```json
{
  "login": "usuario@email.com",
  "contrasena": "tu_contrasena"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Usa el token en las siguientes peticiones:
```
Authorization: Bearer <token>
```

---

## 📡 Endpoints

| Método   | Ruta           | Descripción                        | Auth |
|----------|----------------|------------------------------------|------|
| `POST`   | `/login`       | Iniciar sesión y obtener token JWT | ❌    |
| `POST`   | `/topicos`     | Crear un nuevo tópico              | ✅    |
| `GET`    | `/topicos`     | Listar tópicos activos (paginado)  | ✅    |
| `GET`    | `/topicos/{id}`| Ver detalle de un tópico           | ✅    |
| `PUT`    | `/topicos`     | Actualizar un tópico               | ✅    |
| `DELETE` | `/topicos/{id}`| Eliminar un tópico (borrado lógico)| ✅    |

### Ejemplo — Crear tópico

**POST** `/topicos`

```json
{
  "titulo": "¿Cómo usar Spring Security?",
  "mensaje": "Tengo dudas sobre la configuración de JWT en Spring Boot 3.",
  "autorId": 1,
  "cursoId": 1
}
```

### Ejemplo — Actualizar tópico

**PUT** `/topicos`

```json
{
  "id": 1,
  "titulo": "Título actualizado",
  "mensaje": "Mensaje actualizado",
  "autorId": 1,
  "cursoId": 2
}
```

---

## 📄 Licencia

Este proyecto fue desarrollado con fines educativos como parte del challenge **ForoHub** de Alura Latam.
