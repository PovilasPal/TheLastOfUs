import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext.jsx";

const Navigation = () => {
  const { user, logout } = useAuth();

  return (
    <nav className="bg-gray-800 text-white shadow-md">
      <div className="container mx-auto px-6 py-4 flex justify-between items-center">
        <Link to="/" className="text-2xl font-bold text-white hover:text-gray-300">
          Home
        </Link>

        {user ? (
          <div className="flex items-center space-x-4">
            <span className="text-sm text-gray-300">
              Logged in as <span className="font-semibold text-white">{user.username}</span>
            </span>

            {user.roles?.includes("ROLE_PROVIDER") && (
              <Link to="/provider/profile">
                <button className="bg-blue-500 hover:bg-blue-600 px-4 py-2 rounded text-white text-sm transition">
                  Edit Profile
                </button>
              </Link>
            )}

            <button
              onClick={logout}
              className="bg-red-500 hover:bg-red-600 px-4 py-2 rounded text-white text-sm transition"
            >
              Log Out
            </button>
          </div>
        ) : (
          <div className="flex items-center space-x-2">
            <Link to="/login/client">
              <button className="border border-blue-300 text-blue-200 hover:text-white hover:bg-blue-500 px-3 py-1.5 rounded text-sm transition">
                Log In as Client
              </button>
            </Link>
            <Link to="/login/provider">
              <button className="border border-blue-300 text-blue-200 hover:text-white hover:bg-blue-500 px-3 py-1.5 rounded text-sm transition">
                Log In as Provider
              </button>
            </Link>
            <Link to="/users_clients">
              <button className="border border-green-300 text-green-200 hover:text-white hover:bg-green-500 px-3 py-1.5 rounded text-sm transition">
                Sign Up as Client
              </button>
            </Link>
            <Link to="/register">
              <button className="border border-green-300 text-green-200 hover:text-white hover:bg-green-500 px-3 py-1.5 rounded text-sm transition">
                Sign Up as Provider
              </button>
            </Link>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Navigation;
