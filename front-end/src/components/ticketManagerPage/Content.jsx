import React, { useState, useEffect } from 'react';
import useAuth from "../../hooks/useAuth";
import LoadingSpinner from '../defaultPage/Loading'
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouse, faMagnifyingGlass, faAdd } from "@fortawesome/free-solid-svg-icons";
import { Form } from 'react-bootstrap';


function Content({ theater, theaterName, handleChosen, chosen, params }) {
    const auth = useAuth()
    const handleChosenData = (a) => {
        handleChosen(a);
    }

    const [movies, setMovies] = useState(null);

    const getMovie = async () => {
        try {
            // Địa chỉ API và tham số
            const apiUrl = 'http://localhost:8080/movies/manager';

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
                setMovies(result);
            }
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    useEffect(() => {
        getMovie();
    }, []);
    useEffect(() => {
        console.log(movies);
    }, [movies]);
    useEffect(() => {
        console.log(theater, theaterName);
    }, [theater, theaterName]);

    return (
        <>
            {chosen === 'home' ? (
                <div className="jumbotron" style={{ backgroundColor: '#f0f0f0', height: '70vh' }}>
                    <h1 className="display-4">Quản Lý Phim</h1>
                    <p className="lead">Đây là trang quản lý phim của website.</p>
                    <hr className="my-4" />
                    <p>Vui lòng chọn rạp trước khi thực hiện các thao tác quản lý.</p>
                    <div>
                        <button onClick={() => handleChosenData('movie')} className='btn btn-primary m-3'>Quản lý phim</button>
                        <button onClick={() => handleChosenData('showtime')} className='btn btn-primary m-3'>Xếp lịch chiếu</button>
                        <button onClick={() => handleChosenData('profit')} className='btn btn-primary m-3'>Xem doanh thu</button>
                    </div>
                </div>
            ) : chosen === 'movie' ? (
                <div className="p-4" style={{ backgroundColor: '#f0f0f0' }}>
                    <div className='m-3 d-flex justify-content-start align-items-center'>
                        <div>
                            <FontAwesomeIcon icon={faHouse} /> Trang chủ
                        </div>

                    </div>
                    <div className='m-3 d-flex justify-content-start align-items-center'>
                        <div className="search-container">
                            <form className="d-flex" role="search">
                                <input className="form-control me-2" type="search" placeholder="Tìm kiếm" aria-label="Search" />
                                <button className="btn btn-outline-success" type="submit">
                                    <FontAwesomeIcon icon={faMagnifyingGlass} className='search-icon' />
                                </button>
                            </form>
                        </div>
                        <Form.Group controlId="theater" className='d-flex'>
                            <Form.Label className='d-flex justify-content-center fs-5'>Tìm theo</Form.Label>
                            <Form.Control as="select" >
                                {/* {theater.map((item) => (
                                    <option key={item.id} value={item.id}>{item.name}</option>
                                ))} */}
                            </Form.Control>
                        </Form.Group>
                        <div>
                            <button className='btn btn-primary'><FontAwesomeIcon icon={faAdd} /> Thêm mới</button>
                        </div>
                    </div>
                    <table className="table table-bordered shadow">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">First</th>
                                <th scope="col">Last</th>
                                <th scope="col">Handle</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">2</th>
                                <td>Jacob</td>
                                <td>Thornton</td>
                                <td>@fat</td>
                            </tr>
                            <tr>
                                <th scope="row">3</th>
                                <td colSpan="2">Larry the Bird</td>
                                <td>@twitter</td>
                            </tr>
                        </tbody>
                    </table>

                </div>
            ) : chosen === 'showtime' ? (
                <div className="jumbotron" style={{ backgroundColor: '#f0f0f0', height: '70vh' }}>
                    lịch chiếu
                </div>
            ) : chosen === 'profit' ? (
                <div className="jumbotron" style={{ backgroundColor: '#f0f0f0', height: '70vh' }}>
                    doanh thu
                </div>
            ) : (
                <div className='d-flex justify-content-center my-5'>
                    <LoadingSpinner />
                </div>
            )}

            {/* {movies ? (
                movies.map((item, index) => (
                    <></>
                  ))
            ) : (
                <div className='d-flex justify-content-center my-5'>
                    <LoadingSpinner />
                </div>
            )} */}

        </>
    )
}

export default Content