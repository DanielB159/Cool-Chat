import React, { useState } from "react";
import "./RegisterForm.css";
import { Link } from 'react-router-dom'
import { useNavigate } from "react-router-dom";
import { FaEye, FaEyeSlash } from "react-icons/fa";


function RegisterForm() {
    //  const showPassword=useRef(0);
    const navigate = useNavigate();
    const [isInputValid, setIsInputValid] = useState(true);
    const [imageSrc, setImageSrc] = useState("");
    const [showPassword, setShowPassword] = useState(false);
    const [getPassword, setPassword] = useState("");
    const [passwordTouched, setPasswordTouched] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);
    const [confirmPassword, setConfirmPassword] = useState("");
    const [confirmPasswordTouched, setConfirmPasswordTouched] = useState(false);
    const [getUsername, setUsername] = useState('');
    // const [usernameTouched, setUsernameTouched] = useState(false);
    const [usernameTaken, setUsernameTaken] = useState(false);
    async function handleSubmit(e) {

        e.preventDefault();
        const form = e.target;
        const formData = new FormData(form);
        const formJson = Object.fromEntries(formData.entries());

        // const username = formJson.inputUsername;
        // const password = formJson.inputPassword;
        // const confirmPassword = formJson.inputConfirmPassword;
        const displayName = formJson.inputDisplayName;
        //const picture = formJson.inputPicture;
        // if (isUsernameValid()) {
        //     setIsInputValid(false);
        //     // invalid user
        //     return;
        if (!isPasswordValid()) {
            setIsInputValid(false); // invalid password
            return;
        }
        if (!isConfirmPasswordValid()) {
            setIsInputValid(false);  // invalid password confirmation
            return;
        } else {
            setIsInputValid(true);
            let data = {
                "username": getUsername,
                "password": getPassword,
                "displayName": displayName,
                "profilePic": imageSrc
            }
            const answer = await fetch("http://localhost:5000/api/Users", {
                method: "post",
                headers: {
                    "Content-Type": "application/json", // the data (username/password/displayName/img) is in the form of a JSON object
                },
                body: JSON.stringify(data)
            });
            if (answer.status !== 200) {
                if (answer.status == 409) {
                    setUsernameTaken(true);
                }
                setIsInputValid(false);
            } else {
                // the username has been added to the database. referring to login page
                setUsernameTaken(false);
                setIsInputValid(true);
                navigate('/');
            }
            // siteUsers[username] =
            // {
            //     displayName: displayName,
            //     img: imageSrc,
            //     pass: password
            // };
            // chatContacts[username] = [];
        }
        
    }

    function handleImageChange(e) {
        e.preventDefault();
        if (e.target.files[0]) {
            const file = e.target.files[0];
            const reader = new FileReader();
            reader.onload = () => {
                setImageSrc(reader.result);
            };
            reader.readAsDataURL(file);
        } else {
            setImageSrc("");
        }
    }



    function toggleShowPassword() {
        setShowPassword(!showPassword);
    }

    function toggleShowConfirmPassword() {
        setShowConfirmPassword(!showConfirmPassword);
    }
    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handlePasswordBlur = () => {
        setPasswordTouched(true);
    };

    const isPasswordValid = () => {
        const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
        return passwordPattern.test(getPassword);
    };
    const handleConfirmPasswordBlur = () => {
        setConfirmPasswordTouched(true);
    };

    const isConfirmPasswordValid = () => {
        return (confirmPassword === getPassword);
    };
    const handleConfirmPasswordChange = (event) => {
        setConfirmPassword(event.target.value);
    };

    // const isUsernameValid = async () => {
    //     let answer = await fetch('http://localhost:5000/swagger/Users/' + username, {
    //         method: 'GET',
    //         headers: {
    //             'Content-Type': 'application/json'
    //         }
    //     }
    //     );
    //     console.log(answer);
    //     return siteUsers.hasOwnProperty(username);
    // };
    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    // const handleUsernameBlur = () => {
    //     setUsernameTouched(true);
    // };

    return (
        <form onSubmit={handleSubmit}>
            <title><label>Register</label></title><br />
            <div className="form-group">
                <label>Username</label>
                <input type="text" className="form-control" name="inputUsername" id="Username" placeholder="Username" required
                    value={getUsername}
                    onChange={handleUsernameChange}
                    // onBlur={handleUsernameBlur}
                ></input>
                {usernameTaken && (
                    <small className="form-text text-danger">
                        This username is already taken.
                    </small>
                )}
            </div>

            <div className="form-group">
                <label>Password</label>
                <div className="input-group">
                    <input type={showPassword ? "text" : "password"} className="form-control" name="inputPassword" id="Password" placeholder="Password" required
                        value={getPassword}
                        onChange={handlePasswordChange}
                        onBlur={handlePasswordBlur}
                    />
                    <div className="input-group-append">
                        <span className="input-group-text">
                            {showPassword ? (<FaEyeSlash onClick={toggleShowPassword} />) : (<FaEye onClick={toggleShowPassword} />)}
                        </span>
                    </div>
                </div>

                <small id="passwordNotify" className="form-text text-muted">Password must include at least 8 characters combining both letters and digits.</small>

                {passwordTouched && !isPasswordValid() && (
                    <div className="form-text text-danger" role="alert">
                        Invalid password!
                    </div>
                )}
            </div>
            <div className="form-group">
                <label>Confirm password</label>
                <div className="input-group">
                    <input type={showConfirmPassword ? "text" : "password"} className="form-control" name="inputPassword" id="ConfirmPassword" placeholder="Confirm Password" required
                        value={confirmPassword}
                        onChange={handleConfirmPasswordChange}
                        onBlur={handleConfirmPasswordBlur}

                    />
                    <div className="input-group-append">
                        <span className="input-group-text">
                            {showConfirmPassword ? (<FaEyeSlash onClick={toggleShowConfirmPassword} />) : (<FaEye onClick={toggleShowConfirmPassword} />)}
                        </span>
                    </div>
                </div>
                {confirmPasswordTouched && !isConfirmPasswordValid() && (
                    <div className="form-text text-danger" role="alert">
                        Passwords do not match.
                    </div>
                )}
            </div>
            <div className="form-group">
                <label>Display name</label>
                <input type="text" className="form-control" name="inputDisplayName" id="Display name" placeholder="Display name" required></input>
            </div>
            <br></br>
            <div className="form-group">
                <label>Choose file: </label> &nbsp;
                <input type="file" className="Picture" name="inputPicture" id="Picture" onChange={handleImageChange} required ></input>
                {imageSrc && <img src={imageSrc} alt="" width="100px" />}
            </div>
            <br></br>
            <small id="alreadyRegistered" className="form-text text-muted">Already registered? &nbsp;
                    <Link to='/'>Click here</Link> to log in</small>

            {!isInputValid && (
                <div id="notValidMessage" className="form-text text-danger" role="alert">
                    Input invalid!
                </div>
            )}
            <button type="submit" className="btn btn-warning">Register</button>
        </form>
    )
}

export default RegisterForm;