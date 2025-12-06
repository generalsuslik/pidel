import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import './Pizza.css'
import {apiClient} from "../../api/client.ts";
import {computePrice, getCartFromCookie} from "../../cart/util.ts";
import {useCart} from "../../cart/Cart.tsx";
import type { PizzaProp } from "./PizzaFeed.tsx";

interface PizzaProps {
    setTotalPrice: (price: number) => void
}

export const Pizza: React.FC<PizzaProps> = ({setTotalPrice}) => {
    const { id } = useParams();
    const [pizza, setPizza] = useState<any>(null)
    const [loading, setLoading] = useState(true)
    const [selectedSize, setSelectedSize] = useState('30')
    const [price, setPrice] = useState(0)
    const [added, setAdded] = useState(false);
    const navigate = useNavigate()
    const { addToCart, cartItems, updateQuantity, removeFromCart } = useCart()

    useEffect(() => {
        (async () => {
            try {
                const { data } = await apiClient.get(`/pizza/${id}`);
                setPizza(data)
                setSelectedSize(data.pizzaSizes[0].size)
                setPrice(Math.floor(data.price * data.pizzaSizes[0].coefficient))
                setLoading(false)
            } catch (_) {
                setLoading(false)
            }
        })();
    }, [id]);

    if (loading) {
        return <div className="pizza-page-container">Loading...</div>
    }

    if (!pizza) {
        return <div className="pizza-page-container">Pizza not found</div>
    }

    const ingredients = pizza.ingredients;
    const sizes = pizza.pizzaSizes;

    const onAddToCart = () => {
        if (!document.cookie.includes('token')) {
            navigate('/sign-in')
            return
        }
        // Find selected size object
        const selectedSizeObj = pizza.pizzaSizes.find((s: any) => s.size === selectedSize);
        const priceForSize = Math.floor(pizza.price * selectedSizeObj.coefficient);
        addToCart({
            id: pizza.id,
            size: selectedSize,
            price: priceForSize,
            quantity: 1
        });
        setTotalPrice(computePrice());
    };

    const onIncrement = () => {
        updateQuantity({id: pizza.id, size: selectedSize}, 1);
        setTotalPrice(computePrice());
    };

    const onDecrement = () => {
        if (currentCartItem && currentCartItem.quantity === 1) {
            removeFromCart({id: pizza.id, size: selectedSize});
        } else {
            updateQuantity({id: pizza.id, size: selectedSize}, -1);
        }
        setTotalPrice(computePrice());
    };

    const currentCartItem = cartItems.find(item => item.id === pizza.id && item.size === selectedSize);

    return (
        <div className='pizza-page-container'>
            <div className='pizza-page-card'>
                <div className='pizza-page-image-container'>
                    <img
                        className='pizza-page-image'
                        src={pizza.image?.imageUrl}
                        alt={pizza.name}
                    />
                </div>

                <div className='pizza-page-details'>
                    <h1 className='pizza-page-title'>
                        {pizza.name}
                    </h1>
                    <p className='pizza-page-desc'>
                        {pizza.description}
                    </p>

                    <div className="pizza-section">
                        <div className="pizza-section-title">Ingredients</div>
                        <div className="ingredients-list">
                            {ingredients.map((ingredient: any) => (
                                <span key={ingredient.id} className="ingredient-tag">{ingredient.name}</span>
                            ))}
                        </div>
                    </div>

                    <div className="pizza-section">
                        <div className="pizza-section-title">Size</div>
                        <div className="size-selector">
                            {sizes.map((size: any) => (
                                <div
                                    key={size.size}
                                    className={`size-option ${selectedSize === size.size ? 'active' : ''}`}
                                    onClick={() => {
                                        setSelectedSize(size.size)
                                        setPrice(Math.floor(pizza.price * size.coefficient))
                                    }}
                                >
                                    {size.size}
                                </div>
                            ))}
                        </div>
                    </div>

                    <div className='pizza-page-actions'>
                        <div className="price-tag">{price} р</div>
                        {currentCartItem ? (
                            <div className="cart-qty-controls">
                                <button className="cart-qty-btn" onClick={onIncrement}>+</button>
                                <span className="cart-qty-count">{currentCartItem.quantity}</span>
                                <button className="cart-qty-btn" onClick={onDecrement}>-</button>
                            </div>
                        ) : (
                            <button className='add-to-cart-large-btn' onClick={onAddToCart}>
                                Add to Cart
                            </button>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}