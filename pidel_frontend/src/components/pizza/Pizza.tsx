import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import './Pizza.css'

export const Pizza = () => {
    const { id } = useParams();
    const baseUrl = "http://localhost:8080/api/v1"
    const [pizza, setPizza] = useState<any>(null)
    const [loading, setLoading] = useState(true)
    const [selectedSize, setSelectedSize] = useState('30')
    const [price, setPrice] = useState(0)

    useEffect(() => {
        (async () => {
            try {
                const response = await axios.get(`${baseUrl}/pizza/${id}`)
                setPizza(response.data)
                setSelectedSize(response.data.pizzaSizes[0].size)
                setPrice(response.data.price * response.data.pizzaSizes[0].coefficient)
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
                            {ingredients.map((ingredient) => (
                                <span key={ingredient.id} className="ingredient-tag">{ingredient.name}</span>
                            ))}
                        </div>
                    </div>

                    <div className="pizza-section">
                        <div className="pizza-section-title">Size</div>
                        <div className="size-selector">
                            {sizes.map((size) => (
                                <div
                                    key={size.size}
                                    className={`size-option ${selectedSize === size.size ? 'active' : ''}`}
                                    onClick={() => {
                                        setSelectedSize(size.size)
                                        setPrice(pizza.price * size.coefficient)
                                    }}
                                >
                                    {size.size}
                                </div>
                            ))}
                        </div>
                    </div>

                    <div className='pizza-page-actions'>
                        <div className="price-tag">{Math.floor(price)} р</div>
                        <button className='add-to-cart-large-btn'>
                            Add to Cart
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )
}