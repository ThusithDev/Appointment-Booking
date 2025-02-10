import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Doctors = () => {
    const [doctors, setDoctors] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get('http://localhost:5001/api/doctors')
            .then(response => setDoctors(response.data))
            .catch(error => console.error('Error fetching doctors:', error));
    }, []);

    return (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 p-4">
            {doctors.map(doctor => (
                <div key={doctor.id} className="border p-4 rounded-lg shadow-md hover:shadow-lg cursor-pointer"
                     onClick={() => navigate(`/appointment/${doctor.id}`)}>
                    <h2 className="text-xl font-semibold">{doctor.name}</h2>
                    <p className="text-gray-600">{doctor.specialty}</p>
                </div>
            ))}
        </div>
    );
}

export default Doctors;