import './PizzaFeedComponent.css'
import * as React from "react";
import type {PizzaProp} from "./PizzaFeed.tsx";

export const PizzaFeedComponent: React.FC<PizzaProp> = ({pizza}) => {
    return (
        <div key={pizza.id} className='pizza-component'>
             <div className='pizza-internal'>
                 <img className='product-image' src={pizza.image.imageUrl} alt='pizza image'/>
                 <h3 className='pizza-name'>
                     {pizza.name}
                 </h3>
                 <p className='pizza-desc'>
                     {pizza.description}
                 </p>
                 <button className='buy-pizza-btn'>
                     From {Math.floor(pizza.price * pizza.pizzaSizes[0].coefficient)} rub
                 </button>
             </div>
        </div>
    )
}