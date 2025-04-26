# User API - Evaluación Técnica Java (NTT)

Esta aplicación es una API RESTful desarrollada con Spring Boot que permite la gestión de usuarios, cumpliendo con los requisitos de la evaluación:

- Creación de usuarios (POST /usuarios)
- Listado de usuarios (GET /usuarios)
- Búsqueda de usuario por ID (GET /usuarios/{id})
- Actualización completa de usuario (PUT /usuarios/{id})
- Actualización parcial de usuario (PATCH /usuarios/{id})
- Eliminación de usuario (DELETE /usuarios/{id})

Todos los endpoints aceptan y retornan datos en formato **JSON**.
Todos los mensajes de error siguen el formato:

```json
{"mensaje": "detalle del error"}
```

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (Base de datos en memoria)
- JWT (Json Web Tokens)
- Maven
- Lombok
- Swagger (para documentación automática de la API)

## Instalación y Ejecución

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/isaiascalderon26/userapi-ntt.git
   ```
2. Ingresar al proyecto:
   ```bash
   cd userapi-ntt
   ```
3. Construir el proyecto:
   ```bash
   mvn clean install
   ```
4. Ejecutar la aplicación:
   ```bash
   mvn spring-boot:run
   ```

La aplicación quedará disponible en:

```
http://localhost:8080
```

## Endpoints disponibles

| Método | Ruta               | Descripción                         |
|:-------:|:------------------:|:---------------------------------:|
| POST    | `/usuarios`         | Crear un nuevo usuario            |
| GET     | `/usuarios`         | Listar todos los usuarios         |
| GET     | `/usuarios/{id}`    | Obtener un usuario por ID         |
| PUT     | `/usuarios/{id}`    | Actualizar todos los datos        |
| PATCH   | `/usuarios/{id}`    | Actualizar parcialmente los datos |
| DELETE  | `/usuarios/{id}`    | Eliminar un usuario               |

## Base de Datos en Memoria (H2)

- JDBC URL: `jdbc:h2:mem:testdb`
- Usuario: `sa`
- Contraseña: (vacía)

Consola H2 disponible en:
```
http://localhost:8080/h2-console
```

## Consideraciones

- Todos los correos deben tener formato válido.
- Las contraseñas deben contener al menos 8 caracteres entre letras y números.
- Si un correo ya existe, se retorna un error.
- Al crear un usuario, se genera automáticamente un token JWT.
- Las fechas de creación, modificación y último login se manejan de forma automática.

## Documentación de la API (Swagger)

Al levantar la aplicación, puedes ver la documentación Swagger en:

```
http://localhost:8080/swagger-ui/index.html
```

Desde allí podrás probar los endpoints de forma fácil.

## Autor

Isaias Calderón

