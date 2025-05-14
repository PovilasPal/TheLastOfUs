import React from 'react'
import { Route, Routes } from 'react-router-dom'
import UserProviderRegistration from './components/UserProviderRegistrationForm'
import Navigation from './components/Navigation'
import Home from './components/Home'

import UserClientRegistrationForm from "./components/UserClientRegistrationForm"
import { Routes, Route } from "react-router";
import LoginClientPage from "./pages/LogInClientPage";

function App() {

  return (
    <>
      <Navigation/>
  
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/register" element={<UserProviderRegistration/>}/>
        <Route path="/registration-success" element={<h1>Registration successful</h1>}/>
        <Route path="/*" element={<h1>404 Not Found</h1>}/>
        <Route path="/users_clients" element={<UserClientRegistrationForm />} />
      <Route path="/login/client" element={<LoginClientPage />} />
      </Routes>
  </>
  )
}

export default App
