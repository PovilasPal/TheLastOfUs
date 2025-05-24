import { createContext, useContext, useState } from "react";
import { useNavigate } from "react-router";
import api, { setAuth, clearAuth } from "../api/api.js";

const AuthContext = createContext({
  user: null,
  login: async () => {},
  logout: () => {},
  register: () => {},
});

export const AuthProvider = ({ children }) => {
  const navigate = useNavigate();
  const [user, setUser] = useState(() => {
    const maybeUser = localStorage.getItem("user");

    if (maybeUser) {
      return JSON.parse(maybeUser);
    }
  });

  const login = async (username, password, endpoints) => {
    setAuth(username, password);

    const response = await api.get(endpoints);
    const userData = response.data;


    if (!userData) {
      throw new Error("User not found");
    }

    const user = {
      id: userData.id || null,
      username,
      password,
      licenseNumber : userData.licenseNumber || null,
      roles: userData.roles,
      endpoints
    };

    localStorage.setItem("user", JSON.stringify(user));
    setUser(user);
    navigate("/");
  };

  const registerUser = async (username, password) => {
    await api.post("/register", { username, password });
    navigate("/login");
  };

  const logout = () => {
    setUser(null);
    clearAuth();
    localStorage.removeItem("user");
    navigate("/");
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, registerUser }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  return useContext(AuthContext);
};
