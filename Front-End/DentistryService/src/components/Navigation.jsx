import { Link } from "react-router";
import { useAuth } from "../context/AuthContext.jsx";

const Navigation = () => {
  const { user, logout } = useAuth();

  return (
    <>
      <nav className="bg-gray-800 p-4 text-white">
        <div className="container mx-auto flex justify-between items-center">
          <Link className="text-xl font-bold hover:text-gray-300" to="/">
            <button>Home</button>
          </Link>
        </div>
        <div>
          {user ? (
            <>
              <div className="container mx-auto flex justify-between items-center">
                <p>You are logged in as {user.username}</p>
                <Link className="text-xl font-bold hover:text-gray-300" to="/">
                  <button onClick={logout}>
                    Log out
                  </button>
                </Link>
              </div>
            </>
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
              {user && user.roles.includes('ROLE_PROVIDER') && (
  <Link to="/employees" className="nav-link">Employees</Link>
)}

            </div>
          )}
        </div>
      </nav>
    </>
  );
};

export default Navigation;
