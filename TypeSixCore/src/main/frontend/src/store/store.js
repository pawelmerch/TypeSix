import registrationReducer from "./slices/registration-slice"
import adminReducer from "./slices/admin-slice"
import {configureStore} from "@reduxjs/toolkit"

export default configureStore({
    reducer: {
        registrationData: registrationReducer,
        adminData: adminReducer
    },
});