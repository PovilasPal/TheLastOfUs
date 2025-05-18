import { useEffect, useState } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { getEmployeeById, deleteEmployee } from '../../api/employeeApi';
import { toast } from 'react-toastify';


const EmployeeDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [employee, setEmployee] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchEmployee = async () => {
      try {
        const employeeData = await getEmployeeById(id);
        setEmployee(employeeData);
      } catch (error) {
        console.error("Error fetching employee:", error);
        setError("Failed to load employee details.");
      } finally {
        setLoading(false);
      }
    };
    fetchEmployee();
  }, [id]);

  const handleDelete = async () => {
    if (window.confirm("Are you sure you want to delete this employee?")) {
      try {
        await deleteEmployee(id);
        navigate('/employees');
      } catch (error) {
        console.error("Failed to delete employee:", error);
        alert("Failed to delete employee. Please try again.");
      }
    }
  };


  if (loading) return <div>Loading...</div>;
  if (error) return <div className="text-red-600">{error}</div>;
  if (!employee) return <div>Employee not found</div>;

  return (
    <div className="p-4 max-w-md mx-auto">
      <h1 className="text-2xl font-bold mb-4">Employee Details</h1>
      <div className="space-y-2">
        <p><span className="font-semibold">Name:</span> {employee.firstName} {employee.lastName}</p>
        <p><span className="font-semibold">Licence:</span> {employee.licenceNumber}</p>
        <p><span className="font-semibold">Qualification:</span> {employee.qualification}</p>
        <p><span className="font-semibold">Service:</span> {employee.service}</p>
      </div>
      <div className="mt-4 flex space-x-4">
        <Link 
          to="/employees" 
          className="bg-gray-200 px-4 py-2 rounded hover:bg-gray-300"
        >
          Back to List
        </Link>
        <button
          onClick={handleDelete}
          className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
        >
          Delete
        </button>
      </div>
    </div>
  );
};

export default EmployeeDetail;
