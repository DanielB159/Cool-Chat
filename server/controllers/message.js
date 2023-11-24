const messageService = require('../services/message');
const { doesExist } = require("../models/firebase");
const { sendUpdateChat, sendNewMessage } = require("../controllers/firebase")

const appObjects = require('../app');

// this function uses the mesage service to create a new message in the DB
const createNewMessage = async (req, res) => {
    const msg = req.body.msg;
    const chatId = req.params.id;
    const username = res.locals.username;
    let returnMsg = await messageService.createMessage(chatId, msg, username);
    if (returnMsg == undefined || returnMsg == null || returnMsg[0] === null) {
        res.status(500);
    } else {
        res.send(returnMsg[0]);
        res.status(200);
        //update other users of the message
        appObjects.sendUpdate(username, chatId);
        appObjects.sendUpdate(returnMsg[1], chatId);
        if (doesExist(returnMsg[1])) {
            sendUpdateChat(returnMsg[1]);
            sendNewMessage(returnMsg[1], chatId, username, msg);
        }
    }
    res.end();

    return;
}

// this runction uses the message service to retrieve all messages of this chat from the DB
const getMessages = async (req, res) => {
    const chatId = req.params.id;
    const username = res.locals.username;
    const msgArr = await messageService.getMessages(chatId, username);
    if (msgArr !== null) {
        res.status(200);
        res.send(msgArr);
    } else {
        res.status(401);
    }
    res.end()
    return;
}


module.exports = {createNewMessage, getMessages}