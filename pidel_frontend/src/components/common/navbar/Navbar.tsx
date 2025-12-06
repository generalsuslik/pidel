import { Link } from 'react-router-dom';
import './Navbar.css';
import {useEffect, useState} from "react";
import {getCookie} from "../../../api/client.ts";
import * as React from "react";

interface NavbarProps {
    isLoggedIn: boolean;
    totalPrice: number;
    onCartClick: () => void;
}

export const Navbar: React.FC<NavbarProps> = ({ isLoggedIn, totalPrice, onCartClick }) => {
    const [user, setUser] = useState<{ name?: string; sub?: string } | null>(null);

    useEffect(() => {
        // @ts-ignore
        const token = getCookie('token')
        if (token) {
            try {
                const base64Url = token.split('.')[1];
                const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
                const jsonPayload = decodeURIComponent(
                    window.atob(base64)
                        .split('')
                        .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
                        .join('')
                );

                setUser(JSON.parse(jsonPayload));
            } catch (e) {
                console.error("Failed to decode token", e);
            }
        }
    }, [isLoggedIn, totalPrice]);

    const handleLogout = () => {
        // Clear token cookie
        document.cookie = 'token=; path=/; max-age=0';
        setUser(null);
        window.location.reload();
    };

    return (
        <nav className='navbar'>
            <div className='navbar-container'>
                <Link to='/' className='navbar-logo'>
                    <span className='logo-icon'>🍕</span>
                    <span className='logo-text'>ПицДос</span>
                </Link>

                <ul className='navbar-menu'>
                    <li><a href='#menu' className='nav-link'>Menu</a></li>
                </ul>

                <div className='navbar-actions'>
                    <button className='cart-btn' onClick={onCartClick}>
                        <span className='cart-icon'>🛒</span>
                        <span className='cart-count'>{totalPrice}</span>
                    </button>

                    {user ? (
                        <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                            <span className='nav-link' style={{ color: 'var(--text-color)' }}>
                                {user.name || user.sub || 'User'}
                            </span>
                            <button onClick={handleLogout} className='login-btn' style={{ backgroundColor: '#dc3545' }}>
                                Logout
                            </button>
                        </div>
                    ) : (
                        <Link to='/sign-in'>
                            <button className='login-btn'>Login</button>
                        </Link>
                    )}
                </div>
            </div>
        </nav>
    );
};