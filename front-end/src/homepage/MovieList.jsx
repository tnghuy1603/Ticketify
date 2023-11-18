import React from "react";

function MovieList() {
    return (
        <div className="container movie-list">
            <div className="row">
                <div className="col-12">

                    <div className="btn-group movie-list-menu" role="group" aria-label="Basic radio toggle button group">
                        <span className="movie-list-border-solid"></span>
                        <h3 className="me-4">Phim</h3>
                        <input type="radio" className="btn-check movie-list-item" name="btnradio" id="btnradio1" autocomplete="off" checked />
                        <label className="btn btn-outline-primary me-4 rounded-2" for="btnradio1">Phim đang chiếu</label>

                        <input type="radio" className="btn-check movie-list-item" name="btnradio" id="btnradio2" autocomplete="off" />
                        <label className="btn btn-outline-primary rounded-2" for="btnradio2">Phim sắp chiếu</label>

                    </div>
                </div>
            </div>



            <div className="row mt-4">
                <div className="col-3 mt-4">
                    <div class="card movie-list-card">
                        <img className="card-img-top movie-item-img" src="https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_Ratio0.6763_AL_.jpg" alt="..." />

                        <div className="ratings"></div>
                    </div>
                    <div className="mt-3">
                        <span className="movie-item-name">Infinity War</span>

                    </div>
                </div>

                <div className="col-3 mt-4">
                    <div class="card movie-list-card">
                        <img className="card-img-top movie-item-img" src="https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_Ratio0.6763_AL_.jpg" alt="..." />

                        <div className="ratings"></div>
                    </div>
                    <div className="mt-3">
                        <span className="movie-item-name">Infinity War</span>

                    </div>
                </div>

                <div className="col-3 mt-4">
                    <div class="card movie-list-card">
                        <img className="card-img-top movie-item-img" src="https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_Ratio0.6763_AL_.jpg" alt="..." />

                        <div className="ratings"></div>
                    </div>
                    <div className="mt-3">
                        <span className="movie-item-name">Infinity War</span>

                    </div>
                </div>

                <div className="col-3 mt-4">
                    <div class="card movie-list-card">
                        <img className="card-img-top movie-item-img" src="https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_Ratio0.6763_AL_.jpg" alt="..." />

                        <div className="ratings"></div>
                    </div>
                    <div className="mt-3">
                        <span className="movie-item-name">Infinity War</span>

                    </div>
                </div>

                <div className="col-3 mt-4">
                    <div class="card movie-list-card">
                        <img className="card-img-top movie-item-img" src="https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_Ratio0.6763_AL_.jpg" alt="..." />

                        <div className="ratings"></div>
                    </div>
                    <div className="mt-3">
                        <span className="movie-item-name">Infinity War</span>

                    </div>
                </div>

                <div className="col-3 mt-4">
                    <div class="card movie-list-card">
                        <img className="card-img-top movie-item-img" src="https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_Ratio0.6763_AL_.jpg" alt="..." />

                        <div className="ratings"></div>
                    </div>
                    <div className="mt-3">
                        <span className="movie-item-name">Infinity War</span>

                    </div>
                </div>

                <div className="col-3 mt-4">
                    <div class="card movie-list-card">
                        <img className="card-img-top movie-item-img" src="https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_Ratio0.6763_AL_.jpg" alt="..." />

                        <div className="ratings"></div>
                    </div>
                    <div className="mt-3">
                        <span className="movie-item-name">Infinity War</span>

                    </div>
                </div>

                <div className="col-3 mt-4">
                    <div class="card movie-list-card">
                        <img className="card-img-top movie-item-img" src="https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_Ratio0.6763_AL_.jpg" alt="..." />

                        <div className="ratings"></div>
                    </div>
                    <div className="mt-3">
                        <span className="movie-item-name">Infinity War</span>

                    </div>
                </div>
            </div>
        </div>
    )
}

export default MovieList