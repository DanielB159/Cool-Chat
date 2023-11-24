import ChatHeader from './ChatHeader/ChatHeader';
import ChatBody from './ChatBody/ChatBody';
import './Chat.css';
import {useState } from 'react';
import { Navigate } from "react-router-dom";

function Chat({currentUser, setCurrentUser, JWTtoken, setJWTtoken, socket}) {
    // useState on the current contactList in the chats - according to the first user
    const [getContacts, setContacts] = useState(null);
    const [activeChatter, setActiveChatter] = useState("");
    const [activeChatterChatID, setActiveChatterChatID] = useState(-1);
    const [sentMessage, setSentMessage] = useState(false);

    //protect this page if no user is logged in
    if (JSON.stringify(currentUser) === '{}') {
        return (<Navigate to="/" replace={true} />);
    }

    // pass the current contactList to the ChatBody to show them in the sidebar
    // pass the setContacts and getContacts to the ChatHeader so the module will be able to add new contacts
    // pass the currentUser as well so the chat can update messages
    return (
        <div className="container border border-black" id="chatboxContainer">
            <ChatHeader currentUser={currentUser} setCurrentUser={setCurrentUser}  JWTtoken={JWTtoken} setJWTtoken={setJWTtoken} 
            activeChatterChatID={activeChatterChatID} setActiveChatterChatID={setActiveChatterChatID} setContacts={setContacts} socket={socket}/>
            <ChatBody getContacts={getContacts} setContacts={setContacts} currentUser={currentUser} activeChatter={activeChatter}
             setActiveChatter={setActiveChatter} JWTtoken={JWTtoken} setActiveChatterChatID={setActiveChatterChatID}
             activeChatterChatID={activeChatterChatID} socket={socket} sentMessage={sentMessage} setSentMessage={setSentMessage}/>
        </div>
    );
}

export default Chat;