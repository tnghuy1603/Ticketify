import React, { useState, useEffect } from 'react';
import { Form } from 'react-bootstrap';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouse } from "@fortawesome/free-solid-svg-icons";
import './styles.css'

function SideBar({ theater, handleChangeTheater, handleChosen, chosen, isCollapse }) {
    const handleChosenData = (a) => {
        handleChosen(a);
    }

    return (
        <>
            <nav id="sidebar" className={`py-4 col-2 ${!isCollapse ? '' : 'collapse'}`}>
                <div onClick={() => handleChosenData('home')} className='fs-5 fw-bold my-5' style={{ cursor: 'context-menu' }}>
                    <FontAwesomeIcon icon={faHouse} /> Trang chủ quản lý
                </div>
                <Form.Group controlId="theater" className='my-5'>
                    <Form.Label className='d-flex justify-content-center fs-5'>Chọn rạp</Form.Label>
                    <Form.Control as="select" onChange={handleChangeTheater}>
                        {theater.map((item) => (
                            <option key={item.id} value={item.id}>{item.name}</option>
                        ))}
                    </Form.Control>
                </Form.Group>
                <div onClick={() => handleChosenData('movie')} id='movie' className={`items p-3 ${chosen === 'movie' ? 'activeItems' : ''}`}>Quản lý phim</div>
                <div onClick={() => handleChosenData('showtime')} id='showtime' className={`items p-3 ${chosen === 'showtime' ? 'activeItems' : ''}`}>Xếp lịch chiếu</div>
                <div onClick={() => handleChosenData('profit')} id='profit' className={`items p-3 ${chosen === 'profit' ? 'activeItems' : ''}`}>Doanh thu</div>
            </nav>
            <nav id="sidebar-background" className={`col-2 ${!isCollapse ? '' : 'collapse'}`}>
            </nav>
        </>
    )
}

export default SideBar