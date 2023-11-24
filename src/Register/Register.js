import React from 'react';
import './Register.css'
import {Link} from 'react-router-dom'
import RegisterForm from './RegisterForm/RegisterForm';

function Register(props) {
    return (
        <div id="registerBox" className="border border-light-subtle rounded-4">
         <p className="h2">Sign up for free!</p>
         <RegisterForm />
        </div>
    );
}

export default Register;