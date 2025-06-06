import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../context/AuthContext.jsx";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";

const EditClientProfile = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const [apiError, setApiError] = useState("");

  const {
    register,
    handleSubmit,
    setValue,
    watch,
    formState: { errors },
  } = useForm();

  const password = watch("password", "");
  const confirmPassword = watch("confirmPassword", "");

  useEffect(() => {
    const fetchClientData = async () => {
      try {
        if (!user?.id) {
          setApiError("Missing id. Cannot load profile.");
          return;
        }

        const response = await axios.get(
          `http://localhost:8081/users_clients/${user.id}`
        );

        const fields = [
          "id",
          "name",
          "surname",
          "email",
          "phoneNumber",
          "username",
        ];
        fields.forEach((field) => setValue(field, response.data[field]));
      } catch (error) {
        console.error("Error fetching client data:", error);
        setApiError("Failed to fetch client profile.");
      }
    };

    fetchClientData();
  }, [user, setValue]);

  const onSubmit = async (formData) => {
    const { confirmPassword, ...sendData } = formData;
    if (!sendData.password) {
      delete sendData.password;
    }

    try {
      await axios.put(`http://localhost:8081/users_clients/${user.id}`, {
        ...sendData,
        roles: user.roles,
      });
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
        await axios.delete(`http://localhost:8081/users_clients/${user.id}`);
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
      <h2 className="text-2xl font-bold mb-4">Edit Client Profile</h2>

      {apiError && <p className="text-red-600 mb-4">{apiError}</p>}

      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Name
          </label>
          <input
            type="text"
            {...register("name", {
              required: "Name is required",
            })}
            className="w-full border p-2 rounded"
          />
          {errors.name && (
            <p className="text-red-600 text-sm">{errors.name.message}</p>
          )}
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">
            Surname
          </label>
          <input
            type="text"
            {...register("surname", {
              required: "Surname is required",
            })}
            className="w-full border p-2 rounded"
          />
          {errors.surname && (
            <p className="text-red-600 text-sm">{errors.surname.message}</p>
          )}
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">
            Email
          </label>
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
          {errors.email && (
            <p className="text-red-600 text-sm">{errors.email.message}</p>
          )}
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">
            Phone Number
          </label>
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

        <div>
          <label className="block text-sm font-medium text-gray-700">
           Current or New Password
          </label>
          <input
            type="password"
            {...register("password", {
              validate: (value) =>
                value === "" ||
                value.length >= 6 ||
                "Password must be at least 6 characters",
            })}
            className="w-full border p-2 rounded"
          />
          {errors.password && (
            <p className="text-red-600 text-sm">{errors.password.message}</p>
          )}
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">
            Confirm Password
          </label>
          <input
            type="password"
            {...register("confirmPassword", {
              validate: (value) =>
                value === password || "Passwords do not match",
            })}
            className="w-full border p-2 rounded"
          />
          {errors.confirmPassword && (
            <p className="text-red-600 text-sm">
              {errors.confirmPassword.message}
            </p>
          )}
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">
            Username
          </label>
          <input
            type="text"
            {...register("username", {
              required: "Username is required",
            })}
            readOnly
            className="w-full border p-2 rounded bg-gray-100 text-gray-500"
          />
          {errors.username && (
            <p className="text-red-600 text-sm">{errors.username.message}</p>
          )}
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

export default EditClientProfile;
