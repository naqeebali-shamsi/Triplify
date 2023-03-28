import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { regular } from '@fortawesome/fontawesome-svg-core/import.macro';
import exploreImage from '../assets/Halifax.jpg';
import ImageUpload from "../components/ImageUpload";
import { context } from "../Store";
import { BACKEND_URL } from "../Constants";

function Profile() {

    const navigate = useNavigate();
    const [profileData, updateProfileData] = useState({
        firstName: '',
        lastName: '',
        email: ''
    });
    const [firstName, updateFirstName] = useState();
    const [lastName, updateLastName] = useState();
    const [email, updateEmail] = useState();
    const [profile, updateProfilePic] = useState(exploreImage);
    const [state,] = useContext(context);
    const [data, setData] = useState('');
    const childToParent = (childData) => {
        setData(childData);
    }

    useEffect(() => {
        const formData = new FormData();
        formData.append('username', state.username);
        fetch(BACKEND_URL + 'usersDetails', {
            method: 'POST',
            body: formData
        })
            .then((response) => response.json())
            .then(async (data) => {
                const profileData = data.UserDetails[0];
                console.log('Success:', profileData);
                await updateProfilePic("data:image/png;base64," + profileData.profPicBlob);
                await updateProfileData(profileData);
                await updateFirstName(profileData.firstname);
                await updateLastName(profileData.lastname);
                await updateEmail(profileData.emailAddress);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const updateProfile = () => {
        const formData = new FormData();
        formData.append('username', state.username);
        formData.append('firstname', firstName);
        formData.append('lastname', lastName);
        formData.append('emailAddress', email);
        formData.append('avatar', data);
        fetch(BACKEND_URL + 'updateProfile', {
            method: 'POST',
            body: formData
        })
            .then((response) => response.json())
            .then(async (data) => {
                console.log('Success:', data);
                navigate('/home');
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }

    return (
        <>
            <div className='page-header'>
                <div className="header-logo" onClick={() => {
                    navigate('/home');
                }}>
                    Triplify
                </div>
                <div className="header-options">
                    <div className="header-option" onClick={() => navigate("/explore")}>Explore</div>
                    <div className="header-option" onClick={() => navigate("/posts")}>Posts</div>
                    <div className="header-option">
                        <FontAwesomeIcon icon={regular("user")} style={{ color: '#fff', fontSize: '15px' }} onClick={() => navigate("/profile")} />
                    </div>
                </div>
            </div>
            <div style={{ display: "flex", flexDirection: 'column', alignItems: 'center', paddingTop: '100px' }}>
                <img src={profile} alt="halifax" className="profile-image" />
                <ImageUpload childToParent={childToParent} />
                <input className="add-group-name" placeholder='First Name' value={firstName} onChange={e => updateFirstName(e.target.value)} />
                <input className="add-group-name" placeholder='Last Name' value={lastName} onChange={e => updateLastName(e.target.value)} />
                <input className="add-group-name" placeholder='Email' value={email} onChange={e => updateEmail(e.target.value)} />
                <div className="add-group-btn" onClick={async () => {
                    await updateProfile();
                    await updateProfilePic(data);
                }}>
                    Submit
                </div>
                <div className="discard-btn" onClick={() => {
                    updateFirstName(profileData.firstname);
                    updateLastName(profileData.lastname);
                    updateEmail(profileData.emailAddress);
                }}>
                    Discard
                </div>
            </div>
        </>
    );
}

export default Profile;
