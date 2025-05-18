import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext.jsx";

const Navigation = () => {
  const { user, logout } = useAuth();
const handleLogout = async () => {
    try {
      await logout(); 
    } catch (error) {
      console.error("Logout failed", error);
    }
  };
  return (
    
      <nav className="bg-gray-800 p-4 text-white">
        <div className="container mx-auto flex justify-between items-center">
          <Link className="text-xl font-bold hover:text-gray-300" to="/">
            <button>Home</button>
          </Link>
        </div>
        <div>
          {user ? (
            <div className="flex items-center space-x-6">
            <p>You are logged in as <span className="font-semibold">{user.username}</span></p>

            {user.roles && user.roles.includes("ROLE_PROVIDER") && (
              <Link
                to="/employees"
                className="text-xl font-bold hover:text-gray-300"
              >
                Employees
              </Link>
            )}

            <button
              onClick={handleLogout}
              className="text-xl font-bold hover:text-gray-300 bg-transparent border-none cursor-pointer"
              type="button"
            >
              Log out
            </button>
          </div>
              
            
          ) : (
            <div className="container mx-auto flex justify-between items-center">
              <Link className="text-xl font-bold hover:text-gray-300" to="/login/client">
                <button>Log In As Client</button>
              </Link>
              <Link className="text-xl font-bold hover:text-gray-300" to="/login/provider">
                <button>Log In As Provider</button>
              </Link>
              <Link className="text-xl font-bold hover:text-gray-300" to="/users_clients">
                <button>Sign Up As Client</button>
              </Link>
              <Link className="text-xl font-bold hover:text-gray-300" to="/register">
                <button>Sign Up As Provider</button>
              </Link>

            </div>
          )}
        </div>
      </nav>
    
  );
};

export default Navigation;
