import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import axios from "axios";
import {
    REGISTRATION_CODE_ENDPOINT,
    REGISTRATION_EMAIL_ENDPOINT, REGISTRATION_PASSWORD_SET_ENDPOINT
} from "../constants";

export const sendEmail = createAsyncThunk(
    'online-user/sendEmail',
    async function({email}, {rejectWithValue, dispatch}) {
        try {
            const response = await axios.post(REGISTRATION_EMAIL_ENDPOINT + "?email=" + email)
            return response.data
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);

export const sendCode = createAsyncThunk(
    'online-user/sendCode',
    async function({code, email}, {rejectWithValue, dispatch}) {
        try {
            const response = await axios.post(REGISTRATION_CODE_ENDPOINT + "?code=" + code + "&email=" + email)
            return response.data
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);

export const sendPassword = createAsyncThunk(
    'online-user/sendPassword',
    async function({code, email, password}, {rejectWithValue, dispatch}) {
        try {
            const response = await axios.post(REGISTRATION_PASSWORD_SET_ENDPOINT + "?code=" + code + "&password=" + password + "&email=" + email)
            return response.data
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);

const registrationSlice = createSlice({
    name: 'registration',
    initialState: {
        emailError: undefined,
        codeError: undefined,
        passwordError: undefined
    },
    reducers: {
        clearErrors(state) {
            state.emailError = undefined
            state.codeError = undefined
            state.passwordError = undefined
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(sendEmail.fulfilled, (state, action) => {
                state.emailError = undefined
            })
            .addCase(sendEmail.rejected, (state, action) => {
                state.emailError = action.payload
            })
            .addCase(sendCode.fulfilled, (state, action) => {
                state.codeError = undefined
            })
            .addCase(sendCode.rejected, (state, action) => {
                state.codeError = action.payload
            })
            .addCase(sendPassword.fulfilled, (state, action) =>  {
                state.passwordError = undefined
            })
            .addCase(sendPassword.rejected, (state, action) => {
                state.passwordError = action.payload
            })
    }
});

export const {clearErrors} = registrationSlice.actions
export default registrationSlice.reducer;
