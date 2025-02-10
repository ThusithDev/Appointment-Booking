import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const Appointment = () => {
    const { docId } = useParams();
    const [doctor, setDoctor] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:5001/api/doctors/${docId}`)
            .then(response => setDoctor(response.data))
            .catch(error => console.error('Error fetching doctor details:', error));
    }, [docId]);

    if (!doctor) {
        return <div className="text-center mt-10 text-lg">Loading...</div>;
    }

    return (
        <div className="max-w-xl mx-auto p-6 bg-white shadow-md rounded-lg">
            <h2 className="text-2xl font-semibold mb-2">{doctor.name}</h2>
            <p className="text-gray-600 mb-4">{doctor.specialty}</p>
            <h3 className="text-xl font-medium mb-2">Available Time Slots</h3>
            <div className="grid grid-cols-2 gap-4">
                {Object.entries(doctor.availability).map(([time, isAvailable]) => (
                    <button 
                        key={time} 
                        className={`px-4 py-2 rounded-lg border ${isAvailable ? 'bg-green-500 text-white' : 'bg-gray-300 cursor-not-allowed'}`} 
                        disabled={!isAvailable}
                    >
                        {time}
                    </button>
                ))}
            </div>
        </div>
    );
}

export default Appointment;