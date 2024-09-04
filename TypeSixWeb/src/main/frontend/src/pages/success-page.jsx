import {Box, Button, Card, CardContent, Typography} from "@mui/material";
import {LOGIN_PAGE} from "../store/constants";
import {useNavigate} from "react-router-dom";
import { useTranslation } from 'react-i18next';
export default function SuccessfulAuthorizationPage() {
    const navigate = useNavigate()
    const { t, i18n } = useTranslation()
    return <div className="wrapper-def">
        <div className="sec-success">
            <div className="sec-success__icon"><svg width="97" height="97" viewBox="0 0 97 97" fill="none" xmlns="http://www.w3.org/2000/svg"><g clip-path="url(#clip0_8_272)"><path fill-rule="evenodd" clip-rule="evenodd" d="M48.5 97C21.7871 97 0 75.2129 0 48.5C0 21.7871 21.7871 0 48.5 0C75.2129 0 97 21.7871 97 48.5C97 75.2129 75.2129 97 48.5 97Z" fill="#88DCD6"/><path fill-rule="evenodd" clip-rule="evenodd" d="M48.5 83.6057C29.1758 83.6057 13.3943 67.8242 13.3943 48.5C13.3943 29.1758 29.1758 13.3943 48.5 13.3943C67.8242 13.3943 83.6057 29.1758 83.6057 48.5C83.6057 67.8242 67.9853 83.6057 48.5 83.6057Z" fill="#009999"/><path fill-rule="evenodd" clip-rule="evenodd" d="M39.9651 68.6294C39.6431 68.6294 39.321 68.4684 39.16 68.3073L22.2513 51.5597C21.7682 51.0766 21.7682 50.2714 22.2513 49.7883L28.5316 43.669C28.6927 43.3469 29.0148 43.3469 29.3368 43.3469C29.6589 43.3469 29.8199 43.3469 30.142 43.669L39.8041 53.4921L64.6035 28.6927C65.0866 28.2096 65.8918 28.2096 66.3749 28.6927L72.4942 34.9731C72.8163 35.1341 72.8163 35.4562 72.8163 35.7782C72.8163 35.9393 72.8163 36.2613 72.4942 36.5834L40.7703 68.3073C40.4482 68.4684 40.2872 68.6294 39.9651 68.6294Z" fill="white"/></g><defs><clipPath id="clip0_8_272"><rect width="97" height="97" fill="white"/></clipPath></defs></svg></div>
            <div className="sec-success__title title-def">{t('successTitle')}</div>
            <button onClick={() => navigate(LOGIN_PAGE)} className="sec-success__btn btn-def"><span>{t('successBtn')}</span></button>
        </div>
    </div>
}