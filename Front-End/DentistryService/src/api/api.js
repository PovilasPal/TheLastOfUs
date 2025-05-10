import axios from "axios";

const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL
})

export const setAuth = (username, password) => {
    api.defaults.auth = {
        username,
        password
    }
}

export const clearAuth = () => {
    delete api.defaults.auth;
}

const maybeUser = localStorage.getItem("user");

if (maybeUser) {
    const user = JSON.parse(maybeUser);
    setAuth(user.username, user.password);
}

export default api;