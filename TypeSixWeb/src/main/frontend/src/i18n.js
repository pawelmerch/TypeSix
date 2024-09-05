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
      "errorTitle": "Кажется, что-то пошло не так!",
      "errorText": "Страница, которую вы запрашиваете, не существует. Возможно она устарела, была удалена или был введен неверный адрес в адресной строке.",
      "errorBtn": "Вернуться назад",
      "regTitle": "Регистрация",
      "regText": "Введите адрес своей почты и мы пришлем туда пароль для регистрации.",
      "inpEmail": "Введите почту",
      "next": "Далее",
      "backText": "Вернуться на главную",
      "inpCode": "Введите код из письма",
      "inpPass": "Придумайте пароль",
      "inpPass2": "Повторите пароль",
      "regSuccess": "Регистрация прошла успешно",
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
      "errorTitle": "It seems that something went wrong!",
      "errorText": "The page you are requesting does not exist. It may have been outdated, deleted, or an incorrect address was entered in the address bar.",
      "errorBtn": "Go back",
      "regTitle": "Registration",
      "regText": "Enter your email address and we will send you the password for registration there.",
      "inpEmail": "Enter your email",
      "next": "Next",
      "backText": "Go back to the main page",
      "inpCode": "Enter the code from the email",
      "inpPass": "Password",
      "inpPass2": "Repeat the password",
      "regSuccess": "Registration was successful",
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