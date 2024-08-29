import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import axios from "axios";
import {
    ADMIN_ALL_USERS_LIST_ENDPOINT, ADMIN_SET_ROLE_ENDPOINT,
    IS_ADMIN_ENDPOINT
} from "../constants";

export const checkIsAdmin = createAsyncThunk(
    'admin/checkIsAdmin',
    async function(_, {rejectWithValue, dispatch}) {
        try {
            const response = await axios.get(IS_ADMIN_ENDPOINT)
            return response.data
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);

export const loadUsers = createAsyncThunk(
    'admin/loadUsers',
    async function({offset, count}, {rejectWithValue, dispatch}) {
        try {
            const response = await axios.get(ADMIN_ALL_USERS_LIST_ENDPOINT + `?offset=${offset}&count=${count}`)
            return response.data
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);

export const setRole = createAsyncThunk(
    'admin/setRole',
    async function({id, role}, {rejectWithValue, dispatch}) {
        try {
            await axios.post(ADMIN_SET_ROLE_ENDPOINT.replace("{id}", id) + `?role=${role}`)
            return {id: id, role: role}
        } catch (error) {
            return rejectWithValue(error.response.data);
        }
    }
);

const adminSlice = createSlice({
    name: 'admin',
    initialState: {
        isAdmin: false,
        users: [],
        error: undefined,
        selfEmail: undefined
    },
    reducers: {
    },
    extraReducers: (builder) => {
        builder
            .addCase(checkIsAdmin.fulfilled, (state, action) => {
                state.error = undefined
                state.isAdmin = true
                state.selfEmail = action.payload.email
            })
            .addCase(checkIsAdmin.rejected, (state, action) => {
                state.isAdmin = false
                state.error = action.payload
                state.selfEmail = undefined
            })
            .addCase(loadUsers.fulfilled, (state, action) => {
                state.error = undefined
                state.users = [...action.payload, ...state.users]
                state.users.sort((a, b) => a.id - b.id)
            })
            .addCase(loadUsers.rejected, (state, action) => {
                state.users = []
                state.error = action.payload
            })
            .addCase(setRole.fulfilled, (state, action) => {
                state.error = undefined
                state.users.forEach(user => {
                    if (user.id === action.payload.id) {
                        user.role = action.payload.role
                    }
                })
            })
            .addCase(setRole.rejected, (state, action) => {
                state.isAdmin = false
                state.error = action.payload
            })
    }
});

export default adminSlice.reducer;
