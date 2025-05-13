import React from 'react'
import { Route, Routes } from 'react-router-dom'
import UserProviderRegistration from './components/UserProviderRegistrationForm'
import Navigation from './components/Navigation'


function App() {

  return (
    <>
      <Navigation/>
  
      <Routes>
        
        <Route path="/register" element={<UserProviderRegistration/>}/>
        <Route path="/registration-success" element={<h1>Registration successful</h1>}/>
        <Route path="/*/*" element={<h1>404 Not Found</h1>}/>
      </Routes>
     
  </>
  )
}

export default App
