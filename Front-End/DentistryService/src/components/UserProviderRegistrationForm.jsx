const UserProviderRegistration = () => {
  const [formStructure, setFormStructure] = useState(null);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(userProviderSchema),
  });

  useEffect(() => {
    const fetchFormStructure = async () => {
      try {
        const response = await getRegistrationFormStructure();
        setFormStructure(response.data);
        reset(response.data); // Pre-fill form with default values
      } catch (err) {
        console.error('Error fetching form structure:', err);
      }
    };

    fetchFormStructure();
  }, [reset]);

  const onSubmit = async (data) => {
    try {
      const response = await registerUserProvider(data);
      setSuccess('Provider registered successfully!');
      setError(null);
      setTimeout(() => {
        navigate('/providers');
      }, 1500);
    } catch (err) {
      setError(err.response?.data?.message || 'Registration failed. Please try again.');
      setSuccess(null);
    }
  };

  if (!formStructure) {
    return <div>Loading form...</div>;
  }

  return (
    <div className="card">
      <div className="card-header bg-primary text-white">
        <h2>User Provider Registration</h2>
      </div>
      <div className="card-body">
        {error && <div className="alert alert-danger">{error}</div>}
        {success && <div className="alert alert-success">{success}</div>}

        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="mb-3">
            <label htmlFor="licenceNumber" className="form-label">
              Licence Number
            </label>
            <input
              type="text"
              className={`form-control ${errors.licenceNumber ? 'is-invalid' : ''}`}
              id="licenceNumber"
              {...register('licenceNumber')}
            />
            {errors.licenceNumber && (
              <div className="invalid-feedback">{errors.licenceNumber.message}</div>
            )}
          </div>

          <div className="mb-3">
            <label htmlFor="name" className="form-label">
              Name
            </label>
            <input
              type="text"
              className={`form-control ${errors.name ? 'is-invalid' : ''}`}
              id="name"
              {...register('name')}
            />
            {errors.name && <div className="invalid-feedback">{errors.name.message}</div>}
          </div>

          <div className="mb-3">
            <label htmlFor="email" className="form-label">
              Email
            </label>
            <input
              type="email"
              className={`form-control ${errors.email ? 'is-invalid' : ''}`}
              id="email"
              {...register('email')}
            />
            {errors.email && <div className="invalid-feedback">{errors.email.message}</div>}
          </div>

          <div className="mb-3">
            <label htmlFor="phoneNumber" className="form-label">
              Phone Number
            </label>
            <input
              type="text"
              className={`form-control ${errors.phoneNumber ? 'is-invalid' : ''}`}
              id="phoneNumber"
              {...register('phoneNumber')}
            />
            {errors.phoneNumber && (
              <div className="invalid-feedback">{errors.phoneNumber.message}</div>
            )}
          </div>

          <div className="mb-3">
            <label htmlFor="username" className="form-label">
              Username
            </label>
            <input
              type="text"
              className={`form-control ${errors.username ? 'is-invalid' : ''}`}
              id="username"
              {...register('username')}
            />
            {errors.username && (
              <div className="invalid-feedback">{errors.username.message}</div>
            )}
          </div>

          <div className="mb-3">
            <label htmlFor="password" className="form-label">
              Password
            </label>
            <input
              type="password"
              className={`form-control ${errors.password ? 'is-invalid' : ''}`}
              id="password"
              {...register('password')}
            />
            {errors.password && (
              <div className="invalid-feedback">{errors.password.message}</div>
            )}
          </div>

          <input type="hidden" {...register('roles')} />

          <button type="submit" className="btn btn-primary">
            Register
          </button>
        </form>
      </div>
    </div>
  );
};

export default UserProviderRegistration;