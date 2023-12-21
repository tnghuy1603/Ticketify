import React, { useEffect, useState } from 'react'
import Header from '../components/defaultPage/Header';
import Content from '../components/defaultPage/Content'
import LoginSignup from '../components/defaultPage/Login';
import Footer from '../components/defaultPage/Footer'
import RequireLogin from '../components/defaultPage/requireLogin';

function HomePage() {
    return (
        <>
            <LoginSignup></LoginSignup>
            <Header></Header>
            <Content username={null} email={null}></Content>
            <RequireLogin></RequireLogin>
            <Footer></Footer>
        </>
    )
}

export default HomePage