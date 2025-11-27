import json
from aiokafka import AIOKafkaProducer, AIOKafkaConsumer


class KafkaClient:
    def __init__(self, brokers: str):
        self.brokers = brokers
        self.producer = None
        self.consumer = None

    async def start(self):
        self.producer = AIOKafkaProducer(
            bootstrap_servers=self.brokers,
            value_serializer=lambda v: json.dumps(v).encode('utf-8'),
        )
        await self.producer.start()

    async def stop(self):
        if self.producer is not None:
            self.producer.stop()

    async def consume_orders(self, topic: str) -> AIOKafkaConsumer:
        consumer = AIOKafkaConsumer(
            topic,
            bootstrap_servers=self.brokers,
            value_deserializer=lambda v: json.loads(v.decode('utf-8')),
        )
        await consumer.start()
        return consumer


kafka_client = KafkaClient("kafka:9092")
