  #!/usr/bin/env bash
  set -e

  echo "Stopping ComoGastoApp Platform..."

  docker compose down

  echo "ComoGastoApp Platform stopped."