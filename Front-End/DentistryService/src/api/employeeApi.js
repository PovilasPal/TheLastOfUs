import axios from 'axios';
import { toast } from 'react-toastify';

// Create axios instance with base configuration
const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8081/api',
  timeout: 10000, // 10 second timeout
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true, 
});


api.interceptors.response.use(
  (response) => response,
  (error) => {
    const { response } = error;

    if (response) {
      switch (response.status) {
        case 401: // Unauthorized - session expired or not logged in
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

    return Promise.reject({
      status: response?.status,
      message: response?.data?.message || error.message,
      code: error.code,
      data: response?.data,
    });
  }
);


export const login = async (username, password) => {
  try {
    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);

    const response = await api.post('/login', params, {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    });

    
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Login failed');
  }
};

// Logout function: POST to /logout endpoint
export const logout = async () => {
  try {
    await api.post('/logout');
  } catch (error) {
    console.error('Logout failed', error);
  }
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


export default api;

