from pydantic import BaseModel
from typing import List

class OrderItem(BaseModel):
    pizzaId: int
    qty: int

class OrderCreated(BaseModel):
    orderId: int
    userId: int
    amount: float
    items: List[OrderItem]
    address: str

class PaymentProcessed(BaseModel):
    orderId: int
    status: str
    transactionId: str
    timestamp: str