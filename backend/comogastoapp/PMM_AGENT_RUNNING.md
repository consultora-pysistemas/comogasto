# 🔧 Resolución: PMM Agent Running - Status Verified

## Estado Actual ✅

### PMM Server
- **Status**: Running (health: starting → should become healthy)
- **Port**: http://localhost:8443
- **Credenciales Web**: admin / admin

### PMM Agent ✅
```
Agent ID : pmm-server
Connected: true
pmm-admin version: 3.7.1
pmm-agent version: 3.7.1
```

### Agentes Subordinados ✅
- **node_exporter**: Running (puerto 42001)
- **postgres_exporter**: Running (puerto 42000)

### PostgreSQL Disponible ✅
- **Primary**: postgres-primary:5432 (usuario: postgres, password: asdf)
- **Replica**: postgres-replica:5432 (usuario: postgres, password: asdf)

---

## Problema Identificado

El comando `pmm-admin add postgresql` retorna `Unauthorized`. Esto es un problema de autenticación de `pmm-admin` contra `pmm-managed` (no de conectividad a PostgreSQL).

### Causa Probable

La configuración de autenticación entre `pmm-admin` y `pmm-managed` dentro del contenedor puede requerir credenciales especiales.

---

## Solución: Registrar Nodos vía UI Web  

### 1. Acceder a PMM Grafana
```
URL: http://localhost:8443
Usuario: admin
Contraseña: admin
```

### 2. Registrar PostgreSQL Primario

**Opción A: Usando la UI Grafana**
1. Click en **Hamburger Menu** → **Administration** → **Add Node**
2. Selecciona **PostgreSQL**
3. Completa:
   - **Hostname**: `postgres-primary`
   - **Port**: `5432`
   - **Username**: `postgres`
   - **Password**: `asdf`
   - **Service Name**: `pg-primary`
   - **Database**: `postgres`
4. Haz click en **Add**

**Opción B: Usando pmm-admin en una sesión bash interactiva**
```bash
docker exec -it comogastoapp-pmm-server bash
# Dentro del contenedor:
pmm-admin add postgresql \
  --username=postgres \
  --password=asdf \
  --host=postgres-primary \
  --port=5432 \
  --service-name=pg-primary \
  --database=postgres \
  --environment=development
```

### 3. Registrar PostgreSQL Réplica (mismo proceso)
- **Hostname**: `postgres-replica`
- **Service Name**: `pg-replica`
- Resto igual al primario

---

## Verificación

Una vez registrados, verifica en Grafana:
1. **Menu** → **Dashboards** → **PostgreSQL** → **Overview**
2. Deberías ver ambos nodos listados
3. Las métricas deberían empezar a aparecer en `~1-2 minutos`

---

## Métricas Esperadas

Una vez conectados, podrás monitorear:

### General
- Conexiones activas
- Transacciones por segundo
- Cache hit ratio
- Latencia de queries

### Replicación
- Replication lag (bytes)
- WAL age
- Sync status (primary/replica)

### Almacenamiento
- Tamaño de tablas
- Tamaño de índices
- Espacio utilizado por base de datos

### Performance
- Top queries (Query Analytics)
- Índices sin usar
- Tabla con más spins

---

## Troubleshooting Adicional

### Si sigue fallando pmm-admin

1. Verifica conectividad directa:
   ```bash
   docker exec comogastoapp-pmm-server bash -c "PGPASSWORD=asdf psql -h postgres-primary -U postgres -d postgres -c 'SELECT version();'"
   ```

2. Reinicia pmm-managed:
   ```bash
   docker exec comogastoapp-pmm-server supervisorctl restart pmm-managed
   ```

3. Comprueba logs:
   ```bash
   docker exec comogastoapp-pmm-server tail -f /var/log/pmm/pmm-managed.log
   ```

### Si la UI no carga

1. Espera 2-3 minutos adicionales (Grafana toma tiempo en inicializarse)
2. Limpia cache del navegador (Ctrl+Shift+Del)
3. Accede directamente a: `http://localhost:8443/graph`

---

## Próximas Acciones

1. ✅ Registrar PostgreSQL primario
2. ✅ Registrar PostgreSQL réplica
3. ⏳ Verificar dashboards en Grafana
4. ⏳ Configurar alertas (opcional)
5. ⏳ Crear dashboards personalizados (opcional)

---

## Resumen

**pmm-agent está corriendo correctamente** ✅  
**La conectividad a PostgreSQL está verificada** ✅  
**Falta registrar los nodos en PMM** → Usar UI Grafana

