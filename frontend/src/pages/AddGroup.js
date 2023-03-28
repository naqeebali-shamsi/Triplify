import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { regular } from '@fortawesome/fontawesome-svg-core/import.macro';
import { Switch } from "@mui/material";
import { BACKEND_URL } from "../Constants";
import { context } from "../Store";

function AddGroup() {

    let [name, updateName] = useState("");
    let [description, updateDescription] = useState("");
    let [destination, updateDestination] = useState("");
    let [startDate, updateStartDate] = useState("");
    let [endDate, updateEndDate] = useState("");
    let [state,] = useContext(context);
    let [isPrivate, togglePrivate] = useState(false);
    const navigate = useNavigate();

    const createGroup = () => {
        const formData = new FormData();
        formData.append('groupName', name);
        formData.append('groupStartDate', startDate);
        formData.append('groupEndDate', endDate);
        formData.append('groupDestination', destination);
        formData.append('groupDescription', description);
        formData.append('groupType', isPrivate ? 'Private' : 'Public');
        formData.append('username', state.username);
        console.log(formData);
        fetch(BACKEND_URL + 'groups/createGroup', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(async (data) => {
                console.log(data);
                if (data.SUCCESS) {
                    navigate('/home');
                } else {
                    alert(data.MESSAGE);
                }
            })
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
            <div style={{ display: 'flex', justifyContent: 'center' }}>
                <div style={{ marginTop: '40px', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                    <input className="add-group-name" placeholder='Group Name' value={name} onChange={e => updateName(e.target.value)} />
                    <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                        <input className="add-group-date" placeholder='Trip Start Date(dd-mm-yyyy)' value={startDate} onChange={e => updateStartDate(e.target.value)} />
                        <input className="add-group-date" placeholder='Trip End Date(dd-mm-yyyy)' value={endDate} onChange={e => updateEndDate(e.target.value)} />
                    </div>
                    <input className="add-group-name" placeholder='Description' value={description} onChange={e => updateDescription(e.target.value)} />
                    <input className="add-group-name" placeholder='Destination' value={destination} onChange={e => updateDestination(e.target.value)} />
                    <div>
                        <Switch checked={isPrivate} onChange={(e) => togglePrivate(e.target.checked)} name="Private" /> Private
                    </div>
                    <div className="add-group-btn" onClick={() => createGroup()}>
                        Submit
                    </div>
                </div>
            </div>
        </>
    );
}

export default AddGroup;
