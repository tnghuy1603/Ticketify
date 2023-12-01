import axios from 'axios';
import React, { useRef, useState } from 'react'

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
        const res = await axios.post('/auth/signup', { email, password: pwd, displayName }, {
            headers: {
                "Content-Type": "application/json"
            }
        });
        console.log(res.data)
    }

    return (
        <>
            <div style={{ width: '50%', height: "35rem", margin: 'auto' }}>
                <div className='card m-5 p-3 h-75'>
                    <div className='card-title fw-bold text-primary-emphasis fs-1'>Sign up</div>
                    <div className="card-body" >
                        <form onSubmit={handleRegister}>
                            <div>
                                <label htmlFor='email' className="form-label fw-bold d-flex flex-column align-items-start">Email address</label>
                                <input type="email" className="form-control" id="email" onChange={(e) => setEmail(e.target.value)} />
                            </div>
                            <div className='form-group'>
                                <label htmlFor="displayName" className="form-label fw-bold d-flex flex-column align-items-start">Username</label>
                                <input type="text" className="form-control" id="displayName" onChange={(e) => setDisplayName(e.target.value)} />
                            </div>
                            <div className='form-group'>
                                <label htmlFor="pwd" className="form-label fw-bold d-flex flex-column align-items-start">Password</label>
                                <input type='password' className="form-control" id="pwd" onChange={(e) => setPwd(e.target.value)} />
                            </div>
                            <div className='form-group'>
                                <label htmlFor="matchingPwd" className="form-label fw-bold d-flex flex-column align-items-start">Confirm password</label>
                                <input type="text" className="form-control" id="matchingPwd" />
                            </div>
                            <div className='d-flex justify-content-around my-3'>
                                <button type='submit' className='btn btn-primary'>Sign up</button>
                                <a className="oauth-container btn darken-4 white black-text" href="/users/google-oauth/" style={{ textTransform: 'none' }}>
                                    <img alt="Google sign-in" src="https://th.bing.com/th/id/OIP.ivZ1IGarTlwQ07B1ArmlYAHaHg?rs=1&pid=ImgDetMain" style={{ width: '2rem' }} />
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </>
    )
}

export default Register