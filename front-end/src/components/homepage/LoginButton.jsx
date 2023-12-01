import React, { useEffect, useState, useRef } from "react";

function LoginButton(){
    // console.log(modal);
    // const [isClick, setClick] = useState(false);
    // const loginRef = useRef(null);
    useEffect(() => {
        const modal = document.querySelector('.login-modal');
        const modalContainer = document.querySelector('.login-modal-container');
        const loginBtn = document.querySelector('.login-button');
        const closeBtn = document.querySelector('.login-close-btn');
        // const handleClickOutModal = (event) =>{
        //     if (loginRef.current && !loginRef.current.contains(event.target)){
        //         setClick(false);
        //     }

            return () =>{

                loginBtn.addEventListener("click", () => {
                    modal.classList.add('show');
                });

                modal.addEventListener ("click", () => {
                    modal.classList.remove('show');
                });

                closeBtn.addEventListener("click", () =>{
                    modal.classList.remove('show');
                })
                
                
                modalContainer.addEventListener("click", (event) => {
                    event.stopPropagation();

                })
            }
        }, [])

    // const toggleLogin = () =>{
    //     setClick(!isClick);
    // }
    return (
        <form action="">
            <input type="button" className="login-button rounded-4 bg-light text-black" value={'Đăng Nhập'}/>
        </form>
    )
}

export default LoginButton