import React from 'react'
import Header from '../components/ticketManagerPage/Header'
import Content from '../components/ticketManagerPage/Content'
import Footer from '../components/defaultPage/Footer'
import LogOut from '../components/customerPage/LogOut'
import ChangePW from '../components/customerPage/ChangePW'
function TicketManagerDashBoard(params) {
  return (
    <>
      <Header {...params} ></Header>
      <Content {...params} ></Content>
      <LogOut></LogOut>
      <ChangePW {...params}></ChangePW>
      <Footer></Footer>
    </>
  )
}

export default TicketManagerDashBoard