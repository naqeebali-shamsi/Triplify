import { useContext, useState } from 'react';
import './App.css';
import { BACKEND_URL } from './Constants';
import { useNavigate } from 'react-router-dom';
import ImageUpload from './components/ImageUpload';
import dayjs from 'dayjs';
import { context } from './Store';

function App() {

  const [alreadyRegistered, updateAuthType] = useState(true);
  const [firstname, updateFirstName] = useState("");
  const [lastname, updateLastName] = useState("");
  const [email, updateEmail] = useState("");
  const [username, updateUsername] = useState("");
  const [password, updatePassword] = useState("");
  const [dob,] = useState(dayjs('2000-08-18T21:11:54'));
  let navigate = useNavigate();
  const [data, setData] = useState('');
  const [, dispatch] = useContext(context);
  const childToParent = (childData) => {
    setData(childData);
  }


  const onLogin = () => {

    fetch(BACKEND_URL + 'users/login?emailAddress=' + email + '&password=' + password, {
      method: 'POST',

    })
      .then(response => response.json())
      .then(async (data) => {
        console.log(data);
        if (data.SUCCESS) {
          await dispatch({
            type: 'save_username',
            payload: {
              username: data.USERNAME
            }
          });
          await navigate('/home');
        } else {
          alert(data.MESSAGE);
        }
      })
  }

  const onRegister = () => {

    const formData = new FormData();
    formData.append('username', username);
    formData.append('first_name', firstname);
    formData.append('last_name', lastname);
    formData.append('email', email);
    formData.append('password', password);
    formData.append('dob', dob);
    formData.append('avatar', data);
    console.log(formData);
    fetch(BACKEND_URL + 'users/register', {
      method: 'POST',
      body: formData
    })
      .then(response => response.json())
      .then(async (data) => {
        console.log(data);
        if (data.SUCCESS) {
          await dispatch({
            type: 'save_username',
            payload: {
              username: username
            }
          });
          await navigate('/home');
        } else {
          alert(data.MESSAGE);
        }
      })
  }

  return (
    <>
      <div className='page-header'>
        Triplify
      </div>
      <div className="login-page">
        <div className='login-body'>
          <div className='login-header'>Login</div>
          {alreadyRegistered
            ?
            null
            :
            <>
              <ImageUpload childToParent={childToParent} />
              <input className="first-name" placeholder='First Name' value={firstname} onChange={e => updateFirstName(e.target.value)} />
              <input className="last-name" placeholder='Last Name' value={lastname} onChange={e => updateLastName(e.target.value)} />
              <br />
              <input className="email" placeholder='Username' value={username} onChange={e => updateUsername(e.target.value)} />
            </>
          }
          <br />
          <input className="email" placeholder='Email' value={email} onChange={e => updateEmail(e.target.value)} />
          <br />
          <input className="password" placeholder='Password' value={password} onChange={e => updatePassword(e.target.value)} />
          {/* <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DesktopDatePicker
              label="Date of Birth"
              inputFormat="MM/DD/YYYY"
              value={dob}
              onChange={setDob}
              renderInput={(params) => <TextField {...params} />}
              className="dob"
            />
          </LocalizationProvider> */}
          <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: '15px', marginBottom: '30px', marginInline: '30px', alignItems: 'center' }}>
            <div className={alreadyRegistered ? 'login-btn-selected' : 'login-btn'}
              onClick={() => {
                if (!alreadyRegistered) {
                  updateAuthType(true);
                } else {
                  onLogin();
                }
              }}
            >Login</div>
            <div className={alreadyRegistered ? 'register-btn' : 'register-btn-selected'}
              onClick={() => {
                if (alreadyRegistered) {
                  updateAuthType(false);
                } else {
                  onRegister();
                }
              }}
            >Register</div>
          </div>
        </div>
      </div>
    </>
  );
}

export default App;
