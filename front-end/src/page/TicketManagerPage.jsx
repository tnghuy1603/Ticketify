import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../components/ticketManagerPage/Header'
import Content from '../components/ticketManagerPage/Content'
import Footer from '../components/defaultPage/Footer'
import LogOut from '../components/customerPage/LogOut'
import ChangePW from '../components/customerPage/ChangePW'
import SideBar from '../components/ticketManagerPage/SideBar';
import Home from '../components/ticketManagerPage/Home';
import Movie from '../components/ticketManagerPage/Movie';
import LoadingSpinner from '../components/defaultPage/Loading'

// import Showtime from '../components/ticketManagerPage/Showtime';
// import Profit from '../components/ticketManagerPage/Profit';

function TicketManagerDashBoard(params) {
  const { currentChosen } = useParams();

  const [isCollapse, setIsCollapse] = useState(false);
  const updateData = () => {
    setIsCollapse(!isCollapse);
  }
  const [chosen, setChosen] = useState(currentChosen);
  const handleChosen = (a) => {
    setChosen(a);
  }
  const [theater, setTheater] = useState([]);
  const [theaterChosen, setTheaterChosen] = useState(null);
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
  };

  useEffect(() => {
    getTheater();
  }, []);

  useEffect(() => {
    setTheaterChosen(theater[0]?.id);
  }, [theater]);


  return (
    <>

      <div className="container-fluid">
        <div className="row">
          <SideBar
            theater={theater}
            handleChangeTheater={handleChangeTheater}
            chosen={chosen}
            isCollapse={isCollapse}></SideBar>
          <div className='col p-0 d-flex flex-column justify-content-between'>
            <Header updateData={updateData} isCollapse={isCollapse} params={params} ></Header>
            {currentChosen === 'home' ? (
              <Home></Home>
            ) : currentChosen === 'movie' ? (
              <Movie></Movie>
            ) : currentChosen === 'showtime' ? (
              <div className="jumbotron" style={{ backgroundColor: '#f0f0f0', height: '70vh' }}>
                lịch chiếu
              </div>
            ) : currentChosen === 'profit' ? (
              <div className="jumbotron" style={{ backgroundColor: '#f0f0f0', height: '70vh' }}>
                doanh thu
              </div>
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

export default TicketManagerDashBoard