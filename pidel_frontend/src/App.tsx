import './App.css'
import CartOverlay from './cart/CartOverlay';
import { Routes, Route } from 'react-router-dom'
import {PizzaFeed} from "./components/pizza/PizzaFeed.tsx";
import {Navbar} from "./components/common/navbar/Navbar.tsx";
import {AuthLayout} from "./components/auth/AuthLayout.tsx";
import {Pizza} from "./components/pizza/Pizza.tsx";
import { useState, useEffect } from "react";
import { CartProvider, useCart } from "./cart/Cart.tsx";
import type { CartContextType } from "./cart/Cart.tsx";
import { computePrice } from "./cart/util.ts";
import type { CartItem } from "./cart/util.ts";


export function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [totalPrice, setTotalPrice] = useState(computePrice());
    const [cartOpen, setCartOpen] = useState(false);

    // Helper component to update totalPrice on cart changes
    const CartPriceUpdater = ({ setTotalPrice }: { setTotalPrice: (price: number) => void }) => {
        const { cartItems }: CartContextType = useCart();
        useEffect(() => {
            setTotalPrice(cartItems.reduce((acc: number, item: CartItem) => acc + item.price * item.quantity, 0));
        }, [cartItems, setTotalPrice]);
        return null;
    };

    return (
        <CartProvider>
            <CartPriceUpdater setTotalPrice={setTotalPrice} />
            <Navbar isLoggedIn={isLoggedIn} totalPrice={totalPrice} onCartClick={() => setCartOpen(true)} />
            <CartOverlay isOpen={cartOpen} onClose={() => setCartOpen(false)} />
            <Routes>
                <Route path='/' element={<PizzaFeed />} />
                <Route path='/pizza/:id' element={<Pizza setTotalPrice={setTotalPrice} />} />
                <Route
                    path='/sign-in'
                    element={
                        <AuthLayout
                            title='Welcome back'
                            subtitle='Sign in to continue exploring delicious pizzas.'
                            mode='sign-in'
                            setLoggedIn={setIsLoggedIn}
                        />
                    }
                />
                <Route
                    path='/sign-up'
                    element={
                        <AuthLayout
                            title='Create your account'
                            subtitle='Join us and never miss a slice.'
                            mode='sign-up'
                            setLoggedIn={setIsLoggedIn}
                        />
                    }
                />
            </Routes>
        </CartProvider>
    );
}

export default App
