#!/usr/bin/env bash
set -e

echo "Checking ComoGastoApp Platform services..."

curl -f http://localhost:8761/actuator/health || true
curl -f http://localhost:8888/actuator/health || true
curl -f http://localhost:8080/actuator/health || true
curl -f http://localhost:9090/-/healthy || true
curl -f http://localhost:3000/api/health || true

echo "Healthcheck finished."