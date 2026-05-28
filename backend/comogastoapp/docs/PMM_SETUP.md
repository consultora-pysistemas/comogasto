# Percona PMM - Monitoreo de PostgreSQL

## Descripción

Percona Monitoring and Management (PMM) ha sido agregado a la infraestructura de comogastoapp para proporcionar monitoreo centralizado y alertas para:

- PostgreSQL Primary
- PostgreSQL Replica
- Redis
- Kafka
- Otros servicios

## Acceso a PMM

### URL
```
http://localhost:8443
```

### Credenciales por defecto
- **Usuario**: `admin`
- **Contraseña**: `admin`

> ⚠️ **IMPORTANTE**: Cambiar las credenciales por defecto en producción.

## Inicio rápido

### 1. Iniciar todos los servicios
```powershell
docker compose up -d
```

### 2. Esperar a que PMM Server esté listo
```powershell
docker compose logs pmm-server --follow
```

Busca el mensaje: `"Go to"` cuando esté listo para aceptar conexiones.

### 3. Acceder a la interfaz web
```
http://localhost:8443/graph/
```

## Registrar nodos manualmente

Si por alguna razón los nodos no se registran automáticamente, puedes hacerlo manualmente:

### Opción 1: Usar la interfaz web

1. Ve a **Administration** → **Add Monitoring Services**
2. Selecciona **PostgreSQL**
3. Ingresa los datos:
   - **Hostname/IP**: `postgres-primary` (o `postgres-replica`)
   - **Port**: `5432`
   - **Username**: `postgres`
   - **Password**: (desde `.env` `POSTGRES_PASSWORD`)
   - **Service Name**: `pg-primary` (o `pg-replica`)

### Opción 2: Usar pmm-agent desde CLI

```bash
pmm-admin add postgresql --username=postgres --password=asdf \
  --host=postgres-primary --port=5432 pg-primary
```

## Características principales

### Dashboards disponibles
- **Database Overview**: Vista general de PostgreSQL
- **PostgreSQL Instances**: Detalles por instancia
- **Replication Status**: Estado de la replicación primario/réplica
- **Query Analytics**: Análisis de queries más lentas
- **System Overview**: Métricas del sistema (CPU, memoria, disco)

### Métricas monitoreadas

**PostgreSQL:**
- Conexiones activas
- Transacciones por segundo
- Cache hit ratio
- Tamaño de WAL
- Lag de replicación
- Buffer cache
- Locks

**Sistema:**
- CPU
- Memoria
- Diskspace
- I/O

## Configuración

### Variables de entorno (`.env`)
```dotenv
PMM_SERVER_PORT=8443        # Puerto HTTP de PMM
```

### Persistencia de datos
Los datos de PMM se persisten en el volumen Docker:
```
pmm_server_data:/srv (dentro del contenedor)
```

## Troubleshooting

### PMM Server no inicia

```powershell
# Ver logs
docker compose logs pmm-server

# Borrar volumen y reintentar (borra todos los datos)
docker compose down -v
docker compose up -d pmm-server
```

### No se puede conectar a PostgreSQL desde PMM

1. Verifica que PostgreSQL esté corriendo:
   ```powershell
   docker compose ps postgres-primary postgres-replica
   ```

2. Prueba conectividad manual:
   ```bash
   docker exec comogastoapp-pmm-server bash -c \
     "psql -h postgres-primary -U postgres -d postgres -c 'SELECT version();'"
   ```

3. Verifica credenciales en `.env`:
   ```powershell
   cat .env | grep POSTGRES
   ```

## Links útiles

- [Documentación oficial de Percona PMM](https://docs.percona.com/percona-monitoring-and-management/)
- [PostgreSQL Monitoring Best Practices](https://docs.percona.com/percona-monitoring-and-management/get-started/setting-up-monitoring/postgres.html)
- [Dashboard Customization](https://docs.percona.com/percona-monitoring-and-management/get-started/grafana.html)

## Próximas mejoras

- [ ] Integrar alertas con sistemas externos (Slack, PagerDuty)
- [ ] Configurar backup/exportación de métricas
- [ ] Sintonizar umbrales de alertas
- [ ] Crear dashboards personalizados por tenant

