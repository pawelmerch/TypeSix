import registrationReducer from "./slices/registration-slice"
import {configureStore} from "@reduxjs/toolkit"

export default configureStore({
    reducer: {
        registrationData: registrationReducer
    },
});