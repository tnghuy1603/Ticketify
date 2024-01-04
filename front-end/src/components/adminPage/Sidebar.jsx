import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouse, faUsers, faComment } from "@fortawesome/free-solid-svg-icons";

const Sidebar = () => {
    const [selected, setSelected] = useState(null);
    const handleClickSelected = (index) => {
        const now = new Date().getTime();
        let lastClick = new Date().getTime() - 200;
        if (selected === index && now - lastClick < 300) {
            return;
        }

        // Update the selected item
        setSelected(index);

        // Record the timestamp of the last click
        lastClick = now;
    }
    const getLiStyle = (index) => {
        return {
            borderLeft: index === selected ? '4px solid #fff' : 'none',
        }
    }
    return (
        <section id="menu">
            <div className="logo">
                <img src="" alt="" />
                <h2>Cinema</h2>
            </div>
            <div className="items-admin">
                <li className="d-flex" onClick={() => handleClickSelected(1)} style={getLiStyle(1)}><FontAwesomeIcon icon={faHouse} className="me-3 ms-3"/><Link to={"/"}>Dashboard</Link></li>
                <li className="d-flex" onClick={() => handleClickSelected(2)} style={getLiStyle(2)}><FontAwesomeIcon icon={faUsers}  className="me-3 ms-3"/><Link to={"/admin/manage-users"}>Quản lí người dùng</Link></li>
                <li className="d-flex" onClick={() => handleClickSelected(3)} style={getLiStyle(3)}><FontAwesomeIcon icon={faComment} className="me-3 ms-3"/><Link to={"/admin/manage-comments"}>Quản lí bình luận</Link></li>

            </div>
        </section>

    )
}

export default Sidebar