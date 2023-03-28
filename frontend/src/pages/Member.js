import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";
import { context } from "../Store";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { regular, solid } from '@fortawesome/fontawesome-svg-core/import.macro';
import Menu from "../components/Menu";
import { BACKEND_URL } from "../Constants";

function Member() {

    const navigate = useNavigate();
    const [state,] = useContext(context);
    let [open, setOpen] = useState(false);
    let [name, updateName] = useState("");
    const [openModal, setOpenModal] = useState(false);
    let [members, updateMembers] = useState(state.members);
    const [isAdmin, setIsAdmin] = useState(false);

    useEffect(() => {
        if (state.group.username === state.username) {
            setIsAdmin(true);
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const renderMembers = () => {
        const renderedMembers = members.map((item, index) => {
            return (
                <div className="checklist-item" key={index}>
                    <div className="checklist-item-name">
                        {item.username}
                    </div>
                    {
                        isAdmin && state.username !== item.username ?
                            <div className="checklist-item-checkbox">
                                <FontAwesomeIcon icon={solid("trash")} className="trash-icon" onClick={async () => {
                                    await deleteMember(index, item.username);
                                }} />
                            </div>
                            :
                            <></>
                    }
                </div>
            );
        });
        return renderedMembers;
    }

    const addMember = async () => {
        const formData = new FormData();
        formData.append("username", name);
        await fetch(BACKEND_URL + "groups/" + state.group.id + "/add/member", {
            method: "POST",
            body: formData
        })
            .then(res => res.json())
            .then(async data => {
                console.log(data);
                if (data) {
                    let tempMembers = [...members]
                    let newMember = {
                        username: name,
                        isAdmin: false
                    }
                    await tempMembers.push(newMember);
                    await updateMembers(tempMembers);
                    await setOpenModal(false);
                    await updateName("");
                } else {
                    alert("Member not added. Please try again.");
                }
            })
            .catch(err => {
                console.log(err);
            });
    }

    const deleteMember = async (index, name) => {
        await fetch(BACKEND_URL + "groups/groupMemberDelete?username=" + name + "&group_id=" + state.group.id, {
            method: "POST"
        })
            .then(res => res.json())
            .then(async data => {
                console.log(data);
                if (data) {
                    let newMembers = [...members];
                    await newMembers.splice(index, 1);
                    await updateMembers(newMembers);
                } else {
                    alert("Member not deleted. Please try again.");
                }
            })
            .catch(err => {
                console.log(err);
            });
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
            <div>
                {
                    members.length === 0
                        ?
                        <div className="group-no-member">
                            Please add members.
                        </div>
                        :
                        <div style={{ overflowY: 'scroll', height: '65vh' }}>
                            {renderMembers()}
                        </div>
                }
            </div>
            <div className="home-add" onClick={async () => {
                await setOpenModal(true);
            }}>
                <div>
                    +
                </div>
            </div>
            {
                open
                    ?
                    <Menu toggleOpen={setOpen} />
                    :
                    <></>
            }
            {
                openModal
                    ?
                    <div className="modal-bg" onClick={() => {
                        setOpenModal(false);
                    }}>
                        <div className='member-modal' onClick={e => {
                            e.stopPropagation();
                        }}>
                            <div>
                                <input className="add-member-name" placeholder='Email' value={name} onChange={e => updateName(e.target.value)} />
                            </div>
                            <div className="add-group-btn" onClick={async () => {
                                await addMember();
                            }}>
                                Submit
                            </div>
                        </div>
                    </div>
                    :
                    null
            }
        </div>
    );
}

export default Member;
