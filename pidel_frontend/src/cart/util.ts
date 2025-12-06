import Cookies from 'js-cookie';

export interface CartItem {
    id: number,
    size: string,
    price: number,
    quantity: number
}

export const CART_COOKIE_NAME = 'cart'

export const getCartFromCookie = (): CartItem[] => {
    const cartCookie = Cookies.get(CART_COOKIE_NAME);

    if (!cartCookie) return [];

    const parsedData = JSON.parse(cartCookie);
    if (!Array.isArray(parsedData)) {
        console.log('Invalid cart cookie format, clearing cart');
        return []
    }
    return parsedData;
};

export const saveCartToCookie = (cartItems: CartItem[]): void => {
    Cookies.set(CART_COOKIE_NAME, JSON.stringify(cartItems), { expires: 7 });
};

export const computePrice = () =>  {
    const cart = getCartFromCookie();
    console.log(cart.length)
    return cart.reduce((acc, item) => acc + item.price * item.quantity, 0);
}

export const clearCart = (): void => {
    Cookies.remove(CART_COOKIE_NAME);
}

export const getPizzaNameById = (id: number, pizzas: any[]): string => {
    const pizza = pizzas.find(p => p.id === id);
    return pizza ? pizza.name : 'Pizza';
};
