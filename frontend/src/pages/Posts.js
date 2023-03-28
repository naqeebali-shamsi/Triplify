import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { regular } from '@fortawesome/fontawesome-svg-core/import.macro';
import { BACKEND_URL } from "../Constants";

function Posts() {

    const navigate = useNavigate();
    let [groups, updateGroups] = useState([]);

    useEffect(() => {
        fetch(BACKEND_URL + 'posts', {
            method: 'GET'
        })
            .then((response) => response.json())
            .then(async (data) => {
                console.log('Success:', data);
                await updateGroups(data);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const renderGroups = () => {
        const renderedGroups = groups.map((group) => {
            return (
                <div key={group.id} className="explore-tile">
                    <img src={"data:image/png;base64," + group.postImage} alt="halifax" className="explore-image" />
                    <div style={{ width: '100%' }}>
                        <div className='explore-name'>
                            <div>{group.destination}</div>
                            <div>{group.postedDate}</div>
                        </div>
                        <div className='explore-description'>{group.details}</div>
                        <div className='explore-author'>- {group.username}</div>
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
                            We don’t have any trip groups right now.
                        </div>
                        :
                        <div style={{ marginTop: '15px', marginBottom: '30px', marginInline: '30px' }}>
                            {renderGroups()}
                        </div>
                }
            </div>
            
            <div className="home-add" onClick={() => navigate('/add-post')}>
                <div>
                    +
                </div>
            </div>
        </>
    );
}

export default Posts;
