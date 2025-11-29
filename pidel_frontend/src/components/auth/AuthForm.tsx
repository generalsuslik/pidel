import { useState } from 'react'
import type { AuthMode } from './AuthLayout'
import {Link, useNavigate} from 'react-router-dom'
import axios from "axios";

interface AuthFormProps {
    mode: AuthMode
    setLoggedIn: (isLoggedIn: boolean) => void
}

export const AuthForm: React.FC<AuthFormProps> = ({ mode, setLoggedIn }) => {
    const [username, setPhoneNumber] = useState('')
    const [password, setPassword] = useState('')
    const [name, setName] = useState('')
    const navigate = useNavigate()

    const baseUrl =  "http://127.0.0.1:8080/api/v1/auth/"
    const isSignIn = mode === 'sign-in'

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()

        const url = `${baseUrl}${isSignIn ? 'sign-in' : 'sign-up'}`
        const payload = isSignIn
            ? { username, password }
            : { username, password, name }

        try {
            console.log(payload)
            const { data } = await axios.post(url, payload)

            if (data.token) {
                document.cookie = `token=${data.token}; path=/; max-age=86400; SameSite=Lax`
                setLoggedIn(true)
                navigate("/")
            }
        } catch (err) {
            if (err.response) {
                // server responded with status != 2xx
                console.error('API Error:', err.response.data)
            } else {
                // network error or no response
                console.error('Network Error:', err.message)
            }
        }
    }

    return (
        <form className="auth-form" onSubmit={handleSubmit}>
            {!isSignIn && (
                <div className="auth-field">
                    <label className="auth-label" htmlFor="name">
                        Name
                    </label>
                    <input
                        id="name"
                        className="auth-input"
                        type="text"
                        placeholder="Arik Pizza Lover"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </div>
            )}

            <div className="auth-field">
                <label className="auth-label" htmlFor="phone-number">
                    Phone number
                </label>
                <input
                    id="phone-number"
                    className="auth-input"
                    type="phone-number"
                    placeholder="+1 (123) 456-7890"
                    value={username}
                    onChange={(e) => setPhoneNumber(e.target.value)}
                />
            </div>

            <div className="auth-field">
                <label className="auth-label" htmlFor="password">
                    Password
                </label>
                <input
                    id="password"
                    className="auth-input"
                    type="password"
                    placeholder="••••••••"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
            </div>

            {isSignIn && (
                <div className="auth-meta-row">
                    <label className="auth-checkbox">
                        <input type="checkbox" />
                        <span>Remember me</span>
                    </label>
                    <button type="button" className="auth-link-button">
                        Forgot password?
                    </button>
                </div>
            )}

            <button type="submit" className="auth-submit">
                {isSignIn ? 'Sign in' : 'Sign up'}
            </button>

            <div className="auth-footer-links">
                {isSignIn ? (
                    <>
                        <span>Don&apos;t have an account?</span>
                        <Link to="/sign-up" className="auth-footer-link">
                            Sign up
                        </Link>
                    </>
                ) : (
                    <>
                        <span>Already have an account?</span>
                        <Link to="/sign-in" className="auth-footer-link">
                            Sign in
                        </Link>
                    </>
                )}
            </div>
        </form>
    )
}