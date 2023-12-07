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
            $('#btn-signup').on({
                'click': function() {
                    $('.login-modal').addClass('show');
                },
                'mouseover': function() {
                    $('#btn-signup').css('color', '#295EFF');
                    $('#btn-signup').css('cursor', 'pointer');
                },
                'mouseout': function() {
                    $('#btn-signup').css('color', 'black');
                    $('#btn-signup').css('cursor', 'pointer');
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
            <a> | </a>
            <a id="btn-signup"> Đăng ký</a>
        </>
    )
}

export default LoginButton