import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";
import { context } from "../Store";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { regular, solid } from '@fortawesome/fontawesome-svg-core/import.macro';
import Menu from "../components/Menu";
import { BACKEND_URL } from "../Constants";

function SettleSummary() {

    const currencySign = {
        'CAD': 'CA $',
        'INR': 'Rs.',
    };

    const [open, setOpen] = useState(false);
    const navigate = useNavigate();
    const [state,] = useContext(context);
    let [settlers, updateSettlers] = useState([])

    useEffect(() => {
        const fetchData = async () => {
            let settlersMap = {};
            await fetch(BACKEND_URL + 'api/v1/users/settleExpense?username=' + state.username + '&groupid=' + state.group.id, {
                method: 'GET'
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    settlersMap = data;
                })
            let settlersArray = [];
            const keys = Object.keys(settlersMap);
            const values = Object.values(settlersMap);
            for (let i = 0; i < keys.length; i++) {
                if (keys[i])
                    await settlersArray.push({
                        name: keys[i],
                        amount: values[i]
                    });
            }
            await updateSettlers(settlersArray);
        }
        fetchData();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const renderSettlers = () => {
        const renderedSettlers = settlers.map((item, index) => {
            return (
                <div className="settler-item" key={index} onClick={() => {
                    navigate('/settle-up', { state: { settleWith: item.name, amount: item.amount.toFixed(2) } });
                }}>
                    <div className="settler-item-name">
                        {item.name}
                    </div>
                    you {item.amount < 0 ? 'owe' : 'are owed'} {currencySign["CAD"]}<span style={item.amount < 0 ? { color: "#FF0505" } : { color: "#14C110", marginRight: '10px' }}>{item.amount > 0 ? item.amount : item.amount * -1}    </span> {' >> '}<span style={{ fontSize: '13px', fontStyle: 'italic' }}>   {'('} select to pay {')'}</span>
                </div>
            );
        });
        return renderedSettlers;
    }

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
            {
                settlers.length === 0
                    ?
                    <div className="group-no-member">
                        Nothing to settle.
                    </div>
                    :
                    <div style={{ overflowY: 'scroll', height: '65vh' }}>
                        {renderSettlers()}
                    </div>
            }
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

export default SettleSummary;
