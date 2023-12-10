import { useState } from "react";
import useAuth from "../hooks/useAuth";
import axios from "axios";
import useLocalStorage from "../hooks/useLocalStorage";
import LoadingSpinner from "./defaultPage/Loading"

const PrivateRoute = ({ children }) => {
    const auth = useAuth()
    const [accessToken, setAccessToken] = useLocalStorage('accessToken', '')
    const [isLoading, setIsLoading] = useState(true);
    const [isValid, setIsValid] = useState(null);
    if (auth.accessToken) {
        axios.get(`http://localhost:8080/auth/validate?token=${auth.accessToken}`, {
            headers: {
                'Authorization': `Bearer ${auth.accessToken}`
            },
            params: {
                accessToken: auth.accessToken
            }
        }).then((res) => {
            setIsValid(res.data);
            setIsLoading(false);
        })
    } else {
        // alert('Vui lòng đăng nhập trước khi thực hiện tác vụ này!');
    }
    if (isLoading) {
        return <div className='d-flex justify-content-center align-items-center my-5' style={{ height: '100vh' }}>
            <LoadingSpinner />
        </div>;
    } else if (isValid) {
        return children
    } else {
        // alert('Vui lòng đăng nhập trước khi thực hiện tác vụ này!');
    }

}

export default PrivateRoute