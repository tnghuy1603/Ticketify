import React from "react";

function LoginButton(){
    return (
        <form action="login">
            <input type="submit" className="login-button rounded-4 bg-light text-black" value={'Đăng Nhập'}/>
        </form>
    )
}

export default LoginButton