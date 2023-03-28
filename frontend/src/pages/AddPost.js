import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { regular } from '@fortawesome/fontawesome-svg-core/import.macro';
import ImageUpload from "../components/ImageUpload";
import { context } from "../Store";
import { BACKEND_URL } from "../Constants";

function AddPost() {

    const navigate = useNavigate();
    const [destination, updateDestination] = useState();
    const [details, updateDetails] = useState();
    const [state,] = useContext(context);
    const [data, setData] = useState('');
    const childToParent = (childData) => {
        setData(childData);
    }

    const addPost = () => {
        const date = new Date();
        const postedDate = date.getDate() + '-' + (date.getMonth() + 1) + '-' + date.getFullYear();
        const formData = new FormData();
        formData.append('destination', destination);
        formData.append('details', details);
        formData.append('postedDate', postedDate);
        formData.append('username', state.username);
        formData.append('image', data);
        console.log(formData)
        fetch(BACKEND_URL + 'save/post', {
            method: 'POST',
            body: formData
        })
            .then((response) => response.json())
            .then((data) => {
                console.log('Success:', data);
                navigate('/posts');
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
                <ImageUpload childToParent={childToParent} />
                <input className="add-group-name" placeholder='Destination' value={destination} onChange={e => updateDestination(e.target.value)} />
                <input className="add-group-name" placeholder='Details' value={details} onChange={e => updateDetails(e.target.value)} />
                <div className="add-group-btn" onClick={async () => {
                    await addPost();
                }}>
                    Submit
                </div>
            </div>
        </>
    );
}

export default AddPost;
