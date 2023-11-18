import React from 'react'
import useAuth from '../hooks/useAuth'

function LogoutButton() {
    const auth = useAuth()
    const handleLogout = () => {
        auth.setAccessToken('')
    }
  return (
    <>
     <div className='btn btn-primary' onClick={handleLogout}>Logout</div>
    </>
  )
}

export default LogoutButton