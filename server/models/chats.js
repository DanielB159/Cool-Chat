const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const Chat  = new Schema({
    id: {
        type: Number,
        required: true
    },
    users: [
        {
            type: Schema.Types.ObjectId,
            ref: 'User',
            required: true
        }
    ],
    messages: [
        {
            type: Schema.Types.ObjectId,
            ref: 'Message',
            required: true
        }
    ]

    });

module.exports = mongoose.model('Chat', Chat);