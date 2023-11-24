const express = require('express');
var app = express();

const http = require('http');
const server = http.createServer(app);
const { Server } = require('socket.io');
const io = new Server(server, {
    cors: { origin: "*" },
  });



const key = "hapijamotHozrimUBehadolYeshlanuKetzevVeYeshRockAndRoll"
const jwt = require("jsonwebtoken");

const idDict = {};   // "socketid": "username" (null if none yet)
io.on('connection', (socket) => {
    //add to object of users
    idDict[socket.id.toString()] = null;    //assume unique

    socket.on('disconnect', () => {
        //remove from object of connected users
        delete idDict[socket.id.toString()];
    });
    socket.on('login', (JWTtoken) => {
        //update logged in user
        try {
            // Verify the token is valid
            let data = jwt.verify(JWTtoken, key);
            //if verified
            idDict[socket.id.toString()] = data.username;
        } catch (err) {
            //invalid token
        }
    });
    socket.on('logout', (JWTtoken) => {
        //update user logged out
        try {
            // Verify the token is valid
            jwt.verify(JWTtoken, key);
            //if verified
            idDict[socket.id.toString()] = null;
        } catch (err) {
            //invalid token
        }
    });
});
// need to send message 'update' when sending a message, and when adding a new chat

// id is the id of the chat the was updated
const sendUpdate = async (username, id) => {
    //tell user to update their data
    for (const [key, value] of Object.entries(idDict)) {
        if (value === username) {
            //username matches active socket connection
            io.to(key).emit('updateMessages', id);
            io.to(key).emit('updateContacts', id);
        }
    }
}

// id is the id of the chat the was deleted
const sendUpdateDeleted = async (username, id) => {
    //tell user to update their data
    for (const [key, value] of Object.entries(idDict)) {
        if (value === username) {
            //username matches active socket connection
            io.to(key).emit('updateDeleted', id);
        }
    }
}

module.exports = { sendUpdate, sendUpdateDeleted } //let services use this socket object

app.use(express.static("public"));

const bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({extended : true}));
app.use(express.json());

const cors = require('cors');
app.use(cors());

const mongoose = require('mongoose');
const custonEnv=require('custom-env');
custonEnv.env(process.env.NODE_ENV,'./server/config');
const users = require('./routes/user');
const tokens = require('./routes/token');
const chats = require('./routes/chats');

mongoose.connect(process.env.CONNECTION_STRING, {
     useNewUrlParser: true,
     useUnifiedTopology: true });


// set the routes according to the URL
app.use('/api/Users', users);
app.use('/api/Tokens', tokens);
app.use('/api/Chats', chats);

server.listen(process.env.PORT);


