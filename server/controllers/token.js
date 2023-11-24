
const key = "hapijamotHozrimUBehadolYeshlanuKetzevVeYeshRockAndRoll"
const userService = require('../services/user');
const jwt = require("jsonwebtoken")

const generateToken = async (req, res) => {
    let details = req.body;
    // if this user exists, create a token for him
    if (await userService.validateUserPassword(details.username, details.password)) {
        let data = {username: details.username};
        // create a toke, save it to the database and return it to the user
        const token = jwt.sign(data, key)
        res.status(200);    
        res.send(token);
        res.end();
    } else {
        res.status(404);
        res.end("Incorrect username and/or password");
    }
}

// Ensure that the user sent a valid token
const isLoggedIn = (req, res, next) => {
    // If the request has an authorization header
    if (req.headers.authorization) {
        // Extract the token from that header
        const token = req.headers.authorization.split(" ")[1];
        try {
            // Verify the token is valid
            const data = jwt.verify(token, key);
            // Token validation was successful. Continue to the actual function, next
            res.locals.username = data.username;
            return next();
        } catch (err) {
            return res.status(401).send("Invalid Token");
        }
    }
    else
        return res.status(403).send('Token required');
}

module.exports = {generateToken, isLoggedIn};