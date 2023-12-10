import React, { useEffect, useState } from 'react'
import Header from '../components/defaultPage/Header';
import Content from '../components/defaultPage/Content'
import LoginSignup from '../components/defaultPage/Login';
import Footer from '../components/defaultPage/Footer'


function HomePage() {
    return (
        <>
            <LoginSignup></LoginSignup>
            <Header></Header>
            <Content username={null} email={null}></Content>
            <Footer></Footer>
        </>
    )
}

export default HomePage