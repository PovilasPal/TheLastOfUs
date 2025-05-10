import {createContext, useContext, useState} from "react";
import { useNavigate } from "react-router";
import api, { setAuth, clearAuth } from "../utils/api.js";


const AuthContext = createContext({
    user: null,
    login: () => {},
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

    const login = async (username, password, endpoints = ["login/client", "login/provider"]) => {
        setAuth(username, password);

        let userData = null;
        let usedEndpoint = null;

        for(const endpoint of endpoints) {
            try {
                const response = await api.get(`/${endpoint}`);
                userData = response.data;
                usedEndpoint = endpoint;
                break;
            } catch (error) {
                console.log(error);
            }
        }
        
        if (!userData) {
            throw new Error("User not found");
        }

        const user = {
            username,
            password,
            roles: userData.roles,
            endpoint: usedEndpoint
        }

        localStorage.setItem("user", JSON.stringify(user));
        setUser(user);
        navigate("/");
    };

    const registerUser = async (username, password) => {
        await api.post("auth/register", { username, password });
        navigate("/login");
    };

    const logout = () => {
        setUser(null);
        clearAuth();
        localStorage.removeItem("user");
        navigate("/login");
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