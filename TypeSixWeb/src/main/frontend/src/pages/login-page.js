import {useNavigate, useSearchParams} from "react-router-dom";
import {Box, Button, Card, CardContent, Typography} from "@mui/material";
import {FORM_LOGIN_ENDPOINT, LOGOUT_ENDPOINT, PROVIDERS, REGISTRATION_PAGE} from "../store/constants";

export default function LoginPage() {
    const navigate = useNavigate()
    const [searchParams, setSearchParams] = useSearchParams();
    const errorMessage = searchParams.get("error")

    return <Box>
        <Card>
            <CardContent>
                <Typography variant="h6">
                    Login
                </Typography>
                <form className="form-signin" method="post" action={FORM_LOGIN_ENDPOINT}>
                    <p>
                        <label htmlFor="username" className="sr-only">Username: </label>
                        <input type="text" id="username" name="username" className="form-control" placeholder="Username"
                               required autoFocus/>
                    </p>
                    <p>
                        <label htmlFor="password" className="sr-only">Password: </label>
                        <input type="password" id="password" name="password" className="form-control"
                               placeholder="Password" required/>
                    </p>
                    <button className="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                </form>
                {
                    errorMessage !== null ?
                        <Typography variant="h6">
                            {errorMessage}
                        </Typography>
                        : <div/>
                }
            </CardContent>
        </Card>
        <br/>
        <Card>
            <CardContent>
                <Typography variant="h6">
                    Oauth2
                </Typography>
                {
                    PROVIDERS.map(provider => <Button key={provider.name} onClick={() => {
                        window.location = provider.url
                    }}>{provider.name}</Button>)
                }
            </CardContent>
        </Card>
        <br/>
        <Card>
            <CardContent>
                <Button onClick={() => navigate(REGISTRATION_PAGE)}>Registration</Button>
                <Button onClick={() => {
                    window.location = LOGOUT_ENDPOINT
                }}>Logout</Button>
            </CardContent>
        </Card>
    </Box>
}