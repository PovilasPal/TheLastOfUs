import React from 'react';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
const UserProviderRegistration = () => {
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm();

  const [apiError, setApiError] = React.useState('');
  const [isSubmitting, setIsSubmitting] = React.useState(false);

const [formStructure] = React.useState({
    licenceNumber: '',
    name: '',
    email: '',
    phoneNumber: '',
    username: '',
    password: '',
    roles: [{ id: 2, name: 'PROVIDER' }]
  });

  
  const onSubmit = async (data) => {
    setIsSubmitting(true);
    setApiError('');
    
    try {
      const submissionData = {...data, roles: formStructure.roles};


      const response = await axios.post('http://localhost:8080/api/providerRegistration', submissionData, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      
      // Handle successful registration
      console.log('Registration successful:', response.data);
      navigate('/registration-success'); 
    } catch (error) {
      if (error.response) {
      if (error.response.data && error.response.data.message) {
        setApiError(error.response.data.message);
      } else {
        setApiError('Registration failed. Please check your inputs.');
      }
    } else if (error.request) {
      setApiError('Network error. Please check your internet connection.');
    } else {
      setApiError('An unexpected error occurred.');
    }
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white rounded-lg shadow-md">
      <h2 className="text-2xl font-bold mb-6 text-center">Provider Registration</h2>
      
      {apiError && (
        <div className="mb-4 p-3 bg-red-100 text-red-700 rounded">
          {apiError}
        </div>
      )}
      
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        {/* Licence Number */}
        <div>
          <label htmlFor="licenceNumber" className="block text-sm font-medium text-gray-700">
            Licence Number
          </label>
          <input
            id="licenceNumber"
            type="text"
            {...register('licenceNumber', {
              required: 'Licence number is required',
              pattern: {
                value: /^[A-Z]{3}\d{5}$/,
                message: 'Licence number must be in format ABC12345 (3 uppercase letters + 5 digits)'
              }
            })}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-2 border"
          />
          {errors.licenceNumber && (
            <p className="mt-1 text-sm text-red-600">{errors.licenceNumber.message}</p>
          )}
        </div>

        {/* Name */}
        <div>
          <label htmlFor="name" className="block text-sm font-medium text-gray-700">
            Full Name
          </label>
          <input
            id="name"
            type="text"
            {...register('name', { required: 'Name is required' })}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-2 border"
          />
          {errors.name && (
            <p className="mt-1 text-sm text-red-600">{errors.name.message}</p>
          )}
        </div>

        {/* Email */}
        <div>
          <label htmlFor="email" className="block text-sm font-medium text-gray-700">
            Email
          </label>
          <input
            id="email"
            type="email"
            {...register('email', {
              required: 'Email is required',
              pattern: {
                value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                message: 'Invalid email address'
              }
            })}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-2 border"
          />
          {errors.email && (
            <p className="mt-1 text-sm text-red-600">{errors.email.message}</p>
          )}
        </div>

        {/* Phone Number */}
        <div>
          <label htmlFor="phoneNumber" className="block text-sm font-medium text-gray-700">
            Phone Number
          </label>
          <input
            id="phoneNumber"
            type="tel"
            {...register('phoneNumber', {
              required: 'Phone number is required',
              pattern: {
                value: /^\+\d{1,3}\d{9}$/,
                message: 'Phone number must start with a country code (+ followed by 1-3 digits) and have 9 additional digits (e.g., +37012345678)'
              }
            })}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-2 border"
          />
          {errors.phoneNumber && (
            <p className="mt-1 text-sm text-red-600">{errors.phoneNumber.message}</p>
          )}
        </div>

        {/* Username */}
        <div>
          <label htmlFor="username" className="block text-sm font-medium text-gray-700">
            Username
          </label>
          <input
            id="username"
            type="text"
            {...register('username', { required: 'Username is required' })}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-2 border"
          />
          {errors.username && (
            <p className="mt-1 text-sm text-red-600">{errors.username.message}</p>
          )}
        </div>

        {/* Password */}
        <div>
          <label htmlFor="password" className="block text-sm font-medium text-gray-700">
            Password
          </label>
          <input
            id="password"
            type="password"
            {...register('password', {
              required: 'Password is required',
              pattern: {
                value: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,16}$/,
                message: 'Password must contain one digit, one lowercase, one uppercase, one special character, and be 8-16 characters long'
              }
            })}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-2 border"
          />
          {errors.password && (
            <p className="mt-1 text-sm text-red-600">{errors.password.message}</p>
          )}
        </div>

        {/* Hidden roles field (since it's predefined as PROVIDER) */}
        <input type="hidden" {...register('roles')} />

        <div className="flex justify-end">
          <button
            type="button"
            onClick={() => reset()}
            className="mr-3 px-4 py-2 bg-gray-200 text-gray-800 rounded-md hover:bg-gray-300"
            disabled={isSubmitting}
          >
            Reset
          </button>
          <button
            type="submit"
            className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 disabled:bg-blue-300"
            disabled={isSubmitting}
          >
            {isSubmitting ? 'Registering...' : 'Register'}
          </button>
        </div>
      </form>
    </div>
  );
  
};

export default UserProviderRegistration;