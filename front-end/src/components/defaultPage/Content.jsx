import React from "react";
import MovieBanner from "./MovieBanner";
import MovieList from "./MovieList";

function Content() {
    return (
        <main className="main">
            <MovieBanner />
            <MovieList />
        </main>
    )
}

export default Content