import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../context/AuthContext.jsx";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";

const EditProviderProfile = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const [apiError, setApiError] = useState("");

  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm();

  useEffect(() => {
    const fetchProviderData = async () => {
      try {
        if (!user?.licenseNumber) {
          setApiError("Missing license number. Cannot load profile.");
          return;
        }

        const response = await axios.get(
          `http://localhost:8081/api/providers/${user.licenseNumber}`
        );

        // Populate form with fetched data
        const fields = [
          "licenseNumber",
          "name",
          "email",
          "phoneNumber",
          "username",
          "description",
          "address",
          "contacts",
        ];
        fields.forEach((field) => setValue(field, response.data[field]));
      } catch (error) {
        console.error("Error fetching provider data:", error);
        setApiError("Failed to fetch provider profile.");
      }
    };

    fetchProviderData();
  }, [user, setValue]);

  const onSubmit = async (formData) => {
    try {
      await axios.put(
        `http://localhost:8081/api/providers/${user.licenseNumber}`,
        { ...formData, roles: user.roles } // preserve roles
      );
      alert("Profile updated successfully!");
      navigate("/");
    } catch (err) {
      console.error("Update failed:", err);
      setApiError("Failed to update profile.");
    }
  };

  const handleDelete = async () => {
    if (window.confirm("Are you sure you want to delete your account?")) {
      try {
        await axios.delete(
          `http://localhost:8081/api/providers/${user.licenseNumber}`
        );
        logout();
        navigate("/");
      } catch (err) {
        console.error("Delete failed:", err);
        setApiError("Failed to delete account.");
      }
    }
  };

  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white rounded shadow">
      <h2 className="text-2xl font-bold mb-4">Edit Provider Profile</h2>

      {apiError && <p className="text-red-600 mb-4">{apiError}</p>}

      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        {/* Name */}
        <div>
          <label className="block text-sm font-medium text-gray-700">Full Name</label>
          <input
            type="text"
            {...register("name", { required: "Name is required" })}
            className="w-full border p-2 rounded"
          />
          {errors.name && <p className="text-red-600 text-sm">{errors.name.message}</p>}
        </div>

        {/* Email */}
        <div>
          <label className="block text-sm font-medium text-gray-700">Email</label>
          <input
            type="email"
            {...register("email", {
              required: "Email is required",
              pattern: {
                value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                message: "Invalid email address",
              },
            })}
            className="w-full border p-2 rounded"
          />
          {errors.email && <p className="text-red-600 text-sm">{errors.email.message}</p>}
        </div>

        {/* Phone Number */}
        <div>
          <label className="block text-sm font-medium text-gray-700">Phone Number</label>
          <input
            type="tel"
            {...register("phoneNumber", {
              required: "Phone number is required",
              pattern: {
                value: /^\+370\d{8}$/,
                message:
                  "Phone number must start with +370 followed by 8 digits (e.g., +37062345678)",
              },
            })}
            className="w-full border p-2 rounded"
          />
          {errors.phoneNumber && (
            <p className="text-red-600 text-sm">{errors.phoneNumber.message}</p>
          )}
        </div>

        {/* Description */}
        <div>
          <label className="block text-sm font-medium text-gray-700">Description</label>
          <input
            type="text"
            {...register("description")}
            className="w-full border p-2 rounded"
          />
        </div>

        {/* Address */}
        <div>
          <label className="block text-sm font-medium text-gray-700">Address</label>
          <input
            type="text"
            {...register("address")}
            className="w-full border p-2 rounded"
          />
        </div>

        {/* Contacts */}
        <div>
          <label className="block text-sm font-medium text-gray-700">Contacts</label>
          <input
            type="text"
            {...register("contacts")}
            className="w-full border p-2 rounded"
          />
        </div>

        {/* Username - read-only */}
        <div>
          <label className="block text-sm font-medium text-gray-700">Username</label>
          <input
            type="text"
            {...register("username")}
            readOnly
            className="w-full border p-2 rounded bg-gray-100 text-gray-500"
          />
        </div>

        {/* License Number - read-only */}
        <div>
          <label className="block text-sm font-medium text-gray-700">License Number</label>
          <input
            type="text"
            {...register("licenseNumber")}
            readOnly
            className="w-full border p-2 rounded bg-gray-100 text-gray-500"
          />
        </div>

        <div className="flex justify-between mt-4">
          <button
            type="submit"
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            Update Profile
          </button>
          <button
            type="button"
            className="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700"
            onClick={handleDelete}
          >
            Delete Account
          </button>
        </div>
      </form>
    </div>
  );
};

export default EditProviderProfile;
