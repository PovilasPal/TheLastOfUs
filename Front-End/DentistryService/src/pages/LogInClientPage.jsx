import { useForm } from "react-hook-form";
import { useNavigate } from "react-router";
import { useState } from "react";
import { useAuth } from "../context/AuthContext";
import { Link } from "react-router";

const LoginClientPage = () => {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();

  const [error, setError] = useState("");
  const [, setLogIn] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    setLogIn(true);
    setError("");
    try {
      await login(data.username, data.password);
      reset();
      // navigate("/");
    } catch (err) {
      setError("Incorrect username or password");
    } finally {
      setLogIn(false);
    }
  };

  return (
    <>
      <div className="flex min-h-full flex-col justify-center  h-screen">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <h2 className="mt-10 text-center text-2xl/9 font-bold tracking-tight text-gray-900">
            Login
          </h2>
        </div>

        <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
          <form
            className="space-y-6"
            onSubmit={handleSubmit(onSubmit)}
          >
            <div>
              <label
                htmlFor="username"
                className="block text-sm/6 font-medium text-gray-900"
              >
                Username
              </label>
              <div className="mt-2">
                <input
                  type="text"
                  id="username"
                  {...register("username", {
                    required: "Can't be empty",
                  })}
                  className={`block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6 ${
                    errors.username ? "border-red" : "greyish-blue"
                  }`}
                  onInput={() => setError("")}
                />
                {errors.username?.type === "required" && (
                  <p className=" text-red-500">Can`t be empty</p>
                )}
              </div>
            </div>
            <div>
              <div className="flex items-center justify-between">
                <label
                  htmlFor="password"
                  className="block text-sm/6 font-medium text-gray-900"
                >
                  Password
                </label>
                <div className="text-sm"></div>
              </div>
              <div className="mt-2">
                <input
                  type="password"
                  id="password"
                  {...register("password", { required: "Can't be empty" })}
                  className={`block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6 `}
                  onInput={() => setError("")}
                />
                  <p className="text-red-500">{errors.password?.message}</p>
                <div>
                  {error && <p className="text-red-500">{error}</p>}
                </div>
              </div>
            </div>

            <div>
              <button
                type="submit"
                className="flex w-full justify-center rounded-md bg-slate-600 px-3 py-1.5 text-sm/6 font-semibold text-white shadow-xs hover:bg-slate-900 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
              >
                Sign in
              </button>
            </div>
          </form>
          {/* <p className="mt-10 text-center text-sm/6 text-gray-500">
            Don`t have an account?{" "}
            <Link
              to={`/signup`}
              className="font-semibold text-indigo-600 hover:text-indigo-500"
            >
              Sign Up
            </Link>
          </p> */}
        </div>
      </div>
    </>
  );
};

export default LoginClientPage;
