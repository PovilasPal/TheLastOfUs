import React, { useEffect, useState } from "react";
import axios from "axios";
import { useForm } from "react-hook-form";
import { useAuth } from "../context/AuthContext.jsx";

const ProviderTreatments = () => {
  const { user } = useAuth();
  const licenseNumber = user?.licenseNumber;

  const [providerTreatments, setProviderTreatments] = useState([]);
  const [apiError, setApiError] = useState("");
  const [successMessage, setSuccessMessage] = useState(""); // New success message state
  const [editTreatmentId, setEditTreatmentId] = useState(null);
  const {
    register,
    handleSubmit,
    reset,
    setValue,
    formState: { errors },
  } = useForm();

  const fetchTreatments = async () => {
    try {
      const res = await axios.get(`http://localhost:8081/api/providers/${licenseNumber}`);
      setProviderTreatments(res.data.treatments || []);
    } catch (err) {
      console.error(err);
      setApiError("Failed to load treatments.");
    }
  };

  useEffect(() => {
    if (licenseNumber) {
      fetchTreatments();
    }
  }, [licenseNumber]);

  // Helper to show success message and clear it after 3 seconds
  const showSuccess = (msg) => {
    setSuccessMessage(msg);
    setTimeout(() => setSuccessMessage(""), 3000);
  };

  const onSubmit = async (data) => {
    try {
      if (editTreatmentId) {
        // UPDATE existing treatment
        await axios.put(`http://localhost:8081/api/treatments/${editTreatmentId}`, data);
        showSuccess("Treatment updated!");
      } else {
        // CREATE new treatment
        const res = await axios.post(`http://localhost:8081/api/treatments`, data);

        const newTreatmentId = res.data.id;
        const allTreatmentIds = [...providerTreatments.map(t => t.id), newTreatmentId];

        await axios.put(`http://localhost:8081/api/providers/${licenseNumber}/treatments`, allTreatmentIds);

        showSuccess("Treatment added!");
      }

      reset();
      setEditTreatmentId(null);
      fetchTreatments();
    } catch (err) {
      console.error(err);
      setApiError("Operation failed.");
    }
  };

  const handleEdit = (treatment) => {
    setEditTreatmentId(treatment.id);
    setValue("name", treatment.name);
    setValue("description", treatment.description);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this treatment?")) return;
    try {
      await axios.delete(`http://localhost:8081/api/treatments/${id}`);

      const updatedTreatments = providerTreatments.filter(t => t.id !== id).map(t => t.id);
      await axios.put(`http://localhost:8081/api/providers/${licenseNumber}/treatments`, updatedTreatments);

      showSuccess("Treatment deleted.");
      fetchTreatments();
    } catch (err) {
      console.error(err);
      setApiError("Failed to delete treatment.");
    }
  };

  return (
    <div className="max-w-2xl mx-auto mt-10 p-6 bg-white rounded shadow">
      <h2 className="text-2xl font-bold mb-2">
        {editTreatmentId ? "Edit Treatment" : "Add New Treatment"}
      </h2>

      {/* Success message */}
      {successMessage && (
        <p className="text-green-600 mb-4 font-medium">{successMessage}</p>
      )}

      {apiError && <p className="text-red-600 mb-4">{apiError}</p>}

      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">Name</label>
          <input
            type="text"
            {...register("name", { required: "Name is required" })}
            className="w-full border p-2 rounded"
          />
          {errors.name && <p className="text-red-600">{errors.name.message}</p>}
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">Description</label>
          <textarea
            {...register("description", { required: "Description is required" })}
            className="w-full border p-2 rounded"
          />
          {errors.description && <p className="text-red-600">{errors.description.message}</p>}
        </div>

        <div className="flex gap-2">
          <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
            {editTreatmentId ? "Update" : "Add"} Treatment
          </button>
          {editTreatmentId && (
            <button
              type="button"
              onClick={() => {
                reset();
                setEditTreatmentId(null);
              }}
              className="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
            >
              Cancel
            </button>
          )}
        </div>
      </form>

      <h3 className="text-xl font-bold mt-8 mb-4">My Treatments</h3>
      <ul className="space-y-3">
        {providerTreatments.map((treatment) => (
          <li key={treatment.id} className="border p-4 rounded shadow-sm flex justify-between items-center">
            <div>
              <p className="font-semibold">{treatment.name}</p>
              <p className="text-sm text-gray-700">{treatment.description}</p>
            </div>
            <div className="flex gap-2">
              <button
                onClick={() => handleEdit(treatment)}
                className="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600"
              >
                Edit
              </button>
              <button
                onClick={() => handleDelete(treatment.id)}
                className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
              >
                Delete
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ProviderTreatments;
