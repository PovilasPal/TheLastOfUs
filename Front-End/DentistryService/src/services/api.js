import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

export const registerUserProvider = (userProviderData) => {
    return api.post('/providerRegistration', userProviderData);
};

export const getRegistrationFormStructure = () => {
    return api.get('/register');
};

export default api;