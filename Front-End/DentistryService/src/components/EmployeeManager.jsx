import React, { useEffect, useState } from 'react';
import { useForm, Controller } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import api from '../api/api.js';
import Select from 'react-select';
import { useAuth } from '../context/AuthContext.jsx';

// Validation schema
const schema = yup.object().shape({
  licenseNumber: yup
  .string()
  .required('License number is required')
  .matches(
      /^[A-Z]{3}[0-9]{5}$/,
      'License number must be 3 uppercase letters followed by 5 digits'
    ),
  name: yup
  .string()
  .required('Name is required')
  .min(2, 'Name must be at least 2 characters')
  .max(50, 'Name cannot be longer than 50 characters')
    .matches(
      /^[A-Za-zÀ-ÖØ-öø-ÿ\s-]+$/,
      'Name can only contain letters, spaces and hyphens'
    ),
  lastName: yup
  .string()
  .required('Last name is required')
  .min(2, 'Name must be at least 2 characters')
  .max(50, 'Name cannot be longer than 50 characters'),
  qualification: yup.string().required('Qualification is required'),
  treatmentIds: yup.array().min(1, 'Select at least one treatment'),
});

export default function EmployeeManager() {
  const { user } = useAuth();
  const licenseNumber = user?.licenseNumber;

  const [employees, setEmployees] = useState([]);
  const [treatments, setTreatments] = useState([]);
  const [editingLicenseNumber, setEditingLicenseNumber] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const {
    register,
    handleSubmit,
    control,
    reset,
    formState: { errors, isSubmitting },
  } = useForm({
    resolver: yupResolver(schema),
    defaultValues: {
      licenseNumber: '',
      name: '',
      lastName: '',
      qualification: '',
      treatmentIds: [],
    },
  });

  useEffect(() => {
    fetchEmployees();
    fetchTreatments();
  }, [licenseNumber]);

  const fetchEmployees = async () => {
    try {
      const res = await api.get('/api/provider/employees');
      setEmployees(res.data);
    } catch {
      setError('Failed to fetch employees');
    }
  };

  const fetchTreatments = async () => {
    if (!licenseNumber) return;
    try {
      const res = await api.get(`/api/providers/${licenseNumber}`);
      setTreatments(res.data.treatments || []);
    } catch (err) {
      setError('Failed to fetch treatments');
      setTreatments([]);
    }
  };

  const onSubmit = async (data) => {
    setError(null);
    setLoading(true);
    try {
      if (editingLicenseNumber) {
        await api.put(`/api/provider/employees/${editingLicenseNumber}`, data);
      } else {
        await api.post('/api/provider/employees', data);
      }
      reset();
      setEditingLicenseNumber(null);
      fetchEmployees();
    } catch {
      setError('Failed to save employee');
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (employee) => {
    reset({
      licenseNumber: employee.licenseNumber,
      name: employee.name,
      lastName: employee.lastName,
      qualification: employee.qualification,
      treatmentIds: employee.treatments.map((t) =>
        typeof t === 'string' ? Number(t) : t.id
      ),
    });
    setEditingLicenseNumber(employee.licenseNumber);
    setError(null);
  };

  const handleDelete = async (licenseNumber) => {
    if (!window.confirm('Are you sure you want to delete this employee?')) return;
    setLoading(true);
    setError(null);
    try {
      await api.delete(`/api/provider/employees/${licenseNumber}`);
      if (editingLicenseNumber === licenseNumber) {
        reset();
        setEditingLicenseNumber(null);
      }
      fetchEmployees();
    } catch {
      setError('Failed to delete employee');
    } finally {
      setLoading(false);
    }
  };

  const handleCancelEdit = () => {
    reset();
    setEditingLicenseNumber(null);
    setError(null);
  };

  return (
    <div className="max-w-4xl mx-auto p-6 font-sans">
      <h1 className="text-3xl font-bold mb-6">Employee Management</h1>

      {error && <div className="mb-4 text-red-600 font-semibold">{error}</div>}

      <ul className="mb-8 space-y-4">
        {employees.map((emp) => (
          <li
            key={emp.licenseNumber}
            className="p-4 border rounded shadow-sm flex flex-col md:flex-row md:items-center md:justify-between"
          >
            <div>
              <p className="font-semibold text-lg">
                {emp.name} {emp.lastName}
              </p>
              <p className="text-gray-600">{emp.qualification}</p>
              <p className="text-gray-700 mt-1">
                Treatments:{' '}
                {emp.treatments
                  .map((t) => (typeof t === 'string' ? t : t.name))
                  .join(', ')}
              </p>
              <p className="text-gray-700 mt-1">
                Appointments:{' '}
                {emp.appointments && emp.appointments.length > 0 ? (
                  <ul className="list-disc ml-5">
                    {emp.appointments.map((appt, idx) => (
                      <li key={idx}>
                        {appt.date} {appt.startTime} - {appt.endTime}
                      </li>
                    ))}
                  </ul>
                ) : (
                  <span className="italic text-gray-500">No appointments</span>
                )}
              </p>
            </div>
            <div className="mt-4 md:mt-0 flex space-x-3">
              <button
                onClick={() => handleEdit(emp)}
                disabled={loading}
                className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded transition"
              >
                Edit
              </button>
              <button
                onClick={() => handleDelete(emp.licenseNumber)}
                disabled={loading}
                className="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded transition"
              >
                Delete
              </button>
            </div>
          </li>
        ))}
      </ul>

      {/* Employee Form */}
      <h2 className="text-2xl font-semibold mb-4">
        {editingLicenseNumber ? 'Edit Employee' : 'Add New Employee'}
      </h2>
      <form onSubmit={handleSubmit(onSubmit)} className="max-w-md space-y-5">
        {/* License Number */}
        <div>
          <label htmlFor="licenseNumber" className="block mb-1 font-medium">
            License Number
          </label>
          <input
            id="licenseNumber"
            {...register('licenseNumber')}
            disabled={!!editingLicenseNumber}
            className={`w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 ${
              errors.licenseNumber
                ? 'border-red-500 focus:ring-red-500'
                : 'border-gray-300 focus:ring-blue-500'
            }`}
          />
          {errors.licenseNumber && (
            <p className="text-red-500 mt-1 text-sm">{errors.licenseNumber.message}</p>
          )}
        </div>

        {/* Name */}
        <div>
          <label htmlFor="name" className="block mb-1 font-medium">
            Name
          </label>
          <input
            id="name"
            {...register('name')}
            className={`w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 ${
              errors.name
                ? 'border-red-500 focus:ring-red-500'
                : 'border-gray-300 focus:ring-blue-500'
            }`}
          />
          {errors.name && (
            <p className="text-red-500 mt-1 text-sm">{errors.name.message}</p>
          )}
        </div>

        {/* Last Name */}
        <div>
          <label htmlFor="lastName" className="block mb-1 font-medium">
            Last Name
          </label>
          <input
            id="lastName"
            {...register('lastName')}
            className={`w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 ${
              errors.lastName
                ? 'border-red-500 focus:ring-red-500'
                : 'border-gray-300 focus:ring-blue-500'
            }`}
          />
          {errors.lastName && (
            <p className="text-red-500 mt-1 text-sm">{errors.lastName.message}</p>
          )}
        </div>

        {/* Qualification */}
        <div>
          <label htmlFor="qualification" className="block mb-1 font-medium">
            Qualification
          </label>
          <input
            id="qualification"
            {...register('qualification')}
            className={`w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 ${
              errors.qualification
                ? 'border-red-500 focus:ring-red-500'
                : 'border-gray-300 focus:ring-blue-500'
            }`}
          />
          {errors.qualification && (
            <p className="text-red-500 mt-1 text-sm">{errors.qualification.message}</p>
          )}
        </div>

        {/* Treatments Multi-select */}
        <Controller
          name="treatmentIds"
          control={control}
          render={({ field }) => (
            <div>
              <label className="block mb-1 font-medium">Treatments</label>
              <Select
                isMulti
                options={treatments.map((t) => ({ value: t.id, label: t.name }))}
                value={treatments
                  .filter((t) => field.value?.includes(t.id))
                  .map((t) => ({ value: t.id, label: t.name }))}
                onChange={(selected) =>
                  field.onChange(selected.map((s) => s.value))
                }
              />
              {errors.treatmentIds && (
                <p className="text-red-500 mt-1 text-sm">{errors.treatmentIds.message}</p>
              )}
            </div>
          )}
        />

        {/* Submit + Cancel */}
        <div className="flex gap-3">
          <button
            type="submit"
            disabled={isSubmitting}
            className="bg-green-600 hover:bg-green-700 text-white px-5 py-2 rounded transition"
          >
            {editingLicenseNumber ? 'Update' : 'Add'} Employee
          </button>
          {editingLicenseNumber && (
            <button
              type="button"
              onClick={handleCancelEdit}
              className="bg-gray-500 hover:bg-gray-600 text-white px-5 py-2 rounded transition"
            >
              Cancel
            </button>
          )}
        </div>
      </form>
    </div>
  );
}
