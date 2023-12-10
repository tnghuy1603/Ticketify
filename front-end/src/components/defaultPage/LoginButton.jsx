import React, { useEffect, useState, useRef } from "react";

function LoginButton() {
    useEffect(() => {
        return () => {
            $('#btn-login').on({
                'click': function() {
                    $('.login-modal').addClass('show');
                },
                'mouseover': function() {
                    $('#btn-login').css('color', '#295EFF');
                    $('#btn-login').css('cursor', 'pointer');
                },
                'mouseout': function() {
                    $('#btn-login').css('color', 'black');
                    $('#btn-login').css('cursor', 'pointer');
                }
            });
            $('.login-close-btn').on('click', function() {
                $('.login-modal').removeClass('show');
            });
        }
    }, []);
    
    

    return (
        <>
            <a id="btn-login">Đăng nhập </a>
        </>
    )
}

export default LoginButton