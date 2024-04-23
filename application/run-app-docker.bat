@echo off

REM Étape 1: Package de l'application avec Maven
echo Packaging de l'application avec Maven...
mvn clean package 2>&1 | (
    findstr /C:"BUILD FAILURE" > nul
    if %ERRORLEVEL% equ 0 (
        echo Construction Maven a échoué. Arrêt du script.
        exit /b 1
    )
)

REM Étape 2: Construction de l'image Docker
echo Construction de l'image Docker...
docker build -t tondeuse-app .

REM Étape 3: Exécution du conteneur Docker avec deux volumes
echo Exécution du conteneur Docker...
docker run ^
    -v .\src\main\resources\fileInput:/app/input/fileInput ^
    -v .\src\main\resources:/app/output ^
    --name tondeuse-container ^
    -e "SPRING_PROFILES_ACTIVE=docker" ^
    -p 9000:9000 ^
    -d tondeuse-app

REM Afficher le résultat
echo L'application est en cours d'exécution dans le conteneur Docker.
