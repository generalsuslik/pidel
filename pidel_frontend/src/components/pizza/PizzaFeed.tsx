import axios from 'axios';
import {useEffect, useState} from "react";
import {PizzaFeedComponent} from "./PizzaFeedComponent.tsx";

import './PizzaFeed.css';

export const PizzaFeed = () => {
    const baseUrl = "http://localhost:8080/api/v1"

    const [pizzas, setPizzas] = useState([])
    const [_, setLoading] = useState(true)
    const [__, setError] = useState<string | null>(null)

    useEffect(() => {
        (async () => {
            try {
                const response = await axios.get(`${baseUrl}/pizza`)
                setPizzas(response.data)
                return setLoading(false)
            } catch (_) {}

            setError("Failed to get pizza list");
            setLoading(false)
        })();
    }, []);

    // @ts-ignore
    return (
        <div className='pizza-feed'>
            <div className='pizza-feed-internal'>
                {pizzas.map((pizza) => (
                    <a>
                        <PizzaFeedComponent pizza={pizza}/>
                    </a>
                ))}
            </div>
        </div>
    )
}