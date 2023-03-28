import { useContext, useEffect, useState } from 'react';
import '../App.css';
import { useLocation, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { regular, solid } from '@fortawesome/fontawesome-svg-core/import.macro';
import { PayPalScriptProvider, PayPalButtons } from "@paypal/react-paypal-js";
import Menu from "../components/Menu";
import { BACKEND_URL, CLIENT_ID } from '../Constants';
import { context } from '../Store';

function SettleUp() {
    const location = useLocation();
    let { settleWith, amount } = location.state;

    const [open, setOpen] = useState(false);
    let navigate = useNavigate();
    const [state,] = useContext(context);
    const [success, setSuccess] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [orderID, setOrderID] = useState(null);

    // reference from https://www.unimedia.tech/paypal-checkout-integration-with-react/
    // creates a paypal order
    const createOrder = (data, actions) => {
        return actions.order
            .create({
                purchase_units: [
                    {
                        description: "Payment to " + settleWith,
                        amount: {
                            currency_code: "USD",
                            value: amount < 0 ? -amount : amount,
                        },
                    },
                ],
                application_context: {
                    shipping_preference: "NO_SHIPPING",
                },
            })
            .then((orderID) => {
                setOrderID(orderID);
                return orderID;
            });
    };

    // check Approval
    const onApprove = (data, actions) => {
        return actions.order.capture().then(function (details) {
            const { payer } = details;
            console.log(payer);
            setSuccess(true);
        });
    };

    //capture likely error
    const onError = (data, actions) => {
        setErrorMessage("An Error occured with your payment ");
        console.log(data);
    };

    useEffect(() => {
        // eslint-disable-next-line
        amount = amount < 0 ? amount * -1 : amount;
        if (success) {
            alert("Payment successful!!");
            fetch(BACKEND_URL + 'api/v1/users/settleexpenses?amount=' + amount + '&fromusername=' + state.username + '&tousername=' + settleWith + '&groupid=' + state.group.id, {
                method: 'POST'
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    navigate('/group-detail');
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        }
    },
    // eslint-disable-next-line react-hooks/exhaustive-deps
        [success]
    );

    console.log(1, orderID);
    console.log(2, success);
    console.log(3, errorMessage);

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
            <div className="login-page">
                <div className='settle-body'>
                    <div className='login-header'>${amount < 0 ? -amount : amount}</div>
                    <div className='login-subheader'>Paying {settleWith}</div>
                    {/* <div className='paypal-btn'>
                        Paypal
                    </div> */}
                    <PayPalScriptProvider options={{ "client-id": CLIENT_ID }}>
                        <PayPalButtons
                            style={{ layout: "vertical" }}
                            createOrder={createOrder}
                            onApprove={onApprove}
                            onError={onError}
                        />
                    </PayPalScriptProvider>
                    <div className='paypal-btn'>Cash Payment</div>
                    <div className='paypal-btn' onClick={() => navigate('/settle-summary')}>Cancel</div>
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

export default SettleUp;
