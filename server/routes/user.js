const express = require('express');
var router = express.Router();
const usersController = require('../controllers/user');
const tokensController = require('../controllers/token');

// route the path to the corresponding path
router.route('/').post(usersController.createUser);

router.route('/:username').get(tokensController.isLoggedIn, usersController.getUserDetails);

// export the router
module.exports = router;