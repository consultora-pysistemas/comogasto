# 🎯 COMOGASTOAPP - Infraestructura Completa

## 📊 Resumen de Servicios

Tu entorno está **100% operacional** con todos los componentes críticos funcionando.

### Tabla de Accesos

| Servicio               | URL                       | Puerto   | Usuario  | Contraseña          | Notas                   |
|------------------------|---------------------------|----------|----------|---------------------|-------------------------|
| **PostgreSQL Primary** | localhost                 | **5448** | postgres | asdf                | Escritura + Replicación |
| **PostgreSQL Replica** | localhost                 | **5449** | postgres | asdf                | Lectura (standby)       |
| **Kafka Broker**       | localhost                 | **9092** | —        | —                   | KRaft (sin Zookeeper)   |
| **Kafka UI**           | http://localhost:**8085** | 8085     | —        | —                   | Interfaz gráfica        |
| **Redis**              | localhost                 | **6379** | —        | comogasto-app-redis | Cache distribuido       |
| **PMM Server**         | http://localhost:**8443** | 8443     | admin    | admin               | Monitoreo PostgreSQL    |
| **Portainer**          | http://localhost:**9000** | 9000     | admin    | admin               | Gestión Docker          |

---

## 🔧 Estado Actual

```bash
✅ PostgreSQL Primary      (5448)   RUNNING ↔️↔️↔️ REPLICATING
✅ PostgreSQL Replica      (5449)   RUNNING (standby)
✅ Kafka                   (9092)   RUNNING ✓
✅ Kafka UI                (8085)   RUNNING ✓
✅ Redis                   (6379)   RUNNING ✓
✅ PMM Server              (8443)   RUNNING ✓
✅ Portainer               (9000)   RUNNING ✓
```

Ver estado en tiempo real:
```bash
make ps
```

---

## 📖 Documentación

| Documento                      | Ruta                   | Descripción                |
|--------------------------------|------------------------|----------------------------|
| **PostgreSQL Access**          | `POSTGRES_ACCESS.md`   | Guía de conexión a BD      |
| **PMM Quickstart**             | `PMM_QUICKSTART.md`    | Configurar monitoreo       |
| **PMM Agent Running**          | `PMM_AGENT_RUNNING.md` | Troubleshooting de PMM     |
| **PMM Setup Detallado**        | `docs/PMM_SETUP.md`    | Guía completa de monitoreo |
| **Decisiones Arquitectónicas** | `docs/decisiones/`     | ADR-001 a ADR-004          |

---

## 🚀 Inicio Rápido

### 1️⃣ Verificar todo está funcionando
```bash
docker compose ps
```

### 2️⃣ Ver logs de un servicio
```bash
# PostgreSQL
docker compose logs postgres-primary -f

# Kafka
docker compose logs kafka -f

# PMM
make pmm-logs
```

### 3️⃣ Conectar a PostgreSQL
```bash
# Primario (para escribir)
psql -h localhost -p 5448 -U postgres -d comogasto

# Réplica (para leer)
psql -h localhost -p 5449 -U postgres -d comogasto
```

### 4️⃣ Abrir Grafana PMM
```
http://localhost:8443
admin / admin
```

### 5️⃣ Administrar Docker
```
http://localhost:9000
```

---

## 💾 Datos Persistentes

Los volúmenes Docker almacenan todos los datos:

```yaml
postgres_primary_data:     # BD principal de PostgreSQL
postgres_replica_data:     # BD de réplica standby
kafka_data:                # Logs de Kafka KRaft
redis_data:                # Cache persistente Redis
pmm_server_data:           # Métricas PMM (30 días)
portainer_data:            # Configuración Portainer
```

Para **borrar todo** (⚠️ irreversible):
```bash
docker compose down -v
```

---

## 📡 Configuraciones Clave

