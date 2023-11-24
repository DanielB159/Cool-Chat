import ContactDetails from './ContactDetails/ContactDetails';
import AddFriend from './AddFriend/AddFriend';
import UserDetails from './UserDetails/UserDetails';
import AddFriendModal from './AddFriendModal/AddFriendModal';
import LogoutModal from './LogoutModal/LogoutModal';
import LogOut from './Logout/Logout';
import DeleteChat from './DeleteChat/DeleteChat';
import DeleteChatModal from './DeleteChatModal/DeleteChatModal'
import './ChatHeader.css'

function ChatHeader({currentUser, setCurrentUser, setActiveChatter, JWTtoken, setJWTtoken, activeChatterChatID, setActiveChatterChatID, setContacts, socket}) {
    return (
        <div className="row" id="chatBoxTop">
            <UserDetails displayName={currentUser.displayName} img={currentUser.profilePic} />
            <AddFriend />
            <AddFriendModal currentUser={currentUser} JWTtoken={JWTtoken} setContacts={setContacts}/>
            <LogOut />
            <LogoutModal currentUser={currentUser} setCurrentUser={setCurrentUser} setJWTtoken={setJWTtoken} socket={socket}/>
            <ContactDetails JWTtoken={JWTtoken} 
            activeChatterChatID={activeChatterChatID} />
            <DeleteChat activeChatterChatID = {activeChatterChatID}/>
            <DeleteChatModal setContacts={setContacts} setActiveChatter={setActiveChatter} activeChatterChatID={activeChatterChatID} 
            setActiveChatterChatID={setActiveChatterChatID} JWTtoken={JWTtoken} socket={socket}/>
        </div>
    );
}

export default ChatHeader;