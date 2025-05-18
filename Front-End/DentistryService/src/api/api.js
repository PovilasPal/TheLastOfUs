import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8081/api',
});

// Set JWT token in headers
export const setAuthToken = (token) => {
  if (token) {
    api.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    localStorage.setItem("jwt_token", token); // Store token
  } else {
    delete api.defaults.headers.common["Authorization"];
    localStorage.removeItem("jwt_token"); // Remove token
  }
};

// Initialize auth token if exists
const token = localStorage.getItem("jwt_token");
if (token) {
  setAuthToken(token);
}

// Login function 
export const login = async (username, password) => {
  try {
    const response = await api.post("/api/auth/login", { username, password });
    setAuthToken(response.data.token); 
    return response;
  } catch (error) {
    throw error;
  }
};

// Logout function
export const logout = () => {
  setAuthToken(null);
};

// Employee API methods
export const getEmployees = () => api.get("/api/userProviders/me/employees");

export const createEmployee = (employeeData) =>
  api.post("/api/userProviders/me/employees", employeeData);

export const getEmployeeById = (id) =>
  api.get(`/api/userProviders/me/employees/${id}`);

export const updateEmployee = (id, employeeData) =>
  api.put(`/api/userProviders/me/employees/${id}`, employeeData);

export const deleteEmployee = (id) =>
  api.delete(`/api/userProviders/me/employees/${id}`);

export default api;