### PostgreSQL Replicación
- **Tipo**: Physical streaming replication
- **Modo**: Asincrónico
- **WAL Archiving**: Nativo (pg_basebackup)
- **Health Check**: ✅ Automático

### Kafka
- **Protocolo**: Apache KRaft (sin Zookeeper)
- **Version**: 3.9.0
- **Replication Factor**: 1 (dev)
- **Auto Topics**: Deshabilitado
- **DLQ + Retry Topics**: Configurados en `scripts/create-topics.sh`

### Observabilidad
- **Monitoreo**: Percona PMM 3.7.1
- **Base de Métricas**: VictoriaMetrics
- **Visualización**: Grafana
- **Query Analytics**: QAN-API v2
- **Alertas**: VictoriaMetrics Alerts (configurable)

---

## 🔐 Seguridad

### Variables Sensibles (`.env`)

```bash
cat .env
```

Contiene credenciales para:
- PostgreSQL (usuario: postgres, password: asdf)
- Redis (password: comogasto-app-redis)
- PMM (admin/admin - cambiar en producción)
- JWT (tokens de autenticación)

**⚠️ Nunca commitear `.env` a Git** (ya en `.gitignore`)

---

## 📝 Makefile - Comandos Útiles

```bash
# General
make up              # Iniciar todos los servicios
make down            # Parar todos
make restart         # Reiniciar
make clean           # Borrar todo (volúmenes incluidos)
make ps              # Ver estado
make logs            # Ver logs de todos

# PostgreSQL
make health          # Health check (todos los servicios)

# Kafka
make topics          # Crear topics (si no existen)

# PMM
make pmm-up          # Iniciar PMM Server
make pmm-logs        # Ver logs PMM
make pmm-status      # Estado PMM
make pmm-url         # Ver URL + credenciales
make pmm-down        # Parar PMM
```

---

## 🐛 Troubleshooting Rápido

### PostgreSQL Primary no está replicando
```bash
docker exec comogastoapp-postgres-primary psql -U postgres -d postgres \
  -c "SELECT * FROM pg_stat_replication;"
```

### Kafka UI no muestra topics
```bash
docker exec comogastoapp-kafka kafka-topics.sh --bootstrap-server localhost:9092 --list
```

### PMM no ve PostgreSQL
1. Ve a http://localhost:8443
2. **Administration** → **Add Node** → **PostgreSQL**
3. Completa: host=`postgres-primary`, port=`5432`, user=`postgres`

### Redis no conecta
```bash
docker exec comogastoapp-redis redis-cli ping
```

---

## 🎓 Próximos Pasos (Recomendados)

### Fase 1: Validación
- [ ] Conectar a PostgreSQL desde DBeaver/pgAdmin
- [ ] Verificar replicación en PMM Grafana
- [ ] Crear un topic de prueba en Kafka
- [ ] Escribir/leer desde Redis

### Fase 2: Aplicación
- [ ] Deployar Spring Boot backend
- [ ] Configurar datasource a PostgreSQL 5448
- [ ] Conectar Kafka consumers/producers
- [ ] Integrar Redis para cache

### Fase 3: Monitoreo
- [ ] Crear alertas en PMM
- [ ] Configurar webhooks (Slack/Teams)
- [ ] Exportar métricas a Prometheus externo (opcional)

### Fase 4: Production
- [ ] Cambiar credenciales (especialmente PMM admin/admin)
- [ ] Configurar SSL/TLS
- [ ] Backups programados
- [ ] Disaster recovery plan

---

## 📚 Referencias

- [PostgreSQL 17 Docs](https://www.postgresql.org/docs/17/)
- [Percona PMM](https://docs.percona.com/percona-monitoring-and-management/)
- [Apache Kafka](https://kafka.apache.org/documentation/)
- [Redis](https://redis.io/documentation)
- [Docker Compose](https://docs.docker.com/compose/)

---

**Infraestructura lista para desarrollo y testing** ✨

Cualquier duda → revisa los documentos de cada servicio en este directorio.

