import {Box, Button, Card, CardContent, Typography} from "@mui/material";
import {LOGIN_PAGE} from "../store/constants";
import {useNavigate} from "react-router-dom";
import { useTranslation } from 'react-i18next';
export default function ErrorPage() {
    const navigate = useNavigate()
    const { t, i18n } = useTranslation()
    return <div className="wrapper-def">
        <div className="sec-error">
            <div className="sec-error__content">
                <div className="sec-error__icon"><svg width="263" height="350" viewBox="0 0 263 350" fill="none" xmlns="http://www.w3.org/2000/svg"><g clip-path="url(#clip0_8_186)"><path d="M225.52 349.65H30.1501C28.6979 349.648 27.2631 349.334 25.9432 348.728C24.6233 348.123 23.4492 347.24 22.5006 346.141C21.5519 345.042 20.851 343.751 20.4454 342.356C20.0399 340.962 19.9391 339.497 20.1501 338.06L42.5501 184.58C42.8122 182.776 43.7157 181.127 45.0951 179.935C46.4744 178.743 48.2371 178.088 50.0601 178.09H175.47L186.9 163.82C188.077 162.353 189.569 161.169 191.265 160.355C192.962 159.542 194.819 159.12 196.7 159.12H254.76C255.849 159.12 256.925 159.355 257.915 159.807C258.905 160.26 259.786 160.921 260.499 161.744C261.211 162.567 261.738 163.534 262.043 164.579C262.349 165.624 262.426 166.723 262.27 167.8L237.21 339.55C236.803 342.359 235.397 344.926 233.25 346.781C231.102 348.637 228.358 349.655 225.52 349.65Z" fill="#009999"/><path d="M27.2601 349.65H226.45C227.61 349.649 228.757 349.401 229.814 348.924C230.872 348.446 231.815 347.749 232.583 346.88C233.351 346.01 233.925 344.987 234.267 343.878C234.61 342.77 234.713 341.601 234.57 340.45L217.08 200.56C216.827 198.557 215.853 196.715 214.34 195.379C212.827 194.043 210.879 193.304 208.86 193.3H8.28009C7.10496 193.301 5.94342 193.551 4.87253 194.035C3.80163 194.519 2.84583 195.225 2.06852 196.106C1.29122 196.988 0.710168 198.024 0.363905 199.147C0.0176409 200.27 -0.085919 201.454 0.0600868 202.62L17.3501 340.88C17.6498 343.298 18.8222 345.524 20.6469 347.139C22.4717 348.754 24.8234 349.647 27.2601 349.65Z" fill="#88DCD6"/><path d="M92.98 258.88L92.89 258.8C92.51 258.47 92.11 258.16 91.72 257.8L89.63 256.12L87.43 254.35L88.17 253.41C88.86 252.53 89.57 251.67 90.23 250.77C90.9171 249.976 91.4496 249.06 91.8 248.07C91.8903 247.721 91.8877 247.354 91.7924 247.005C91.6971 246.657 91.5124 246.34 91.2567 246.086C91.0009 245.831 90.6831 245.647 90.3346 245.553C89.9861 245.46 89.6191 245.458 89.27 245.55C87.27 246.12 85.61 247.98 84.17 249.41L82.91 250.71C82.1 250.05 81.28 249.41 80.43 248.82C78.89 247.74 77.02 245.65 75 245.82C74.3454 245.835 73.722 246.103 73.2599 246.567C72.7978 247.031 72.5327 247.655 72.52 248.31C72.64 250.69 75 252.31 76.52 253.89C77.14 254.55 77.79 255.19 78.46 255.82C77.69 256.77 76.95 257.74 76.25 258.74C74.99 260.55 73.25 262.64 73.25 264.92C73.2599 265.403 73.3944 265.875 73.6405 266.29C73.8866 266.705 74.236 267.05 74.6546 267.291C75.0732 267.531 75.547 267.659 76.0298 267.663C76.5126 267.666 76.9881 267.545 77.41 267.31C78.4481 266.659 79.3101 265.763 79.92 264.7C80.5 263.89 81.07 263.07 81.66 262.26C82.25 261.45 82.66 260.86 83.17 260.16L85.41 262.27C86.14 262.96 86.86 263.66 87.62 264.32C88.1174 264.806 88.6561 265.247 89.23 265.64C90.2742 266.222 91.5011 266.383 92.66 266.09C93.4685 265.86 94.1805 265.375 94.689 264.706C95.1976 264.036 95.4752 263.22 95.48 262.38V262.29C95.4688 261.532 95.222 260.797 94.7741 260.186C94.3261 259.575 93.6991 259.119 92.98 258.88Z" fill="#145050"/><path d="M146.33 256.45L146.25 256.37C145.87 256.05 145.47 255.73 145.08 255.42L142.99 253.74L140.79 251.97L141.53 251.02C142.22 250.15 142.93 249.28 143.59 248.38C144.279 247.591 144.812 246.678 145.16 245.69C145.249 245.34 145.246 244.973 145.151 244.625C145.055 244.277 144.871 243.96 144.616 243.705C144.36 243.449 144.043 243.265 143.695 243.169C143.347 243.074 142.98 243.071 142.63 243.16C140.63 243.74 138.97 245.6 137.53 247.03L136.27 248.32C135.46 247.67 134.64 247.03 133.78 246.43C132.25 245.36 130.38 243.27 128.36 243.43C127.706 243.443 127.083 243.708 126.621 244.171C126.158 244.633 125.893 245.256 125.88 245.91C125.99 248.29 128.36 249.91 129.88 251.49C130.5 252.16 131.15 252.79 131.82 253.42C131.05 254.42 130.3 255.35 129.61 256.35C128.35 258.16 126.61 260.24 126.56 262.53C126.57 263.013 126.704 263.485 126.951 263.9C127.197 264.315 127.546 264.66 127.965 264.901C128.383 265.141 128.857 265.269 129.34 265.273C129.823 265.276 130.298 265.155 130.72 264.92C131.756 264.266 132.617 263.37 133.23 262.31C133.81 261.49 134.38 260.67 134.97 259.87L136.48 257.77L138.71 259.88C139.44 260.57 140.16 261.26 140.91 261.93C141.412 262.408 141.95 262.846 142.52 263.24C143.567 263.824 144.797 263.988 145.96 263.7C146.768 263.469 147.479 262.983 147.987 262.314C148.495 261.645 148.774 260.83 148.78 259.99V259.88C148.774 259.126 148.535 258.392 148.097 257.778C147.658 257.164 147.041 256.701 146.33 256.45Z" fill="#145050"/><path d="M125.87 275.42C123.93 274.514 121.924 273.755 119.87 273.15C116 271.902 111.927 271.403 107.87 271.68C104.397 271.911 101.051 273.072 98.1801 275.04C96.7226 276.083 95.4284 277.336 94.3401 278.76C93.244 280.039 92.6567 281.676 92.6901 283.36C92.6928 283.646 92.7701 283.925 92.9143 284.172C93.0584 284.418 93.2644 284.623 93.5119 284.765C93.7594 284.908 94.0398 284.983 94.3254 284.984C94.6109 284.985 94.8918 284.911 95.14 284.77C96.2013 283.959 97.1825 283.048 98.07 282.05C98.9559 281.205 99.968 280.503 101.07 279.97C102.151 279.404 103.296 278.971 104.48 278.68C105.72 278.315 107.007 278.133 108.3 278.14C111.55 278.132 114.774 278.722 117.81 279.88C119.42 280.44 121 281.11 122.58 281.75H122.68L123.45 282.08H123.53C123.852 282.244 124.186 282.381 124.53 282.49C125.296 282.766 126.097 282.935 126.91 282.99C127.615 282.98 128.288 282.695 128.787 282.197C129.285 281.698 129.57 281.025 129.58 280.32C129.66 278.08 127.82 276.36 125.87 275.42Z" fill="#145050"/><path d="M216.5 179.82C216.401 179.467 216.211 179.147 215.95 178.89C215.696 178.625 215.375 178.435 215.02 178.34C214.659 178.238 214.277 178.236 213.915 178.333C213.552 178.429 213.222 178.622 212.96 178.89L212.57 179.39C212.5 179.57 212.42 179.74 212.35 179.91C212.28 180.08 212.26 180.11 212.23 180.21C212.144 180.578 212.094 180.953 212.08 181.33C212.028 181.751 212.001 182.175 212 182.6C211.991 183.155 212.028 183.711 212.11 184.26C212.143 184.497 212.207 184.729 212.3 184.95C212.442 185.245 212.631 185.516 212.86 185.75C213.069 185.955 213.339 186.086 213.63 186.124C213.92 186.162 214.215 186.104 214.47 185.96C214.696 185.831 214.908 185.676 215.1 185.5C215.313 185.271 215.485 185.007 215.61 184.72C215.829 184.23 216.013 183.726 216.16 183.21C216.281 182.823 216.378 182.429 216.45 182.03C216.671 181.312 216.689 180.547 216.5 179.82Z" fill="white"/><path d="M229.45 180.33C229.353 179.969 229.162 179.64 228.897 179.377C228.632 179.113 228.302 178.925 227.94 178.83C227.612 178.755 227.274 178.738 226.94 178.78C226.45 178.854 225.994 179.073 225.63 179.41C225.454 179.543 225.287 179.686 225.13 179.84C224.811 180.188 224.514 180.556 224.24 180.94C223.57 181.8 222.95 182.68 222.35 183.6L221.64 184.72C221.356 185.231 221.112 185.762 220.91 186.31C220.718 186.715 220.573 187.141 220.48 187.58C220.353 188.026 220.396 188.503 220.6 188.92C220.709 189.115 220.867 189.278 221.058 189.394C221.249 189.51 221.467 189.574 221.69 189.58C222.69 189.67 223.58 188.58 224.27 187.94C224.96 187.3 225.42 186.68 225.99 186.04C226.56 185.4 227.11 184.79 227.66 184.15C228.6 183.1 229.92 181.88 229.45 180.33Z" fill="white"/><path d="M231.21 194.62C230.868 194.281 230.454 194.021 230 193.86C229.62 193.75 229.226 193.693 228.83 193.69C228.217 193.698 227.607 193.772 227.01 193.91C226.563 194.012 226.123 194.139 225.69 194.29C225.339 194.389 225.003 194.533 224.69 194.72C224.403 194.921 224.141 195.156 223.91 195.42C223.742 195.597 223.621 195.813 223.558 196.049C223.495 196.285 223.492 196.533 223.55 196.77C223.612 197.011 223.737 197.231 223.913 197.407C224.089 197.583 224.309 197.708 224.55 197.77C224.71 197.77 224.87 197.84 225.04 197.86C225.21 197.88 225.46 197.93 225.67 197.94H225.91H226.01C226.673 197.853 227.342 197.827 228.01 197.86C228.37 197.86 228.74 197.86 229.1 197.86C229.41 197.839 229.718 197.792 230.02 197.72C230.314 197.626 230.593 197.491 230.85 197.32C231.079 197.19 231.275 197.008 231.423 196.789C231.57 196.571 231.665 196.321 231.7 196.059C231.735 195.798 231.709 195.532 231.624 195.282C231.539 195.033 231.397 194.806 231.21 194.62Z" fill="white"/><path d="M128.87 38.13L130.62 19.32L106.52 38.13H128.87Z" fill="#6779B7"/><path d="M159.78 67.75C161.03 66.27 162.48 64.75 162.96 62.83C163.049 62.4826 163.046 62.1181 162.951 61.7722C162.857 61.4263 162.674 61.1109 162.421 60.8568C162.168 60.6027 161.853 60.4188 161.508 60.3229C161.162 60.2271 160.798 60.2226 160.45 60.31C158.66 60.66 157.19 61.91 155.78 62.99C154.37 64.07 152.78 65.43 151.32 66.7C149.27 68.49 147.32 70.4 145.32 72.32C144.32 70.89 143.32 69.45 142.32 68.02C141.32 66.59 140.32 65.15 139.32 63.76C138.3 62.168 136.995 60.7781 135.47 59.66C134.07 58.82 131.71 59.87 131.93 61.66C132.34 65.21 134.88 68.66 136.67 71.66C137.83 73.6 139.02 75.5133 140.24 77.4L135.95 81.78C133.38 84.42 129.89 87.2 128.64 90.78C127.9 92.88 130.32 95.58 132.52 94.66C136.06 93.17 138.57 89.74 141.19 87.04C142.28 85.91 143.38 84.8 144.48 83.68C145.82 85.58 147.18 87.46 148.59 89.31C149.72 90.78 150.86 92.25 152.02 93.69C152.571 94.4458 153.176 95.161 153.83 95.83C154.364 96.3559 155.025 96.7353 155.748 96.9315C156.471 97.1277 157.233 97.1341 157.96 96.95C158.525 96.7942 159.04 96.4951 159.455 96.0816C159.87 95.6681 160.172 95.1543 160.33 94.59C161.15 92.12 159.33 90.32 157.82 88.59C156.707 87.31 155.617 86.01 154.55 84.69C152.87 82.63 151.26 80.52 149.67 78.39C151.88 76.17 154.12 73.96 156.25 71.66C157.46 70.37 158.66 69.1 159.78 67.75Z" fill="#EF877B"/><path d="M90.8687 125.04L99.9679 27.1313L123.752 8.57H190.731L179.904 125.04H90.8687Z" fill="white" stroke="#D9E3EB"/><path d="M121.83 26.87L123.58 8.07L99.49 26.87H121.83Z" fill="#D9E3EB"/><path d="M152.75 56.5C153.99 55.01 155.44 53.5 155.92 51.57C156.007 51.2233 156.002 50.86 155.907 50.5156C155.811 50.1711 155.628 49.8573 155.376 49.6046C155.123 49.3518 154.809 49.1689 154.465 49.0734C154.12 48.978 153.757 48.9734 153.41 49.06C151.63 49.4 150.15 50.66 148.75 51.73C147.35 52.8 145.75 54.17 144.28 55.45C142.23 57.24 140.28 59.15 138.28 61.06L135.28 56.77C134.28 55.33 133.28 53.9 132.28 52.51C131.266 50.9105 129.96 49.5163 128.43 48.4C127.03 47.56 124.68 48.61 124.89 50.4C125.3 53.95 127.84 57.4 129.64 60.4C130.79 62.34 131.99 64.24 133.21 66.14C131.77 67.5933 130.34 69.05 128.92 70.51C126.35 73.16 122.86 75.94 121.61 79.51C120.87 81.62 123.29 84.31 125.48 83.39C129.02 81.91 131.54 78.48 134.15 75.77C135.24 74.65 136.35 73.54 137.44 72.42C138.79 74.32 140.15 76.2 141.56 78.04C142.68 79.52 143.823 80.9833 144.99 82.43C145.544 83.1838 146.149 83.8988 146.8 84.57C147.335 85.091 147.994 85.4673 148.715 85.6632C149.436 85.8591 150.195 85.8684 150.92 85.69C151.488 85.536 152.005 85.2363 152.421 84.8205C152.836 84.4048 153.136 83.8875 153.29 83.32C154.12 80.85 152.29 79.06 150.78 77.32C149.68 76.0267 148.59 74.7267 147.51 73.42C145.84 71.36 144.22 69.25 142.64 67.11C144.85 64.89 147.09 62.68 149.21 60.39C150.42 59.11 151.63 57.88 152.75 56.5Z" fill="#EF6E74"/><path d="M105.38 7.05C104.96 6.58 104.57 6.11 104.11 5.68C103.65 5.25 103.24 4.84 102.76 4.46L102.01 3.86C101.847 3.71943 101.665 3.60168 101.47 3.51L101.33 3.45L100.94 3.28C100.725 3.16269 100.48 3.10975 100.235 3.12747C99.9907 3.1452 99.7563 3.23285 99.56 3.38C99.364 3.52719 99.2165 3.72958 99.1363 3.96121C99.0561 4.19284 99.0469 4.44314 99.11 4.68C99.2141 5.05761 99.3583 5.42301 99.54 5.77C99.71 6.01 99.9 6.24 100.07 6.48C100.527 7.07462 101.014 7.64537 101.53 8.19C101.969 8.62933 102.434 9.04334 102.92 9.43C103.233 9.70941 103.567 9.96354 103.92 10.19C104.329 10.4886 104.824 10.6465 105.33 10.64C105.673 10.6322 106.001 10.4967 106.25 10.26C106.48 10.0069 106.614 9.68152 106.63 9.34C106.67 8.46 105.91 7.66 105.38 7.05Z" fill="#9DA7B4"/><path d="M103.41 15.43C103.214 15.1314 102.946 14.8872 102.63 14.72C102.202 14.4062 101.69 14.2285 101.16 14.21C100.78 14.21 100.4 14.21 100.02 14.21C99.5417 14.24 99.0667 14.3103 98.6001 14.42C98.0718 14.4708 97.5789 14.7084 97.21 15.09C97.0826 15.2223 96.9864 15.3815 96.9286 15.5558C96.8707 15.7301 96.8526 15.9152 96.8757 16.0974C96.8987 16.2796 96.9622 16.4544 97.0616 16.6088C97.161 16.7633 97.2937 16.8935 97.45 16.99C97.7475 17.1854 98.0942 17.2929 98.45 17.3C98.6399 17.31 98.8302 17.31 99.02 17.3C99.3798 17.32 99.7403 17.32 100.1 17.3C100.959 17.3301 101.812 17.1445 102.58 16.76C102.729 16.7104 102.871 16.6397 103 16.55C103.122 16.4608 103.236 16.3604 103.34 16.25C103.399 16.2009 103.447 16.1407 103.482 16.0728C103.518 16.005 103.539 15.9308 103.546 15.8546C103.552 15.7783 103.544 15.7016 103.52 15.6287C103.497 15.5559 103.46 15.4883 103.41 15.43Z" fill="#9DA7B4"/><path d="M112.82 2.26C112.82 2 112.82 1.74 112.82 1.49C112.792 1.12532 112.656 0.777167 112.43 0.490002C112.305 0.283629 112.105 0.133848 111.872 0.0722772C111.638 0.010706 111.39 0.0421595 111.18 0.16C110.84 0.302613 110.556 0.551791 110.37 0.869999C110.2 1.18997 110.059 1.52467 109.95 1.87C109.77 2.42769 109.659 3.00534 109.62 3.59C109.53 4.17208 109.5 4.7618 109.53 5.35C109.506 6.04419 109.661 6.7328 109.98 7.35C110.088 7.51719 110.236 7.65467 110.411 7.74987C110.585 7.84507 110.781 7.89494 110.98 7.89494C111.179 7.89494 111.375 7.84507 111.549 7.74987C111.724 7.65467 111.872 7.51719 111.98 7.35C112.183 6.98353 112.306 6.57779 112.34 6.16C112.34 6.05 112.34 5.94 112.34 5.83C112.342 5.79003 112.342 5.74997 112.34 5.71C112.44 5.08 112.51 4.46 112.57 3.83C112.57 3.58 112.62 3.33 112.66 3.09V2.98C112.735 2.74535 112.789 2.50436 112.82 2.26Z" fill="#9DA7B4"/></g><defs><clipPath id="clip0_8_186"><rect width="262.36" height="349.65" fill="white"/></clipPath></defs></svg></div>
                <div className="sec-error__title title-big">{t('errorTitle')}</div>
                <div className="sec-error__text text-def">{t('errorText')}</div>
                <button onClick={() => navigate(LOGIN_PAGE)} className="sec-error__btn btn-def-2"><span>{t('errorBtn')}</span></button>
            </div>
        </div>
    </div>
}