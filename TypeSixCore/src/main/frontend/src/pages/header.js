import {AppBar, Box, Button, Toolbar} from "@mui/material";
import {Outlet} from "react-router-dom";

export default () => {
    return <div>
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static">
                <Toolbar>
                    <Button color="inherit">Type-6-SSO</Button>
                </Toolbar>
            </AppBar>
        </Box>
        <Outlet/>
    </div>
}