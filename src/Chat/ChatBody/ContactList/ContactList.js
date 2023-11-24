import Contact from "./Contact/Contact";
import './ContactList.css';
import { useEffect } from "react";

function ContactList({getContacts, setContacts, activeChatter, setActiveChatter, JWTtoken, setActiveChatterChatID, socket, sentMessage, activeChatterChatID}) {
    
    let contactList;
    const updateContacts = async () => {
        try {
            fetch("http://localhost:5000/api/Chats", {
                method: 'get',
                headers: {
                    "Content-Type": "application/json", // the data (username/password/displayName/img) is in the form of a JSON object
                    authorization: "bearer " + JWTtoken // attach the token
                }
            })
            .then(res => res.json())
            .then(contacts => {
                if (JSON.stringify(contacts) !== JSON.stringify(getContacts)) {
                    setContacts(contacts)
                }
            });
        } catch (e) {
            // do nothing
        }
    }

    // update the socket when something has changed in it
    useEffect(() => {
        socket.off('updateContacts');
        socket.on('updateContacts', (msg) => {
            updateContacts();
            setContactList();
        });
    }, [socket, getContacts, JWTtoken])
    



    useEffect(() => {
        updateContacts();
        setContactList();
    }, [contactList, sentMessage]);
    // use the current state of currentChat to indicate who is the current active chat


    // this function returns a list of contacts to print in the contacts tav
    const setContactList = () => {
        if (getContacts == null) {
            contactList = "Loading contacts..."
        } else {
            contactList = getContacts.map((contact, key) => {
                let lastMsgTime = "";
                if (contact.lastMessage !== null) {
                    lastMsgTime = contact.lastMessage.created;
                }
                //console.log(contact.lastMessage);
                return <Contact identifier={contact.id} displayName={contact.user.displayName}
                LastMessageSent={contact.lastMessage} imgSrc={contact.user.profilePic} activeChatter = {activeChatter}
                    setActiveChatter = {setActiveChatter} JWTtoken={JWTtoken} setActiveChatterChatID={setActiveChatterChatID}
                    activeChatterChatID={activeChatterChatID} userChatter={contact.user.username} lastMsgTime={lastMsgTime} key={key}/>
            });
        }
    }
    
    // set the initial contact list to be the updated one from the database
    setContactList()
    
    
    return (
        
        <div className="col-12 col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-5 col-xxl-5" id="contacts" >
            <ul className="list-group">
                {contactList}
            </ul>
        </div >
    )
}

export default ContactList;