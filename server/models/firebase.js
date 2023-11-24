
// firebase initialize
const admin = require('firebase-admin');
const serviceAccount = require('../cool-chat-supreme-firebase-adminsdk-mv4jm-480dc79543.json');
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});


// create a mapping for username -> token for all android connected users
var mapping_firebase = new Map();

// adds the input pair to the map
const addToMap = (username, token) => {
    mapping_firebase.set(username, token);
}

// deletes the key from the map
const deleteFromMap = (username) => {
    mapping_firebase.delete(username);
}

// checks if the username exists in the map
const doesExist = (username) => {
    return  (mapping_firebase.get(username) !== undefined)
}

// get a value for a given key
const getValue = (username) => {
    return mapping_firebase.get(username);
}

module.exports = {addToMap, deleteFromMap, doesExist, getValue, admin};