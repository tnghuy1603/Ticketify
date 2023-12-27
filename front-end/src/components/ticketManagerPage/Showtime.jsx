import React, { useState, useEffect } from 'react';
import useAuth from "../../hooks/useAuth";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouse, faMagnifyingGlass, faAdd, faEdit, faRemove, faAngleRight, faUnsorted, faSortUp, faSortDown } from "@fortawesome/free-solid-svg-icons";
import LoadingSpinner from '../defaultPage/Loading'
import { set } from 'date-fns';
import { Form } from 'react-bootstrap';

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
    const getRoom = async () => {
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

    useEffect(() => {
        console.log(theaterChosen);
    }, [theaterChosen]);

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

                <div className='m-3 d-flex justify-content-between align-items-center'>
                    <form className='mx-3 d-flex justify-content-start align-items-center'>
                        <label htmlFor='chooseTheater' className='mx-2'>Chọn rạp</label>
                        <select id='chooseTheater' className='p-1 rounded-2' style={{ backgroundColor: 'white', color: 'black' }} onChange={handleChangeTheater}>
                            {theater.map((item) => (
                                <option key={item.id} value={item.id}>{item.name}</option>
                            ))}
                        </select>
                    </form>
                    <div className='d-flex justify-content-start align-items-center'>
                        <form className='mx-3 d-flex justify-content-start align-items-center'>
                            <label htmlFor='filter' className='mx-2'>Tìm theo</label>
                            <select className='p-1 rounded-2' style={{ backgroundColor: 'white', color: 'black' }}>
                                <></>
                            </select>
                        </form>
                        <div className="search-container">
                            <div className="d-flex">
                                <input className="form-control me-2" type="search" placeholder="Tìm kiếm" aria-label="Search" />
                                <button className="btn btn-outline-success">
                                    <FontAwesomeIcon icon={faMagnifyingGlass} className='search-icon' />
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </>
    )
}

export default Showtime