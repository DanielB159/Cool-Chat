import React from 'react';
import './Login.css';
import {Link} from 'react-router-dom'
import LoginForm from './LoginForm/LoginForm.js';

function Login({currentUser, setCurrentUser, setJWTtoken, socket}) {

    return (
        <div id="loginBox" className="border border-light-subtle rounded-4">
            <p className="h2">Log in</p>
            <LoginForm currentUser={currentUser} setCurrentUser={setCurrentUser} setJWTtoken={setJWTtoken} socket={socket}/>
            <small id="signUp" className="form-text text-muted">Don't have an account?&nbsp;
                    <Link to='/register'>Sign up now!</Link></small>
        </div>
    );
}

export default Login;