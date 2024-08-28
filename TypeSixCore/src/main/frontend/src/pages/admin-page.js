import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {LOGIN_PAGE, NO_ROLE, ROLES, USERS_PER_LOAD} from "../store/constants";
import {Button, Card, CardContent, MenuItem, Select, Typography} from "@mui/material";
import {useEffect} from "react";
import {checkIsAdmin, loadUsers, setRole} from "../store/slices/admin-slice";

export default () => {
    const navigate = useNavigate()
    const dispatch = useDispatch()

    const isAdmin = useSelector((state) => state.adminData.isAdmin)
    const users = useSelector((state) => state.adminData.users)
    const error = useSelector((state) => state.adminData.error)
    const selfEmail = useSelector((state) => state.adminData.selfEmail)

    useEffect(() => {
        dispatch(checkIsAdmin())
    }, []);

    useEffect(() => {
        if (isAdmin) {
            dispatch(loadUsers({offset: users.length, count: USERS_PER_LOAD}))
        }
    }, [isAdmin])

    if (error !== undefined) {
        navigate(LOGIN_PAGE)
    }

    if (!isAdmin) {
        return <div/>
    }

    return <div>
        <Card>
            <CardContent>
                <Typography variant="h6">
                    Welcome, {selfEmail}!
                </Typography>
            </CardContent>
        </Card>
        <br/>
        {
            users.map(user => <div>
                <Card>
                    <CardContent>
                        Email: {user.email}
                        <br/>
                        <br/>
                        Role:
                        <Select
                            labelId="select-role"
                            value={user.role}
                            disabled={user.email === selfEmail}
                            label="Role"
                            onChange={(event) => {
                                dispatch(setRole({id: user.id, role: event.target.value}))
                            }}
                        >
                            {
                                ROLES.map(role => <MenuItem value={role}>{role}</MenuItem>)
                            }
                            <MenuItem value={NO_ROLE}>{NO_ROLE}</MenuItem>
                        </Select>
                    </CardContent>
                </Card>
            </div>)
        }
        <Card>
            <CardContent>
                <Button onClick={() => {
                    dispatch(loadUsers({offset: users.length, count: USERS_PER_LOAD}))
                }}>Load more</Button>
            </CardContent>
        </Card>
    </div>
}