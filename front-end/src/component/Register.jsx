import axios from 'axios';
import React, { useRef, useState } from 'react'
import api from './api';

const Register = () => {
    const emailRef = useRef();
    const errRef = useRef();
    
    const [email, setEmail] = useState('');
    const [validEmail, setValidEmail] = useState(false);
    const [emailFocus, setEmailFocus] = useState(false);
    
    const [displayName, setDisplayName] = useState('');
    const [validDisplayName, setValidDisplayName] = useState(false);
    const [displayNameFocus, setDisplayNameFocus] = useState(false);

    const [pwd, setPwd] = useState('');
    const [validPwd, setValidPwd] = useState(false);
    const [pwdFocus, setPwdFocus] = useState(false);

    const [matchingPwd, setMatchingPwd] = useState('');
    const [validMatchingPwd, setValidMatchingPwd] = useState(false);
    const [matchingPwdFocus, setMatchingPwdFocus] = useState(false);

    const [success, setSuccess] = useState(false);
    const [errMsg, setErrMsg] = useState('');
    const [test, setTest] = useState()


    const handleRegister = async (e) => {
        console.log(email, pwd, displayName);
        e.preventDefault();
        const res =  await api.post('/auth/signup', {email, password: pwd, displayName }, {
            headers: {
                "Content-Type": "application/json"
            }
        });
        console.log(res.data)
    }
    
  return (
    <>
        <form onSubmit={handleRegister} className='d-flex flex-column justify-content-center align-items-center gap-2'>
            <h3>Sign up</h3>
            <div>
                <label for="email" className="form-label lead">Email address</label>
                <input type="email" className="form-control" id="email" placeholder="Enter your email" onChange={(e) => setEmail(e.target.value)}/>
            </div>
            <div className='form-group'>
                <label for="displayName" class="form-label">Display name</label>
                <input type="text" class="form-control" id="displayName" placeholder="Enter your display name" onChange={(e) => setDisplayName(e.target.value)}/>
            </div>
            <div className='form-group'>
                <label for="pwd" className="form-label">Password</label>
                <input type='password' className="form-control" id="pwd" placeholder="Enter your password" onChange={(e) => setPwd(e.target.value)}/>
            </div>
            <div className='form-group'>
                <label for="matchingPwd" className="form-label">Confirm password</label>
                <input type="text" className="form-control" id="matchingPwd" placeholder="Confirm your password"/>
            </div>
            <div>
                <button type='submit' className='btn btn-primary'>Sign up</button>
            </div>
            <a class="oauth-container btn darken-4 white black-text" href="/users/google-oauth/" style="text-transform:none">
            <img alt="Google sign-in" src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/512px-Google_%22G%22_Logo.svg.png" />
        
    </a>
        </form>
    </>
  )
}

export default Register