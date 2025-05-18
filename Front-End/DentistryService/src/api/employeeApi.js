// src/api/employeeApi.js
import axios from 'axios';

// Create axios instance with base configuration
const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8081/api',
  timeout: 10000, // 10 second timeout
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor for JWT tokens
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('jwt_token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor for error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Handle unauthorized (401) errors
    if (error.response?.status === 401) {
      localStorage.removeItem('jwt_token');
      window.location.href = '/login'; // Redirect to login
    }
    api.interceptors.response.use(
  (response) => {
    // Successful responses (2xx)
    return response;
  },
  (error) => {
    // Error responses (4xx, 5xx)
    const { response } = error;
    
    // Handle specific status codes
    if (response) {
      switch (response.status) {
        case 401: // Unauthorized
          localStorage.removeItem('jwt_token');
          window.location.href = '/login?reason=session_expired';
          break;
          
        case 403: // Forbidden
          toast.error("You don't have permission for this action");
          break;
          
        case 404: // Not Found
          toast.error("Resource not found");
          break;
          
        case 429: // Too Many Requests
          toast.error("Too many requests, please try again later");
          break;
          
        case 500: // Server Error
          toast.error("Server error - please try again later");
          break;
          
        default:
          toast.error(response.data?.message || "An error occurred");
      }
    } else if (error.code === 'ECONNABORTED') {
      toast.error("Request timeout - please check your connection");
    } else {
      toast.error("Network error - please check your connection");
    }

    // Always reject to allow component-level error handling
    return Promise.reject({
      status: response?.status,
      message: response?.data?.message || error.message,
      code: error.code,
      data: response?.data
    });
  }
);
    // Handle other errors
    if (error.response) {
      // Server responded with error status (4xx, 5xx)
      console.error('API Error:', {
        status: error.response.status,
        data: error.response.data,
        headers: error.response.headers,
      });
    } else if (error.request) {
      // Request was made but no response received
      console.error('No response received:', error.request);
    } else {
      // Something happened in setting up the request
      console.error('Request setup error:', error.message);
    }

    return Promise.reject(error);
  }
);

// Auth functions
export const login = async (credentials) => {
  try {
    const response = await api.post('/auth/login', credentials);
    localStorage.setItem('jwt_token', response.data.token);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Login failed');
  }
};

export const logout = () => {
  localStorage.removeItem('jwt_token');
};

// Employee CRUD operations
export const getEmployees = async () => {
  try {
    const response = await api.get('/userProviders/me/employees');
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Failed to fetch employees');
  }
};

export const getEmployeeById = async (id) => {
  try {
    const response = await api.get(`/userProviders/me/employees/${id}`);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Employee not found');
  }
};

export const createEmployee = async (employeeData) => {
  try {
    const response = await api.post('/userProviders/me/employees', employeeData);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Failed to create employee');
  }
};

export const updateEmployee = async (id, employeeData) => {
  try {
    const response = await api.put(`/userProviders/me/employees/${id}`, employeeData);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Failed to update employee');
  }
};

export const deleteEmployee = async (id) => {
  try {
    const response = await api.delete(`/userProviders/me/employees/${id}`);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Failed to delete employee');
  }
};

// Verify token validity
export const verifyToken = async () => {
  try {
    const response = await api.get('/auth/verify');
    return response.data.valid;
  } catch (error) {
    return false;
  }
};

export default api;
