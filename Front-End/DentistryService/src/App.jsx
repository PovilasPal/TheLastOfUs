import React from 'react'
import UserProviderRegistration from './components/UserProviderRegistrationForm'
import Navigation from './components/Navigation'
import Home from './components/Home'
import UserClientRegistrationForm from "./components/UserClientRegistrationForm"
import { Routes, Route } from "react-router";
import LoginClientPage from "./components/LogInClient";
import LoginProviderPage from './components/LoginProvider'
import EditProviderProfile from './components/EditProviderProfile';

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
      <Route path='/login/provider' element={<LoginProviderPage />} />
      <Route path="/provider/profile" element={<EditProviderProfile />} />
      </Routes>
  </>
  )
}

export default App
