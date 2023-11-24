const Chat = require('../models/chats');
const User = require('../models/user');
const Message = require('../models/messages');

// creating a chat with the aquaintance, assuming one exists
const createChat = async (username, aquaintanceUsername)=>{
    let query = await Chat.find({});
    let max = 0;
    query.forEach(element => {
        max = element.id > max ? element.id : max;  
    });
    // getting the reference to the other user
    let otherid = await User.findOne({username: aquaintanceUsername});
    let id = await User.findOne({username: username});
    let Usersarr = [{_id: id._id},{_id: otherid._id}];
    const chat = new Chat ({
        id: max + 1,
        users: Usersarr,
        messages: []
    });
    await chat.save();
    return ({
        "id": max + 1,
        "user": {
            "username": otherid.username,
            "displayName": otherid.displayName,
            "profilePic": otherid.img
        }
    });
};

// this function checks if a user exists in the database
const checkIfExists = async (username, aquaintanceUsername) => {
    let otherid = await User.findOne({username: aquaintanceUsername});
    let id = await User.findOne({username: username});
    let var1 = await Chat.findOne({'users': [id._id, otherid._id]})
    let var2 = await Chat.findOne({'users': [otherid._id, id._id]})
    if (var1 === null && var2 === null) {
        return false;
    }
    return true;
}

// this function returns the chats of the user, in the form that the client expects
const getChats = async (username) => {
    let chats = await Chat.find({});
    let output = [];
    for (let i = 0; i < chats.length; i++) {
        let user1 = await User.findById(chats[i].users[0]);
        let user2 = await User.findById(chats[i].users[1]);
        let chat = chats[i], lastMsg = null, lastMsgId = null;
        if (chat.messages.length !== 0) {
            lastMsgId = chat.messages[chat.messages.length - 1];
            lastMsg = await Message.findById(lastMsgId);
            let sender = await User.findById(lastMsg.sender);
            //construct message
            lastMsg = {
                "id": lastMsg.id,
                "created": lastMsg.created,
                "sender": {
                    "username": sender.username
                },
                "content": lastMsg.content
            }
        }
        if (user1.username === username) {
            output.push({
                "id": chats[i].id,
                "user": {
                    "username": user2.username,
                    "displayName": user2.displayName,
                    "profilePic": user2.img
                },
                "lastMessage": lastMsg 
            });
        }
        if (user2.username === username) {
            output.push({
                "id": chats[i].id,
                "user": {
                    "username": user1.username,
                    "displayName": user1.displayName,
                    "profilePic": user1.img
                },
                "lastMessage": lastMsg 
            });
        }
    }
    return output;
}

// this function returns the chats of the user, in their natural mongoDB schema definition 
const getChatsMongo = async (username) => {
    let chats = await Chat.find({});
    let output = [];
    for (let i = 0; i < chats.length; i++) {
        let user1 = await User.findById(chats[i].users[0]);
        let user2 = await User.findById(chats[i].users[1]);
        if (user1.username === username || user2.username === username) {
            output.push(chats[i]);
        }
    }
    return output;
}

//returns true if this id belongs to a chat
const checkIfExistsId = async (id) => {
    let chat = await Chat.findOne({id: id});
    return chat !== null;
}

//returns true if this username owns this chat
const ownsChat = async (username, id) => {
    let chat = await Chat.findOne({id: id});
    let user1 = await User.findById(chat.users[0]);
    let user2 = await User.findById(chat.users[1]);
    return ((user1.username === username) || (user2.username === username));
}


// this function deletes all of the messages in the messages arr
const deleteMessagesInChat = async (messages) => {
    messages.forEach(async messageId => {
        await Message.deleteOne({_id: messageId});    
    });
}

//deletes a chat assuming it exists
const deleteChat = async (id) => {
    let chat = await Chat.findOne({id: id});
    //get users to return their usernames
    let user1 = await User.findById(chat.users[0]);
    let user2 = await User.findById(chat.users[1]);
    await deleteMessagesInChat(chat.messages);
    await Chat.deleteOne({_id: chat._id});
    return [user1, user2];
}

const getChatById = async (id, username) => {
    let chats = await Chat.findOne({ id: id });
    let user1 = await User.findById(chats.users[0]);
    let user2 = await User.findById(chats.users[1]);
    let output = [];
    if (user1.username === username) {
        output = {
            "id": chats.id,
            "users": [{
                "username": user1.username,
                "displayName": user1.displayName,
                "profilePic": user1.img
            },
            {
                "username": user2.username,
                "displayName": user2.displayName,
                "profilePic": user2.img

            }],
            "messages": [] //TODO
        };
    } if (user2.username === username) {
        output = {
            "id": chats.id,
            "users": [{
                "username": user2.username,
                "displayName": user2.displayName,
                "profilePic": user2.img
            },
            {
                "username": user1.username,
                "displayName": user1.displayName,
                "profilePic": user1.img

            }],
            "messages": [] //TODO
        };
    }

    return output;
}

module.exports = {createChat, checkIfExists, getChats, getChatsMongo ,ownsChat, deleteChat, checkIfExistsId, getChatById}
