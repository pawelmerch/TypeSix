import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Provider} from 'react-redux'
import LoginPage from "./pages/login-page";
import SuccessfulAuthorizationPage from "./pages/success-page";
import store from './store/store';
import Header from "./pages/header";
import ErrorPage from "./pages/error-page";
import {ERROR_PAGE, LOGIN_PAGE, REGISTRATION_PAGE, SUCCESS_LOGIN_PAGE, UNAUTHORIZED_PAGE} from "./store/constants";
import RegistrationPage from "./pages/registration-page";

function App() {
  return <Provider store={store}>
    <BrowserRouter>
      <Routes>
        <Route element={<Header/>} path="/">
          <Route element={<LoginPage/>} path={LOGIN_PAGE}/>
          <Route element={<ErrorPage/>} path={ERROR_PAGE}/>
          <Route element={<RegistrationPage/>} path={REGISTRATION_PAGE}/>
          <Route element={<SuccessfulAuthorizationPage/>} path={SUCCESS_LOGIN_PAGE}/>
        </Route>
      </Routes>
    </BrowserRouter>
  </Provider>
}

export default App;
