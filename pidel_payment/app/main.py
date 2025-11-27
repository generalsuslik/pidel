import asyncio
from fastapi import FastAPI
from app.api.payment_controller import router
from app.core.kafka import kafka_client
from app.services.payment_service import PaymentService
from app.models.events import OrderCreated

app = FastAPI()
app.include_router(router)

service = PaymentService()

@app.on_event("startup")
async def startup_event():
    await kafka_client.start()
    asyncio.create_task(order_consumer_loop())

@app.on_event("shutdown")
async def shutdown_event():
    await kafka_client.stop()

async def order_consumer_loop():
    consumer = await kafka_client.consume_orders("orders")

    async for msg in consumer:
        order = OrderCreated(**msg.value)
        payment = await service.process_payment(order)

        await kafka_client.producer.send_and_wait(
            "payments",
            payment.model_dump()
        )
