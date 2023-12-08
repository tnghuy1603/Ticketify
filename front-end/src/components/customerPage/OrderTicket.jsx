import React from 'react'
import Header from './Header'
import Footer from '../defaultPage/Footer'
import LogOut from './LogOut'

function CustomerDashBoard(username) {
  return (
    <>
      <Header {...username}></Header>
      <LogOut></LogOut>
      <Footer></Footer>
    </>
  )
}

export default CustomerDashBoard