package py.com.pysistemas.common.tracing.publisher;

import java.util.Map;

import py.com.pysistemas.common.events.envelope.EventEnvelope;
import py.com.pysistemas.common.events.envelope.EventEnvelopeFactory;
import py.com.pysistemas.common.events.publisher.EventPublisher;
import py.com.pysistemas.common.events.topic.PlatformTopics;
import py.com.pysistemas.common.tracing.event.TraceProcessPayload;
import py.com.pysistemas.common.tracing.event.TraceStepPayload;

/*
 * @author josec
 * @project comogastoapp
 */
public class TraceEventPublisher {
    private final EventPublisher eventPublisher;
    private final EventEnvelopeFactory eventEnvelopeFactory;

    public TraceEventPublisher(
            EventPublisher eventPublisher,
            EventEnvelopeFactory eventEnvelopeFactory
    ) {
        this.eventPublisher = eventPublisher;
        this.eventEnvelopeFactory = eventEnvelopeFactory;
    }

    public void publishProcessStarted(TraceProcessPayload payload) {
        publishProcess(PlatformTopics.TRACING_PROCESS_STARTED_V1, payload);
    }

    public void publishProcessCompleted(TraceProcessPayload payload) {
        publishProcess(PlatformTopics.TRACING_PROCESS_COMPLETED_V1, payload);
    }

    public void publishProcessFailed(TraceProcessPayload payload) {
        publishProcess(PlatformTopics.TRACING_PROCESS_FAILED_V1, payload);
    }

    public void publishStepStarted(TraceStepPayload payload) {
        publishStep(PlatformTopics.TRACING_STEP_STARTED_V1, payload);
    }

    public void publishStepCompleted(TraceStepPayload payload) {
        publishStep(PlatformTopics.TRACING_STEP_COMPLETED_V1, payload);
    }

    public void publishStepFailed(TraceStepPayload payload) {
        publishStep(PlatformTopics.TRACING_STEP_FAILED_V1, payload);
    }

    private void publishProcess(String topic, TraceProcessPayload payload) {
        EventEnvelope<TraceProcessPayload> event = eventEnvelopeFactory.create(
                topic,
                "1.0",
                payload,
                payload.traceId(),
                "trace-process-" + payload.processId() + "-" + topic,
                Map.of("source", "common-tracing")
        );

        eventPublisher.publish(topic, event);
    }

    private void publishStep(String topic, TraceStepPayload payload) {
        EventEnvelope<TraceStepPayload> event = eventEnvelopeFactory.create(
                topic,
                "1.0",
                payload,
                payload.traceId(),
                "trace-step-" + payload.stepId() + "-" + topic,
                Map.of("source", "common-tracing")
        );

        eventPublisher.publish(topic, event);
    }
}
