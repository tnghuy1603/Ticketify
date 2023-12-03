import axios from 'axios';
import React, { useState } from 'react'
import useAuth from '../hooks/useAuth';

const Login = () => {
  const auth = useAuth()
  const [email, setEmail] = useState('');
  const [pwd, setPwd] = useState('')
  const handleSignIn = async () => {
    console.log(email, pwd);
    const res = await axios.post('http://localhost:8080/auth/login', { email, password: pwd }, {
      headers: {
        'Content-Type': 'application/json'
      }
    })
    auth.setAccessToken(res.data.accessToken);
    window.location.href = '/'
  }

  return (
    <>
      <div style={{ width: '50%', height: "34rem", margin: 'auto' }}>
        <div className='card m-5 p-3'>
          <div className='card-title fw-bold text-primary-emphasis fs-1'>Login</div>
          <div className="card-body">
            <div className='mb-3 d-flex flex-column align-items-start'>
              <label htmlFor='email' className='form-label text-start'>Email</label>
              <input type='email' className="form-control" id="email" onChange={(e) => setEmail(e.target.value)} />
            </div>
            <div className='mb-3 d-flex flex-column align-items-start'>
              <label htmlFor="pwd" className="form-label">Password</label>
              <input type='password' className="form-control" id="pwd" onChange={(e) => setPwd(e.target.value)} />
            </div>
            <div>
              <a onClick={handleSignIn} className='btn text-light btn-primary m-3 '>Sign in</a>
              <a href='/signup' className='btn text-light btn-primary m-3'>Sign up</a>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default Login