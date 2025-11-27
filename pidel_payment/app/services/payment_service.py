import uuid
from datetime import datetime
from app.models.events import OrderCreated, PaymentProcessed

class PaymentService:

    async def process_payment(self, order: OrderCreated) -> PaymentProcessed:
        transaction_id = f"txn_{uuid.uuid4().hex}"

        return PaymentProcessed(
            orderId=order.orderId,
            status="PAID",
            transactionId=transaction_id,
            timestamp=datetime.utcnow().isoformat()
        )
