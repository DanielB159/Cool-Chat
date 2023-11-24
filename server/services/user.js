const User = require('../models/user');

// creating a user in the database
const createUser = async ({ username, password, displayName, profilePic }) => {
    const user = new User({
        username: username,
        password: password,
        displayName: displayName,
        img: profilePic
    });
    return await user.save()
};

// this function checks if a user exists in the database
const checkIfExists = async (username) => {
    try {
        let user = await User.findOne({ 'username': username })
        if (user !== null) {
            return true;
        } else {
            return false;
        }
    } catch (e) {
        // if an error occured in the DB server, reject by default
        return false;
    }
    
}

// this function returns the user details, assuming that he is in the database
const getUserDetails = async (username) => {
    return User.findOne({ username: username });
}

// this function validates that the password of the user is correct
const validateUserPassword = async (username, password) => {
    try {
        let user = await User.findOne({ 'username': username })
        if (user !== null) {
            if (user.password === password) {
                // correct password
                return true;
            }
            return false;
        } else {
            return false;
        }
    } catch (e) {
        // if an error occured in the DB server, reject by default
        return false;
    }
}



module.exports = { createUser, checkIfExists, getUserDetails, validateUserPassword }