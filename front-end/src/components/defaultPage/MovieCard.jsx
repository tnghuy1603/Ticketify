import React, { useState, useEffect } from 'react';
import './styles.css';
import useAuth from '../../hooks/useAuth';
import { redirect } from 'react-router-dom';

const MovieCard = ({ isLogin, index, id, poster, title, cast, director, trailerLink, onClick }) => {
    const styles = ['rgb(219, 137, 45)', 'rgb(81, 156, 247)', 'rgb(201, 102, 247)', 'rgb(242, 118, 143)'];
    const handleOrderClick = () => {
        if (isLogin) {
            window.location.href = '/order';
        } else {
            alert("Vui lòng đăng nhập trước khi thực hiện thao tác này!");
            $('.login-modal').addClass('show');
            $('.login-close-btn').on('click', function() {
                $('.login-modal').removeClass('show');
            });
        }
    };
    // useEffect(() => {
        
    // }, [isLogin]);
    return (
        <div style={{ backgroundColor: styles[index % 4], userSelect: 'none' }} id={`${id}`}
            className="movie-card col-md-6 m-0 p-3 shadow border border-5 rounded-5 d-flex justify-content-around text-light"
            onClick={() => onClick(index)}>
            <img src={poster} alt={`${id} Poster`} style={{ height: "20rem" }} />
            <div className="d-flex flex-column justify-content-between" style={{ width: "10rem" }}>
                <h5 className="mx-auto">{title}</h5>
                <div>
                    <p className="mx-auto">Đạo diễn: {director}</p>
                    <p className="mx-auto">Diễn viên: {cast}</p>
                </div>
                <div style={{ width: "100%", height: "1px", backgroundColor: styles[(index + 1) % 4] }}></div>
                <div className="d-flex justify-content-around">
                    <a href={`${trailerLink}`} style={{ backgroundColor: styles[(index + 1) % 4] }} className="btn text-light">Trailer</a>
                    <a onClick={(e) => {
                        e.stopPropagation();
                        handleOrderClick();
                    }}
                        style={{ backgroundColor: styles[(index + 1) % 4] }} className="btn text-light">Đặt vé</a>
                </div>
            </div>
        </div>
    );
};

export default MovieCard;
