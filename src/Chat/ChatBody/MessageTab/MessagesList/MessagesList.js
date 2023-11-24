import './MessagesList.css';
import Message from './Message/Message';
import { useRef, useEffect, useState } from 'react';
//import messages from '../../../ContactMessages';

function MessagesList({ chatHistory, activeChatterChatID }) {
    
    const [showMessages, setShowMessages] = useState(false);
    // show the messages only if there is a contact selecter
    useEffect(() => {
        if (activeChatterChatID === -1) {
            setShowMessages(false);
        } else {
            setShowMessages(true);
        }
    }, [activeChatterChatID]);
    
    // read from chatHistory
    // set auto scroll down to the latest message when there is a change in messages
    const messagesList = useRef(null);
    useEffect(() => {
        if (showMessages) {
            scrollToBottom();
        }
    }, [chatHistory]);

    // This function scrolls to the bottom of the messages list
    const scrollToBottom = () => {
        messagesList.current.scrollTop = messagesList.current.scrollHeight;
    }

    let msgCount = 0;
    return (
        <>
            {(showMessages === true) && (<div id="messagesList" ref={messagesList}>
                {
                    chatHistory.map(message => (
                        <Message key={msgCount++} who={message.who} text={message.text} time={message.time} />
                    ))
                }
            </div>)}
        </>
    );
}

export default MessagesList;