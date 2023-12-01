import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import Header from '../components/homepage/header';
import Content from '../components/homepage/Content'
import LoginSignup from '../components/homepage/Login';
import Footer from '../components/homepage/Footer'


function HomePage() {
    // const navigate = useNavigate()
    // const [ongoingMovies, setOngoingMovies] = useState([]);
    // const [upComingMovies, setUpcomingMovies] = useState([]);
    // const [viewUpcoming, setViewUpcoming] = useState(false)
    // useEffect(() => {
    //     const getUpcomingMovies = async () => {
    //         const res = await axios.get(`http://localhost:8080/movies`, {
    //             headers: {
    //                 'Content-Type' : 'application/json'
    //             },
    //             params: {
    //                 status: 'Upcoming'
    //             }
                
    //         })
    //         setUpcomingMovies(res.data)
    //         console.log(res.data);
    //     }
    //     const getOngoingMovies = async () => {
    //         const res = await axios.get(`http://localhost:8080/movies`, {
    //             headers:{
    //                 headers: {
    //                     'Content-Type' : 'application/json'
    //                   }
    //             },
    //             params: {
    //                 status: 'Ongoing'
    //             }
    //         })
    //         setOngoingMovies(res.data);
    //         console.log(res.data)
    //     }
    //     getUpcomingMovies();
    //     getOngoingMovies();
    // }, [])
    // const redirectToMovieDetails = (movie) => {
    //     navigate(`/${movie.id}`, {state: {movie}})
    // }

    
    
  return (
    <>
        <Header></Header>
        <Content></Content>
        <LoginSignup></LoginSignup>
        <Footer></Footer>
        {/* {viewUpcoming ?
            <div>
            {upComingMovies.map(movie => <>
                <div className='card' key={movie.id}>
                    <div className='card-title'>{movie.title}</div>
                    <div className='card-body'>
                        <div className='btn btn-primary' onClick={redirectToMovieDetails}>View details</div>
                    </div>
                </div>
            </>)}
            </div>
            : 
            <div>
                {ongoingMovies.map(movie => <>
                    <div className='card' key={movie.id}>
                        <div className='card-title'>{movie.title}</div>
                        <div className='card-body'>
                            <div className='btn btn-primary' onClick={redirectToMovieDetails}>View details</div>
                        </div>
                    </div>
                </>)}
            </div>
            
        } */}
       
    </>
  )
}

export default HomePage