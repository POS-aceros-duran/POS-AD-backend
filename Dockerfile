FROM gradle:jdk21-jammy

# Variables de entorno para la versión de Gradle
ENV GRADLE_HOME=/opt/gradle
ENV PATH=${GRADLE_HOME}/bin:${PATH}
ENV UNSISMILE_DATABASE=jdbc:mysql://pos_db:3306/aceros_duran?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false&useTimezone=true&serverTimezone=America/Mexico_City&useLegacyDatetimeCode=false
ENV DATABASE_USERNAME=root
ENV DATABASE_PASSWORD=password
ENV DATABASE_PLATFORM=org.hibernate.dialect.MySQLDialect
ENV DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver

# Crear directorio para la aplicación
WORKDIR /app

# Copiar solo los archivos de configuración de Gradle para cachear las dependencias
COPY build.gradle settings.gradle ./

# Descargar dependencias de Gradle
RUN gradle build --no-daemon || return 0

# Copiar el código fuente de la aplicación
COPY src ./src

# Construir la aplicación
RUN gradle build --no-daemon

# Exponer el puerto 8080
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "build/libs/unsiSmile-0.0.1-SNAPSHOT.jar"]
