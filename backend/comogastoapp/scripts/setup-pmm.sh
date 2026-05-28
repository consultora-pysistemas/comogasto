#!/usr/bin/env bash
set -e

# Setup Percona PMM: Auto-register PostgreSQL nodes
PMM_SERVER_HOST="${PMM_SERVER_HOST:-pmm-server}"
PMM_SERVER_PORT="${PMM_SERVER_PORT:-80}"
PMM_ADMIN_PASSWORD="${PMM_ADMIN_PASSWORD:-admin}"

# Wait for PMM Server to be ready
echo "Waiting for PMM Server to be ready at http://${PMM_SERVER_HOST}:${PMM_SERVER_PORT}..."
until curl -sf "http://${PMM_SERVER_HOST}:${PMM_SERVER_PORT}/graph/" > /dev/null; do
  echo "PMM Server not ready yet, waiting..."
  sleep 5
done
echo "PMM Server is ready!"

# Function to add PostgreSQL node to PMM
add_postgres_node() {
  local node_name=$1
  local postgres_host=$2
  local postgres_port=$3
  local postgres_user=$4
  local postgres_password=$5

  echo "Adding PostgreSQL node to PMM: ${node_name} (${postgres_host}:${postgres_port})..."

  # Use pmm-admin CLI to register the node
  # Note: This requires pmm-agent running on a system with connectivity
  # Alternative: Use REST API endpoint

  curl -X POST "http://${PMM_SERVER_HOST}:${PMM_SERVER_PORT}/v1/management/PostgreSQL/AddNode" \
    -H "Content-Type: application/json" \
    -d '{
      "node_name": "'${node_name}'",
      "host": "'${postgres_host}'",
      "port": '${postgres_port}',
      "username": "'${postgres_user}'",
      "password": "'${postgres_password}'"
    }' || echo "Node may already exist or API not ready yet"
}

# Register PostgreSQL Primary
add_postgres_node \
  "comogastoapp-postgres-primary" \
  "${POSTGRES_PRIMARY_HOST:-postgres-primary}" \
  "${POSTGRES_PRIMARY_PORT:-5448}" \
  "${POSTGRES_USER:-postgres}" \
  "${POSTGRES_PASSWORD:-asdf}"

# Register PostgreSQL Replica
add_postgres_node \
  "comogastoapp-postgres-replica" \
  "${POSTGRES_REPLICA_HOST:-postgres-replica}" \
  "${POSTGRES_REPLICA_PORT:-5449}" \
  "${POSTGRES_REPLICATION_USER:-postgres}" \
  "${POSTGRES_REPLICATION_PASSWORD:-asdf}"

echo "PMM setup complete!"

