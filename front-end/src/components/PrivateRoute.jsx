import { useState } from "react";
import useAuth from "../hooks/useAuth";
import { Navigate } from "react-router-dom";
import axios from "axios";
import useLocalStorage from "../hooks/useLocalStorage";

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
        return <Navigate to={'/login'} />
    }
    if (isLoading) {
        return <div className="loading-indicator">Is loading ...</div>
    } else if (isValid) {
        return children
    } else {
        return <Navigate to={'/login'} />
    }

}

export default PrivateRoute