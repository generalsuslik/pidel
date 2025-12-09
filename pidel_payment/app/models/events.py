from pydantic import BaseModel
from typing import List

class OrderItem(BaseModel):
    pizzaId: int
    quantity: int
    price: float
    pizzaSize: int

class OrderCreated(BaseModel):
    orderId: int
    username: int
    totalPrice: float
    address: str
    items: List[OrderItem]

class PaymentProcessed(BaseModel):
    orderId: int
    status: str
    transactionId: str
    timestamp: str