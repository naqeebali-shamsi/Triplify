import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { regular } from '@fortawesome/fontawesome-svg-core/import.macro';
import exploreImage from '../assets/Halifax.jpg';
import { BACKEND_URL } from "../Constants";
import { context } from "../Store";

function Explore() {

    const navigate = useNavigate();
    const [search, setSearch] = useState('');
    const [state,] = useContext(context);
    const [searchDone, setSearchDone] = useState(false);
    let [groups, updateGroups] = useState([]);

    const renderGroups = () => {
        const renderedGroups = groups.map((group) => {
            return (
                <div key={group.groupId} className="explore-tile">
                    <img src={exploreImage} alt="halifax" className="explore-image" />
                    <div style={{ width: '100%' }}>
                        <div className='explore-name'>{group.groupName}</div>
                        <div className='explore-description'>{group.placeDescription}</div>
                        <div className="join-group-btn" onClick={() => joinGroup(group.groupId)}>
                            Join Group
                        </div>
                    </div>
                </div>
            )
        });
        return renderedGroups;
    }

    const doSearch = () => {
        const formData = new FormData();
        formData.append('location', search);
        fetch(BACKEND_URL + 'search', {
            method: 'POST',
            body: formData
        })
            .then((response) => response.json())
            .then(async (data) => {
                console.log('Success:', data);
                await updateGroups(data);
                await setSearchDone(true);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }

    const joinGroup = (group_id) => {
        const formData = new FormData();
        formData.append("username", state.username);
        fetch(BACKEND_URL + "groups/" + group_id + "/add/member", {
            method: "POST",
            body: formData
        })
            .then(res => res.json())
            .then(async data => {
                console.log(data);
                navigate("/home");
            })
            .catch(err => {
                console.log(err);
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
            <div>
                <div>
                    <div style={{ marginTop: '15px', marginBottom: '30px', marginInline: '30px' }}>
                        <div style={{ display: 'flex' }}>
                            <input type="text" placeholder="Search" className="search-input" value={search} onChange={(e) => setSearch(e.target.value)} />
                            <div className="explore-search" onClick={() => doSearch()}>search</div>
                        </div>
                    </div>
                    {
                        searchDone
                            ?
                            groups.length === 0
                                ?
                                <div className="home-no-group">
                                    We don’t have any related trip groups right now.
                                </div>
                                :
                                <div style={{ marginTop: '15px', marginBottom: '30px', marginInline: '30px' }}>
                                    {renderGroups()}
                                </div>
                            :
                            <div className="home-no-group">
                                Please type in search to find related groups.
                            </div>
                    }
                </div>
            </div>
        </>
    );
}

export default Explore;
