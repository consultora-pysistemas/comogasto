#!/usr/bin/env bash
set -e

DATA_DIR="/var/lib/postgresql/data"

if [ ! -s "$DATA_DIR/PG_VERSION" ]; then
  echo "Initializing PostgreSQL replica..."

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

exec docker-entrypoint.sh postgres