# 🎯 Percona PMM - Instalado y Listo

Se ha agregado **Percona Monitoring and Management (PMM)** para proveer monitoreo centralizado de PostgreSQL.

## Acceso inmediato

```
URL: http://localhost:8443
Usuario: admin
Contraseña: admin
```

## Qué se agregó

### 1. Docker Compose
- ✅ Servicio `pmm-server` (percona/pmm-server:latest)
- ✅ Volumen persistente: `pmm_server_data`
- ✅ Health check automático
- ✅ Puerto configurable: `PMM_SERVER_PORT=8443`

### 2. Variables de Entorno
- ✅ `PMM_SERVER_PORT=8443` en `.env` y `.env.example`
- ✅ Retención de métricas: 30 días
- ✅ Actualizaciones deshabilitadas en dev

### 3. Infraestructura
- ✅ Script de setup: `scripts/setup-pmm.sh` (auto-registrar nodos)
- ✅ Documentación: `docs/PMM_SETUP.md`
- ✅ ADR: `docs/decisiones/ADR-004 - Percona PMM.md`
- ✅ Targets en Makefile:
  ```bash
  make pmm-up      # Iniciar PMM Server
  make pmm-logs    # Ver logs
  make pmm-status  # Estado
  make pmm-url     # Ver URL + credenciales
  make pmm-down    # Detener
  ```

## Primeros pasos

### 1. Verificar que todo esté arriba
```powershell
docker compose ps
```
Debes ver `comogastoapp-pmm-server` en estado **Up**.

### 2. Acceder a PMM
Abre en navegador:
```
http://localhost:8443
```

### 3. Cambiar contraseña (importante en producción)
- Ve a **Menu** → **Administration** → **Settings**
- Busca **Security**
- Cambia la contraseña de `admin`

### 4. Registrar nodos PostgreSQL
Hay dos formas:

#### Opción A: Manual por UI
1. **Administration** → **Add Monitoring Services**
2. Selecciona **PostgreSQL**
3. Completa:
   - Host: `postgres-primary` o `postgres-replica`
   - Port: `5432`
   - Username: `postgres`
   - Password: (mira `.env` `POSTGRES_PASSWORD`)

#### Opción B: Script automático
```powershell
bash scripts/setup-pmm.sh
```

## Qué se monitorea

### PostgreSQL
- Conexiones activas
- Transacciones/segundo
- Query performance
- Cache hit ratio
- Replication lag (primario ↔ réplica)
- Tamaño de base de datos

### Sistema
- CPU, memoria, disco
- I/O de base de datos
- Network traffic

## Configuración en `.env`

```dotenv
PMM_SERVER_PORT=8443     # Cambiar si hay conflicto de puerto
```

## Componentes dentro de PMM

El servidor PMM incluye internamente:
- **Grafana**: Dashboards y visualización
- **VictoriaMetrics**: Base de datos de series temporales
- **PostgreSQL**: Almacenar config y usuarios
- **ClickHouse**: Analytics optimizado
- **pmm-managed**: Orquestador central

## Troubleshooting

### PMM no arranca
```powershell
docker compose logs pmm-server
```

### Sin datos de PostgreSQL
1. Verifica que PostgreSQL esté corriendo:
   ```powershell
   docker compose ps postgres-primary postgres-replica
   ```

2. Prueba conectividad:
   ```bash
   docker exec comogastoapp-pmm-server bash -c \
     "psql -h postgres-primary -U postgres -d postgres -c 'SELECT 1;'"
   ```

3. Revisa credenciales en `.env`

## Productividad

### Dashboards clave
1. **Home**: Resumen general
2. **PostgreSQL** → **Overview**: Estado de todas las instancias
3. **PostgreSQL** → **Instances**: Drill-down por instancia
4. **Query Analytics**: Queries más lentas

### Alertas
Se pueden configurar en **Alerting** → **Alert Rules** (futuro)

## Recursos usados

- **Memoria**: ~1-2 GB (depende de carga)
- **Disco**: ~10 GB (retención de 30 días)
- **CPU**: ~1-2 cores

## Documentación adicional

- [`docs/PMM_SETUP.md`](../docs/PMM_SETUP.md) - Guía detallada
- [`docs/decisiones/ADR-004`](../docs/decisiones/ADR-004%20-%20Percona%20PMM.md) - Contexto arquitectónico
- [Docs oficiales Percona PMM](https://docs.percona.com/percona-monitoring-and-management/)

---

✨ **¡PMM está listo para monitoreoSu infraestructura está bajo control!**

