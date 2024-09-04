import {AppBar, Box, Button, Toolbar} from "@mui/material";
import {Outlet, useLocation, useNavigate} from "react-router-dom";
import {useEffect} from "react";
import {checkIsAdmin} from "../store/slices/admin-slice";
import {useDispatch, useSelector} from "react-redux";
import {ADMIN_ROLE_SET_PAGE, LOGIN_PAGE} from "../store/constants";

export default () => {
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const location = useLocation()
    const isAdmin = useSelector((state) => state.adminData.isAdmin)

    useEffect(() => {
        dispatch(checkIsAdmin())
    }, [location]);

    return <div>
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static">
            </AppBar>
        </Box>
        <Outlet/>
    </div>
}