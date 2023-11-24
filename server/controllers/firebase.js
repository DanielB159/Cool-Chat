const { admin, getValue, doesExist } = require("../models/firebase");



const sendUpdateChat = (username) => {
    if (doesExist(username)) {
        const message = {
            data: {
                key1: 'updateChats',
            },
            token: getValue(username)
        };
        admin.messaging().send(message)
        .catch((exception) => {
            console.log("firebase google server not available");
        });
    }
}


const sendNewMessage = (username, chatId, sender, msg) => {
    if (doesExist(username)) {
        const message = {
            data: {
                key1: 'newMessages',
                key2: chatId + "," + sender + "," + msg
            },
            token: getValue(username)
        };
        admin.messaging().send(message)
        .catch((exception) => {
            console.log("firebase google server not available");
        });
    }
}


module.exports = {sendUpdateChat, sendNewMessage}