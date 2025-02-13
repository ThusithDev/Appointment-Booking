import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const MyAppointments = () => {
    const [appointments, setAppointments] = useState([]);
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    // Function to decode JWT token
    const parseJwt = (token) => {
        try {
            const base64Url = token.split('.')[1];
            if (!base64Url) return null;

            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const jsonPayload = decodeURIComponent(
                atob(base64)
                    .split('')
                    .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
                    .join('')
            );

            return JSON.parse(jsonPayload);
        } catch (error) {
            console.error('Error decoding token:', error);
            return null;
        }
    };

    // Fetch appointments for the logged-in user
    const fetchAppointments = async () => {
        const token = localStorage.getItem('token');
        if (!token) {
            alert('Sign in to view appointments');
            navigate('/login');
            return;
        }

        const decoded = parseJwt(token);
        if (!decoded || !decoded.userId) {
            alert('Invalid session. Please log in again.');
            localStorage.removeItem('token');
            navigate('/login');
            return;
        }

        try {
            const response = await axios.get(`http://localhost:5001/api/appointments?userId=${decoded.userId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setAppointments(response.data);
        } catch (error) {
            console.error('Error fetching appointments:', error);
            setMessage('Error fetching appointments');
        }
    };

    // Delete an appointment
    const deleteAppointment = async (id) => {
        const token = localStorage.getItem('token');
        if (!token) {
            alert('Sign in to delete appointments');
            navigate('/login');
            return;
        }

        try {
            await axios.delete(`http://localhost:5001/api/appointments/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setMessage('Appointment deleted successfully');
            fetchAppointments(); // Refresh the list after deletion
        } catch (error) {
            console.error('Error deleting appointment:', error);
            setMessage('Error deleting appointment');
        }
    };

    // Fetch appointments on component mount
    useEffect(() => {
        fetchAppointments();
    }, []);

    return (
        <div className="max-w-4xl mx-auto p-6 bg-white shadow-md rounded-lg">
            <h2 className="text-2xl font-semibold mb-4">My Appointments</h2>

            {message && <p className="mb-4 text-green-600">{message}</p>}

            {appointments.length > 0 ? (
                <div className="space-y-4">
                    {appointments.map((appointment) => (
                        <div key={appointment.id} className="p-4 border rounded-lg">
                            <div className="flex justify-between items-center">
                                <div>
                                    <h3 className="text-xl font-medium">{appointment.doctorName}</h3>
                                    <p className="text-gray-600">{appointment.specialty}</p>
                                    <p className="text-gray-600">
                                        {appointment.date} at {appointment.timeSlot}
                                    </p>
                                </div>
                                <button
                                    onClick={() => deleteAppointment(appointment.id)}
                                    className="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600"
                                >
                                    Cancel
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            ) : (
                <p className="text-gray-500">No appointments found.</p>
            )}
        </div>
    );
};

export default MyAppointments;