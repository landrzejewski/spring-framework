# Docker
docker compose up

# Podman
podman run --name postgres -d -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=payments -p 5432:5432 docker.io/library/postgres:15
