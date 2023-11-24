import React from 'react';
import './App.css';
import Login from './Login/Login';
import Register from './Register/Register';
import Chat from './Chat/Chat';
// import siteUsers from './SiteUsers';
// import chatContacts from './Chat/ChatContacts';
import {Routes, Route, Navigate} from 'react-router-dom'
import {useState} from 'react'

function App({socket}) {
    const [currentUser, setCurrentUser] = useState({});
    const [JWTtoken, setJWTtoken] = useState("");
    // const [currentSiteUsers, setCurrentSiteUsers] = useState(siteUsers);
    // const [currentChatContacts, setCurrentChatContacts] = useState(chatContacts);
    return (
        <Routes>
            <Route path="/" element = {<Login currentUser={currentUser} setCurrentUser={setCurrentUser} setJWTtoken={setJWTtoken} socket={socket}/>} />
            <Route path="/chat" element = {JSON.stringify(currentUser) === '{}' ? <Navigate to="/" replace={true}/>
            : <Chat currentUser={currentUser} setCurrentUser={setCurrentUser} JWTtoken={JWTtoken} setJWTtoken={setJWTtoken} socket={socket}/>} />
            <Route path="/register" element = {<Register setCurrentUser={setCurrentUser} />} />
        </Routes>
    )
}

export default App;
