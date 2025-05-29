import React from 'react';
import { Routes, Route } from "react-router";
import Navigation from './components/Navigation';
import Home from './components/Home';
import UserProviderRegistration from './components/UserProviderRegistrationForm';
import UserClientRegistrationForm from "./components/UserClientRegistrationForm";
import LoginClientPage from "./components/LogInClient";
import LoginProviderPage from './components/LoginProvider';
import EditProviderProfile from './components/EditProviderProfile';
import EditClientProfile from './components/EditClientProfile';
import EmployeeManager from './components/EmployeeManager';
import ProviderTreatments from './components/ProviderTreatments'; // ← New import

function App() {
  return (
    <>
      <Navigation />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/register" element={<UserProviderRegistration />} />
        <Route path="/registration-success" element={<h1>Registration successful</h1>} />
        <Route path="/users_clients" element={<UserClientRegistrationForm />} />
        <Route path="/login/client" element={<LoginClientPage />} />
        <Route path="/login/provider" element={<LoginProviderPage />} />
        <Route path="/provider/profile" element={<EditProviderProfile />} />
        <Route path="/provider/employees" element={<EmployeeManager />} />
        <Route path="/provider/treatments" element={<ProviderTreatments />} /> {/* ← New route */}
        <Route path="/client/profile" element={<EditClientProfile />} />
        <Route path="/*" element={<h1>404 Not Found</h1>} />
      </Routes>
    </>
  );
}

export default App;
