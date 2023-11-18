import axios from 'axios';
import React, { useState } from 'react'
import useAuth from '../hooks/useAuth';

function ChangePassword() {
    const auth = useAuth()
    const [oldPassword, setOldPassword] = useState('')
    const [newPassword, setNewPassword] = useState('');
    const [matchingPassword, setMatchingPassword] = useState('');
    const handleChangePassword = async () => {
        console.log(oldPassword, newPassword)
        const res = await axios.post(`http://localhost:8080/auth/change-password`, {oldPassword, newPassword}, {
            headers: {
                "Content-Type": 'application/json',
                'Authorization' : `Bearer ${auth.accessToken}`
            },
            method: 'POST'
        })
        console.log(res.data);
    }
    
  return (
    <>
        <div class="mb-3">
            <label for="oldPassword" class="form-label">Old password</label>
            <input type="password" class="form-control" id="oldPassword" onChange={(e) => setOldPassword(e.target.value)}/>
        </div>
        <div class="mb-3">
            <label for="newPassword" class="form-label">New password</label>
            <input type="password" class="form-control" id="newPassword" onChange={(e) => setNewPassword(e.target.value)}/>
        </div>
        <div class="mb-3">
            <label for="cofirmPassword" class="form-label">Confirm new password</label>
            <input type="password" class="form-control" id="confirmPassword" onChange={(e) => setMatchingPassword(e.target.value)}/>
        </div>
        <button className='btn btn-primary' onClick={handleChangePassword}>Change password</button>
    </>
  )
}

export default ChangePassword