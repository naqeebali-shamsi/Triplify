import { useNavigate } from "react-router-dom";
import "../App.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { solid } from '@fortawesome/fontawesome-svg-core/import.macro';

function Menu({ toggleOpen }) {

    const navigate = useNavigate();

    return (
        <div className="menu-body">
            <FontAwesomeIcon icon={solid("xmark")} style={{ color: '#1c1c1c', fontSize: '15px', cursor: 'pointer', position: 'absolute', right: '15px', top: '15px' }} onClick={() => toggleOpen(false)} />
            <div className="menu-item-1" onClick={() => navigate("/group-detail")}>Group</div>
            <div className="menu-item" onClick={() => navigate("/members")}>Members</div>
            <div className="menu-item" onClick={() => navigate("/checklist")}>Checklist</div>
            <div className="menu-item" onClick={() => navigate("/live-tracking")}>Live Tracking</div>
        </div >
    );
}

export default Menu;
