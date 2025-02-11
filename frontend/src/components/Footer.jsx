import React from 'react'
import { assets } from '../assets/assets'

const Footer = () => {
  return (
    <div className='md:mx-10 my-10'>
        <div className='flex flex-col sm:grid grid-cols-[3fr_1fr_1fr] gap-14 my-10 mt-40 text-sm'>

            {/* ---- Left Section ---- */}
            <div>
                <img className='mb-5 w-40' src={assets.thusith_logo} />
                <p className='w-full md:w-2/3 text-gray-600 leading-6'>Thusith Hospitals are operating since 2025 February making competition in healthcare industry to provide valuable services for Sri Lankans. The company consists with 9 hospitals islandwide covering all the regions in Sri Lanka under CEO Thusith.</p>
            </div>

            {/* ---- Center Section ---- */}
            <div>
                <p className='text-x1 font-medium mb-5'>COMPANY</p>
                <ul className='flex flex-col gap-2 text-gray-600'>
                    <li>Home</li>
                    <li>About us</li>
                    <li>Contact us</li>
                    <li>Privacy Policy</li>
                </ul>
            </div>

            {/* ---- Right Section ---- */}
            <div>
                <p className='text-x1 font-medium mb-5'>GET IN TOUCH</p>
                <ul className='flex flex-col gap-2 text-gray-600'>
                    <li>+94 77 8661 293</li>
                    <li>anjulathusith@gmail.com</li>
                </ul>
            </div>
        </div>
        {/* ------------------- Copyright text ------------------- */}
        <div>
            <hr />
            <p className='py-5 text-sm text-center'>Copyright 2025@ Thusith Hospitals - All Right Reserved.</p>
        </div>
    </div>
  )
}

export default Footer