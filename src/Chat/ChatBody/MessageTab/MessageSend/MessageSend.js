import './MessageSend.css';
import {useRef, useEffect} from 'react';

function MessageSend({chatHistory, setChatHistory, activeChatterChatID, JWTtoken, updateDetails, sentMessage, setSentMessage}) {
    const inputRef = useRef(null);

    // setting the input box to be available only of there is a chat open
    useEffect(() => {
        if (activeChatterChatID == -1) {
            inputRef.current.disabled = true;
        } else {
            inputRef.current.disabled = false;
        }
      }, [activeChatterChatID]);


    function handleSubmit(e) {
        e.preventDefault();

        const form = e.target;
        const formData = new FormData(form);
        
        //turn into json
        const formJson = Object.fromEntries(formData.entries());
        
        inputRef.current.value = "";

        if (formJson.sendMessageInput != "") {
            sendMessage(formJson.sendMessageInput);
            
        }
    }

    const sendMessage = async (message) => {
        if (activeChatterChatID !== -1) {
            try {
                //send post to server
                let msg = {
                    "msg": message
                }

                let res = await fetch("http://localhost:5000/api/Chats/" + activeChatterChatID + "/Messages", {
                    method: "post",
                    headers: {
                        "Content-Type": "application/json",
                        authorization: "bearer " + JWTtoken // attach the token
                    },
                    body: JSON.stringify(msg)
                })
                let resJson = await res.json(); //.text?
                //console.log("send status: " + res.status);
                //FORCE UPDATE
                updateDetails();
                let cond = sentMessage;
                cond = !cond;
                setSentMessage(cond);

            } catch (e) {
                // do nothing
            }
        }
    }

    return (
        <form method="post" onSubmit={handleSubmit}>
            <div id="messageSend">
                <div className="input-group mb-3">
                    <input type="text" ref={inputRef} autoComplete="off" name="sendMessageInput" id="sendMessageBox" placeholder="Write message here" aria-label="Write message here" aria-describedby="basic-addon2"></input>
                    <button type="submit" className="btn btn-secondary input-group-text" id="basic-addon2">
                        Send
                        <i className="bi bi-send"></i>
                    </button>
                </div>
            </div>
        </form>
    );
}

export default MessageSend;