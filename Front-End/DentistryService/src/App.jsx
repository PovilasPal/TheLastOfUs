import React from 'react'
import { Route, Routes } from 'react-router-dom'
import UserProviderRegistration from './components/UserProviderRegistrationForm'
import Navigation from './components/Navigation'
import Home from './components/Home'

import UserClientRegistrationForm from "./components/UserClientRegistrationForm"

function App() {

  return (
    <>
      <Navigation/>
  
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/register" element={<UserProviderRegistration/>}/>
        <Route path="/registration-success" element={<h1>Registration successful</h1>}/>
        <Route path="/*" element={<h1>404 Not Found</h1>}/>
      </Routes>
     <UserClientRegistrationForm />
  </>
    
  )
}

export default App
