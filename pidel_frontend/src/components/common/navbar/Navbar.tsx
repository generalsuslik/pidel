import './Navbar.css';

export const Navbar = () => {
    return (
        <nav className='navbar'>
            <div className='navbar-container'>
                <div className='navbar-logo'>
                    <span className='logo-icon'>🍕</span>
                    <span className='logo-text'>ПицДос</span>
                </div>

                <ul className='navbar-menu'>
                    <li><a href='#menu' className='nav-link'>Menu</a></li>
                    <li><a href='#about' className='nav-link'>About</a></li>
                    <li><a href='#contact' className='nav-link'>Contact</a></li>
                </ul>

                <div className='navbar-actions'>
                    <button className='cart-btn'>
                        <span className='cart-icon'>🛒</span>
                        <span className='cart-count'>0</span>
                    </button>
                    <button className='login-btn'>Login</button>
                </div>
            </div>
        </nav>
    );
};