import React from 'react'
import useAuth from '../hooks/useAuth'
import LogoutButton from './LogoutButton'
import ChangePassword from './ChangePassword'

function TicketManagerDashBoard() {
  
  return (
    <>
      <div>Ticket manager dashboard</div>
      <LogoutButton/>
      <ChangePassword/>
      
    </>
  )
}

export default TicketManagerDashBoard