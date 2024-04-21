# Utilisation de l'image openjdk:21-jdk-slim
FROM openjdk:21-jdk-slim

# Copie du JAR dans le conteneur
COPY target/tondeuse-application.jar /app/tondeuse-application.jar

# Définition du répertoire de travail
WORKDIR /app

# Point d'entrée de l'application
CMD ["java", "-jar", "/app/tondeuse-application.jar"]


# pour debugger
#CMD ["bash", "-c", "while true; do sleep 60; done"]