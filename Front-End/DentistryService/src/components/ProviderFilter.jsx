import { useEffect, useState } from "react";
import axios from "axios";
import defaultLogo from "../assets/tooth.png";

const ProviderFilter = () => {
  const [providers, setProviders] = useState([]);
  const [address, setAddress] = useState("");

  useEffect(() => {
    const fetchProviders = async () => {
      try {
        const params = {};
        if (address) params.address = address;

        const response = await axios.get("http://localhost:8081/api/provider/search", { params });
        setProviders(response.data);
      } catch (error) {
        console.error("Error fetching filtered providers", error);
      }
    };

    fetchProviders();
  }, [address]);

  return (
    <div className="space-y-6 p-6">
      <div className="max-w-md mx-auto">
        <input
          type="text"
          placeholder="Search by address"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
          className="border px-4 py-2 rounded w-full shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {providers.length > 0 ? (
          providers.map((provider) => (
            <div
              key={provider.id}
              className="border rounded-xl p-6 shadow-lg bg-white hover:shadow-xl transition-shadow duration-200"
            >
              <h2 className="text-xl font-semibold text-center mb-4 text-gray-800">
                {provider.name}
              </h2>
              <div className="flex justify-center mb-4">
                <img
                  src={provider.logoUrl || defaultLogo}
                  alt="Provider logo"
                  className="w-20 h-20 object-cover rounded-full border"
                />
              </div>
              <div className="text-sm text-gray-700 space-y-1">
                <p>
                  <span className="font-medium">Address:</span> {provider.address}
                </p>
                <p>
                  <span className="font-medium">Phone:</span> {provider.phoneNumber}
                </p>
              </div>
            </div>
          ))
        ) : address ? (
          <div className="text-center col-span-full text-gray-500">
            No providers found for „{address}“.
          </div>
        ) : (
          <div className="text-center col-span-full text-gray-500">
            Please enter an address to search or leave empty to see all providers.
          </div>
        )}
      </div>
    </div>
  );
};

export default ProviderFilter;
