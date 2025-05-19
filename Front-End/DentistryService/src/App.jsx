import React from 'react'
import UserProviderRegistration from './components/UserProviderRegistrationForm'
import Navigation from './components/Navigation'
import Home from './components/Home'
import UserClientRegistrationForm from "./components/UserClientRegistrationForm"
import { Routes, Route } from "react-router-dom";
import LoginClientPage from "./components/LogInClient";
import LoginProviderPage from './components/LoginProvider'
import EmployeeList from './components/employees/EmployeeList'
import EmployeeForm from './components/employees/EmployeeForm'
import EmployeeDetail from './components/employees/EmployeeDetail'
import ProtectedRoute from './components/ProtectedRoute';
 

function App() {

  return (
    <>
      <Navigation/>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/register" element={<UserProviderRegistration/>}/>
        <Route path="/registration-success" element={<h1>Registration successful</h1>}/>
        <Route path="/users_clients" element={<UserClientRegistrationForm />} />
      <Route path="/login/client" element={<LoginClientPage />} />
      <Route path='/login/provider' element={<LoginProviderPage />} />
      
 <Route path="/employees" element={
        <ProtectedRoute>
          <EmployeeList />
        </ProtectedRoute>
      } />
      <Route path="/employees/new" element={
        <ProtectedRoute>
          <EmployeeForm />
        </ProtectedRoute>
      } />
      <Route path="/employees/:id" element={
        <ProtectedRoute>
          <EmployeeDetail />
        </ProtectedRoute>
      } />
        <Route path="/*" element={<h1>404 Not Found</h1>} />
      </Routes>
      
  </>
  )
}

export default App
