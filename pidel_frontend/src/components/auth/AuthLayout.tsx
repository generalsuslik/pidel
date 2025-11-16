import './Auth.css'
import { AuthForm } from './AuthForm'
import * as React from "react";

export type AuthMode = 'sign-in' | 'sign-up' | 'sign-up-admin'

interface AuthLayoutProps {
    title: string
    subtitle?: string
    mode: AuthMode
}

export const AuthLayout: React.FC<AuthLayoutProps> = ({ title, subtitle, mode }) => {
    return (
        <div className="auth-page">
            <div className="auth-background-gradient" />
            <div className="auth-background-blobs">
                <div className="blob blob-1" />
                <div className="blob blob-2" />
                <div className="blob blob-3" />
            </div>

            <div className="auth-card">
                <div className="auth-card-header">
                    <h1 className="auth-title">{title}</h1>
                    {subtitle && <p className="auth-subtitle">{subtitle}</p>}
                </div>

                <AuthForm mode={mode} />
            </div>
        </div>
    )
}