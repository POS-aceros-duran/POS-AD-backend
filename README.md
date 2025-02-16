# Cómo levantar un ticket

1. Crear una rama con el código de la incidencia.
2. Subir la rama para cambiar el estado del ticket.

# Cómo hacer un Pull Request (PR)

1. Subir todos los cambios.
2. En GitHub, realizar el PR especificando que se hará a la rama `dev` y asignar un revisor.
3. Asegurarse de que los commits sigan el formato de [Conventional Commits](https://www.conventionalcommits.org/).

# Configuración del Proyecto Spring Boot

1. Clonar el repositorio (el proyecto es privado, asegúrate de tener acceso):
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    ```
2. Navegar al directorio del proyecto:
    ```bash
    cd POS-AD-backend
    ```
3. Importar el proyecto en tu IDE preferido (IntelliJ IDEA, Eclipse, etc.).
4. Configurar las propiedades de la aplicación en `src/main/resources/application.properties`.
5. Ejecutar la aplicación:
    ```bash
    ./gradlew bootRun
    ```

# Ejecutar Pruebas

Para ejecutar las pruebas unitarias, usar el siguiente comando:
```bash
./gradlew test
```

# Uso de Docker

Para construir la imagen Docker del proyecto, usar el siguiente comando:
```bash
docker build -t nombre-imagen .
```

Para ejecutar un contenedor Docker con la imagen construida, usar el siguiente comando:
```bash
docker run -p 8080:8080 nombre-imagen
```

# Uso de Docker Compose

Para levantar los servicios definidos en el archivo `docker-compose.yml`, usar el siguiente comando:
```bash
docker-compose up
```

Para detener y eliminar los contenedores, redes y volúmenes definidos en `docker-compose.yml`, usar el siguiente comando:
```bash
docker-compose down
```