import React, { useState } from 'react';
import axios from 'axios';


function UserClientRegistrationForm() {
  const [formData, setFormData] = useState({
    name: '',
    surname: '',
    email: '',
    phoneNumber: '',
    username: '',
    password: ''
  });

  const [validationErrors, setValidationErrors] = useState({});

  // Validate the form fields
  const validateForm = () => {
    const errors = {};
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const phoneRegex = /^\+?[1-9]\d{7,20}$/;
    const passwordRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$/;

    if (!formData.name.trim()) errors.name = 'First name is required';
    if (!formData.surname.trim()) errors.surname = 'Last name is required';
    if (!emailRegex.test(formData.email)) errors.email = 'Invalid email format';
    if (!phoneRegex.test(formData.phoneNumber)) errors.phoneNumber = 'Invalid phone number';
    if (!formData.username.trim()) errors.username = 'Username is required';
    if (!passwordRegex.test(formData.password))
      errors.password = 'Password must be 8–20 characters, include uppercase, lowercase, number, and no spaces';

    return errors;
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    const errors = validateForm();
    if (Object.keys(errors).length > 0) {
      setValidationErrors(errors); // Set errors if validation fails
      return;
    }

    const dataToSend = {
      ...formData,
      roles: [{ id: 1, name: 'User Role', authority: 'USER' }]
    };

    try {
      await axios.post(`${import.meta.env.VITE_API_URL}users_clients`, dataToSend);
      alert('User registered successfully');
      setFormData({
        name: '',
        surname: '',
        email: '',
        phoneNumber: '',
        username: '',
        password: ''
      });
      setValidationErrors({});
    } catch (error) {
      const errorData = error.response?.data;
      if (errorData && typeof errorData === 'object') {
        setValidationErrors(errorData); // Set backend validation errors
      } else {
        setValidationErrors({ general: 'Registration failed. Please try again.' });
      }
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setValidationErrors({}); // Clear errors when user edits the form
  };

  return (
    <div className="max-w-lg mx-auto p-6 bg-white rounded-xl shadow-lg">
      <h2 className="text-2xl font-bold mb-6 text-center">User Registration</h2>

      {/* Display general error message */}
      {validationErrors.general && (
        <div className="text-red-600 mb-4 text-center">
          {validationErrors.general}
        </div>
      )}

      <form onSubmit={handleSubmit}>
        {/* Name Input */}
        <div className="mb-4">
          <input
            name="name"
            placeholder="First Name"
            onChange={handleChange}
            value={formData.name}
            required
            className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          {validationErrors.name && <div className="text-red-600 text-sm">{validationErrors.name}</div>}
        </div>

        {/* Surname Input */}
        <div className="mb-4">
          <input
            name="surname"
            placeholder="Last Name"
            onChange={handleChange}
            value={formData.surname}
            required
            className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          {validationErrors.surname && <div className="text-red-600 text-sm">{validationErrors.surname}</div>}
        </div>

        {/* Email Input */}
        <div className="mb-4">
          <input
            name="email"
            type="email"
            placeholder="Email"
            onChange={handleChange}
            value={formData.email}
            required
            className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          {validationErrors.email && <div className="text-red-600 text-sm">{validationErrors.email}</div>}
        </div>

        {/* Phone Number Input */}
        <div className="mb-4">
          <input
            name="phoneNumber"
            placeholder="Phone Number"
            onChange={handleChange}
            value={formData.phoneNumber}
            required
            className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          {validationErrors.phoneNumber && <div className="text-red-600 text-sm">{validationErrors.phoneNumber}</div>}
        </div>

        {/* Username Input */}
        <div className="mb-4">
          <input
            name="username"
            placeholder="Username"
            onChange={handleChange}
            value={formData.username}
            required
            className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          {validationErrors.username && <div className="text-red-600 text-sm">{validationErrors.username}</div>}
        </div>

        {/* Password Input */}
        <div className="mb-6">
          <input
            name="password"
            type="password"
            placeholder="Password"
            onChange={handleChange}
            value={formData.password}
            required
            className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          {validationErrors.password && <div className="text-red-600 text-sm">{validationErrors.password}</div>}
        </div>

        {/* Submit Button */}
        <button
          type="submit"
          className="w-full p-3 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
        >
          Register
        </button>
      </form>
    </div>
  );
}

export default UserClientRegistrationForm;
