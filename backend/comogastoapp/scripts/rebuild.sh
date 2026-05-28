#!/usr/bin/env bash
set -e

echo "Rebuilding ComoGastoApp Platform..."

docker compose down
docker compose build --no-cache
docker compose up -d

echo "ComoGastoApp Platform rebuilt."