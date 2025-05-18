import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getEmployeeById } from '../../api/employeeApi';

const EmployeeDetail = () => {
  const { id } = useParams();
  const [employee, setEmployee] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchEmployee = async () => {
      try {
        const response = await getEmployeeById(id);
        setEmployee(response.data);
      } catch (error) {
        console.error("Error fetching employee:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchEmployee();
  }, [id]);

  if (loading) return <div>Loading...</div>;
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
      <Link 
        to="/employees" 
        className="mt-4 inline-block bg-gray-200 px-4 py-2 rounded hover:bg-gray-300"
      >
        Back to List
      </Link>
    </div>
  );
};

export default EmployeeDetail;