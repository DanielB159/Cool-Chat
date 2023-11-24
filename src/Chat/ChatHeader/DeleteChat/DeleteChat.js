import './DeleteChat.css'
import { useEffect, useState } from 'react';

function DeleteChat(activeChatterChatID) {

    const [showButton, setShowButton] = useState(false);

    useEffect(() => {
        if (activeChatterChatID.activeChatterChatID === -1) {
            setShowButton(false);
        } else {
            setShowButton(true);
        }
    }, [activeChatterChatID]);
    
    return (
        <>
            {(showButton === true) && (<button type="button" className="btn btn-secondary col-12 col-xs-2 col-sm-2 col-md-2 col-lg-2 col-xl-1 col-xxl-1" data-bs-toggle="modal" data-bs-target="#DeleteContactModal" id="deleteContactButton">
                <h1><i className="bi bi-person-x-fill"></i></h1>
            </button>)}
        </>
    );
}

export default DeleteChat;