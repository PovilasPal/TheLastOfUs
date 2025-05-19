import React from 'react';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

const UserClientRegistrationForm = () => {
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();

  const [apiError, setApiError] = React.useState('');
  const [isSubmitting, setIsSubmitting] = React.useState(false);

  const onSubmit = async (data) => {
    setIsSubmitting(true);
    setApiError('');

    const submissionData = {
      ...data,
      roles: [{ id: 1, name: 'USER', authority: 'USER' }],
    };

    try {
      const response = await axios.post(
        `${import.meta.env.VITE_API_URL}users_clients`,
        submissionData,
        {
          headers: { 'Content-Type': 'application/json' },
        }
      );

      console.log('Client registration successful:', response.data);
      navigate('/registration-success');
    } catch (error) {
      if (error.response?.data?.message) {
        setApiError(error.response.data.message);
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
      <h2 className="text-2xl font-bold mb-6 text-center">Client Registration</h2>

      {apiError && (
        <div className="mb-4 p-3 bg-red-100 text-red-700 rounded">{apiError}</div>
      )}

      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        {/* First Name */}
        <div>
          <label htmlFor="name" className="block text-sm font-medium text-gray-700">
            First Name
          </label>
          <input
            id="name"
            type="text"
            {...register('name', { required: 'First name is required' })}
            className="mt-1 block w-full border p-2 rounded-md shadow-sm border-gray-300 focus:border-blue-500 focus:ring-blue-500"
          />
          {errors.name && (
            <p className="mt-1 text-sm text-red-600">{errors.name.message}</p>
          )}
        </div>

        {/* Last Name */}
        <div>
          <label htmlFor="surname" className="block text-sm font-medium text-gray-700">
            Last Name
          </label>
          <input
            id="surname"
            type="text"
            {...register('surname', { required: 'Last name is required' })}
            className="mt-1 block w-full border p-2 rounded-md shadow-sm border-gray-300 focus:border-blue-500 focus:ring-blue-500"
          />
          {errors.surname && (
            <p className="mt-1 text-sm text-red-600">{errors.surname.message}</p>
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
                value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                message: "Email must include a valid username, '@' symbol, and a domain name (e.g., user@example.com).",
              },
            })}
            className="mt-1 block w-full border p-2 rounded-md shadow-sm border-gray-300 focus:border-blue-500 focus:ring-blue-500"
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
                value: /^\+370\d{8}$/,
                message:
                  'Phone number must start with +370 followed by 8 digits (e.g., +37062345678)',
              },
            })}
            className="mt-1 block w-full border p-2 rounded-md shadow-sm border-gray-300 focus:border-blue-500 focus:ring-blue-500"
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
            className="mt-1 block w-full border p-2 rounded-md shadow-sm border-gray-300 focus:border-blue-500 focus:ring-blue-500"
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
                value:
                  /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,16}$/,
                message:
                  'Password must contain one digit, one lowercase, one uppercase, one special character, and be 8–16 characters long',
              },
            })}
            className="mt-1 block w-full border p-2 rounded-md shadow-sm border-gray-300 focus:border-blue-500 focus:ring-blue-500"
          />
          {errors.password && (
            <p className="mt-1 text-sm text-red-600">{errors.password.message}</p>
          )}
        </div>

        {/* Hidden roles field */}
        <input type="hidden" value="USER" {...register('roles')} />

        <div className="flex justify-end">
          <button
            type="button"
            onClick={() => {
              reset();
              setApiError('');
            }}
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

      <p className="mt-6 text-center text-sm text-gray-500">
        Already have an account?{' '}
        <Link to="/login" className="font-semibold text-indigo-600 hover:text-indigo-500">
          Sign In
        </Link>
      </p>
    </div>
  );
};

export default UserClientRegistrationForm;
