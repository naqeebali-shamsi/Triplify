import React, { useState } from 'react';
import axios from 'axios';
import { makeStyles } from '@mui/styles';
import dayjs from 'dayjs';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { DesktopDatePicker } from '@mui/x-date-pickers/DesktopDatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import ImageUpload from './ImageUpload';

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    padding: theme.spacing(2),

    '& .MuiTextField-root': {
      margin: theme.spacing(1),
      width: '300px',
    },
    '& .MuiButtonBase-root': {
      margin: theme.spacing(2),
    },
  },
}));

const SignUpForm = ({ handleClose }) => {
  const classes = useStyles();
  // create state variables for each input
  const [userName, setUserName] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [dob, setDob] = useState(dayjs('2014-08-18T21:11:54'));
  const [data, setData] = useState('');
  const childToParent = (childData) => {
    setData(childData);
  }
  const handleDateChange = (newValue) => {setDob(newValue);};

  const handleSubmit = e => {
    e.preventDefault();
    const formData = new FormData();
    formData.append('username',userName);
    formData.append('first_name',userName);
    formData.append('last_name',userName);
    formData.append('email',userName);
    formData.append('password',userName);
    formData.append('dob',userName);
    formData.append('avatar', data);
    console.log(formData);
    axios({
      method: "post",
      url: "http://localhost:8080/users/register",
      data: formData,
      headers: { "Content-Type": "multipart/form-data" },
    })
      .then(function (response) {
        //handle success
        console.log(response);
      })
      .catch(function (response) {
        //handle error
        console.log(response);
      });
    handleClose();
  };

  return (
    <form className={classes.root} onSubmit={handleSubmit}>
      <TextField
        label="Username"
        variant="filled"
        required
        value={userName}
        onChange={e => setUserName(e.target.value)}
      />
      <TextField
        label="First Name"
        variant="filled"
        required
        value={firstName}
        onChange={e => setFirstName(e.target.value)}
      />
      <TextField
        label="Last Name"
        variant="filled"
        required
        value={lastName}
        onChange={e => setLastName(e.target.value)}
      />
      <TextField
        label="Email"
        variant="filled"
        type="email"
        required
        value={email}
        onChange={e => setEmail(e.target.value)}
      />
      <TextField
        label="Password"
        variant="filled"
        type="password"
        required
        value={password}
        onChange={e => setPassword(e.target.value)}
      />
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DesktopDatePicker
            label="Date of Birth"
            inputFormat="MM/DD/YYYY"
            value={dob}
            onChange={handleDateChange}
            renderInput={(params) => <TextField {...params} />}
          />
      </LocalizationProvider>
      <div>
        <ImageUpload childToParent={childToParent}/>
        <Button variant="contained" onClick={handleClose}>
          Cancel
        </Button>
        <Button type="submit" variant="contained" color="primary">
          Signup
        </Button>
      </div>
    </form>
  );
};

export default SignUpForm;