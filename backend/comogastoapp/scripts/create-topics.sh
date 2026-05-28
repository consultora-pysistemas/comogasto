#!/usr/bin/env bash
set -e

KAFKA_CONTAINER="comogastoapp-kafka"

create_topic() {
  local topic=$1

  docker exec "$KAFKA_CONTAINER" kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --create \
    --if-not-exists \
    --topic "$topic" \
    --partitions 3 \
    --replication-factor 1
}

TOPICS=(
  "tenant.created.v1"
  "tenant.updated.v1"
  "tenant.deleted.v1"

  "auth.login.succeeded.v1"
  "auth.login.failed.v1"
  "auth.logout.v1"
  "auth.password.changed.v1"
  "auth.password.reset.requested.v1"
  "auth.email.verified.v1"
  "auth.refresh-token.rotated.v1"

  "iam.user.created.v1"
  "iam.user.updated.v1"
  "iam.user.disabled.v1"
  "iam.role.created.v1"
  "iam.role.permission.assigned.v1"
  "iam.permission.cache.invalidated.v1"

  "audit.event.created.v1"

  "tracing.process.started.v1"
  "tracing.step.started.v1"
  "tracing.step.completed.v1"
  "tracing.step.failed.v1"
  "tracing.kafka.published.v1"
  "tracing.kafka.consumed.v1"
  "tracing.http.called.v1"
  "tracing.process.completed.v1"
  "tracing.process.failed.v1"

  "notification.requested.v1"
  "notification.sent.v1"
  "notification.failed.v1"

  "gateway.request.received.v1"
)

for topic in "${TOPICS[@]}"; do
  echo "Creating topic: $topic"
  create_topic "$topic"
done

echo "Kafka topics created."

DLQ_TOPICS=(
  "tenant.created.v1.dlq"
  "iam.user.created.v1.dlq"
  "audit.event.created.v1.dlq"
  "tracing.process.started.v1.dlq"
  "notification.requested.v1.dlq"
)

RETRY_TOPICS=(
  "iam.user.created.v1.retry.1"
  "iam.user.created.v1.retry.2"
  "iam.user.created.v1.retry.3"

  "audit.event.created.v1.retry.1"
  "audit.event.created.v1.retry.2"
  "audit.event.created.v1.retry.3"

  "notification.requested.v1.retry.1"
  "notification.requested.v1.retry.2"
  "notification.requested.v1.retry.3"
)

for topic in "${DLQ_TOPICS[@]}"; do
  echo "Creating DLQ topic: $topic"
  create_topic "$topic"
done

for topic in "${RETRY_TOPICS[@]}"; do
  echo "Creating retry topic: $topic"
  create_topic "$topic"
done