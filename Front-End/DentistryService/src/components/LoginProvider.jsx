import { useForm } from "react-hook-form";
import { useNavigate } from "react-router";
import { useState } from "react";
import { useAuth } from "../context/AuthContext";
import { Link } from "react-router";

const LoginProviderPage = () => {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();

  const [error, setError] = useState("");
  const { login } = useAuth();

  const onSubmit = async (data) => {
    try {
      setError("");
      await login(data.username, data.password, "api/login/provider");
    } catch (err) {
      setError("Incorrect username or password");
    }
  };

  return (
    <>
      <div className="max-w-lg mx-auto p-6 bg-white rounded-xl shadow-lg">
        <div>
          <h2 className="text-2xl font-bold mb-6 text-center">
            Login as provider
          </h2>
        </div>

        <div className="mb-4">
          <form className="space-y-6" onSubmit={handleSubmit(onSubmit)}>
            <div>
              <label
                htmlFor="username"
                className="block text-sm/6 font-medium text-gray-900"
              >
                Username
              </label>
              <div className="mb-4">
                <input
                  type="text"
                  id="username"
                  {...register("username", {
                    required: "Can't be empty",
                  })}
                  className={`w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 ${
                    errors.username ? "border-red" : "greyish-blue"
                  }`}
                  onInput={() => setError("")}
                />
                {errors.username?.type === "required" && (
                  <p className=" text-red-600 mb-4 text-center">Can`t be empty</p>
                )}
              </div>
            </div>
            <div>
              <div className="mb-4">
                <label
                  htmlFor="password"
                  className="block text-sm/6 font-medium text-gray-900"
                >
                  Password
                </label>
                <div className="text-sm"></div>
              </div>
              <div className="mb-4">
                <input
                  type="password"
                  id="password"
                  {...register("password", { required: "Can't be empty" })}
                  className={`w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 `}
                  onInput={() => setError("")}
                />
                <p className="text-red-600 mb-4 text-center">{errors.password?.message}</p>
                <div>{error && <p className="text-red-600 mb-4 text-center">{error}</p>}</div>
              </div>
            </div>

            <div>
              <button
                type="submit"
                className="w-full p-3 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                Sign in
              </button>
            </div>
          </form>
          <p className="mt-10 text-center text-sm/6 text-gray-500">
            Don`t have an account?{" "}
            <Link
              to={`/register`}
              className="font-semibold text-indigo-600 hover:text-indigo-500"
            >
              Sign Up
            </Link>
          </p>
        </div>
      </div>
    </>
  );
};

export default LoginProviderPage;
