const Message = require('../models/messages');
const User = require('../models/user');
const Chat = require('../models/chats');
const chatsService = require('../services/chats');
const userService = require('../services/user');

// find the last chat Message
const findLatestMsg = async (chatId, username) => {
    let allChats = await chatsService.getChats(username);
    for (let i = 0; i < allChats.length; i++) {
        let element = allChats[i];
        if (element.id === chatId) {
            return element.lastMessage; 
        }
    };
}


// this function creates a new message in the database, given the usernames and the text
const createMessage = async (chatId, text, senderUsermame) => {
    let allChats = await chatsService.getChatsMongo(senderUsermame);
    let userDetails = await userService.getUserDetails(senderUsermame);;
    let userId = userDetails._id;
    let newMsgChatId = null, newMessages = null;
    let date = Date.now();
    // define the return message that we will return to the client
    let returnMsg = {
        "id": null,
        "created": date,
        "sender": {
            "username": userDetails.username,
            "displayName": userDetails.displayName,
            "profilePic": userDetails.img
        },
        "content": text
    };
    // find the correct chat
    for (let i = 0; i < allChats.length; i++) {
        let element = allChats[i];
        // once found the correct chat, add the message to the database and update the chat
        if (element.id == chatId) {
            if (element.users[0].valueOf() === userId.valueOf()) {
                let msgid = element.messages.length + 1;
                const message = new Message({
                    id: msgid,
                    created: date,
                    content: text,
                    sender: element.users[0],
                    receiver: element.users[1]
                });
                await message.save();
                // update all of the return values and update parameters
                returnMsg.id = msgid;
                element.messages.push(message._id);
                newMsgChatId = element._id;
                newMessages = element.messages;
            }
            else if (element.users[1].valueOf() === userId.valueOf()) {
                let msgid = element.messages.length + 1;
                const message = new Message({
                    id: msgid,
                    created: date,
                    content: text,
                    sender: element.users[1],
                    receiver: element.users[0]
                });
                await message.save();
                // update all of the return values and update parameters
                returnMsg.id = msgid;
                element.messages.push(message._id);
                newMsgChatId = element._id;
                newMessages = element.messages;
            }
        }
    };
    // update the chat to be with the new message, if one was indeed added
    if (newMsgChatId !== null) {
        try {
            await Chat.updateOne({_id: newMsgChatId}, { messages: newMessages });
            let thischat = await Chat.findOne({_id: newMsgChatId});
            let user1 = await User.findById(thischat.users[0]);
            let user2 = await User.findById(thischat.users[1]);
            let otherusername = returnMsg.sender.username === user1.username ? user2.username : user1.username; //get other username for use later
            
            return [returnMsg, otherusername];
        } catch(e) {
            return null;
        }
    }
}   

// this function parses an array of message IDs to the actual list of messages
const parseMessages = async (messageIdList) => {
    let finishedMessages = [];
    for (let i = 0; i < messageIdList.length; i++) {
        let msgid = messageIdList[i];
        let message = await Message.findById(msgid);
        let user = await User.findById(message.sender);
        finishedMessages.push({
            "id": message.id,
            "created": message.created,
            "sender": {
                "username": user.username
            },
            "content": message.content
        });
    }
    return finishedMessages;
}


// this function retrieves all messages from the chatId chat username
const getMessages = async (chatId, username) => {
    let allChats = await chatsService.getChatsMongo(username);
    for (let i = 0; i < allChats.length; i++) {
        let element = allChats[i];
        if (element.id == chatId) {
            return await parseMessages(element.messages);
        }
    };
    return null;
}


module.exports = {createMessage, findLatestMsg, getMessages}