import React from "react";
import MovieBanner from "./MovieBanner";
import MovieList from "./MovieList";

function Content(username) {
    return (
        <div>
            <MovieBanner {...username}/>
            <MovieList {...username}/>
        </div>
    )
}

export default Content