import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createEmployee } from '../../api/employeeApi';

const EmployeeForm = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    licenceNumber: '',
    qualification: '',
    service: ''
  });
  const [error, setError] = useState('');
  

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await createEmployee(formData);
      navigate('/employees');
    } catch (err) {
      setError(err.response?.data?.message || 'Error creating employee');
    }
  };

  return (
    <div className="p-4 max-w-md mx-auto">
      <h1 className="text-2xl font-bold mb-4">Add New Employee</h1>
      {error && <div className="text-red-500 mb-4">{error}</div>}
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block mb-1">First Name (Vardas)</label>
          <input
            type="text"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
            className="w-full p-2 border rounded"
            required
          />
        </div>
        
        <div>
          <label className="block mb-1">Last Name (Pavardė)</label>
          <input
            type="text"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
            className="w-full p-2 border rounded"
            required
          />
        </div>
        
        <div>
          <label className="block mb-1">License Code (Lic. kodas)</label>
          <input
            type="text"
            name="licenceNumber"
            value={formData.licenceNumber}
            onChange={handleChange}
            className="w-full p-2 border rounded"
            required
          />
        </div>
        
        <div>
          <label className="block mb-1">Qualification (Kvalifikacija)</label>
          <input
            type="text"
            name="qualification"
            value={formData.qualification}
            onChange={handleChange}
            className="w-full p-2 border rounded"
          />
        </div>
        
        <div>
          <label className="block mb-1">Service (Paslaugos)</label>
          <input
            type="text"
            name="service"
            value={formData.service}
            onChange={handleChange}
            className="w-full p-2 border rounded"
          />
        </div>
        
        <div className="flex justify-between">
          <button 
            type="submit" 
            className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600"
          >
            Save (Išsaugoti)
          </button>
          <button 
            type="button" 
            onClick={() => navigate('/employees')}
            className="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
          >
            Cancel (Atšaukti)
          </button>
        </div>
      </form>
    </div>
  );
};

export default EmployeeForm;