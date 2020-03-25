package backend.Service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaService {
    // Kafka listener to deal with user order
    public void dealOrder(ConsumerRecord record);
}
