import React, { useEffect, useState, useRef } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark } from "@fortawesome/free-solid-svg-icons";

function LoginSignup() {
    return (
        <div className="login-modal">
            <div className="row">
                <div className="col-md-6 mx-auto p-0">

                    <div className="login-box">
                        <button className="login-close-btn">

                            <FontAwesomeIcon icon={faXmark} />
                        </button>
                        <div className="login-snip login-modal-container">
                            <input id="tab-1" type="radio" name="tab" className="sign-in" checked /><label for="tab-1" className="tab">Login</label>
                            <input id="tab-2" type="radio" name="tab" className="sign-up" /><label for="tab-2" className="tab">Sign Up</label>
                            <div className="login-space">
                                <div className="login">
                                    <div className="group group-login-signup">
                                        <label for="user" className="label">Username</label>
                                        <input id="user" type="text" className="input" placeholder="Enter your username" />
                                    </div>
                                    <div className="group group-login-signup">
                                        <label for="pass" className="label">Password</label>
                                        <input id="pass" type="password" className="input" data-type="password" placeholder="Enter your password" />
                                    </div>
                                    <div className="group group-login-signup">
                                        <input id="check" type="checkbox" className="check" />
                                        <label for="check"><span className="icon"></span> Keep me Signed in</label>
                                    </div>
                                    <div className="group group-login-signup">
                                        <input type="submit" className="button" value="Sign In" />
                                    </div>
                                    <div className="hr"></div>
                                    <div className="foot">
                                        <a href="#">Forgot Password?</a>
                                    </div>
                                </div>
                                <div className="sign-up-form">
                                    <div className="group group-login-signup">
                                        <label for="user" className="label">Username</label>
                                        <input id="user" type="text" className="input" placeholder="Create your Username" />
                                    </div>
                                    <div className="group group-login-signup">
                                        <label for="pass" className="label">Password</label>
                                        <input id="pass" type="password" className="input" data-type="password" placeholder="Create your password" />
                                    </div>
                                    <div className="group group-login-signup">
                                        <label for="pass" className="label">Repeat Password</label>
                                        <input id="pass" type="password" className="input" data-type="password" placeholder="Repeat your password" />
                                    </div>
                                    <div className="group group-login-signup">
                                        <label for="pass" className="label">Email Address</label>
                                        <input id="pass" type="text" className="input" placeholder="Enter your email address" />
                                    </div>
                                    <div className="group group-login-signup">
                                        <input type="submit" className="button" value="Sign Up" />
                                    </div>
                                    <div className="hr"></div>
                                    <div className="foot">
                                        <label for="tab-1">Already Member?</label>
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