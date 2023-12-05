import { useState, useEffect } from 'react'
import './App.css'
import 'bootstrap/dist/css/bootstrap.css'
import { jwtDecode } from "jwt-decode"
import { BrowserRouter as Router, Route, Routes, BrowserRouter } from 'react-router-dom';
import PrivateRoute from './components/PrivateRoute'
import useAuth from './hooks/useAuth'
import AdminPage from './page/AdminPage'
import CustomerPage from './page/CustomerPage'
import DefaultPage from './page/DefaultPage'
import TicketManagerPage from './page/TicketManagerPage'
import ReceptionistPage from './page/ReceptionistPage'
function App() {
  const getRoles = (accessToken) => {
    if (accessToken) {
      let decodedJwt = jwtDecode(accessToken);
      return decodedJwt.roles;
    }
    return [];
  }
  const { accessToken, setAccessToken } = useAuth();
  const [roles, setRoles] = useState(getRoles(accessToken));
  useEffect(() => {
    // Update roles when accessToken changes
    setRoles(getRoles(accessToken));
  }, [accessToken]);

  const getUserName = (accessToken) => {
    if (accessToken) {
      let decodedJwt = jwtDecode(accessToken);
      return decodedJwt.sub;
    }
    return [];
  }
  const username = getUserName(accessToken);
  console.log(username);
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route
            path='/' element={
              roles.find(role => role === "ROLE_CUSTOMER") ? (
                <PrivateRoute>
                  <CustomerPage username={username} />
                </PrivateRoute>
              ) : roles.find(role => role === "ROLE_RECEPTIONIST") ? (
                <PrivateRoute>
                  <ReceptionistPage username={username} />
                </PrivateRoute>
              ) : roles.find(role => role === "ROLE_ADMIN") ? (
                <PrivateRoute>
                  <AdminPage username={username} />
                </PrivateRoute>
              ) : roles.find(role => role === "ROLE_TICKETMANAGER") ? (
                <PrivateRoute>
                  <TicketManagerPage username={username} />
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
