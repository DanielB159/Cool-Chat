const express = require('express');
var router = express.Router();
const chatsController = require('../controllers/chats');
const tokensController = require('../controllers/token');
const msgController = require('../controllers/message');

//{"username": "string"}
// route the path to the corresponding path
router.route('/').post(tokensController.isLoggedIn, chatsController.createChat);

router.route('/').get(tokensController.isLoggedIn, chatsController.getChats);

router.route('/:id').delete(tokensController.isLoggedIn, chatsController.deleteChat);

router.route('/:id').get(tokensController.isLoggedIn,chatsController.getChatById);

router.route('/:id/Messages')
    .post(tokensController.isLoggedIn, msgController.createNewMessage)
    .get(tokensController.isLoggedIn, msgController.getMessages);

// export the router
module.exports = router;