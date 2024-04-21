#!/bin/sh

# Étape 1: Package de l'application avec Maven
echo "Packaging de l'application avec Maven..."
mvn clean package 2>&1 | {
    grep -q "BUILD FAILURE"
    if [ $? -eq 0 ]; then
        echo "Construction Maven a échoué. Arrêt du script."
        exit 1
    fi
}

# Exécution du fichier JAR
echo "Exécution du fichier JAR..."
java -jar target/tondeuse-application.jar