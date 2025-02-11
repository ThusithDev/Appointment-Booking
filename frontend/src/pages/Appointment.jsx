import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const Appointment = () => {
    const { docId } = useParams();
    const navigate = useNavigate();
    const [doctor, setDoctor] = useState(null);
    const [selectedTime, setSelectedTime] = useState(null);
    const [message, setMessage] = useState('');

    useEffect(() => {
        axios.get(`http://localhost:5001/api/doctors/${docId}`)
            .then(response => setDoctor(response.data))
            .catch(error => console.error('Error fetching doctor details:', error));
    }, [docId]);

    const base64Decode = (base64) => {
        const decoded = atob(base64.replace(/-/g, '+').replace(/_/g, '/'));
        return decodeURIComponent(escape(decoded)); // Properly decode UTF-8 characters
    };
    

    const parseJwt = (token) => {
        try {
            const base64Url = token.split('.')[1];
            if (!base64Url) return null;

            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const jsonPayload = base64Decode(base64);
        
            return JSON.parse(jsonPayload);
        } catch (error) {
          console.error('Error decoding token:', error);
          return null;
        }
    };


    const handleTimeSlotClick = (time) => {
        const token = localStorage.getItem('token');
        if (!token) {
            alert('Sign in to book an appointment');
            navigate('/login');
            return;
        }
        setSelectedTime(time);
    };

    const handleBooking = async (event) => {
        event.preventDefault();
        const token = localStorage.getItem('token');

        if (!token) {
            alert('Sign in to book an appointment');
            navigate('/login');
            return;
        }

        const decoded = parseJwt(token);
        console.log("Decoded Token:", decoded);

        if (!decoded || !decoded.userId) {
            alert('Invalid session. Please log in again.');
            localStorage.removeItem('token');
            navigate('/login');
            return;
        }

        if (!selectedTime) {
            alert('Please select a time slot before booking.');
            return;
        }

        try {
            const response = await axios.post('http://localhost:5001/api/appointments/book', {
                doctorId: docId,
                userId: decoded.userId, 
                timeSlot: selectedTime,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            setMessage(response.data.message);
            setSelectedTime(null);
        } catch (error) {
            setMessage(error.response?.data.error || 'Error booking appointment');
        }
    };

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
                        onClick={() => handleTimeSlotClick(time)}
                    >
                        {time}
                    </button>
                ))}
            </div>

            {selectedTime && (
                <form onSubmit={handleBooking} className="mt-4 p-4 border rounded-lg">
                    <h3 className="text-lg font-semibold">Confirm Booking</h3>
                    <p className="text-gray-600">Time Slot: {selectedTime}</p>
                    <button type="submit" className="w-full mt-3 bg-blue-500 text-white p-2 rounded">Confirm</button>
                </form>
            )}

            {message && <p className="mt-4 text-green-600">{message}</p>}
        </div>
    );
}

export default Appointment;
