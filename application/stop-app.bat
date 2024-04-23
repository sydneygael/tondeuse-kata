@echo off

REM Stopper le conteneur Docker
echo "Arrêt du conteneur Docker..."
docker stop tondeuse-container

REM Supprimer le conteneur Docker
echo "Suppression du conteneur Docker..."
docker rm tondeuse-container

REM Supprimer l'image Docker
echo "Suppression de l'image Docker..."
docker rmi tondeuse-app
