import React, { useEffect, useState } from "react";
import axios from "axios"
import useAuth from "../../hooks/useAuth";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFilter, faChevronDown } from "@fortawesome/free-solid-svg-icons";
import 'bootstrap/dist/css/bootstrap.min.css'

const ManageUser = () => {
    const auth = useAuth();
    const [search, setSearch] = useState('');
    const [selectedOption, setSelectedOption] = useState('');
    const [selectedStatus, setSelectedStatus] = useState('');
    const [searchPage, setSearchPage] = useState('');
    const [users, setUsers] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const recordsPerPage = 7;
    const lastIndex = currentPage * recordsPerPage;
    const firstIndex = lastIndex - recordsPerPage;
    const records = users.slice(firstIndex, lastIndex);
    const numberOfPages = Math.ceil(users.length / recordsPerPage);
    const maxPageDisplay = 5;
    // console.log(dataSearch);
    const getUsers = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/users`, {
                headers: {
                    'Authorization': `Bearer ${auth.accessToken}`
                },
            });
            setUsers(response.data);
        } catch (error) {
            console.log(error);
        }
    }

    const getPages = () => {
        const startPage = Math.max(currentPage - Math.floor(maxPageDisplay / 2), 1);
        const endPage = Math.min(startPage + maxPageDisplay - 1, numberOfPages);
        return Array.from({ length: endPage - startPage + 1 }, (_, index) => startPage + index);
    };

    const handleClickPrev = () => {
        if (currentPage > 1)
            setCurrentPage(currentPage - 1);
    }

    const handleClickNext = () => {
        if (currentPage < getPages().length)
            setCurrentPage(currentPage + 1);
    }

    const changeCurrentPage = (page) => {
        setCurrentPage(page)
    }

    const handleSearchPage = () => {
        setCurrentPage(searchPage);
    }

    const handleOptionChange = (e) => {
        const newOption = e.target.value;
        setSelectedOption(newOption);
    };

    const handleStatusChange = (e) => {
        const newOption = e.target.value;
        setSelectedStatus(newOption);
    };

    useEffect(() => {
        getUsers();
    }, [])

    useEffect(() => {
        setCurrentPage(1);
    }, [search, users, recordsPerPage]);

    // console.log(dataOption);
    return (
        <>
            <div className="row justify-content-between">
                <h2 className="i-name w-25 pe-5 mt-3">Manage Users</h2>
                <div className="btn-add-user-wrapper">

                <button type="button" className="btn-add-user btn btn-outline-secondary">Add user</button>
                </div>
            </div>
            <div className="table-filter mb-0 pb-0">
                <div className="row">
                    <div className="col-sm-9 ms-auto mt-4">
                        <div className="filter-group d-flex">
                            <label>Search</label>
                            <input type="text" className="form-control" onChange={(e) => setSearch(e.target.value)} />
                        </div>
                        <div className="filter-group d-flex">
                            <label>Information</label>
                            <select className="form-control" onChange={handleOptionChange}>
                                <option value="id">ID</option>
                                <option value="displayName">Name</option>
                                <option value="email">Email</option>
                                <option value="role">Role</option>
                            </select>
                        </div>
                        <div className="filter-group d-flex">
                            <label>Status</label>
                            <select className="form-control" onChange={handleStatusChange}>
                                <option value="active">Active</option>
                                <option value="inactive">Inactive</option>
                            </select>
                        </div>
                        <span className="filter-icon-wrapper"><FontAwesomeIcon icon={faFilter} className="filter-icon" /></span>
                    </div>
                </div>
            </div>
            <div className="board">
                <table width="100%">
                    <thead>
                        <tr>
                            <td className="text-center">#</td>
                            <td className="text-center">Name</td>
                            <td className="text-center">Email</td>
                            <td className="text-center">Status</td>
                            <td className="text-center">Role</td>
                            <td className="text-center"></td>
                        </tr>
                    </thead>

                    <tbody>
                        {
                            users && users.filter((itemStatus) => {
                                if (selectedStatus === 'active') {
                                    return itemStatus.locked === false;
                                }

                                else if (selectedStatus === 'inactive') {

                                    return itemStatus.locked === true;
                                }
                                return itemStatus;
                            }).filter((item) => {
                                let lowerCaseSearch = search.toLowerCase();
                                if (selectedOption === 'id') {
                                    lowerCaseSearch = search;
                                    return lowerCaseSearch === '' ? item : item[`${selectedOption}`].toString().match(lowerCaseSearch)
                                }
                                return lowerCaseSearch === '' ? item : item[`${selectedOption}`].toLowerCase().includes(lowerCaseSearch)
                            }).slice(firstIndex, lastIndex).map((user, index) => (
                                <tr key={index}>
                                    <td className="pb-4">
                                        {user.id}
                                    </td>
                                    <td className="people px-auto">
                                        <h5 className="mt-1">
                                            {user.displayName}
                                        </h5>
                                    </td>

                                    <td className="people-des">
                                        <p>{user.email}</p>
                                    </td>

                                    <td className="active"><p>{user.locked === false ? "Active" : "Inactive"}</p></td>

                                    <td className="role">
                                        <p>{user.role.replace("ROLE_", "")}</p>
                                    </td>

                                    <td className="edit">
                                        <a className="me-3" type="button" data-bs-toggle="modal" data-bs-target="#LockModal">Lock</a>
                                    </td>
                                </tr>
                            ))}
                    </tbody>
                </table>

                <nav aria-label="Page navigation example">
                    <ul className="pagination justify-content-center">
                        <li className="page-item">
                            <button className="page-link" href="#" aria-label="Previous" onClick={handleClickPrev}>
                                <span aria-hidden="true">&laquo;</span>
                            </button>
                        </li>
                        {
                            getPages().map((page, index) => (

                                <li className={`page-item ${currentPage === page || searchPage.match(page) ? "active" : ""}`} key={index}><button className="page-link" href="#" onClick={() => changeCurrentPage(page)}>{page}</button></li>
                            ))
                        }

                        <li className="page-item">
                            <button className="page-link" href="#" aria-label="Next" onClick={handleClickNext}>
                                <span aria-hidden="true">&raquo;</span>
                            </button>
                        </li>
                        <input
                            type="text"
                            className="bg-white text-black search-page-input"
                            value={searchPage}
                            onInput={(e) => setSearchPage(e.target.value)}
                        />
                        <button onClick={handleSearchPage} className="ms-3 btn btn-outline-primary">Search</button>
                    </ul>
                </nav>

                <div className="modal fade" id="LockModal" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h1 className="modal-title fs-5" id="exampleModalLabel">Lock user</h1>
                                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div className="modal-body">
                                Do you want to lock this user ?
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="button" className="btn btn-primary">Confirm</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default ManageUser