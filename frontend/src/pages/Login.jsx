import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const navigate = useNavigate();

  const [state, setState] = useState('Login');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [error, setError] = useState('');

  const onSubmitHandler = async (event) => {
    event.preventDefault();
  
    try {
      const response = await fetch(`http://localhost:5001/api/auth/${state === 'Sign Up' ? 'register' : 'login'}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(state === 'Sign Up' ? { fullName: name, email, password } : { email, password }),
      });
  
      const text = await response.text(); // Read response as text

      let data;
      try {
        data = JSON.parse(text); // Convert text to JSON
      } catch (error) {
        throw new Error(`Invalid JSON response from server: ${text}`);
      }
  
      if (!response.ok) {
        throw new Error(data.error || 'Something went wrong'); // Display backend error message
      }
  
      if (state === 'Login') {
        localStorage.setItem('token', data.token); 
        navigate('/'); 
        window.location.reload();
      } else {
        setState('Login'); 
      }
    } catch (error) {
      setError(error.message);
    }
  };  

  return (
    <form className='min-h-[80vh] flex items-center' onSubmit={onSubmitHandler}>
      <div className='flex flex-col gap-3 m-auto items-start p-8 min-w-[340px] sm:min-w-96 border rounded-xl text-zinc-600 text-sm shadow-lg'>
        <p className='text-2xl font-semibold'>{state === 'Sign Up' ? 'Create Account' : 'Login'}</p>
        <p>Please {state === 'Sign Up' ? 'Sign Up' : 'Login'} to book an appointment</p>

        {error && <p className='text-red-500'>{error}</p>}

        {state === 'Sign Up' && (
          <div className='w-full'>
            <p>Full Name</p>
            <input
              className='border border-zinc-300 rounded w-full p-2 mt-1'
              type='text'
              onChange={(e) => setName(e.target.value)}
              value={name}
              required
            />
          </div>
        )}

        <div className='w-full'>
          <p>Email</p>
          <input
            className='border border-zinc-300 rounded w-full p-2 mt-1'
            type='email'
            onChange={(e) => setEmail(e.target.value)}
            value={email}
            required
          />
        </div>

        <div className='w-full'>
          <p>Password</p>
          <input
            className='border border-zinc-300 rounded w-full p-2 mt-1'
            type='password'
            onChange={(e) => setPassword(e.target.value)}
            value={password}
            required
          />
        </div>

        <button className='bg-primary text-white w-full py-2 rounded-md text-base'>
          {state === 'Sign Up' ? 'Create Account' : 'Login'}
        </button>

        {state === 'Sign Up' ? (
          <p>
            Already have an account?{' '}
            <span onClick={() => setState('Login')} className='text-primary underline cursor-pointer'>
              Login here
            </span>
          </p>
        ) : (
          <p>
            Don't have an account?{' '}
            <span onClick={() => setState('Sign Up')} className='text-primary underline cursor-pointer'>
              Click here
            </span>
          </p>
        )}
      </div>
    </form>
  );
};

export default Login;