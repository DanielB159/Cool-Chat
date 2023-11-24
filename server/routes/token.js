const express = require('express');
var router = express.Router();
const tokensController = require('../controllers/token');

// route the path to the corresponding path
router.route('/')
    .post(tokensController.generateToken);


module.exports = router;