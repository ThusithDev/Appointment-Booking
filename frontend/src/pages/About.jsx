import React from 'react';
import { assets } from '../assets/assets';

const About = () => {
  return (
    <div className="mx-auto max-w-4xl p-6">
      <div className="text-center text-2xl pt-10 text-gray-500">
        <p>ABOUT <span className="text-gray-700 font-medium">US</span></p>
      </div>

      <div className="my-10 flex flex-col md:flex-row gap-12 items-center">
        <img className="w-full md:max-w-[360px] rounded-lg shadow-lg" src={assets.about_image} alt="About Us" />
        <div className="space-y-4 text-gray-700">
          <p>
            Welcome to <b>THUSITH Hospitals</b>, your trusted partner in managing your healthcare needs conveniently and efficiently. 
            At THUSITH, we understand the challenges individuals face when scheduling doctor appointments and managing their health records.
          </p>
          <p>
            THUSITH is committed to excellence in healthcare technology. We continuously strive to enhance our platform, integrating the latest advancements to improve user experience and deliver superior service. 
            Whether you're booking your first appointment or managing ongoing care, THUSITH is here to support you every step of the way.
          </p>
          <div>
            <b className="text-lg">Our Vision</b>
            <p>
              Our vision at THUSITH is to create a seamless healthcare experience for every user. 
              We aim to bridge the gap between patients and healthcare providers, making it easier for you to access the care you need, when you need it.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default About;
