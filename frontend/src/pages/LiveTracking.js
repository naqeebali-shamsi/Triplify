import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";
import { context } from "../Store";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { regular, solid } from '@fortawesome/fontawesome-svg-core/import.macro';
import Menu from "../components/Menu";
import GroupLocator from "../components/GroupLocator";

function LiveTracking() {

    const navigate = useNavigate();
    const [state,] = useContext(context);
    let [open, setOpen] = useState(false);

    return (
        <div>
            <div className='page-header'>
                <FontAwesomeIcon icon={solid("bars")} className="header-menu-icon" onClick={() => setOpen(true)} />
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
            <div className="group-header">
                <div className="group-title">
                    <div className="group-name">
                        {state.group.groupName}
                    </div>
                    <div className="group-destination">
                        {state.group.destination}
                    </div>
                </div>
                <div className="group-interval">
                    {state.group.tripStartDate} - {state.group.tripEndDate}
                </div>
            </div>
            <GroupLocator />
            {
                open
                    ?
                    <Menu toggleOpen={setOpen} />
                    :
                    <></>
            }
        </div>
    );
}

export default LiveTracking;
