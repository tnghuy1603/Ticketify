import React, { useState, useEffect } from 'react';
import Header from '../components/ticketManagerPage/Header'
import Content from '../components/ticketManagerPage/Content'
import Footer from '../components/defaultPage/Footer'
import LogOut from '../components/customerPage/LogOut'
import ChangePW from '../components/customerPage/ChangePW'
import SideBar from '../components/ticketManagerPage/SideBar';
import Home from '../components/ticketManagerPage/Home';

function TicketManagerDashBoard(params) {
  const [isCollapse, setIsCollapse] = useState(false);
  const updateData = () => {
    setIsCollapse(!isCollapse);
  }
  const [chosen, setChosen] = useState('home');
  const handleChosen = (a) => {
    setChosen(a);
  }
  const [theater, setTheater] = useState([]);
  const [theaterChosen, setTheaterChosen] = useState(theater[0]?.id);
  const [theaterChosenName, setTheaterChosenName] = useState(theater[0]);
  const getTheater = async () => {
    try {
      // Địa chỉ API và tham số
      const apiUrl = 'http://localhost:8080/theaters';

      // Gọi API bằng phương thức GET
      const response = await fetch(apiUrl);

      // Kiểm tra trạng thái của response
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      else {
        // Chuyển đổi response thành JSON
        const result = await response.json();
        // Cập nhật state với dữ liệu từ API
        setTheater(result);
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  const handleChangeTheater = (event) => {
    setTheaterChosen(event.target.value);
    setTheaterChosenName((theater.filter(e => e.id === parseInt(event.target.value)))[0]?.name);
  };

  useEffect(() => {
    getTheater();
  }, []);

  useEffect(() => {
    setTheaterChosen(theater[0]?.id);
    setTheaterChosenName((theater.filter(e => e.id === theater[0]?.id))[0]?.name);
  }, [theater]);


  return (
    <>

      <div className="container-fluid">
        <div className="row">
          <SideBar
            theater={theater}
            handleChangeTheater={handleChangeTheater}
            handleChosen={handleChosen}
            chosen={chosen}
            isCollapse={isCollapse}></SideBar>
          <div className='col p-0 d-flex flex-column justify-content-between'>
            <Header updateData={updateData} isCollapse={isCollapse} params={params} ></Header>
            {/* <Content
              theater={theaterChosen}
              theaterName={theaterChosenName}
              params={params}
              handleChosen={handleChosen}
              chosen={chosen} ></Content> */}
            <Home></Home>
            <LogOut></LogOut>
            <ChangePW {...params}></ChangePW>
            <Footer></Footer>
          </div>
        </div>
      </div>

    </>
  )
}

export default TicketManagerDashBoard