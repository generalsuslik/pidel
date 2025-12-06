import type { CartItem } from "./util.ts";
import { getCartFromCookie, saveCartToCookie, clearCart as clearCartUtil } from "./util.ts";
import { createContext, type ReactNode, useEffect, useState } from "react";
import * as React from "react";

export interface CartContextType {
    cartItems: CartItem[];
    addToCart: (product: CartItem) => void;
    removeFromCart: (key: {id: number, size: string}) => void;
    updateQuantity: (key: {id: number, size: string}, delta: number) => void;
    clearCart: () => void;
}

const CartContext = createContext<CartContextType | undefined>(undefined);

export const CartProvider = ({ children }: { children: ReactNode }) => {
    const [cartItems, setCartItems] = useState<CartItem[]>(getCartFromCookie());

    // Sync cart state with cookie whenever cartItems changes
    useEffect(() => {
        saveCartToCookie(cartItems);
    }, [cartItems]);

    const addToCart = (product: CartItem) => {
        setCartItems(prevItems => {
            const itemExists = prevItems.find(item => item.id === product.id && item.size === product.size);
            if (itemExists) {
                return prevItems.map(item =>
                    item.id === product.id && item.size === product.size
                        ? { ...item, quantity: item.quantity + 1 }
                        : item
                );
            } else {
                return [...prevItems, { ...product, quantity: 1 }];
            }
        });
    };

    const removeFromCart = (key: {id: number, size: string}) => {
        setCartItems(prevItems => prevItems.filter(item => !(item.id === key.id && item.size === key.size)));
    };

    const updateQuantity = (key: {id: number, size: string}, delta: number) => {
        setCartItems(prevItems => {
            return prevItems
                .map(item =>
                    item.id === key.id && item.size === key.size
                        ? { ...item, quantity: Math.max(0, item.quantity + delta) }
                        : item
                )
                .filter(item => item.quantity > 0);
        });
    };

    const clearCart = () => {
        setCartItems([]);
        clearCartUtil();
    };

    return (
        <CartContext.Provider value={{ cartItems, addToCart, removeFromCart, updateQuantity, clearCart }}>
            {children}
        </CartContext.Provider>
    )
}

export const useCart = () => {
    const context = React.useContext(CartContext);
    if (context === undefined) {
        throw new Error("useCart must be used within a CartProvider");
    }
    return context;
}
