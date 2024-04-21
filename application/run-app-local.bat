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

REM Exécution du fichier JAR
echo Exécution du fichier JAR...
java -jar target/tondeuse-application.jar
