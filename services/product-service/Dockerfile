# ───────── ETAPA 1: compilar ─────────
FROM maven:3.9-eclipse-temurin-21 AS build
# imagen base con Maven + JDK 21. "AS build" le pone nombre a esta etapa

WORKDIR /app
# carpeta de trabajo dentro de la imagen (como hacer "cd /app")

COPY pom.xml .
# copia SOLO el pom.xml primero

RUN mvn dependency:go-offline -B
# descarga todas las dependencias del pom (-B = sin colores ni preguntas)

COPY src ./src
# ahora sí copia el código fuente

RUN mvn package -DskipTests -B
# compila y genera el .jar en /app/target/. Los tests correrán en el pipeline


# ───────── ETAPA 2: ejecutar ─────────
FROM eclipse-temurin:21-jre
# imagen base solo con Java para EJECUTAR (sin Maven, sin JDK completo)

WORKDIR /app

RUN useradd --system --uid 1001 appuser
# crea un usuario sin privilegios

COPY --from=build /app/target/*.jar app.jar
# trae SOLO el .jar desde la etapa "build". Todo lo demás se descarta

USER appuser
# la app correrá con ese usuario, no como root

EXPOSE 8081
# documenta el puerto que usa la app (el de tu application.yaml)

ENTRYPOINT ["java", "-jar", "app.jar"]
# el comando que se ejecuta al arrancar el contenedor