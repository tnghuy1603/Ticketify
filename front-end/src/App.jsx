import { useState } from 'react'

import './App.css'
import 'bootstrap/dist/css/bootstrap.css'
import {jwtDecode} from "jwt-decode"
import Register from './components/Register'
import Login from './components/Login'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import PrivateRoute from './components/PrivateRoute'
import useAuth from './hooks/useAuth'
import TicketManagerDashBoard from './components/TicketManagerDashBoard'
import ReceptionistDashBoard from './components/ReceptionistDashBoard'


function App() {
  const getRoles = (accessToken) => {
    if(accessToken){
      let decodedJwt = jwtDecode(accessToken);
      console.log(decodedJwt.roles);
      return decodedJwt.roles
    }
    return [];
  }
  const {accessToken, setAccessToken} = useAuth();
  const [roles, setRoles] = useState(getRoles(accessToken));
  return (
    <>
    <BrowserRouter>
      <Routes>
        <Route path='/dashboard' element={
          roles.find(role => role === "ROLE_CUSTOMER")? 
          (
            <PrivateRoute>
              <TicketManagerDashBoard/>
            </PrivateRoute>
          ) : (
            <PrivateRoute>
              <ReceptionistDashBoard/>
            </PrivateRoute>
          )
        }/>
        <Route path='/login' element={<Login/>}/>
        <Route path='/register' element={<Register/>}/>
      </Routes>
    </BrowserRouter>
      
    </>
  )
}

export default App
