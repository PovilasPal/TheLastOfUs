import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { getEmployees } from "../../api/employeeApi";
import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";   
import { toast } from 'react-toastify'; 
import { ToastContainer } from 'react-toastify';




const EmployeeList = () => {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { logout } = useAuth();
  const navigate = useNavigate();

 useEffect(() => {
    const fetchEmployees = async () => {
      try {
      const employeesData = await getEmployees();
      setEmployees(employeesData);
      setError(null);
    } catch (err) {
      if (err.response?.status === 401) {
        logout();
        navigate("/login");
      } else {
        setError(err.message || "Failed to load employees");
      }
    } finally {
      setLoading(false);
    }
    };
    fetchEmployees();
  }, [logout, navigate]);

  if (loading) return <div>Loading employees...</div>;
  if (error) return <div className="text-red-500">{error}</div>;

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Employees</h1>
      <Link 
        to="/employees/new" 
        className="bg-blue-500 text-white px-4 py-2 rounded mb-4 inline-block"
      >
        Add Employee
      </Link>
      <div className="overflow-x-auto">
        <table className="min-w-full bg-white">
          <thead>
            <tr className="bg-gray-100">
              <th className="py-2 px-4">Name</th>
              <th className="py-2 px-4">Licence</th>
              <th className="py-2 px-4">Qualification</th>
              <th className="py-2 px-4">Service</th>
            </tr>
          </thead>
          <tbody>
            {employees.map(employee => (
              <tr key={employee.id} className="border-b">
                <td className="py-2 px-4">
                  <Link to={`/employees/${employee.id}`} className="text-blue-500">
                    {employee.firstName} {employee.lastName}
                  </Link>
                </td>
                <td className="py-2 px-4">{employee.licenceNumber}</td>
                <td className="py-2 px-4">{employee.qualification}</td>
                <td className="py-2 px-4">{employee.service}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default EmployeeList;
   