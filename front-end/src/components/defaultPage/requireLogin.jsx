import React, { useEffect, useState, useRef } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark } from "@fortawesome/free-solid-svg-icons";
import useAuth from '../../hooks/useAuth';

function RequireLogin() {
    return (
        <div className="modal fade" id={`MovieCard-modal`} aria-labelledby="exampleModalLabel" onClick={(e) => { e.stopPropagation() }}>
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalLabel">Thông báo</h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        Vui lòng đăng nhập trước khi thực hiện thao tác này!
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-primary" data-bs-dismiss="modal" onClick={() => {
                            $('.login-modal').addClass('display');
                            $('.login-close-btn').on('click', function () {
                                $('.login-modal').removeClass('display');
                            });
                        }}>Đăng nhập</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default RequireLogin