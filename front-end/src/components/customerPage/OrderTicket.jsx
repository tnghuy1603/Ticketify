import React, { useState, useEffect } from 'react';
import Header from './Header'
import Footer from '../defaultPage/Footer'
import LogOut from './LogOut'
import ChangePW from './ChangePW'
import { useParams } from 'react-router-dom';
import { Form } from 'react-bootstrap';
import LoadingSpinner from '../defaultPage/Loading'
import { addDays, format } from 'date-fns';

function CustomerDashBoard(params) {
  const { id } = useParams();
  const [movie, setMovie] = useState(null);
  const [theater, setTheater] = useState([]);
  const [date, setDate] = useState([]);

  const getMovie = async () => {
    try {
      // Địa chỉ API và tham số
      const apiUrl = 'http://localhost:8080/movies';

      // Gọi API bằng phương thức GET
      const response = await fetch(apiUrl);

      // Kiểm tra trạng thái của response
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      else {
        // Chuyển đổi response thành JSON
        const result = await response.json();
        const data = result.filter(e => e.id === parseInt(id));
        // Cập nhật state với dữ liệu từ API
        setMovie(data[0]);
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };
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
  const getDate = () => {
    const today = new Date();
    const fiveDays = Array.from({ length: 5 }, (_, index) => {
      const currentDate = addDays(today, index);
      return format(currentDate, 'dd/MM/yyyy');
    });
    setDate(fiveDays);
  };
  const [theaterChosen, setTheaterChosen] = useState(theater[0]);
  const [dateChosen, setDateChosen] = useState(date[0]);

  useEffect(() => {
    getMovie();
    getTheater();
    getDate();
  }, []);

  useEffect(() => {
    // console.log(movie);
    // console.log(theater);
    // console.log(date);
    setTheaterChosen(theater[0]?.id);
    setDateChosen(date[0]);
  }, [movie, theater, date]);


  useEffect(() => {
    console.log(theaterChosen);
    console.log(dateChosen);
  }, [dateChosen, theaterChosen]);

  const handleChangeTheater = (event) => {
    setTheaterChosen(event.target.value);
  };
  const handleChangeDate = (event) => {
    setDateChosen(event.target.value);
  };

  return (
    <>
      <Header {...params} ></Header>
      {movie ? (
        <>
          <Form className='d-flex justify-content-center mt-5'>
            <Form.Group controlId="theater" className='mx-5'>
              <Form.Label>Rạp</Form.Label>
              <Form.Control as="select" className="" onChange={handleChangeTheater}>
                {theater.map((item) => (
                  <option key={item.id} value={item.id}>{item.name}</option>
                ))}
              </Form.Control>
            </Form.Group>
            <Form.Group controlId="date" className='mx-5'>
              <Form.Label>Lịch chiếu</Form.Label>
              <Form.Control as="select" className="" onChange={handleChangeDate}>
                {date.map((item) => (
                  <option key={item} value={item}>{item}</option>
                ))}
              </Form.Control>
            </Form.Group>
          </Form>
          <div className="row mx-0 my-5">
            <div style={{ backgroundColor: 'rgb(219, 137, 45)', userSelect: 'none' }}
              className="m-0 p-3 shadow border border-5 rounded-5 d-flex text-light col-12">
              <img src={movie.poster} className='col-4 p-0' alt={`${movie.id} Poster`} />
              <div className="col-8 d-flex flex-column justify-content-between px-5">
                <h2 className="mx-auto">{movie.title}</h2>
                <div className="d-flex flex-column align-items-start" >
                  <h6>{theaterChosen}</h6>
                  <h6>{dateChosen}</h6>
                </div>
                
              </div>
            </div>
          </div>
        </>

      ) : (
        <div className='d-flex justify-content-center my-5'>
          <LoadingSpinner />
        </div>
      )}

      <LogOut></LogOut>
      <ChangePW {...params}></ChangePW>
      <Footer></Footer>
    </>
  )
}

export default CustomerDashBoard