import './Logout.css'
function Logout() {
    return (
        <button type="button" className="btn btn-danger col-12 col-xs-2 col-sm-2 col-md-2 col-lg-2 col-xl-1 col-xxl-1" data-bs-toggle="modal" data-bs-target="#LogoutModal" id="logoutButton">
            <h1><i className="bi bi-box-arrow-right"></i></h1>
        </button>
    );
}

export default Logout;

