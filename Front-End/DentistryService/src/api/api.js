import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8081/api',
  withCredentials: true,
});

export const login = async (username, password) => {
  try {
    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);

    const response = await api.post("/login", params, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    });
    // No token to store, session cookie is set automatically
    return response;
  } catch (error) {
    throw error;
  }
};

export const logout = async () => {
  try {
    await api.post("/logout");
  } catch (error) {
    console.error("Logout failed", error);
  }
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