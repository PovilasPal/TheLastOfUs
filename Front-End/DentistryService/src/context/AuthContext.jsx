import { createContext, useContext, useState, useEffect, useCallback, useMemo } from "react";
import { useNavigate } from "react-router-dom";
import { setAuthToken, login as apiLogin, logout as apiLogout } from "../api/api";

const AuthContext = createContext({
  user: null,
  login: async () => {},
  logout: () => {},
  register: async () => {},
  isAuthenticated: false,
  loading: true,
  error: null,
  clearError: () => {},
});

export const AuthProvider = ({ children }) => {
  const navigate = useNavigate();
  const [authState, setAuthState] = useState({
    user: null,
    loading: true,
    error: null,
  });

  const updateAuthState = useCallback((updates) => {
    setAuthState(prev => ({ ...prev, ...updates }));
  }, []);

  useEffect(() => {
    const initializeAuth = async () => {
      const token = localStorage.getItem("jwt_token");
      const savedUser = localStorage.getItem("user");

      if (token && savedUser) {
        try {
          updateAuthState({ loading: true });
          const response = await api.get("/user/me");
          
          const userData = JSON.parse(savedUser);
          if (response.data.username !== userData.username) {
            throw new Error("Session mismatch");
          }

          updateAuthState({
            user: userData,
            loading: false,
          });
        } catch (error) {
          console.error("Session validation failed:", error);
          apiLogout();
          updateAuthState({
            user: null,
            loading: false,
            error: "Session expired. Please login again.",
          });
        }
      } else {
        updateAuthState({ loading: false });
      }
    };

    initializeAuth();
  }, [updateAuthState]);

  const login = useCallback(async (username, password, endpoints) => {
    try {
      updateAuthState({ loading: true, error: null });
      const authResponse = await apiLogin(username, password);
      const userResponse = await api.get(endpoints);
      const userData = userResponse.data;

      if (!userData) {
        throw new Error("User data not found");
      }

      const user = {
        username,
        roles: userData.roles,
        endpoints,
      };

      localStorage.setItem("user", JSON.stringify(user));
      updateAuthState({
        user,
        loading: false,
      });
      navigate("/");
      return user;
    } catch (error) {
      updateAuthState({
        loading: false,
        error: error.response?.data?.message || error.message,
      });
      throw error;
    }
  }, [navigate, updateAuthState]);

  const registerUser = useCallback(async (username, password) => {
    try {
      updateAuthState({ loading: true, error: null });
      await api.post("/register", { username, password });
      navigate("/login");
      updateAuthState({ loading: false });
    } catch (error) {
      updateAuthState({
        loading: false,
        error: error.response?.data?.message || "Registration failed",
      });
      throw error;
    }
  }, [navigate, updateAuthState]);

  const logout = useCallback(() => {
    apiLogout();
    localStorage.removeItem("user");
    updateAuthState({ user: null });
    navigate("/login");
  }, [navigate, updateAuthState]);

  const clearError = useCallback(() => {
    updateAuthState({ error: null });
  }, [updateAuthState]);

  // Memoize the context value to prevent unnecessary re-renders
  const value = useMemo(() => ({
    user: authState.user,
    login,
    logout,
    register: registerUser,
    isAuthenticated: !!authState.user,
    loading: authState.loading,
    error: authState.error,
    clearError,
  }), [
    authState.user,
    authState.loading,
    authState.error,
    login,
    logout,
    registerUser,
    clearError,
  ]);

  return (
    <AuthContext.Provider value={value}>
      {!authState.loading && children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};