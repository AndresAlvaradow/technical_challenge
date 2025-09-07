FROM openjdk:17-jdk-slim

LABEL maintainer="Banco Microservicios"

# Variables de entorno
ENV SPRING_PROFILES_ACTIVE=docker

# Crear directorio de la aplicación
WORKDIR /app

# Copiar archivos Maven
COPY pom.xml .
COPY src ./src

# Instalar Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Compilar la aplicación
RUN mvn clean package -DskipTests

# Exponer puerto
EXPOSE 8081

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/microservicio-cliente-1.0.0.jar"]