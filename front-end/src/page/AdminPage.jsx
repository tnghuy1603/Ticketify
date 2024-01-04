import React, { useEffect, useState } from "react";
import { useParams } from 'react-router-dom';
import Header from "../components/adminPage/Header";
import Home from '../components/adminPage/Home'
import Sidebar from '../components/adminPage/Sidebar'
import ManageUser from '../components/adminPage/ManageUser'
import ManageComment from '../components/adminPage/ManageComment'
import Footer from '../components/defaultPage/Footer'
import LogOut from '../components/customerPage/LogOut'
import ChangePW from '../components/customerPage/ChangePW'
import '../components/adminPage/styles.css'

function AdminDashBoard(params) {
  const { currentChosen } = useParams();
  console.log(currentChosen);
  const [dataReceived, setDataReceived] = useState('')
  const [optionReceived, setOptionReceived] = useState('')
  const receiveSearchHeader = (data) => {
    setDataReceived(data);
  }

  const receiveOptionHeader = (data) => {
    setOptionReceived(data);
  }
  return (
    <>
      <div className="container-fluid">
        <div className="row">
          {/* <SideBar
            chosen={currentChosen}
            isCollapse={isCollapse}></SideBar> */}
          <div className='col p-0 d-flex flex-column justify-content-between' style={{ flexShrink: '0' }}>
            {/* <Header params={params} /> */}
            <Header onSearchHeader={receiveSearchHeader} onOptionChange={receiveOptionHeader} />
            <Sidebar />
            {currentChosen === 'home' ? (
              <Home></Home>
            ) : currentChosen === 'manage-users' ? (
              <ManageUser dataSearch={dataReceived} dataOption={optionReceived} />
            ) : currentChosen === 'manage-comments' ? (
              <ManageComment />
            ) : (
              <Home></Home>
            )}

            <LogOut></LogOut>
            <ChangePW {...params}></ChangePW>
            <Footer></Footer>
          </div>
        </div>
      </div>
    </>
  )
}

export default AdminDashBoard