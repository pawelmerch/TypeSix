import {Box, Button, Card, CardContent, Typography} from "@mui/material";
import {LOGIN_PAGE} from "../store/constants";
import {useNavigate} from "react-router-dom";

export default function SuccessfulAuthorizationPage() {
    const navigate = useNavigate()
    return <Box>
            <Card>
                <CardContent>
                    <Typography variant="h6">
                        Success
                    </Typography>
                    <Button onClick={() => navigate(LOGIN_PAGE)}>Back to login page</Button>
                </CardContent>
            </Card>
    </Box>
}