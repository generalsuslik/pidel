import asyncio


KAFKA_BOOTSTRAP_SERVERS = "kafka:9092"
KAFKA_TOPIC = "orders"
KAFKA_CONSUMER_GROUP = "orders"

loop = asyncio.get_event_loop()