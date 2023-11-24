import './Contact.css'

// return <Contact identifier={contact.user.username} displayName={contact.user.displayName}
// LastMessageSent={contact.lastMessage} imgSrc={contact.user.profilePic} activeChatter = {activeChatter}
//  setActiveChatter = {setActiveChatter} JWTtoken={JWTtoken} setActiveChatterChatID={setActiveChatterChatID} key={key}/>


function Contact({ identifier, displayName, LastMessageSent, imgSrc, activeChatter, setActiveChatter,
    JWTtoken, setActiveChatterChatID, activeChatterChatID, userChatter, lastMsgTime }) {

    // format the time to be correct
    const date = new Date(lastMsgTime);
    const day = date.getDate().toString().padStart(2, '0'); // Get the day and pad with leading zero if necessary
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Get the month (months are zero-based) and pad with leading zero if necessary
    const year = date.getFullYear().toString().slice(-2); // Get the year and take the last two digits
    const hours = date.getHours().toString().padStart(2, '0'); // Get the hours and pad with leading zero if necessary
    const minutes = date.getMinutes().toString().padStart(2, '0'); // Get the minutes and pad with leading zero if necessary
    const formattedTime = lastMsgTime !== "" ? `${day}/${month}/${year} ${hours}:${minutes}` : "";

    let styles;

    // identify if the chat is the current main chat or not
    if (identifier === activeChatterChatID) {
        styles = {
            'borderTopLeftRadius': 0,
            'borderTopRightRadius': 0,
            'borderBottomRightRadius': 0,
            'borderBottomLeftRadius': 0,
            'backgroundColor': 'lightgray'
        }
    } else {
        styles = {
            'borderTopLeftRadius': 0,
            'borderTopRightRadius': 0,
            'borderBottomRightRadius': 0,
            'borderBottomLeftRadius': 0,
            'backgroundColor': 'white'
        }
    }


    // const contactChat = useRef(null);
    // this function changes the current chat to be the one clicked
    const changeChat = async () => {
        setActiveChatter(userChatter);
        setActiveChatterChatID(identifier);
    }

    return (
        <li className="list-group-item d-flex align-items-center" onClick={() => { changeChat() }} style={styles}>
            <img id="profilePic" className="border rounded-4 me-2" src={imgSrc} alt="Friend" width="35" height="35" />
            {displayName}
            <span className="lastMsgTime">{formattedTime}</span>
            <span className="lastMessageSent">{LastMessageSent !== null ? LastMessageSent.content : ""}</span>
        </li>
    );
}

export default Contact;