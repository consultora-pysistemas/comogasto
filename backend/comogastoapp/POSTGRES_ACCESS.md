# 🐘 PostgreSQL - Acceso Rápido

## Puertos

| Instancia       | Host      | Puerto   | Usuario                 | Contraseña |
|-----------------|-----------|----------|-------------------------|------------|
| **Primary**     | localhost | **5448** | postgres                | asdf       |
| **Replica**     | localhost | **5449** | postgres                | asdf       |
| **Replicactor** | (interno) | 5432     | comogastoapp-replicator | asdf       |

## Conexión desde tu máquina

### Opción A: CLI (psql)
```bash
# PostgreSQL Primary
psql -h localhost -p 5448 -U postgres -d postgres

# PostgreSQL Replica (read-only)
psql -h localhost -p 5449 -U postgres -d postgres
```

### Opción B: DBeaver / pgAdmin

1. **Crear nueva conexión**
2. **Rellenar campos:**
   - **Host**: localhost
   - **Port**: 5448 (primario) o 5449 (réplica)
   - **Database**: postgres (o comogasto)
   - **Username**: postgres
   - **Password**: asdf

### Opción C: Desde aplicación Java/Node/Python

**Connection String:**
```
# Primary (escritura)
jdbc:postgresql://localhost:5448/postgres?user=postgres&password=asdf

# Replica (lectura)
jdbc:postgresql://localhost:5449/postgres?user=postgres&password=asdf
```

## Verificar Replicación

### Primary - Ver estado de réplica
```bash
psql -h localhost -p 5448 -U postgres -d postgres
postgres# SELECT * FROM pg_stat_replication;
```

Deberías ver una fila con:
- `client_addr`: IP de réplica (172.19.0.3 en Docker)
- `state`: *streaming*
- `sync_state`: *async* o *sync*

### Replica - Verificar lag
```bash
psql -h localhost -p 5449 -U postgres -d postgres
postgres# SELECT now() - pg_last_xact_replay_time() AS replication_lag;
```

Deberías ver lag de milisegundos (cercano a 0).

## Bases de Datos

```bash
psql -h localhost -p 5448 -U postgres -c "\l"
```

Encontrarás:
- `postgres` (default)
- `comogasto` (aplicación)

### Schemas

```bash
psql -h localhost -p 5448 -U postgres -d comogasto -c "\dn"
```

Disponibles:
- `platform`
- `tenant`
- `auth`
- `iam`
- `audit`
- `tracing`
- `notification`
- `outbox`
- `inbox`

## Monitoreo

### Ver conexiones activas
```bash
# Primary
psql -h localhost -p 5448 -U postgres -d postgres \
  -c "SELECT pid, usename, application_name, state FROM pg_stat_activity;"

# Replica
psql -h localhost -p 5449 -U postgres -d postgres \
  -c "SELECT pid, usename, application_name, state FROM pg_stat_activity;"
```

### Ver tamaño de base de datos
```bash
psql -h localhost -p 5448 -U postgres -d postgres \
  -c "SELECT datname, pg_size_pretty(pg_database_size(datname)) FROM pg_database ORDER BY pg_database_size(datname) DESC;"
```

### Ver tamaño de tablas
```bash
psql -h localhost -p 5448 -U postgres -d comogasto \
  -c "SELECT schemaname, tablename, pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename)) FROM pg_tables ORDER BY pg_total_relation_size(schemaname||'.'||tablename) DESC LIMIT 20;"
```

## PMM Monitoring

Ver métricas en Grafana:
```
http://localhost:8443
Admin / admin
```

Dashboards:
- **PostgreSQL Overview**: Estado general
- **Database | PostgreSQL**: Instancias
- **Replication**: Lag y sync status
- **Query Analytics**: Queries lentas

## Backup & Recovery

### Crear backup desde primary
```bash
pg_dump -h localhost -p 5448 -U postgres -d comogasto > backup-$(date +%Y%m%d-%H%M%S).sql
```

### Restore en primary
```bash
psql -h localhost -p 5448 -U postgres -d comogasto < backup.sql
```

## Troubleshooting

### No se puede conectar
```bash
# Verificar que PostgreSQL esté corriendo
docker compose ps postgres-primary postgres-replica

# Ver logs
docker compose logs postgres-primary
docker compose logs postgres-replica
```

### Replicación rota
```bash
# Reiniciar réplica
docker compose restart postgres-replica

# Verificar logs de réplica
docker compose logs postgres-replica --tail 100
```

### Cliente psql no encontrado
```bash
# Usando Docker CLI
docker exec comogastoapp-postgres-primary psql -U postgres -d postgres -c "SELECT version();"
```

## Credenciales de Aplicación

Para conectar la aplicación Spring/Backend:

```yaml
# application.properties / application.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5448/comogasto
    username: postgres
    password: asdf
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
```

Para lectura en réplica (opcionalmente):
```yaml
# Réplica (read-only)
spring:
  datasource-replica:
    url: jdbc:postgresql://localhost:5449/comogasto
    username: postgres
    password: asdf
```

---

**¡Infraestructura PostgreSQL completamente lista!** 🚀

