const Navigation = () => {
    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <div className="container">
                <Link className="navbar-brand" to="/">Home</Link>
                <div className="navbar-nav">
                    <Link className="nav-link" to="/register">Register Provider</Link>
                </div>
            </div>
        </nav>
    );
}