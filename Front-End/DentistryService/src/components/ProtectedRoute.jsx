import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const ProtectedRoute = ({ children, requiredRole }) => {
  const { user, isAuthenticated } = useAuth();

  if (!isAuthenticated) {
    // Not logged in
    return <Navigate to="/login" replace />;
  }

  if (requiredRole && (!user.roles || !user.roles.includes(requiredRole))) {
    // Logged in but missing required role
    return <Navigate to="/unauthorized" replace />;
  }

  // Authorized
  return children;
};

export default ProtectedRoute;
