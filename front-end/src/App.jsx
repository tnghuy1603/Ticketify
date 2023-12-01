import { useState } from 'react'
import './App.css'
import 'bootstrap/dist/css/bootstrap.css'
import Register from './components/Register'
import { jwtDecode } from "jwt-decode"
import Login from './components/Login'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import PrivateRoute from './components/PrivateRoute'
import useAuth from './hooks/useAuth'
import TicketManagerDashBoard from './components/TicketManagerDashBoard'
import ReceptionistDashBoard from './components/ReceptionistDashBoard'
import HomePage from './page/HomePage'
import ShowTimeOfMovie from './components/ShowTimeOfMovie'
import ShowTimeOfTheater from './components/ShowTimeOfTheater'


function App() {
  const getRoles = (accessToken) => {
    if (accessToken) {
      let decodedJwt = jwtDecode(accessToken);
      console.log(decodedJwt.roles);
      return decodedJwt.roles
    }
    return [];
  }
  const { accessToken, setAccessToken } = useAuth();
  const [roles, setRoles] = useState(getRoles(accessToken));
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path='/dashboard' element={
            roles.find(role => role === "ROLE_CUSTOMER") ?
              (
                <PrivateRoute>
                  <TicketManagerDashBoard />
                </PrivateRoute>
              ) : (
                <PrivateRoute>
                  <ReceptionistDashBoard />
                </PrivateRoute>
              )
          } />
          <Route path='/' element={<HomePage />} />
          <Route path='/login' element={<Login />} />
          <Route path='/register' element={<Register />} />
          <Route path='/schedule' element={<ShowTimeOfTheater />} />
        </Routes>
      </BrowserRouter>


    </>
  )
}

export default App
