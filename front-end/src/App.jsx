import { useState } from 'react'
import './App.css'
import 'bootstrap/dist/css/bootstrap.css'
import { jwtDecode } from "jwt-decode"
import { BrowserRouter as Router, Route, Routes, BrowserRouter } from 'react-router-dom';
import PrivateRoute from './components/PrivateRoute'
import useAuth from './hooks/useAuth'
import AdminPage from './page/AdminPage'
import CustormerPage from './page/CustormerPage'
import DefaultPage from './page/DefaultPage'
import TicketManagerPage from './page/TicketManagerPage'
import ReceptionistPage from './page/ReceptionistPage'
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
          <Route
            path='/' element={
              roles.find(role => role === "ROLE_CUSTOMER") ? (
                <PrivateRoute>
                  <CustormerPage />
                </PrivateRoute>
              ) : roles.find(role => role === "ROLE_RECEPTIONIST") ? (
                <PrivateRoute>
                  <ReceptionistPage />
                </PrivateRoute>
              ) : roles.find(role => role === "ROLE_ADMIN") ? (
                <PrivateRoute>
                  <AdminPage />
                </PrivateRoute>
              ) : roles.find(role => role === "ROLE_TICKETMANAGER") ? (
                <PrivateRoute>
                  <TicketManagerPage />
                </PrivateRoute>
              ) : (
                <DefaultPage />
              )
            }
          />
        </Routes>
      </BrowserRouter>

    </>
  )
}

export default App
