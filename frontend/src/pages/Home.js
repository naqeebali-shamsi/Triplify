import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";
import { context } from "../Store";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { regular } from '@fortawesome/fontawesome-svg-core/import.macro';
import { BACKEND_URL } from "../Constants";

function Home() {

    const navigate = useNavigate();
    let [groups, updateGroups] = useState([]);
    const [state, dispatch] = useContext(context);

    useEffect(() => {
        fetch(BACKEND_URL + 'groups/groupsByUsername?username=' + state.username, {
            method: 'POST',
            headers: {
                accept: 'application/json',
            },
        })
            .then((response) => response.json())
            .then(async (data) => {
                console.log('Success:', data);
                await updateGroups(data.groupDetails);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    useEffect(() => {
        const success = async (position) => {
            const latitude = position.coords.latitude;
            const longitude = position.coords.longitude;
            console.log(latitude, longitude);
            await dispatch({
                type: 'location_updated',
                payload: {
                    latitude: position.coords.latitude,
                    longitude: position.coords.longitude
                }
            });
            const formData = new FormData();
            formData.append('username', state.username);
            formData.append('latitude', latitude);
            formData.append('longitude', longitude);
            fetch(BACKEND_URL + 'save/location', {
                method: 'PATCH',
                headers: {
                    accept: 'application/json',
                },
                body: formData
            })
                .then((response) => response.json())
                .then(async (data) => {
                    console.log('Success:', data);
                }
                )
                .catch((error) => {
                    console.error('Error:', error);
                });
        }
        const fail = (err) => {
            console.log(err);
        }
        if (!state.isRunning) {
            dispatch({
                type: 'started_running',
                payload: {
                    isRunning: true
                }
            });
            navigator.geolocation.getCurrentPosition(success, fail);
            // eslint-disable-next-line
            const interval = setInterval(async () => {
                if (navigator.geolocation) {
                    // Call getCurrentPosition with success and failure callbacks
                    await navigator.geolocation.getCurrentPosition(success, fail);
                }
                else {
                    alert("Sorry, your browser does not support geolocation services.");
                }
            }, 30000);
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const renderGroups = () => {
        const renderedGroups = groups.map((group) => {
            return (
                <div key={group.id} className="home-tile" onClick={async () => {
                    console.log(group);
                    await dispatch({
                        type: 'open_group',
                        payload: {
                            group: group
                        }
                    });
                    await navigate('/group-detail');
                }} >
                    <div className='home-name'>{group.groupName}</div>
                    <div className='home-destination'>{group.destination}</div>
                    <div className='home-start'>{group.tripStartDate} - {group.tripEndDate}</div>
                    <div className="home-expense">
                        <div className="home-positive">$232</div>
                        <div className="home-negative">$50</div>
                    </div>
                </div>
            )
        });
        return renderedGroups;
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
            <div>
                {
                    groups.length === 0
                        ?
                        <div className="home-no-group">
                            You don’t have any trip groups right now. Please create one or join from explore.
                        </div>
                        :
                        <div style={{ display: 'flex', justifyContent: 'flex-start', marginTop: '15px', marginBottom: '30px', flexWrap: 'wrap', paddingInline: '20px' }}>
                            {renderGroups()}
                        </div>
                }
            </div>
            <div className="home-add" onClick={() => navigate('/add-group')}>
                <div>
                    +
                </div>
            </div>
        </>
    );
}

export default Home;
