from fastapi import APIRouter
from app.models.events import OrderCreated
from app.services.payment_service import PaymentService

router = APIRouter()
service = PaymentService()

@router.post("/pay")
async def pay(order: OrderCreated):
    print(f"Received order for payment: {order}")
    return await service.process_payment(order)