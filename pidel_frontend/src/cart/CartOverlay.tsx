import React, { useEffect, useState } from 'react';
import './CartOverlay.css';
import { getCartFromCookie, type CartItem, getPizzaNameById, computePrice } from './util';
import { useCart } from './Cart';


interface CartOverlayProps {
  isOpen: boolean;
  onClose: () => void;
}

const CartOverlay: React.FC<CartOverlayProps> = ({ isOpen, onClose }) => {
  const { cartItems, removeFromCart, updateQuantity, clearCart } = useCart();
  const [pizzas, setPizzas] = useState<any[]>([]);

  useEffect(() => {
    if (isOpen) {
      // Try to get pizzas from window or fetch if not present
      if ((window as any).allPizzas) {
        setPizzas((window as any).allPizzas);
      } else {
        fetch('http://localhost:8080/api/v1/pizza')
          .then(res => res.json())
          .then(data => {
            setPizzas(data);
            (window as any).allPizzas = data;
          });
      }
    }
  }, [isOpen]);

  useEffect(() => {
    if (!isOpen) return;
    const handleEsc = (e: KeyboardEvent) => {
      if (e.key === 'Escape') onClose();
    };
    window.addEventListener('keydown', handleEsc);
    return () => window.removeEventListener('keydown', handleEsc);
  }, [isOpen, onClose]);

  if (!isOpen) return null;

  return (
    <div className="cart-overlay-backdrop" onClick={onClose}>
      <div className="cart-overlay-window" onClick={e => e.stopPropagation()}>
        <button className="cart-overlay-close" onClick={onClose}>×</button>
        <div style={{display: 'flex', alignItems: 'center', justifyContent: 'space-between'}}>
          <h2>Your Cart</h2>
        </div>
        {cartItems.length === 0 ? (
          <p>Your cart is empty.</p>
        ) : (
          <>
            <div className="cart-overlay-list-scroll">
              <ul className="cart-overlay-list">
                {cartItems.map(item => (
                  <li key={item.id + '-' + item.size} className="cart-overlay-item">
                    <span className="cart-overlay-item-name">{getPizzaNameById(item.id, pizzas)} <span style={{fontSize: '0.9em', color: '#888'}}>({item.size} cm)</span></span>
                    <div className="cart-overlay-item-controls">
                      <button className="cart-overlay-qty-btn" onClick={() => updateQuantity({id: item.id, size: item.size}, -1)}>-</button>
                      <span className="cart-overlay-item-qty">{item.quantity}</span>
                      <button className="cart-overlay-qty-btn" onClick={() => updateQuantity({id: item.id, size: item.size}, 1)}>+</button>
                    </div>
                    <span className="cart-overlay-item-price">{item.price * item.quantity} p</span>
                    <button className="cart-overlay-remove-btn" onClick={() => removeFromCart({id: item.id, size: item.size})}>🗑️</button>
                  </li>
                ))}
              </ul>
            </div>
            <button
              className="cart-overlay-checkout"
              disabled={computePrice() === 0}
              style={computePrice() === 0 ? { background: '#eee', color: '#aaa', cursor: 'not-allowed' } : {}}
            >
              Proceed to checkout ({computePrice()} p)
            </button>
          </>
        )}
      </div>
    </div>
  );
};

export default CartOverlay;
