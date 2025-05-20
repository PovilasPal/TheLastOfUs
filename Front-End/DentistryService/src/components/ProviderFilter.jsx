import { useEffect, useState } from "react";
import axios from "axios";
import defaultLogo from "../assets/tooth.png";

const ProviderFilter = () => {
  const [providers, setProviders] = useState([]);
  const [address, setAddress] = useState("");
  const [flippedCardId, setFlippedCardId] = useState(null);

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

  const toggleFlip = (id) => {
    setFlippedCardId((prev) => (prev === id ? null : id));
  };

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
          providers.map((provider) => {
            const id = provider.licenseNumber;
            const isFlipped = flippedCardId === id;

            return (
              <div
                key={id}
                className="w-full h-80 [perspective:1000px] cursor-pointer"
                onClick={() => toggleFlip(id)}
              >
                <div
                  className="relative w-full h-full transition-transform duration-500"
                  style={{
                    transformStyle: "preserve-3d",
                    transform: isFlipped ? "rotateY(180deg)" : "rotateY(0deg)",
                  }}
                >
                  {/* Front side */}
                  <div
                    className="absolute w-full h-full backface-hidden bg-white border rounded-xl p-6 shadow-lg flex flex-col items-center justify-center"
                    style={{
                      backfaceVisibility: "hidden",
                    }}
                  >
                    <h2 className="text-xl font-semibold text-center mb-4 text-gray-800">
                      {provider.name}
                    </h2>
                    <img
                      src={provider.logoUrl || defaultLogo}
                      alt="Provider logo"
                      className="w-20 h-20 object-cover rounded-full border mb-4"
                    />
                    <div className="text-sm text-gray-700 space-y-1 text-center">
                      <p>
                        <span className="font-medium">Address:</span> {provider.address}
                      </p>
                      <p>
                        <span className="font-medium">Phone:</span> {provider.phoneNumber}
                      </p>
                    </div>
                  </div>

                  {/* Back side */}
                  <div
                    className="absolute w-full h-full bg-blue-50 border rounded-xl p-6 shadow-lg flex items-center justify-center text-center"
                    style={{
                      transform: "rotateY(180deg)",
                      backfaceVisibility: "hidden",
                      overflow: "auto",
                      wordBreak: "break-word",
                      whiteSpace: "normal"
                    }}
                  > <div>
                    <h1 className="text-2xl font-bold mb-4 text-gray-800">About us</h1>
                    <p className="text-gray-700 text-sm">
                      {provider.description || "No description available."}
                    </p>
                    </div>
                  </div>
                </div>
              </div>
            );
          })
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
