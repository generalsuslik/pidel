import './App.css'
import {PizzaFeed} from "./components/pizza/PizzaFeed.tsx";
import {Navbar} from "./components/common/navbar/Navbar.tsx";

function App() {
  return (
    <>
        <Navbar />
        <PizzaFeed/>
    </>
  )
}

export default App
