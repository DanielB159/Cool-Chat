
// import noProfile from '../../public/noProfile.png'
const mongoose = require('mongoose');

// const noProfilePic = noProfile;

const Schema = mongoose.Schema;
// Create a Schema for a user that is using tha Chat App
const User  = new Schema({
    username:{
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true
    },
    displayName: {
        type: String,
        required: true
    },
    img: {
        type: String,
        required: true
    }
});
    


module.exports = mongoose.model('User', User);