import { createContext, useState, useEffect } from "react";
import {getCookie} from "../../api/client.ts";

export const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);

    const loadUser = () => {
        const token = getCookie("token");
        if (!token) return setUser(null);

        try {
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const jsonPayload = JSON.parse(
                decodeURIComponent(
                    atob(base64)
                        .split('')
                        .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
                        .join('')
                )
            );
            setUser(jsonPayload);
        } catch {
            setUser(null);
        }
    };

    useEffect(() => {
        loadUser();
    }, []);

    return (
        <AuthContext.Provider value={{ user, setUser, loadUser }}>
    {children}
    </AuthContext.Provider>
);
};
