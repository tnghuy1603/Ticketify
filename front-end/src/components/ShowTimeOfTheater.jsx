import axios from 'axios';
import React, { useEffect, useState } from 'react'

function ShowTimeOfTheater() {
    const [theaters, setTheaters] = useState([]);
    const [selectedTheaterId, setSelectedTheaterId] = useState(1);
    //schedule contains movies and showtimes
    const [schedule, setSchedule] = useState(null);
    useEffect(() => {
        const getSchedule = async () => {
            const res = await axios.get(`http://localhost:8080/showtime/`, {
                headers:{
                    'Content-Type': 'application/json'
                },
                params: {
                    theater: selectedTheaterId,
                    available: true
                }

            })
            console.log(res.data);
            setSchedule(res.data);
        };
        getSchedule();
    }, [selectedTheaterId]);
    useEffect(() => {
        const getTheaters = async () =>{
            const res = await axios.get(`http://localhost:8080/theaters`)
            setTheaters(res.data);
            console.log(res.data)
        };
        getTheaters();
    }, [])
  return (
    <>
        <div>Showtime of theater</div>
    </>
  )
}

export default ShowTimeOfTheater