import { useRef, useEffect } from 'react';

function DeleteChatModal({setContacts, setActiveChatter, activeChatterChatID, setActiveChatterChatID, JWTtoken, socket}) {

    // define a Hook to the identifier input
    const newContact = useRef(null);


    // this function deletes this contact
    const deleteContact = async (e) => {
        try {
            let res = await fetch("http://localhost:5000/api/Chats/" + activeChatterChatID, {
                method: 'delete',
                headers: {
                    "Content-Type": "application/json", // the data (username/password/displayName/img) is in the form of a JSON object
                    authorization: "bearer " + JWTtoken // attach the token
                }
            })
            
            if (res.status === 204) {
                //update contacts
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
                
                setActiveChatterChatID(-1); // no chatter
                setActiveChatter("");       // no chatter, THIS CLOSES THE SCRIPT
            } else {
                console.log("Error: contact deletion failed");
            }
        } catch (e) {
            // do nothing
        }
    }

    useEffect(() => {
        socket.off('updateDeleted');
        socket.on('updateDeleted', (id) => {
            if (activeChatterChatID == id) {
                setActiveChatterChatID(-1); // no chatter
                //setActiveChatter("");       // no chatter, THIS CLOSES THE SCRIPT
            }
        });
    }, [socket, activeChatterChatID]);

    return (
        <div className="modal" id="DeleteContactModal" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalLabel">Delete Contact</h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        Are you sure you would like to <b>delete this contact</b>?
                    </div>
                    <div className="modal-footer">
                        <button type="button" onClick={deleteContact} data-bs-dismiss="modal" className="btn btn-danger">Delete Contact
                            <i className="bi bi-person-x-fill"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default DeleteChatModal;