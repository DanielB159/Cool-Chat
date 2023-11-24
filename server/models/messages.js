const mongoose = require('mongoose');

const Schema = mongoose.Schema;

// message schema
const Message  = new Schema({
    id: {
        type: Number,
        required: true
    },
    created: {
        type: Date,
        default: Date.now,
    },
    content: {
        type: String,
        required: true
    },
    sender: {
        type: Schema.Types.ObjectId,
        ref: 'User',
        required: true
    },
    receiver: {
        type: Schema.Types.ObjectId,
        ref: 'User',
        required: true
    }
});
    
module.exports = mongoose.model('Message', Message);
