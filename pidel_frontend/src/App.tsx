import './App.css'
import { Routes, Route } from 'react-router-dom'
import {PizzaFeed} from "./components/pizza/PizzaFeed.tsx";
import {Navbar} from "./components/common/navbar/Navbar.tsx";
import {AuthLayout} from "./components/auth/AuthLayout.tsx";
import {Pizza} from "./components/pizza/Pizza.tsx";

function App() {
  return (
    <>
        <Navbar />
        <Routes>
            <Route path='/' element={<PizzaFeed />} />
            <Route path="/pizza/:id" element={<Pizza />} />
            <Route
                path="/sign-in"
                element={
                    <AuthLayout
                        title="Welcome back"
                        subtitle="Sign in to continue exploring delicious pizzas."
                        mode="sign-in"
                    />
                }
            />
            <Route
                path="/sign-up"
                element={
                    <AuthLayout
                        title="Create your account"
                        subtitle="Join us and never miss a slice."
                        mode="sign-up"
                    />
                }
            />
        </Routes>
    </>
  )
}

export default App
