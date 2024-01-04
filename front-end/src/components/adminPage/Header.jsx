import React, { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBell, faMagnifyingGlass, faBars, faUser } from "@fortawesome/free-solid-svg-icons";

const Header = ({ onSearchHeader, onOptionChange }) => {
    const [search, setSearch] = useState('');
    const [selectedOption, setSelectedOption] = useState('');
    const sendDataSearch = () => {
        onSearchHeader(search);
    }

    const handleOptionChange = (e) => {
        const newOption = e.target.value;
        setSelectedOption(newOption);

        onOptionChange(newOption);
    };

    useEffect(() => {
        sendDataSearch();
    }, [search])
    return (
        <div className="navigation">
            <div className="collapse navbar-collapse d-flex justify-content-end mx-4" id="navbarNavDropdown">
                <ul className="navbar-nav nav-bar">
                    <li className="nav-item dropdown ps-4">
                        <a className="nav-link dropdown-toggle text-light" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <FontAwesomeIcon icon={faUser} className="ms-0 ps-0 text-black" />
                        </a>
                        <ul className="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                            <li><a className="dropdown-item btn-logout" href="#" >Đăng xuất</a></li>
                            <li><a className="dropdown-item btn-changePW" href="#">Đổi mật khẩu</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    )
}

export default Header