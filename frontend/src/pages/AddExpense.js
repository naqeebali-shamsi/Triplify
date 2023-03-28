import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";
import Select from 'react-select'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { regular, solid } from '@fortawesome/fontawesome-svg-core/import.macro';
import { Menu } from "@material-ui/core";
import { context } from "../Store";
import { BACKEND_URL } from "../Constants";

function AddExpense() {

    let [amount, updateAmount] = useState("");
    let [date, updateDate] = useState("");
    let [description, updateDescription] = useState("");
    let [currency, updateCurrency] = useState("CAD");
    let [paidBy, updatePaidBy] = useState();
    let [paidFor, updatePaidFrom] = useState();
    const navigate = useNavigate();
    const [state,] = useContext(context);
    let [open, setOpen] = useState(false);
    let [userOptions, setUserOptions] = useState(false);
    const options = [
        { value: 'CAD', label: 'CAD' },
        { value: 'INR', label: 'INR' }
    ]

    useEffect(() => {
        const updateOptions = async () => {
            let newOptions = [];
            await state.members.forEach(member => {
                if (newOptions.findIndex(user => user.value === member.username) === -1) {
                    newOptions.push({ value: member.username, label: member.username });
                }
            });
            await setUserOptions(newOptions);
        }
        updateOptions();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const addExpense = async () => {
        const usernamelist = await paidFor.map(user => user.value).join(',');
        await fetch(BACKEND_URL + 'api/v1/users/addexpenses?description=' + description + '&amount=' + amount + '&currency=' + currency.value + '&usernamelist=' + usernamelist + '&paidbyusername=' + paidBy.value + '&groupid=' + state.group.id + '&dateadded=' + date, {
            method: 'POST'
        })
            .then(data => {
                console.log(data);
                navigate('/group-detail')
            })
            .catch(err => {
                console.log(err);
            })
    }

    return (
        <>
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
            <div style={{ display: 'flex', justifyContent: 'center' }}>
                <div style={{ marginTop: '40px', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                    <input className="add-group-name" placeholder='Date(dd-mm-yyyy)' value={date} onChange={e => updateDate(e.target.value)} />
                    <input className="add-group-name" placeholder='Description' value={description} onChange={e => updateDescription(e.target.value)} />
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <Select options={options} className="expense-select" defaultValue="CAD" onChange={(value) => updateCurrency(value)} />
                        <input className="add-expense-amount" placeholder='Amount' value={amount} onChange={e => updateAmount(e.target.value)} />
                    </div>
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <span style={{ marginRight: '10px', marginTop: '10px' }}>Paid by </span>
                        <Select options={userOptions} className="expense-select" onChange={(value) => updatePaidBy(value)} />
                        <span style={{ marginRight: '10px', marginTop: '10px' }}> and split between </span>
                        <Select isMulti options={userOptions} className="expense-select" onChange={(value) => updatePaidFrom(value)} />
                    </div>
                    <div className="add-group-btn" onClick={() => addExpense()}>
                        Submit
                    </div>
                </div>
            </div>
            {
                open
                    ?
                    <Menu toggleOpen={setOpen} />
                    :
                    <></>
            }
        </>
    );
}

export default AddExpense;
