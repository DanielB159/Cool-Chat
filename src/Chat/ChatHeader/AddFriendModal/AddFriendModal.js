import { useRef } from 'react';
import { useState } from 'react';

function AddFriendModal({ currentUser, JWTtoken, setContacts}) {

    // define a Hook to the identifier input
    const newContact = useRef(null);
    
    const [doesntExists, setDoesntExists] = useState(false);


    // // define a function to check weather the user is already in chats
    // const isInChats = async (name) => {
    //     let inChat = false;
    //     try {
    //         let res = await fetch()
    //     } catch(e) {
    //         return false;
    //     }
    //     getContacts.forEach((contact) => {
    //         if (name === contact.identifier) {
    //             inChat = true;
    //         }
    //     })
    //     return inChat;
    // }

    // this function adds a new contact if exist and isn't already a contact
    const addContact = async (e) => {
        e.preventDefault();

        let currentAdd = newContact.current.value;
        if (currentAdd == "") {
            return;
        }

        let addFriend = {"username": currentAdd};
        // sending a request to add a new friend
        let res = await fetch("http://localhost:5000/api/Chats", {
            method: "post",
            headers: {
                "Content-Type": "application/json", // the data (username/password/displayName/img) is in the form of a JSON object
                authorization: "bearer " + JWTtoken // attach the token
            },
            body: JSON.stringify(addFriend)
        });
       // console.log(res);
        if(await res.status===404){
            setDoesntExists(true);
        } else{
            setDoesntExists(false);
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
                setContacts(contacts)
            });
        } catch (e) {
            // do nothing
        }
        }

        
    }



        // if (!isInChats(currentAdd)) {
        //     let addedFriend = false;
        //     const now = new Date();
        //     const date = now.toLocaleDateString('en-GB', { day: '2-digit', month: '2-digit', year: '2-digit' });
        //     const time = now.toLocaleTimeString('en-GB', { hour12: false, hour: '2-digit', minute:'2-digit' });
        //     const dateTime = `${date} ${time}`;
        //     Object.keys(currentSiteUsers).forEach((user) => { //check if the current user is in the listed users
        //         if (user === currentAdd) {
        //             setContacts([...getContacts, { identifier: user, displayName: currentSiteUsers[user].displayName ,lastSeenDate: dateTime, imgSrc: currentSiteUsers[user].img }]);
        //             let chatContactsTemp = currentChatContacts;
        //             chatContactsTemp[user] = [...currentChatContacts[user], { identifier: currentUser, displayName: currentSiteUsers[currentUser].displayName, lastSeenDate: dateTime, imgSrc: currentSiteUsers[currentUser].img }];
        //             chatContactsTemp[currentUser] = [...currentChatContacts[currentUser], { identifier: user, displayName: currentSiteUsers[user].displayName ,lastSeenDate: dateTime, imgSrc: currentSiteUsers[user].img }];
        //             setCurrentChatContacts(chatContactsTemp);
        //             addedFriend = true;
        //         }
        //     })
        //     if (!addedFriend) {
        //         let newList = currentSiteUsers;
        //         newList[currentAdd] = { displayName: currentAdd ,img: 'https://m.media-amazon.com/images/I/61i7zA27EzL.jpg', pass: '123456789123' };
        //         setCurrentSiteUsers(newList);
        //         //set the current time
        //         // as requested, adding a user that does not exists opens a chat. In order to open a new chat - creating a user and adding chat
        //         setContacts([...getContacts, { identifier: currentAdd, displayName: currentAdd ,lastSeenDate: dateTime, imgSrc: currentSiteUsers[currentAdd].img }]);
        //         let chatContactsTemp = currentChatContacts;
        //         chatContactsTemp[currentAdd] = [{ identifier: currentUser, displayName: currentSiteUsers[currentUser].displayName ,imgSrc: currentSiteUsers[currentUser].img }];
        //         chatContactsTemp[currentUser] = [...chatContactsTemp[currentUser], { identifier: currentAdd, 
        //             displayName: currentAdd ,lastSeenDate: dateTime , imgSrc: currentSiteUsers[currentAdd].img }];
        //         setCurrentChatContacts(chatContactsTemp);
    //     //     }
    //     }
    //     //clear text
    //     newContact.current.value = "";
    // }

    return (
        <div className="modal" id="AddContactModal" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <form method="post" onSubmit={addContact}>
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h1 className="modal-title fs-5" id="exampleModalLabel">Add new contact</h1>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div className="modal-body">
                            <input type="text" ref={newContact} className="form-control" placeholder="Contact's Identifier" aria-label="Contact's Identifier" aria-describedby="basic-addon2"></input>
                        </div>
                        <div className="modal-footer">
                            <button type="submit" className="btn btn-success">Add Contact
                                <i className="bi bi-person-plus-fill"></i>
                            </button>
                        
                { doesntExists &&(
                    <div id="notValidMessage" className="form-text text-danger" role="alert">
                    User does not exist
                    </div>
                    )}
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default AddFriendModal;