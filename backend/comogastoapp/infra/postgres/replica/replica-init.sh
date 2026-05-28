#!/usr/bin/env bash
set -e

DATA_DIR="/var/lib/postgresql/data"
REPLICA_MAX_CONNECTIONS="${POSTGRES_REPLICA_MAX_CONNECTIONS:-300}"

if [ ! -s "$DATA_DIR/PG_VERSION" ]; then
  echo "Initializing PostgreSQL replica..."

  # Ensure replication role exists even when primary volume was initialized earlier.
  PGPASSWORD="$POSTGRES_PASSWORD" psql \
    -h "$POSTGRES_PRIMARY_HOST" \
    -U "$POSTGRES_USER" \
    -d "$POSTGRES_DB" \
    -v ON_ERROR_STOP=1 <<SQL
DO \
\$\$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = '${POSTGRES_REPLICATION_USER}') THEN
    EXECUTE format('CREATE ROLE %I WITH REPLICATION LOGIN PASSWORD %L', '${POSTGRES_REPLICATION_USER}', '${POSTGRES_REPLICATION_PASSWORD}');
  ELSE
    EXECUTE format('ALTER ROLE %I WITH REPLICATION LOGIN PASSWORD %L', '${POSTGRES_REPLICATION_USER}', '${POSTGRES_REPLICATION_PASSWORD}');
  END IF;
END
\$\$;
SQL

  rm -rf "$DATA_DIR"/*

  PGPASSWORD="$POSTGRES_REPLICATION_PASSWORD" pg_basebackup \
    -h "$POSTGRES_PRIMARY_HOST" \
    -D "$DATA_DIR" \
    -U "$POSTGRES_REPLICATION_USER" \
    -v \
    -P \
    -R \
    -X stream

  chown -R postgres:postgres "$DATA_DIR"
  chmod 700 "$DATA_DIR"
fi

exec docker-entrypoint.sh postgres -c "max_connections=${REPLICA_MAX_CONNECTIONS}"
