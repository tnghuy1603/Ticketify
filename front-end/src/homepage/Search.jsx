import React, { useState } from 'react';

const SearchButton = () => {
    const [isExpanded, setIsExpanded] = useState(false);

    const handleToggle = () => {
        setIsExpanded(!isExpanded);
    };

    return (
        <div className="container mt-3">
            <div className="input-group">
                <input
                    type="text"
                    className={`form-control ${isExpanded ? 'rounded-0' : 'rounded'}`}
                    placeholder="Search..."
                    aria-label="Search"
                    aria-describedby="basic-addon2"
                    style={{ display: isExpanded ? 'block' : 'none' }}
                />
                <div className="input-group-append">
                    <button
                        className="btn btn-outline-secondary"
                        type="button"
                        onClick={handleToggle}
                    >
                        {isExpanded ? 'Minimize' : 'Search'}
                    </button>
                </div>
            </div>
        </div>
    );
};

export default SearchButton;
