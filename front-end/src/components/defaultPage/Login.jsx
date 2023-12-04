import React, { useEffect, useState, useRef } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark } from "@fortawesome/free-solid-svg-icons";
import { faFacebookSquare, faGoogle } from "@fortawesome/free-brands-svg-icons";

function LoginSignup() {
    const [isChecked, setIsChecked] = useState(true);

    const handleCheckboxChange = () => {
        setIsChecked(!isChecked);
    };
    return (
        <div className="login-modal">
            <div className="row">
                <div className="col-md-6 mx-auto" >
                    <div className="login-box" style={{ backgroundColor: "#f0f0f0" }}>
                        <button className="login-close-btn">
                            <FontAwesomeIcon icon={faXmark} />
                        </button>
                        <div className="login-snip login-modal-container py-3">
                            <input id="tab-1" type="radio" name="tab" className="sign-in" checked={isChecked}
                                onChange={handleCheckboxChange} /><label htmlFor="tab-1" className="tab">Login</label>
                            <input id="tab-2" type="radio" name="tab" className="sign-up" checked={!isChecked}
                                onChange={handleCheckboxChange} /><label htmlFor="tab-2" className="tab">Sign Up</label>
                            <div className="login-space">
                                <div className="login">
                                    <div className="group group-login-signup">
                                        <label htmlFor="user-login" className="label">Username</label>
                                        <input id="user-login" type="text" className="input py-2" placeholder="Enter your username" />
                                    </div>
                                    <div className="group group-login-signup">
                                        <label htmlFor="pass-login" className="label">Password</label>
                                        <input id="pass-login" type="password" className="input py-2" data-type="password" placeholder="Enter your password" />
                                    </div>
                                    <div className="group group-login-signup d-flex justify-content-start">
                                        <input id="check" type="checkbox" className="check" />
                                        <label htmlFor="check"><span className="icon"></span> Keep me Signed in</label>
                                    </div>
                                    <div className="group group-login-signup">
                                        <button type="submit" className="button py-2">LOGIN</button>
                                    </div>
                                    <div className="foot d-flex justify-content-between">
                                        <label htmlFor="tab-2">Don't have an account?</label>
                                        <a href="#">Forgot Password?</a>
                                    </div>
                                    <hr className="text-white"></hr>
                                    <div>
                                        <h5 className="m-4 text-light">Or Login Using</h5>
                                        <div className="d-flex justify-content-center">
                                            <h3 className="px-1 mx-3 rounded bg-primary text-white">
                                                <FontAwesomeIcon icon={faFacebookSquare} />
                                            </h3>
                                            <h3 className="px-1 mx-3 rounded bg-danger text-white">
                                                <FontAwesomeIcon icon={faGoogle} />
                                            </h3>                                            
                                        </div>
                                    </div>
                                </div>
                                <div className="sign-up-form">
                                    <div className="group group-login-signup">
                                        <label htmlFor="user" className="label">Username</label>
                                        <input id="user" type="text" className="input py-2" placeholder="Create your Username" />
                                    </div>
                                    <div className="group group-login-signup">
                                        <label htmlFor="pass" className="label">Password</label>
                                        <input id="pass" type="password" className="input py-2" data-type="password" placeholder="Create your password" />
                                    </div>
                                    <div className="group group-login-signup">
                                        <label htmlFor="confirmpass" className="label">Repeat Password</label>
                                        <input id="confirmpass" type="password" className="input py-2" data-type="password" placeholder="Repeat your password" />
                                    </div>
                                    <div className="group group-login-signup">
                                        <label htmlFor="email" className="label">Email Address</label>
                                        <input id="email" type="text" className="input py-2" placeholder="Enter your email address" />
                                    </div>
                                    <div className="group group-login-signup my-4">
                                        <button type="submit" className="button py-2">SIGN UP</button>
                                    </div>
                                    <div className="foot d-flex justify-content-end">
                                        <label htmlFor="tab-1">Already Member?</label>
                                    </div>
                                    <hr className="text-white"></hr>
                                    <div>
                                        <h5 className="m-4 text-light">Or Sign up Using</h5>
                                        <div className="d-flex justify-content-center">
                                            <h3 className="px-1 mx-3 rounded bg-primary text-white">
                                                <FontAwesomeIcon icon={faFacebookSquare} />
                                            </h3>
                                            <h3 className="px-1 mx-3 rounded bg-danger text-white">
                                                <FontAwesomeIcon icon={faGoogle} />
                                            </h3>                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>)
}

export default LoginSignup