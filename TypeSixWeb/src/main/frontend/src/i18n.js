import i18n from 'i18next';
import {
  initReactI18next
} from 'react-i18next';

const savedLanguage = localStorage.getItem('i18nextLng') || 'ru'; // язык по умолчанию

// Переводы
const resources = {
  ru: {
    translation: {
      "title": "Cервис единого входа",
      "signTitle": "Войти",
      "loginTitle": "Логин",
      "loginError": "Имя пользователя не найдено",
      "passTitle": "Пароль",
      "passForgot": "Забыли пароль?",
      "signBtn": "Авторизоваться",
      "providerTitle": "Войти через сторонние провайдеры",
      "btnReg": "Зарегистрироваться",
      "or": "или",
      "successTitle": "Авторизация прошла успешно",
      "successBtn": "Вернуться на главную",
    }
  },
  en: {
    translation: {
      "title": "Single sign on service",
      "signTitle": "Log in",
      "loginTitle": "Login",
      "loginError": "The user's name was not found",
      "passTitle": "Password",
      "passForgot": "Forgot your password?",
      "signBtn": "Log in",
      "providerTitle": "Log in through third-party providers",
      "btnReg": "Register",
      "or": "or",
      "successTitle": "Authorization was successful",
      "successBtn": "Go back to the main page",
    }
  },
};

i18n
  .use(initReactI18next) // подключение i18n к react-i18next
  .init({
    resources,
    lng: savedLanguage,
    fallbackLng: 'ru', // язык по умолчанию при отсутствии перевода
    interpolation: {
      escapeValue: false, // отключаем экранирование
    },
  });

export default i18n;