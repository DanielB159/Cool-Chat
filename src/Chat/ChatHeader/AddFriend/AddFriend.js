import './AddFriend.css'
function AddFriend() {
    return (
        <button type="button" className="btn btn-success col-12 col-xs-2 col-sm-2 col-md-2 col-lg-2 col-xl-1 col-xxl-1" data-bs-toggle="modal" data-bs-target="#AddContactModal" id="addContactButton">
            <h1><i className="bi bi-person-plus-fill"></i></h1>
        </button>
    );
}

export default AddFriend;