import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

import { BrowserRouter } from 'react-router-dom';

import { io } from 'socket.io-client';
export const socket = io("http://localhost:5000");  //use it later

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <App socket={socket}/>
    </BrowserRouter>
  </React.StrictMode>
);

