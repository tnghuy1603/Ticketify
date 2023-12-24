import React, { useState, useEffect } from 'react';
import useAuth from "../../hooks/useAuth";


function Content(params) {
    const auth = useAuth()

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

    return (
        <div>
            Manager Page
        </div>
    )
}

export default Content