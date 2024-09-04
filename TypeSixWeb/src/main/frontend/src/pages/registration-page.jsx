import {useLocation, useNavigate} from "react-router-dom";
import {Box, Button, Card, CardContent, TextField, Typography} from "@mui/material";
import {useEffect, useState} from "react";
import {LOGIN_PAGE} from "../store/constants";
import {useDispatch, useSelector} from "react-redux";
import {sendCode, sendEmail, sendPassword, clearErrors} from "../store/slices/registration-slice";

const EMAIL_STAGE = "email_stage"
const CODE_STAGE = "code_stage"
const PASSWORD_STAGE = "password_stage"

export default function RegistrationPage() {
    const navigate = useNavigate()
    const dispatch = useDispatch()

    const [email, setEmail] = useState("")
    const [code, setCode] = useState("")
    const [password, setPassword] = useState("")
    const [stage, setStage] = useState(EMAIL_STAGE)

    const location = useLocation()

    const emailError = useSelector((state) => state.registrationData.emailError)
    const codeError = useSelector((state) => state.registrationData.codeError)
    const passwordError = useSelector((state) => state.registrationData.passwordError)

    useEffect(() => {
        dispatch(clearErrors())
        setStage(EMAIL_STAGE)
    }, [location]);

    if (emailError !== undefined && stage !== EMAIL_STAGE) {
        setStage(EMAIL_STAGE)
    }
    if (codeError !== undefined && stage !== CODE_STAGE) {
        setStage(CODE_STAGE)
    }
    if (passwordError !== undefined && stage !== PASSWORD_STAGE) {
        setStage(PASSWORD_STAGE)
    }

    return <Box>
        { stage === EMAIL_STAGE ?
            <Card>
                <CardContent>
                    Login:
                    <TextField onChange={(event) => setEmail(event.target.value)}></TextField>
                    <Button onClick={() => {
                        dispatch(sendEmail({email}))
                        setStage(CODE_STAGE)
                    }}>Submit</Button>
                    {emailError !== undefined ? <Typography>{emailError}</Typography> : <div/>}
                </CardContent>
            </Card> : <div/>
        }
        {
            stage === CODE_STAGE ?
            <Card>
                <CardContent>
                    Code:
                    <TextField onChange={(event) => setCode(event.target.value)}></TextField>
                    <Button onClick={() => {
                        dispatch(sendCode({email, code}))
                        setStage(PASSWORD_STAGE)
                    }}>Submit</Button>
                    {codeError !== undefined ? <Typography>{codeError}</Typography> : <div/>}
                </CardContent>
            </Card> : <div/>
        }
        {
            stage === PASSWORD_STAGE ?
            <Card>
                <CardContent>
                    Password:
                    <TextField onChange={(event) => setPassword(event.target.value)}></TextField>
                    <Button onClick={() => {
                        dispatch(sendPassword({email, code, password}))
                        navigate(LOGIN_PAGE)
                    }}>Submit</Button>
                    {passwordError !== undefined ? <Typography>{passwordError}</Typography> : <div/>}
                </CardContent>
            </Card> : <div/>
        }
    </Box>
}