import './UserDetails.css';

function UserDetails({displayName, img}) {
    return (
        <div className="col-12 col-xs-8 col-sm-8 col-md-8 col-lg-8 col-xl-3 col-xxl-3" id="profileName">
            <img id="profilePic" className="border rounded-4 me-2" src={img !== null ? img : "https://m.media-amazon.com/images/I/61i7zA27EzL.jpg"} alt="add Friend" width="35" height="35" />
            {displayName}
        </div>
    );
}

export default UserDetails;