
import { useEffect, useState } from 'react';
import './ContactDetails.css';

function ContactDetails({JWTtoken, activeChatterChatID }) {
    const [img, setImg] = useState(null);
    const [displayName, setDisplayName] = useState("");

    const getContactDetails = async () => {
        if (activeChatterChatID !== -1) {
            try {
                let res = await fetch("http://localhost:5000/api/Chats/" + activeChatterChatID, {
                    method: "get",
                    headers: {
                        "Content-Type": "application/json",
                        authorization: "bearer " + JWTtoken // attach the token
                    }
                })
                // set the correct user details
                let resJson = await res.json();
                //the second user is the other user!
                setImg(resJson.users[1].profilePic);
                setDisplayName(resJson.users[1].displayName);
            } catch (e) {
                // set the default image and displayName
                setImg("https://img.freepik.com/free-icon/user_318-804790.jpg");
                setDisplayName("");
            }
        } else {
            setImg("https://img.freepik.com/free-icon/user_318-804790.jpg");
            setDisplayName("");
        }
    }

    useEffect(() => {
        getContactDetails();
    }, [activeChatterChatID])

    return (
        <>
            <div className="col-11 col-xs-0 col-sm-0 col-md-0 col-lg-0 col-xl-6 col-xxl-6 border-start contactName">
                {img !== null ? <img id="profilePic" className="border rounded-4 me-3" src={img} alt="add Friend" width="35" height="35" /> : null}
                {displayName}
            </div>
        </>
    );
}


export default ContactDetails;