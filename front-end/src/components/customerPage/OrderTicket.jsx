import React, { useState, useEffect } from 'react';
import Header from './Header'
import Footer from '../defaultPage/Footer'
import LogOut from './LogOut'
import ChangePW from './ChangePW'
import { useParams } from 'react-router-dom';
import { Form } from 'react-bootstrap';
import LoadingSpinner from '../defaultPage/Loading'
import { addDays, format, parseISO } from 'date-fns';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark } from "@fortawesome/free-solid-svg-icons";
import './styles.css'

function CustomerDashBoard(params) {
  const { id } = useParams();
  const [step, setStep] = useState('choose-time');
  const [prevStep, setPrevStep] = useState(null);
  const [movie, setMovie] = useState(null);
  const [theater, setTheater] = useState([]);
  const [date, setDate] = useState([]);

  const [showtimes, setShowTimes] = useState(null);

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
  const getShowTime = async (idMovie, idTheater, date) => {
    try {
      // Địa chỉ API và tham số
      const apiUrl = `http://localhost:8080/showtimes?theater=${idTheater}&movie=${idMovie}`;

      // Gọi API bằng phương thức GET
      const response = await fetch(apiUrl);

      // Kiểm tra trạng thái của response
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      else {
        // Chuyển đổi response thành JSON
        const result = await response.json();
        const data = result.filter(e => {
          const dateString = e.startAt;
          const dateTime = parseISO(dateString);

          const datePart = format(dateTime, 'dd/MM/yyyy');
          return datePart === date;
        });
        data.sort((a, b) => {
          const timeA = parseISO(a.startAt);
          const timeB = parseISO(b.startAt);
          return timeA - timeB;
        });
        setShowTimes(data);
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }
  const [theaterChosen, setTheaterChosen] = useState(theater[0]?.id);
  const [dateChosen, setDateChosen] = useState(date[0]);

  const [theaterChosenName, setTheaterChosenName] = useState(theater[0]);

  useEffect(() => {
    getMovie();
    getTheater();
    getDate();
  }, []);

  useEffect(() => {
    setTheaterChosen(theater[0]?.id);
    setDateChosen(date[0]);
    setTheaterChosenName((theater.filter(e => e.id === theater[0]?.id))[0]?.name);
  }, [movie, theater, date]);


  useEffect(() => {
    // console.log(theaterChosen);
    // console.log(dateChosen);
    if (theaterChosen) {
      getShowTime(id, theaterChosen, dateChosen);
    }
  }, [dateChosen, theaterChosen]);

  const handleChangeTheater = (event) => {
    setTheaterChosen(event.target.value);
    setTheaterChosenName((theater.filter(e => e.id === parseInt(event.target.value)))[0]?.name);
  };
  const handleChangeDate = (event) => {
    setDateChosen(event.target.value);
  };

  const [showtimeChosen, setShowTimeChosen] = useState(null);
  const [tickets, setTickets] = useState(null);

  const getTickets = async (idShowtime) => {
    try {
      // Địa chỉ API và tham số
      const apiUrl = `http://localhost:8080/tickets?showtime=${idShowtime}`;

      // Gọi API bằng phương thức GET
      const response = await fetch(apiUrl);

      // Kiểm tra trạng thái của response
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      else {
        // Chuyển đổi response thành JSON
        const result = await response.json();
        result.sort((a, b) => {
          const aPrefix = a.seat.seatNumber.substring(0, 1);
          const bPrefix = b.seat.seatNumber.substring(0, 1);
          if (aPrefix === bPrefix) {
            const aNumber = parseInt(a.seat.seatNumber.substring(1), 10);
            const bNumber = parseInt(b.seat.seatNumber.substring(1), 10);
            return aNumber - bNumber;
          }
          return aPrefix.localeCompare(bPrefix);
        });
        setTickets(result);
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }

  const handleChooseSeat = (showtime) => {
    setStep('choose-seat');
    setShowTimeChosen(showtime);
  }

  useEffect(() => {
    if (showtimeChosen !== null) {
      getTickets(showtimeChosen?.id);
    }
  }, [showtimeChosen]);

  const handleDisabledTime = (time) => {
    const currentTime = new Date();
    const targetTime = new Date(time);
    // Tính toán và kiểm tra khoảng thời gian
    const timeDifference = (targetTime.getTime() + 30 * 60 * 1000) - currentTime.getTime();
    return timeDifference < 0;
  }

  const handleBack = () => {
    setStep(prevStep);
  }
  const handleBuyFood = () => {
    setStep('choose-food');
  }
  const handlePayment = () => {
    setStep('choose-payment');
  }
  const handleCloseBooking = () => {
    setStep('choose-time');
  }

  useEffect(() => {
    if (step === 'choose-time') {
      setShowTimeChosen(null);
      setPrevStep(null);
      setSeatNumbers('');
      setSeats([]);
      setTotal(0);
    } else if (step === 'choose-seat') {
      setPrevStep('choose-time');
    } else if (step === 'choose-food') {
      setPrevStep('choose-seat');
    } else if (step === 'choose-payment') {
      if (prevStep === 'choose-seat') {
        setPrevStep('choose-food');
      } else {
        setPrevStep('choose-seat');
      }
    }
  }, [step]);

  const [seats, setSeats] = useState([]);
  const [total, setTotal] = useState(0);
  const [seatNumbers, setSeatNumbers] = useState('');

  const handleSelectSeats = (ticket) => {
    const seatIndex = seats.indexOf(ticket);
    if (seatIndex === -1) {
      // Ghế chưa được chọn, thêm vào mảng
      setSeats([...seats, ticket]);
    } else {
      // Ghế đã được chọn, loại bỏ khỏi mảng
      const newSeats = [...seats];
      newSeats.splice(seatIndex, 1);
      setSeats(newSeats);
    }
  }

  useEffect(() => {
    console.log(seats);
    let sum = 0;
    let str = '';
    seats.forEach(e => {
      sum += e.price;
      str += e.seat.seatNumber + ', ';
    })
    setSeatNumbers(str.substring(0, str.length - 2));
    setTotal(sum);
  }, [seats])



  return (
    <>
      <Header {...params} ></Header>
      {step === 'choose-time' && movie ? (
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
          <div className="container mx-auto my-5">
            <div style={{ backgroundColor: 'rgb(219, 137, 45)', userSelect: 'none' }}
              className="m-0 p-3 shadow border border-5 rounded-5 d-flex text-light col-12">
              <img src={movie.poster} className='col-4 p-0' alt={`${movie.id} Poster`} />
              <div className="col-8 d-flex flex-column justify-content-start px-5">
                <h1 className="mx-auto my-4">{movie.title}</h1>
                <div className="d-flex flex-column align-items-start my-4" >
                  <h3>{theaterChosenName}</h3>
                  <h3>{dateChosen}</h3>
                </div>
                {!showtimes ? (
                  <div className='d-flex justify-content-center my-5'>
                    <LoadingSpinner />
                  </div>
                ) : showtimes.length === 0 ? (
                  <h5>Chưa có suất chiếu</h5>
                ) : (
                  < div className='flex-wrap d-flex justify-content-start'>
                    {showtimes.map((item) => (
                      <button onClick={() => handleChooseSeat(item)} key={item.id} className='btn btn-success col-2 text-light m-2' disabled={handleDisabledTime(item.startAt)}>{format(parseISO(item.startAt), 'HH:mm:ss').substring(0, 5)}</button>
                    ))}
                  </div>
                )}
              </div>
            </div>
          </div>
        </>
      ) : step === 'choose-seat' && showtimeChosen ? (
        <div >
          <div className='d-flex flex-column justify-content-start align-items-center p-5' style={{ position: 'relative', overflow: 'hidden', margin: '1px', background: 'linear-gradient(176deg, rgba(251,255,200,1) 42%, rgba(149,255,169,1) 100%)' }}>
            <button onClick={handleCloseBooking} type="button" className="btn btn-lg btn-secondary rounded-circle position-absolute top-0 end-0" style={{ marginRight: '1rem', marginTop: '1rem' }}>
              <FontAwesomeIcon icon={faXmark} />
            </button>
            <div className='d-flex justify-content-start align-items-center' >
              <div className='m-3 '>Tên phim</div>
              <h4 className='text-success text-start fw-bold'>{showtimeChosen.movie.title}</h4>
            </div>
            <div className='d-flex justify-content-center'>
              <div >
                <div id='time' className='d-flex justify-content-center' style={{ marginBottom: '1px' }}>
                  <div className='p-3' style={{ marginRight: '1px', backgroundColor: 'rgb(219, 137, 45)', width: '10rem' }}>
                    <div>Suất chiếu</div>
                    <h4 className='fw-bold'>
                      {format(parseISO(showtimeChosen.startAt), 'HH:mm:ss').substring(0, 5)}
                    </h4>
                  </div>
                  <div className='p-3' style={{ backgroundColor: 'rgb(219, 137, 45)', width: '20rem' }}>
                    <div>
                      Ngày chiếu
                    </div>
                    <h4 className='fw-bold'>
                      {format(parseISO(showtimeChosen.startAt), 'dd/MM/yyyy')}
                    </h4>
                  </div>
                </div>
                <div id='seat' className='d-flex justify-content-start'>
                  <div style={{ width: '10rem', backgroundColor: 'rgb(201, 102, 247)', marginRight: '1px' }} className='p-3 d-flex justify-content-center align-items-center'>Số ghế</div>
                  <div style={{ width: '20rem', backgroundColor: 'rgb(201, 102, 247)' }} className='fw-bold p-3'>
                    <h4>
                      {seatNumbers}
                    </h4>
                  </div>
                </div>
              </div>
              <div className='p-3 d-flex flex-column justify-content-center align-items-center' style={{ backgroundColor: 'rgb(242, 118, 143)', marginLeft: '1px', width: '15rem' }}>
                <div>Tổng tiền</div>
                <h4 className='fw-bold'>{total}<u>đ</u></h4>
              </div>
            </div>
          </div>
          <h5 className='d-flex justify-content-center align-items-center p-3' style={{ margin: '1px', background: 'rgb(81, 156, 247)' }}>
            {showtimeChosen.room.theater.name.toUpperCase()}
          </h5>
          <div className='container text-light'>
            <h5 className='m-5 p-3 bg-secondary border border-5 rounded-5'>MÀN HÌNH</h5>
          </div>
          <div className='p-5' style={{ userSelect: 'none' }}>
            {tickets ? (
              tickets.map((item, index) => (
                <React.Fragment key={item.id}>
                  <button
                    className={`btn seat 
                      ${item.booked ? 'seat-booked' : seats.indexOf(item) === -1 ? 'seat-vacant' : 'seat-selecting'}
                      ${item.seat.category === 'Couple' ? 'seat-couple' : 'seat-standard'}
                      
                      `}
                    disabled={item.booked}
                    onClick={() => handleSelectSeats(item)}
                  >
                    {item.seat.seatNumber}
                  </button>
                  {((item.seat.category !== 'Couple' && index % 10 === 4) || (item.seat.category === 'Couple' && (index % 10 === 2 || index % 10 === 7))) && <div style={{ width: '8rem', display: 'inline-block' }}></div>}
                  {(index % 10 === 9 || (item.seat.category === 'Couple' && index % 10 === 4)) && <br />}
                </React.Fragment>
              ))
            ) : (
              <div className='d-flex justify-content-center my-5'>
                <LoadingSpinner />
              </div>
            )}
          </div>
          <div className='container d-flex justify-content-around my-5'>
            <div>
              <button className={`btn m-3 seat-note seat-note-standard-vacant`} disabled={true}></button><label>Ghế thường</label>
            </div>
            <div>
              <button className={`btn m-3 seat-note seat-note-couple-vacant`} disabled={true}></button><label>Ghế đôi</label>
            </div>
            <div>
              <button className={`btn m-3 seat-note seat-note-standard-booked`} disabled={true}></button><label>Ghế đã chọn</label>
            </div>
            <div>
              <button className={`btn m-3 seat-note seat-note-standard-selecting`} disabled={true}></button><label>Ghế đang chọn</label>
            </div>
          </div>
          <div className='container'>
            <button onClick={handleBack} className='btn btn-lg text-light fw-bold m-2' style={{ background: 'orange' }}>Quay lại</button>
            <button onClick={handleBuyFood} className='btn btn-lg text-light fw-bold m-2' style={{ background: 'orange' }}>Mua đồ ăn</button>
            <button onClick={handlePayment} className='btn btn-lg text-light fw-bold m-2' style={{ background: 'orange' }}>Thanh toán</button>
          </div>
        </div >

      ) : step === 'choose-food' ? (
        <div>
          <div className='d-flex flex-column justify-content-start align-items-center p-5' style={{ position: 'relative', overflow: 'hidden', margin: '1px', background: 'linear-gradient(176deg, rgba(251,255,200,1) 42%, rgba(149,255,169,1) 100%)' }}>
            <button onClick={handleCloseBooking} type="button" className="btn btn-lg btn-secondary rounded-circle position-absolute top-0 end-0" style={{ marginRight: '1rem', marginTop: '1rem' }}>
              <FontAwesomeIcon icon={faXmark} />
            </button>
            <div className='d-flex justify-content-start align-items-center' >
              <div className='m-3 '>Tên phim</div>
              <div className='text-success text-start display-6 fw-bold'>{showtimeChosen.movie.title}</div>
            </div>
            <div className='d-flex justify-content-center'>
              <div >
                <div id='time' className='d-flex justify-content-center' style={{ marginBottom: '1px' }}>
                  <div className='p-3' style={{ marginRight: '1px', backgroundColor: 'rgb(219, 137, 45)', width: '10rem' }}>
                    <div>Suất chiếu</div>
                    <div className='display-6 fw-bold'>
                      {format(parseISO(showtimeChosen.startAt), 'HH:mm:ss').substring(0, 5)}
                    </div>
                  </div>
                  <div className='p-3' style={{ backgroundColor: 'rgb(219, 137, 45)', width: '20rem' }}>
                    <div>
                      Ngày chiếu
                    </div>
                    <div className='display-6 fw-bold'>
                      {format(parseISO(showtimeChosen.startAt), 'dd/MM/yyyy')}
                    </div>
                  </div>
                </div>
                <div id='seat' className='d-flex justify-content-start '>
                  <div style={{ width: '10rem', backgroundColor: 'rgb(201, 102, 247)', marginRight: '1px' }} className='p-3 d-flex justify-content-center align-items-center'>Số ghế</div>
                  <div style={{ width: '20rem', backgroundColor: 'rgb(201, 102, 247)' }} className='display-6 fw-bold p-3'>A12</div>
                </div>
              </div>
              <div className='p-3 d-flex flex-column justify-content-center align-items-center' style={{ backgroundColor: 'rgb(242, 118, 143)', marginLeft: '1px', width: '15rem' }}>
                <div>Tổng tiền</div>
                <div className='display-6 fw-bold'>120000đ</div>
              </div>
            </div>
          </div>
          <div>Choose food</div>
          <div className='container'>
            <button onClick={handleBack} className='btn btn-lg text-light fw-bold m-2' style={{ background: 'orange' }}>Quay lại</button>
            <button onClick={handlePayment} className='btn btn-lg text-light fw-bold m-2' style={{ background: 'orange' }}>Thanh toán</button>
          </div>
        </div>
      ) : step === 'choose-payment' ? (
        <div>
          <div className='d-flex flex-column justify-content-start align-items-center p-5' style={{ position: 'relative', overflow: 'hidden', margin: '1px', background: 'linear-gradient(176deg, rgba(251,255,200,1) 42%, rgba(149,255,169,1) 100%)' }}>
            <button onClick={handleCloseBooking} type="button" className="btn btn-lg btn-secondary rounded-circle position-absolute top-0 end-0" style={{ marginRight: '1rem', marginTop: '1rem' }}>
              <FontAwesomeIcon icon={faXmark} />
            </button>
            <div className='d-flex justify-content-start align-items-center' >
              <div className='m-3 '>Tên phim</div>
              <div className='text-success text-start display-6 fw-bold'>{showtimeChosen.movie.title}</div>
            </div>
            <div className='d-flex justify-content-center'>
              <div >
                <div id='time' className='d-flex justify-content-center' style={{ marginBottom: '1px' }}>
                  <div className='p-3' style={{ marginRight: '1px', backgroundColor: 'rgb(219, 137, 45)', width: '10rem' }}>
                    <div>Suất chiếu</div>
                    <div className='display-6 fw-bold'>
                      {format(parseISO(showtimeChosen.startAt), 'HH:mm:ss').substring(0, 5)}
                    </div>
                  </div>
                  <div className='p-3' style={{ backgroundColor: 'rgb(219, 137, 45)', width: '20rem' }}>
                    <div>
                      Ngày chiếu
                    </div>
                    <div className='display-6 fw-bold'>
                      {format(parseISO(showtimeChosen.startAt), 'dd/MM/yyyy')}
                    </div>
                  </div>
                </div>
                <div id='seat' className='d-flex justify-content-start '>
                  <div style={{ width: '10rem', backgroundColor: 'rgb(201, 102, 247)', marginRight: '1px' }} className='p-3 d-flex justify-content-center align-items-center'>Số ghế</div>
                  <div style={{ width: '20rem', backgroundColor: 'rgb(201, 102, 247)' }} className='display-6 fw-bold p-3'>A12</div>
                </div>
              </div>
              <div className='p-3 d-flex flex-column justify-content-center align-items-center' style={{ backgroundColor: 'rgb(242, 118, 143)', marginLeft: '1px', width: '15rem' }}>
                <div>Tổng tiền</div>
                <div className='display-6 fw-bold'>120000đ</div>
              </div>
            </div>
          </div>
          <div>choose payment</div>
          <div className='container'>
            <button onClick={handleBack} className='btn btn-lg text-light fw-bold m-2' style={{ background: 'orange' }}>Quay lại</button>
            <button onClick={() => console.log("handle payment")} className='btn btn-lg text-light fw-bold m-2' style={{ background: 'orange' }}>Thanh toán</button>
          </div>
        </div>
      ) : (
        <div className='d-flex justify-content-center my-5'>
          <LoadingSpinner />
        </div>
      )
      }

      <LogOut></LogOut>
      <ChangePW {...params}></ChangePW>
      <Footer></Footer>
    </>
  )
}

export default CustomerDashBoard