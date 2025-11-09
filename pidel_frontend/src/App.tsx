import './App.css'
import { Routes, Route } from 'react-router-dom'
import {PizzaFeed} from "./components/pizza/PizzaFeed.tsx";
import {Navbar} from "./components/common/navbar/Navbar.tsx";

function App() {
  return (
    <>
        <Navbar />
        <Routes>
            <Route path='/' element={<PizzaFeed />} />
        </Routes>
    </>
  )
}

export default App
