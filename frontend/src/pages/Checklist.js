import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";
import { context } from "../Store";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { regular, solid } from '@fortawesome/fontawesome-svg-core/import.macro';
import Menu from "../components/Menu";
import { BACKEND_URL } from "../Constants";

function Checklist() {

    const navigate = useNavigate();
    const [state,] = useContext(context);
    let [open, setOpen] = useState(false);
    let [name, updateName] = useState("");
    const [openModal, setOpenModal] = useState(false);
    let [checklist, updateChecklist] = useState([]);

    useEffect(() => {
        fetch(BACKEND_URL + "api/v1/groups/showchecklist?group_id=" + state.group.id, {
            method: "GET"
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
                updateChecklist(data);
            })
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const addChecklistItem = () => {
        fetch(BACKEND_URL + "api/v1/groups/addchecklist?group_id=" + state.group.id + "&checklist_name=" + name + "&checklisted=false", {
            method: "POST"
        })
            .then(res => res.json())
            .then(async data => {
                console.log(data);
                let tempChecklist = [...checklist]
                let newChecklist = {
                    checklist_name: name,
                    checklisted: false
                };
                await tempChecklist.push(newChecklist);
                await updateChecklist(tempChecklist);
                await setOpenModal(false);
            })
            .catch(err => {
                console.log(err);
            })
    }

    const toggleChecklistItem = (index, name, checked) => {
        fetch(BACKEND_URL + "api/v1/groups/addchecklist?group_id=" + state.group.id + "&checklist_name=" + name + "&checklisted=" + checked, {
            method: "PATCH"
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
                let newChecklist = [...checklist];
                newChecklist[index].checklisted = checked;
                console.log(newChecklist);
                updateChecklist(newChecklist);
            })
            .catch(err => {
                console.log(err);
            })
    }

    const renderChecklist = () => {
        const renderedChecklist = checklist.map((item, index) => {
            return (
                <div className="checklist-item" key={index}>
                    <div className="checklist-item-name">
                        {item.checklist_name}
                    </div>
                    <div className="checklist-item-checkbox">
                        <input type="checkbox" checked={item.checklisted} onChange={() => {
                            toggleChecklistItem(index, item.checklist_name, !item.checklisted);
                        }} />
                        {/* <FontAwesomeIcon icon={solid("trash")} className="trash-icon" onClick={async () => {
                            let newChecklist = [...checklist];
                            await newChecklist.splice(index, 1);
                            await updateChecklist(newChecklist);
                        }} /> */}
                    </div>
                </div>
            );
        });
        return renderedChecklist;
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
                    checklist.length === 0
                        ?
                        <div className="group-no-member">
                            Please add checklists.
                        </div>
                        :
                        <div style={{ overflowY: 'scroll', height: '65vh' }}>
                            {renderChecklist()}
                        </div>
                }
            </div>
            <div className="home-add" onClick={async () => {
                await updateName("");
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
                                <input className="add-member-name" placeholder='Item name' value={name} onChange={e => updateName(e.target.value)} />
                            </div>
                            <div className="add-group-btn" onClick={async () => {
                                await addChecklistItem();
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

export default Checklist;
