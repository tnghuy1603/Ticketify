import React, { useEffect, useState, useRef } from "react";

function LoginButton() {
    useEffect(() => {
        return () => {
            $('.btn-login').on('click', function() {
                $('.login-modal').addClass('show');
            });
            $('.login-close-btn').on('click', function() {
                $('.login-modal').removeClass('show');
            });
        }
    }, [])
    return (
        <>
            <button className="bg-light text-black btn-login">Login</button>
        </>
    )
}

export default LoginButton