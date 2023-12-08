import React, { useEffect, useState } from 'react'
import Header from '../components/defaultPage/Header';
import Content from '../components/defaultPage/Content'
import LoginSignup from '../components/defaultPage/Login';
import Footer from '../components/defaultPage/Footer'


function HomePage() {
    return (
        <>
            <Header></Header>
            <Content></Content>
            <LoginSignup></LoginSignup>
            <Footer></Footer>
        </>
    )
}

export default HomePage