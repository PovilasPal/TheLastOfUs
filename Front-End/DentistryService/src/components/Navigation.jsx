import React from 'react';
import { Link } from 'react-router-dom';
const Navigation = () => {
    return (
        <nav className="bg-gray-800 p-4 text-white">
            <div className="container mx-auto flex justify-between items-center">
                <Link className="text-xl font-bold hover:text-gray-300" to="/">Home</Link>
                <div className="flex space-x-4">
                    <Link className="px-3 py-2 rounded-md hover:bg-gray-700 transition-colors" to="/register">Register Provider</Link>
                </div>
            </div>
        </nav>
    );
}

export default Navigation;