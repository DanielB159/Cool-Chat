
const userService = require("../services/user");
const {addToMap, deleteFromMap} = require("../models/firebase");

// this funciton creates a new user in the app
const createUser = async (req, res) => {
    let userDetails = req.body;
    if (!(await userService.checkIfExists(userDetails.username, userDetails.password))) {
        userService.createUser(userDetails)
        res.status(200);
        res.end();
    }
    res.status(409);
    res.end();
}


// this functin returns the user details
const getUserDetails = async (req, res) => {
    
    // Parse the URL query parameters
    if (req.params.username !== res.locals.username) {
        //only allow looking for this user's details if you are that user
        res.status(401);
        res.end();
        return;
    }
    // if the request has a firebase token attached to it
    if (req.headers.firebasetoken) {
        // add the mapping of of the username to the specific token
        addToMap(req.params.username, req.headers.firebasetoken);
    }

    let lookup = await userService.getUserDetails(req.params.username);
    res.status(200);
    res.send({
        "username": lookup.username,
        "displayName": lookup.displayName,
        "profilePic": lookup.img
    });
    res.end();
}

module.exports = {createUser, getUserDetails}