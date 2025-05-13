import React from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import Home from './components/Home'
import UserProviderRegistration from './components/UserProviderRegistrationForm'
import Navigation from './components/Navigation'


function App() {

  return (
    <Router>
      <Navigation/>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/register" element={<UserProviderRegistration/>}/>
        
      </Routes>
     
    </Router>
  )
}

export default App
