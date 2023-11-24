
const userService = require("../services/user");
const chatService = require("../services/chats");
const { doesExist } = require("../models/firebase");
const { sendUpdateChat } = require("./firebase")

const appObjects = require('../app');

const mapping_firebase = require("../models/firebase");

// this funciton creates a new user in the app
const createChat = async (req, res) => {
    let username = res.locals.username;
    let otherUsername = req.body.username;
    if (username === otherUsername) {
        res.status(400);
        res.send('Thou shalt not talk with thy self');
        res.end();
        return;
    }
    if (!(await userService.checkIfExists(otherUsername))) {
        res.status(404);
        res.end();
        return;
    }

    let otherinfo = await chatService.createChat(username, otherUsername);

    // if there is an android user currently logged in, send them a firebase push notification
    if (doesExist(otherUsername)) {
        sendUpdateChat(otherUsername);
    }

    res.send(otherinfo);
    res.status(200);
    res.end();

    //update other user that chat was created
    appObjects.sendUpdate(otherinfo.user.username, otherinfo.id);
    return;
}


// this functin returns the user details
const getChats = async (req, res) => {
    let username = res.locals.username;
    res.status(200);
    res.send(await chatService.getChats(username));
    res.end();
}

const deleteChat = async (req, res) => {
    let id = req.params.id;
    let username = res.locals.username;
    if (!(await chatService.checkIfExistsId(id))) {
        res.status(404); //id doesnt exist
        res.end();
        return;
    }
    if (await chatService.ownsChat(username, id)) {
        let users = await chatService.deleteChat(id);
        res.status(204);
        res.end();

        //update other user that the chat was deleted
        let otherUsername = users[0].username === username ? users[1].username : users[0].username;
        // if there is an android user currently logged in, send them a firebase push notification
        if (doesExist(otherUsername)) {
            sendUpdateChat(otherUsername);
        }
        appObjects.sendUpdate(otherUsername, id);
        appObjects.sendUpdateDeleted(otherUsername, id);
        return;
    }
    res.status(401);    // not owned by this user
    res.end();
}

const getChatById = async (req, res) => {
    let id = req.params.id;
    let username = res.locals.username;
    if (!(await chatService.checkIfExistsId(id))) {
        res.status(404); //id doesnt exist
        res.end();
        return;
    }
    if (await chatService.ownsChat(username, id)) {
        res.send(await chatService.getChatById(id, username));
        res.status(200);
        res.end();
        return;
    }

    res.status(401);
    res.end();

}
module.exports = { createChat, getChats, getChatById, deleteChat }