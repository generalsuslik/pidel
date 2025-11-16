import { Link } from 'react-router-dom';
import './Navbar.css';

export const Navbar = () => {
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
                    <button className='cart-btn'>
                        <span className='cart-icon'>🛒</span>
                        <span className='cart-count'>0</span>
                    </button>
                    <Link to='/sign-in'>
                        <button className='login-btn'>Login</button>
                    </Link>
                </div>
            </div>
        </nav>
    );
};