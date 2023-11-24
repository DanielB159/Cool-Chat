import './Message.css';

function Message({ who, text, time }) {
    let arrow = who === 'sender' ? "arrow-right" : "arrow-left";

    // format the time to be correct
    const date = new Date(time);
    const hours = date.getHours().toString().padStart(2, '0'); // Get the hours and pad with leading zero if necessary
    const minutes = date.getMinutes().toString().padStart(2, '0'); // Get the minutes and pad with leading zero if necessary
    const formattedTime = `${hours}:${minutes}`;
    let timeSent = formattedTime;

    return (
        <>
            {(who === 'sender') && (<p className={who}><span className="timeSent">{timeSent}</span> {' '} <span className={arrow}></span>{text} </p>)}
            {(who === 'receiver') && (<p className={who}><span className={arrow}></span>{text} {' '} <span className="timeSent">{timeSent}</span></p>)}
        </>
    );
}

export default Message;