import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";
import { context } from "../Store";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { regular, solid } from '@fortawesome/fontawesome-svg-core/import.macro';
import Menu from "../components/Menu";
import { BACKEND_URL } from "../Constants";

function GroupDetail() {

    const currencySign = {
        'CAD': 'CA $',
        'INR': 'Rs.',
    };

    const navigate = useNavigate();
    const [total, setTotal] = useState(0);
    const [state, dispatch] = useContext(context);

    let [expenses, updateExpenses] = useState([
        {
            id: 1,
            transaction_id: 1,
            description: 'Lunch',
            amount: 20,
            currency: 'CAD',
            fromuserid: 'Jaimin',
            touserid: 'Taksh',
            fulllAmount: 100,
            groupid: state.group.id,
            date: '2 Dec'
        },
        {
            id: 2,
            transaction_id: 2,
            description: 'Dinner',
            amount: -20,
            currency: 'CAD',
            fromuserid: 'Taksh',
            touserid: 'Jaimin',
            fulllAmount: 83,
            groupid: state.group.id,
            date: '1 Dec'
        },
        {
            id: 3,
            transaction_id: 1,
            description: 'Lunch',
            amount: 20,
            currency: 'CAD',
            fromuserid: 'Jaimin',
            touserid: 'Taksh',
            fulllAmount: 100,
            groupid: state.group.id,
            date: '2 Dec'
        },
        {
            id: 4,
            transaction_id: 2,
            description: 'Dinner',
            amount: -20,
            currency: 'CAD',
            fromuserid: 'Taksh',
            touserid: 'Jaimin',
            fulllAmount: 83,
            groupid: state.group.id,
            date: '1 Dec'
        },
        {
            id: 5,
            transaction_id: 1,
            description: 'Lunch',
            amount: 20,
            currency: 'CAD',
            fromuserid: 'Jaimin',
            touserid: 'Taksh',
            fulllAmount: 100,
            groupid: state.group.id,
            date: '2 Dec'
        },
        {
            id: 6,
            transaction_id: 2,
            description: 'Dinner',
            amount: -20,
            currency: 'CAD',
            fromuserid: 'Taksh',
            touserid: 'Jaimin',
            fulllAmount: 83,
            groupid: state.group.id,
            date: '1 Dec'
        }
    ]);
    let [open, setOpen] = useState(false);

    useEffect(() => {
        fetch(BACKEND_URL + 'api/v1/users/userexpenses?username=' + state.username + "&groupid=" + state.group.id, {
            method: 'GET'
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if (data)
                    updateExpenses(data);
            })
            .catch((error) => {
                console.error('Error:', error);
            });

        fetch(BACKEND_URL + 'api/v1/users/calculatetotal?username=' + state.username + "&groupid=" + state.group.id, {
            method: 'GET'
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if (data)
                    console.log(data);
                setTotal(+data);
            })
            .catch((error) => {
                console.error('Error:', error);
            });

        fetch(BACKEND_URL + 'groups/' + state.group.id + '/members', {
            method: 'GET'
        })
            .then(response => response.json())
            .then(async data => {
                console.log(data)
                if (data.members) {
                    await dispatch({
                        type: 'members_fetched',
                        payload: {
                            members: data.members
                        }
                    });
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    const renderExpenses = () => {
        // eslint-disable-next-line
        const renderedExpenses = expenses.map((expense) => {
            if (expense.toUsername)
            expense.full_amount = expense.full_amount > 0 ? expense.full_amount : -expense.full_amount;
                return (
                    <div key={expense.id} className="expense">
                        <div className="expense-date">
                            {expense.date_added}
                        </div>
                        <div className="expense-name">
                            <div className="expense-description">
                                {expense.description}
                            </div>
                            <div className="expense-details">
                                {expense.fromUsername === state.username ? 'You' : expense.fromUsername} paid {currencySign[expense.currency]}{expense.full_amount} for {expense.toUsername}
                            </div>
                        </div>
                        <div className={expense.fromUsername === state.username ? "expense-amount-positive" : "expense-amount-negative"}>
                            {
                                expense.amount >= 0
                                    ?
                                    <>
                                        {currencySign[expense.currency]}{expense.amount}
                                    </>
                                    :
                                    <>
                                        {currencySign[expense.currency]}{expense.amount * -1}
                                    </>
                            }
                        </div>
                    </div>
                )
        });
        return renderedExpenses;
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
                isNaN(total)
                    ?
                    null
                    :
                    <div className="group-total">
                        you {total > 0 ? 'are owed' : 'owe'} {currencySign["CAD"]}<span style={total > 0 ? { color: "#14C110" } : { color: "#FF0505" }}>{total > 0 ? total : total * -1}</span>
                    </div>
            }
            <div>
                {
                    expenses.length === 0
                        ?
                        state.members.length === 0
                            ?
                            <div className="group-no-member">
                                Please add other group members.
                            </div>
                            :
                            <div className="group-no-member">
                                You don’t have any expenses right now.
                            </div>
                        :
                        <div style={{ overflowY: 'scroll', height: '65vh' }}>
                            {renderExpenses()}
                        </div>
                }
            </div>
            <div className="add-expense">
                {
                    state.members.length !== 0 && expenses.length !== 0
                        ?
                        <div className="add-expense-btn">
                            <div onClick={() => navigate('/settle-summary')}>
                                Settle Up
                            </div>
                        </div>
                        : null
                }
                {
                    state.members.length !== 0
                        ?
                        <div className="add-expense-btn">
                            <div onClick={() => navigate('/add-expense')}>
                                Add Expense
                            </div>
                        </div>
                        :
                        null
                }
            </div>
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

export default GroupDetail;
