import React, { useState, useEffect } from 'react';
import useAuth from "../../hooks/useAuth";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouse, faAdd, faAngleRight } from "@fortawesome/free-solid-svg-icons";
import LoadingSpinner from '../defaultPage/Loading'
import { addDays, format, parseISO } from 'date-fns';

function Showtime() {
    const auth = useAuth()
    const [chainAction, setChainAction] = useState([{ text: 'Xếp lịch chiếu', href: '/manage/showtime' }]);

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

    const [room, setRoom] = useState([]);
    const [roomOption, setRoomOption] = useState([]);
    const [roomChosen, setRoomChosen] = useState(null);
    const getRoom = async () => {
        try {
            // Địa chỉ API và tham số
            const apiUrl = 'http://localhost:8080/rooms';

            // Gọi API bằng phương thức GET
            const response = await fetch(apiUrl, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${auth.accessToken}`
                }
            });

            // Kiểm tra trạng thái của response
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            else {
                // Chuyển đổi response thành JSON
                const result = await response.json();
                // Cập nhật state với dữ liệu từ API
                setRoom(result);
            }
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    const handleChangeTheater = (event) => {
        setTheaterChosen(event.target.value);
    };

    const handleChangeRoom = (event) => {
        setRoomChosen(event.target.value);
    };

    useEffect(() => {
        getTheater();
        getRoom();
        getDefaultDate();
        generateTimeList();
    }, []);

    useEffect(() => {
        if (theater.length > 0) {
            setTheaterChosen(theater[0]?.id);
        }
    }, [theater]);

    useEffect(() => {
        if (theaterChosen !== null && theaterChosen !== undefined && room.length > 0) {
            setRoomOption(room.filter(e => e.theater.id === parseInt(theaterChosen)));
        }
    }, [room]);

    useEffect(() => {
        if (theaterChosen !== null && theaterChosen !== undefined && room.length > 0) {
            setRoomOption(room.filter(e => e.theater.id === parseInt(theaterChosen)));
        }
    }, [theaterChosen]);

    useEffect(() => {
        if (roomOption.length > 0) {
            setRoomChosen(roomOption[0]?.id);
        }
    }, [roomOption]);

    useEffect(() => {
        if (roomChosen !== null) {
            getShowTime(theaterChosen, roomChosen, defaultDate[0]);
            console.log(timeList);
            console.log(defaultDate);
        }
    }, [roomChosen]);

    const [date, setDate] = useState([]);
    const [defaultDate, setDefaultDate] = useState([]);
    const [showtime, setShowtime] = useState([]);

    const getDefaultDate = () => {
        const today = new Date();
        today.setHours(0, 0, 0, 0); // Đặt giờ, phút, giây và mili giây về 0 để so sánh chính xác ngày

        const sevenDays = Array.from({ length: 7 }, (_, index) => {
            const currentDate = new Date(today);
            currentDate.setDate(today.getDate() + index);
            return format(currentDate, 'yyyy-MM-dd');
        });

        setDefaultDate(sevenDays);
    };

    

    const [timeList, setTimeList] = useState([]);

    const generateTimeList = () => {
        const timeList = [];
        let currentTime = new Date("2000-01-01T00:00:00"); // Sử dụng một ngày cố định (đủ dài) để đảm bảo chạy qua cả 24 giờ

        while (currentTime.getDate() === 1) {
            const hours = currentTime.getHours().toString().padStart(2, "0");
            const minutes = currentTime.getMinutes().toString().padStart(2, "0");
            const timeString = `${hours}:${minutes}`;
            timeList.push(timeString);

            // Thêm 30 phút cho thời gian hiện tại
            currentTime.setMinutes(currentTime.getMinutes() + 30);
        }
        setTimeList(timeList);
    }

    const getShowTime = async (theater, room, date) => {
        try {
            // Địa chỉ API và tham số
            const apiUrl = `http://localhost:8080/showtimes?theater=${theater}&date=${date}`;

            // Gọi API bằng phương thức GET
            const response = await fetch(apiUrl);

            // Kiểm tra trạng thái của response
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            else {
                // Chuyển đổi response thành JSON
                const result = await response.json();
                const temp = result.showTimes;
                const data = temp.filter(e => e.room.id === parseInt(room));
                data.sort((a, b) => new Date(a.startAt) - new Date(b.startAt));
                console.log(data);
                setShowtime(data);
            }
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    const compareTimes = (time1, time2) => {
        // Chia chuỗi thời gian thành giờ và phút
        if (time1.length > time2.length) {
            time1 = time1.split('T')[1];
        } else {
            time2 = time2.split('T')[1];
        }
        const [hour1, minute1] = time1.split(':').map(Number);
        const [hour2, minute2] = time2.split(':').map(Number);

        console.log(hour1, minute1);
        console.log(hour2, minute2);
        // So sánh giờ và phút
        if (hour1 < hour2 || (hour1 === hour2 && minute1 < minute2)) {
            return -1;
        } else if (hour1 > hour2 || (hour1 === hour2 && minute1 > minute2)) {
            return 1;
        } else {
            return 0;
        }
    };

    let i =0;
    
    return (
        <>
            <h3 className='m-2'>Vui lòng chọn rạp trước khi thực hiện thao tác quản lý.</h3>
            <div className="p-4" style={{ backgroundColor: '#f0f0f0' }}>
                <div className='m-3 d-flex justify-content-start align-items-center'>
                    <div>
                        <a className='text-black' href='/manage/home'><FontAwesomeIcon icon={faHouse} /> Trang chủ</a>
                    </div>
                    {chainAction.map((item, index) => (
                        <div key={index}>
                            <FontAwesomeIcon className='mx-3' icon={faAngleRight} />
                            <a className='text-black' href={item.href}>{item.text}</a>
                        </div>
                    ))}
                </div>
                <hr />

                <div className='my-3 mx-1 d-flex justify-content-between align-items-center'>
                    <form className='mx-3 d-flex justify-content-start align-items-center'>
                        <label htmlFor='chooseTheater' className='mx-2'>Chọn rạp</label>
                        <select id='chooseTheater' className='p-1 rounded-2' style={{ backgroundColor: 'white', color: 'black' }} onChange={handleChangeTheater}>
                            {theater.map((item) => (
                                <option key={item.id} value={item.id}>{item.name}</option>
                            ))}
                        </select>
                    </form>
                    <form className='mx-3 d-flex justify-content-start align-items-center'>
                        <label htmlFor='chooseRoom' className='mx-2'>Chọn Phòng</label>
                        <select id='chooseRoom' className='p-1 rounded-2' style={{ backgroundColor: 'white', color: 'black' }} onChange={handleChangeRoom}>
                            {roomOption.map((item) => (
                                <option key={item.id} value={item.id}>{item.roomNumber}</option>
                            ))}
                        </select>
                    </form>
                    <form className='mx-3 d-flex justify-content-start align-items-center'>
                        <label htmlFor='from' className='mx-2'>Từ ngày</label>
                        <input id='from' className='p-1 rounded-2' type='date'
                            style={{ color: 'black', background: 'linear-gradient(90deg, rgba(255,255,255,1) 80%, rgba(101, 190, 195,1) 80%, rgba(101, 190, 195,1) 100%)' }} ></input>
                    </form>
                    <form className='mx-3 d-flex justify-content-start align-items-center'>
                        <label htmlFor='to' className='mx-2'>Đến ngày</label>
                        <input id='to' className='p-1 rounded-2' type='date'
                            style={{ color: 'black', background: 'linear-gradient(90deg, rgba(255,255,255,1) 80%, rgba(101, 190, 195,1) 80%, rgba(101, 190, 195,1) 100%)' }} ></input>
                    </form>
                </div>
                <div id='table' className='bg-light p-1 shadow' style={{ overflowX: 'auto' }}>
                    <div id='header' className='d-flex'>
                        {defaultDate.length > 0 ? (
                            <>
                                <div className='d-flex flex-column' style={{ width: '5rem' }}>
                                    <div className='p-2 fw-bold rounded-2 border border-light d-flex justify-content-center align-items-center'
                                        style={{ width: '5rem', height: '3rem', background: '#cfe2ff' }}>Ngày</div>
                                </div>
                                {defaultDate.map((item) => (
                                    <div key={item} className='p-2 fw-bold rounded-2 border border-light d-flex justify-content-center align-items-center'
                                        style={{ width: '100%', height: '3rem', minWidth: '8rem', background: '#cfe2ff' }}>{item}</div>

                                ))}
                            </>
                        ) : (<></>)}
                    </div>
                    <div className='d-flex'>
                        <div id='body' className='d-flex flex-column' style={{ width: '5rem' }}>
                            {timeList.length > 0 ? (
                                <>
                                    {timeList.map((item) => (
                                        <div key={item} className='p-2 fw-bold rounded-2 border border-light d-flex justify-content-center align-items-center'
                                            style={{ width: '5rem', height: '3rem', background: '#cfe2ff' }}>{item}</div>
                                    ))}
                                </>
                            ) : (<></>)}
                        </div>
                        <div id='body' className='d-flex flex-column' style={{ width: '100%', minWidth: '8rem' }}>
                            {timeList.length > 0 ? (
                                <>
                                    {timeList.map((item, index) => (
                                        console.log(i), i++
                                        // compareTimes(timeList[index+1], )
                                        // <div key={item} className='p-2 fw-bold showtime rounded-2 border border-light d-flex justify-content-center align-items-center'
                                        //     style={{ width: '100%', height: `${3}rem`, minWidth: '8rem', background: 'white', color: 'gray' }}>
                                        //     <FontAwesomeIcon className='mx-3' icon={faAdd} />
                                        // </div>
                                    ))}
                                </>
                            ) : (<></>)}
                        </div>


                    </div>
                </div>
            </div >
        </>
    )
}

export default Showtime