import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import './index.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Store from './Store';
import GroupDetail from './pages/GroupDetail';
import AddGroup from './pages/AddGroup';
import AddExpense from './pages/AddExpense';
import Checklist from './pages/Checklist';
import Member from './pages/Member';
import Explore from './pages/Explore';
import Posts from './pages/Posts';
import Profile from './pages/Profile';
import SettleUp from './pages/SettleUp';
import LiveTracking from './pages/LiveTracking';
import AddPost from './pages/AddPost';
import SettleSummary from './pages/SettleSummary';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  // <React.StrictMode>
    <Store>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<App />} />
          <Route path="/home" element={<Home />} />
          <Route path="/group-detail" element={<GroupDetail />} />
          <Route path="/add-group" element={<AddGroup />} />
          <Route path="/add-expense" element={<AddExpense />} />
          <Route path="/checklist" element={<Checklist />} />
          <Route path="/members" element={<Member />} />
          <Route path="/explore" element={<Explore />} />
          <Route path="/posts" element={<Posts />} />
          <Route path="/live-tracking" element={<LiveTracking />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/settle-up" element={<SettleUp />} />
          <Route path="/settle-summary" element={<SettleSummary />} />
          <Route path="/add-post" element={<AddPost />} />
          <Route path="/*" element={<Home />} />
        </Routes>
      </BrowserRouter>
    </Store>
  // </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
