import React from 'react'
import Header from '../components/customerPage/Header'
import Content from '../components/defaultPage/Content'
import Footer from '../components/defaultPage/Footer'
import LogOut from '../components/customerPage/LogOut'

function CustomerDashBoard(username) {
  return (
    <>
      <Header {...username}></Header>
      <Content></Content>
      <LogOut></LogOut>
      <Footer></Footer>
    </>
  )
}

export default CustomerDashBoard