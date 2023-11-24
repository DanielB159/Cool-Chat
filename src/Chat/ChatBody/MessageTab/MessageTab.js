import MessageSend from "./MessageSend/MessageSend";
import MessagesList from "./MessagesList/MessagesList";
import './MessagesTab.css';
import { useEffect, useState } from 'react';

function idcmp(a, b) {
    return a.id - b.id;
}

function parseHistory(resArr, senderName, receiverName) {
    let history = [];
    //sort resArr by id since that is the chronological ordering
    resArr.sort(idcmp);
    resArr.forEach(message => {
        if (message.sender.username === senderName) {
            history.push({
                "who": "sender",
                "text": message.content,
                "time": message.created
            })
        } else if (message.sender.username === receiverName) {
            history.push({
                "who": "receiver",
                "text": message.content,
                "time": message.created
            })
        } else {
            console.log("Error: unrecognized sender username.");
        }
    });
    return history;
}

function MessageTab({ currentUser, activeChatter, activeChatterChatID, JWTtoken, socket, sentMessage, setSentMessage }) {
    // //pass activeChatter so that MessagesList can list the messages, and messageSend can send messages to it
    const [chatHistory, setChatHistory] = useState([]);

    const fetchMessages = async () => {
        if (activeChatterChatID !== -1) {
            try {
                // console.log("the active chatter chat id is:")
                // console.log(activeChatterChatIID);
                let res = await fetch("http://localhost:5000/api/Chats/" + activeChatterChatID + "/Messages", {
                    method: "get",
                    headers: {
                        "Content-Type": "application/json",
                        authorization: "bearer " + JWTtoken // attach the token
                    }
                })
                let resJson = await res.json();
                setChatHistory(parseHistory(resJson, currentUser.username, activeChatter));
            } catch (e) {
                // set the default image and displayName
                //setImg("https://img.freepik.com/free-icon/user_318-804790.jpg");
                setChatHistory([]);
            }
        }
    }


    useEffect(() => {
        socket.off('updateMessages');
        socket.on('updateMessages', (msg) => {
            if (msg == activeChatterChatID) {
                fetchMessages();
            }
        });
    }, [socket, activeChatterChatID, JWTtoken, activeChatter])


    useEffect(() => {
        // console.log("now using effect")
        if (activeChatterChatID !== -1) {
            setChatHistory("loading...");
            fetchMessages();
        }
    }, [activeChatterChatID])

    return (
        <div className="col-12 col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-7 col-xxl-7 border-start" id="chat">
            {chatHistory === "loading..." ? "loading..." : <MessagesList chatHistory={chatHistory} activeChatterChatID={activeChatterChatID} />}
            {activeChatter !== {} ? <MessageSend chatHistory={chatHistory} setChatHistory={setChatHistory}
                activeChatterChatID={activeChatterChatID} JWTtoken={JWTtoken}
                updateDetails={fetchMessages} sentMessage={sentMessage} setSentMessage={setSentMessage} /> : null}
        </div>
    );
}

export default MessageTab;