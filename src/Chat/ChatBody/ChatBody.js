import ContactList from './ContactList/ContactList';
import MessageTab from './MessageTab/MessageTab';
import './ChatBody.css';

function ChatBody({getContacts, setContacts, currentUser, activeChatter, setActiveChatter, JWTtoken,
     setActiveChatterChatID, activeChatterChatID, socket, sentMessage, setSentMessage}) {
    //pass activeChatter so that MessagesList can list the messages, and messageSend can send messages to it
    return (
        <div className="row" id="chatBoxBody">
            <ContactList getContacts={getContacts} setContacts={setContacts} activeChatter={activeChatter} 
            setActiveChatter={setActiveChatter} JWTtoken={JWTtoken} setActiveChatterChatID={setActiveChatterChatID}
            socket={socket} sentMessage={sentMessage} activeChatterChatID={activeChatterChatID}/>
            <MessageTab currentUser={currentUser} activeChatter={activeChatter} activeChatterChatID={activeChatterChatID}
            JWTtoken={JWTtoken} socket={socket} sentMessage={sentMessage} setSentMessage={setSentMessage}/>
        </div>
    );
}

export default ChatBody;