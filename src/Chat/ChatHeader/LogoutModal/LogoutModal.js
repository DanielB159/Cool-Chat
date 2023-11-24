import { useNavigate } from "react-router-dom";

function LogoutModal({currentUser, setCurrentUser, setJWTtoken, socket}) {
    const navigate = useNavigate();
    function handleLogout(e) {
        //update websocket we logged out
        socket.emit('logout', "");

        setJWTtoken("");
        setCurrentUser(null);
        navigate('/');
    }

    return (
        <div className="modal" id="LogoutModal" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalLabel">Log Out</h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        Are you sure you would like to <b>log out</b>?
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal" onClick={handleLogout}>Log Out 
                            <i className="bi bi-box-arrow-right"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default LogoutModal;