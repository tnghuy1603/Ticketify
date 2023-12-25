import React from 'react';


function Home() {

    return (
        <div className="jumbotron" style={{ backgroundColor: '#f0f0f0', height: '70vh' }}>
            <h1 className="display-4">Quản Lý Phim</h1>
            <p className="lead">Đây là trang quản lý phim của website.</p>
            <hr className="my-4" />
            <p>Vui lòng chọn rạp trước khi thực hiện các thao tác quản lý.</p>
            <div>
                <a href='/' className='btn btn-primary m-3'>Quản lý phim</a>
                <a href='/' className='btn btn-primary m-3'>Xếp lịch chiếu</a>
                <a href='/' className='btn btn-primary m-3'>Xem doanh thu</a>
            </div>
        </div>
    )
}

export default Home