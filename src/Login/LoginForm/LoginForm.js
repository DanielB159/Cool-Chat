import React from 'react';
import './LoginForm.css';
import { useState } from 'react';
import { useNavigate } from "react-router-dom";
import { FaEye, FaEyeSlash } from "react-icons/fa";

function LoginForm({ currentUser, setCurrentUser, setJWTtoken, socket}) {
    const navigate = useNavigate();

    const [usedLogin, setUsedLogin] = useState(false);

    // const [nameValid, setNameValid] = useState(true);
    // const [passValid, setPassValid] = useState(true);

    async function handleSubmit(e) {
        e.preventDefault();

        const form = e.target;
        const formData = new FormData(form);
        //turn into json
        const formJson = Object.fromEntries(formData.entries());

        const username = formJson.inputUsername;
        const password = formJson.inputPassword;
        // define the login details
        let loginDetails = {
            "username": username,
            "password": password
        }

        let res = await fetch("http://localhost:5000/api/Tokens", {
            method: 'post',
            headers: {
                "Content-Type": "application/json", // the data (username/password/displayName/img) is in the form of a JSON object
            },
            body: await JSON.stringify(loginDetails)
        });

        if (res.status === 200) {
            // taking the token from the response and setting it to be the current token
            let reader = res.body.getReader();
            let toDecode = await reader.read()
            let token = new TextDecoder().decode(toDecode.value);
            setJWTtoken(token);
            // now that the token is recieved, retrieveing the other details of the user
            let userRes = await fetch("http://localhost:5000/api/Users/" + username, {
                method: 'get',
                headers: {
                    "Content-Type": "application/json", // the data (username/password/displayName/img) is in the form of a JSON object
                    authorization: "bearer " + token // attach the token
                }
            })
            let userReader = userRes.body.getReader();
            let userToDecode = await userReader.read();
            let userDetails = new TextDecoder().decode(userToDecode.value);
            let newCurrentUser = await JSON.parse(userDetails);
            setCurrentUser(newCurrentUser);
            
            //update websocket we changed user
            socket.emit('login', token);

            navigate('/chat');
        } else {
            setUsedLogin(true);
        }
    }

    //set the eye button thing
    const [showPassword, setShowPassword] = useState(false);
    function toggleShowPassword() {
        setShowPassword(!showPassword);
    }

    return (
        <form onSubmit={handleSubmit}>
            <title><label>Register</label></title><br />
            <div className="form-group">
                <label>Username</label>
                <input type="text" className="form-control" name="inputUsername" id="Username" placeholder="Username" required />
            </div>
            <div className="form-group">
                <label>Password</label>
                <div className="input-group">
                    <input type={showPassword ? "text" : "password"} className="form-control" name="inputPassword" id="Password" placeholder="Password" required />
                    <div className="input-group-append">
                        <span className="input-group-text">
                            {showPassword ? (<FaEyeSlash onClick={toggleShowPassword} />) : (<FaEye onClick={toggleShowPassword} />)}
                        </span>
                    </div>
                </div>
                { usedLogin &&(
                    <div id="notValidMessage" className="form-text text-danger" role="alert">
                    Incorrect username and/or password
                </div>
                )}
            </div>
            <br></br>
            <button type="submit" className="btn btn-warning">Submit</button>
        </form>
    );
}

export default LoginForm;