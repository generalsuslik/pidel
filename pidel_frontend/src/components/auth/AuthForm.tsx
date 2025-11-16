import { useState } from 'react'
import type { AuthMode } from './AuthLayout'
import { Link } from 'react-router-dom'

interface AuthFormProps {
    mode: AuthMode
}

export const AuthForm: React.FC<AuthFormProps> = ({ mode }) => {
    const [phoneNumber, setPhoneNumber] = useState('')
    const [password, setPassword] = useState('')
    const [name, setName] = useState('')

    const isSignIn = mode === 'sign-in'

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault()
        // Replace with real auth logic / API call
        console.log('Auth submit:', { mode, phoneNumber, password, name })
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
                        placeholder="Jane Pizza Lover"
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
                    value={phoneNumber}
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