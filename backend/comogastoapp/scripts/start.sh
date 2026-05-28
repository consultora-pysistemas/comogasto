#!/usr/bin/env bash
set -e

echo "Starting ComoGastoApp Platform..."

docker compose up -d

echo "ComoGastoApp Platform started."
docker compose ps