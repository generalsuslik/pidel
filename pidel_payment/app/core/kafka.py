import asyncio
import json
import os

from aiokafka import AIOKafkaProducer, AIOKafkaConsumer


class KafkaClient:
    def __init__(self, brokers: str):
        self.brokers = brokers
        self.producer = None
        self.consumer = None

    async def start(self, retries=10, delay=3):
        self.producer = AIOKafkaProducer(
            bootstrap_servers=self.brokers,
            value_serializer=lambda v: json.dumps(v).encode('utf-8'),
        )
        for attempt in range(retries):
            try:
                await self.producer.start()
                return
            except Exception as e:
                if attempt < retries - 1:
                    print(f"Kafka not ready, retrying in {delay}s... ({attempt+1}/{retries})")
                    await asyncio.sleep(delay)
                else:
                    print("Failed to connect to Kafka after retries.")
                    raise

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


kafka_client = KafkaClient(os.getenv("KAFKA_BROKERS", "kafka:9092"))
