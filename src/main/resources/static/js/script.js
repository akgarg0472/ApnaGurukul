const validateRole = () => {
    const role = $("#role").val();

    if (role.trim() !== "default" && role.trim() !== "") {
        $("#role-error").hide();
        return true;
    } else {
        $("#role-error").show();
        return false;
    }
};

const validateName = () => {
    const name = $("#name").val();

    if (name.trim() !== "" && name.trim().length > 0) {
        $("#name-error").hide();
        return true;
    } else {
        $("#name-error").show();
        return false;
    }
};

const validateEmail = () => {
    const emailValidator =
        /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    const email = $("#username").val();

    if (email.trim() !== "" && email.match(emailValidator)) {
        $("#email-error").hide();
        return true;
    } else {
        $("#email-error").show();
        return false;
    }
};

const validatePassword = () => {
    const passwordValidator = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";
    const password = $("#password").val();

    if (
        password.trim() !== "" &&
        password.trim().length >= 8 &&
        password.match(passwordValidator)
    ) {
        $("#password-error").hide();
        return true;
    } else {
        $("#password-error").show();
        return false;
    }
};

const validatePhone = () => {
    const phoneValidator = /^\d{10}$/;
    const phone = $("#phone").val();

    if (
        phone.trim() !== "" &&
        phone.trim().length === 10 &&
        phone.match(phoneValidator)
    ) {
        $("#phone-error").hide();
        return true;
    } else {
        $("#phone-error").show();
        return false;
    }
};

const validateAddress = () => {
    const address = $("#address").val();

    if (address.trim() !== "") {
        $("#address-error").hide();
        return true;
    } else {
        $("#address-error").show();
        return false;
    }
};

const validateCity = () => {
    const city = $("#city").val();

    if (city.trim() !== "" && city.trim().length > 3) {
        $("#city-error").hide();
        return true;
    } else {
        $("#city-error").show();
        return false;
    }
};

const validateCountry = () => {
    const country = $("#country").val();

    if (country.trim() !== "" && country.trim() !== "-1") {
        $("#country-error").hide();
        $("#state-country-error").hide();
        return true;
    } else {
        $("#country-error").show();
        return false;
    }
};

const validateState = () => {
    const state = $("#state").val();

    if (state === null) {
        $("#state-country-error").show();
        $("#state-error").hide();
        return false;
    } else if (state.trim() !== "" && state.trim() !== "Select State") {
        $("#state-error").hide();
        $("#state-country-error").hide();
        return true;
    } else {
        $("#state-error").show();
        $("#state-country-error").hide();
        return false;
    }
};

const validatePincode = () => {
    const pincode = $("#pincode").val();

    if (pincode.trim() !== "" && pincode.trim().length === 6) {
        $("#pincode-error").hide();
        return true;
    } else {
        $("#pincode-error").show();
        return false;
    }
};

const validateUserPersonalInformation = async () => {
    const pib = $("#signup-pib");
    const aib = $("#signup-aib");
    const loader = $("#processing-container");
    const email = $("#username").val();
    const _csrf = $("input[name=_csrf]").val();
    let isEmailRegistered;

    loader.show();
    await $.post("/verify-username", `_csrf=${_csrf}&email=${email}`)
        .done((resp) => {
            isEmailRegistered = resp;
        })
        .fail((resp) => {
            console.log(resp);
        });
    loader.hide();

    if (!isEmailRegistered) {
        let isRoleValidated = validateRole();
        let isNameValidated = validateName();
        let isEmailValidated = validateEmail();
        let isPasswordValidated = validatePassword();
        let isPhoneValidated = validatePhone();

        if (isNameValidated && isRoleValidated && isEmailValidated && isPasswordValidated && isPhoneValidated) {
            // proceed application to address info
            pib.hide();
            aib.show();
        } else {
            // show correct input warnings
            console.log("rectify errors in personal info");
        }
    } else {
        // email already registered, show error message
        $("#email-registered-error").show();
    }
};

const submitSignupForm = async () => {
    const signupForm = $("#signup-form");
    const formData = signupForm.serialize();
    const isAddressValidated = validateAddress();
    const isCityValidated = validateCity();
    const isCountryValidated = validateCountry();
    const isStateValidated = validateState();
    const isPincodeValidated = validatePincode();
    const loader = $("#loader-container");

    if (isAddressValidated && isCityValidated && isCountryValidated && isStateValidated && isPincodeValidated) {
        let response;

        loader.show();
        await $.post("/register", formData)
            .done((resp) => {
                response = resp;
            })
            .fail((resp) => {
                console.log(resp);
            });
        loader.hide();

        if (response === "ERRORS" || response === "null") {
            window.location = "/signup";
        }

        if (response === "SUCCESS") {
            window.location = "/verify-otp";
        }

        if (response === "FAIl") {
            swal("Error processing request");
        }
    } else {
        // there is some error, fix those error and then proceed
        console.log("errors in registration form");
    }
};

const startTimer = (duration, display) => {
    let timer = duration;
    let minutes;
    let seconds;

    let myInterval = setInterval(() => {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        if (display !== null) {
            display.textContent = minutes + ":" + seconds;
        }

        if (--timer < 0) {
            document.getElementById("orb").disabled = false;
            clearInterval(myInterval);
            $("#timer-countdown").hide();
            $("#orb").show();
        }
    }, 1000);
};

const resendOTP = async () => {
    const loaderContainer = $("#resend-loader-container");
    const _csrf = $("input[name=_csrf]").val();
    let otpResend;

    loaderContainer.show();

    await $.post("/resend-otp", `_csrf=${_csrf}`)
        .done((resp) => {
            otpResend = resp;
        })
        .fail((resp) => {
            console.log("Failure resp: " + resp);
        });

    if (otpResend) {
        swal("OTP resend successfully");
    } else {
        swal("Failed to resend OTP. Please try again later");
    }

    loaderContainer.hide();
    $("#orb").hide();
    let duration = 60;
    let display = document.getElementById("timer");
    startTimer(duration, display);
    $("#timer-countdown").show();
};

const confirmOTP = async () => {
    const otp = $("#otp-sb-ti").val();
    const _csrf = $("input[name=_csrf]").val();
    const url = "/process-otp-verification";
    const loader = $("#loader-container");
    let confirmOtpResult = null;

    if (otp.trim().length !== 6) {
        swal("Please enter valid OTP");
    } else {
        loader.show();
        await $.post(url, `_csrf=${_csrf}&otp=${otp}`)
            .done((resp) => {
                confirmOtpResult = resp;
            })
            .fail((resp) => {
                console.log("OTP verification failed response: " + resp);
            });

        loader.hide();

        switch (confirmOtpResult) {
            case "OTP_EXPIRED":
                swal("OTP Expired");
                break;
            case "OTP_MISMATCHED":
                swal("OTP mismatched");
                break;
            case "UNAUTHORIZED_ACCESS":
                swal("Unauthorized access");
                break;
            case "REGISTRATION_SUCCESSFUL":
                swal("Congratulations", "Registration successful!", "success");
                window.location = "/login";
                break;
            default:
                swal("Something went wrong, Please try again after some time");
                break;
        }
    }
};

const login = async () => {
    const _csrf = $("input[name=_csrf]").val();
    const email = $("#username").val();
    const password = $("#password").val();
    const loader = $("#lbl-anim");
    const ere = $("#ere");
    const eee = $("#eee");
    const pe = $("#password-error");
    let isEmailRegistered;
    const emailValidator = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    const passwordValidator = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";

    if (email.trim() === '') {
        eee.show();
        ere.hide();
        return;
    }

    if (!email.trim().match(emailValidator)) {
        eee.show();
        ere.hide();
        return;
    }

    if (password.trim() === '') {
        pe.show();
        return;
    }

    if (!password.trim().match(passwordValidator)) {
        pe.show();
        return;
    }

    const loginForm = $("#login-form");
    console.log(loginForm.serialize());
    loader.show();

    await $.post("/verify-username", `_csrf=${_csrf}&email=${email}`)
        .done((resp) => {
            isEmailRegistered = resp;
        })
        .fail((resp) => {
            console.log(resp);
        });
    loader.hide();

    if (isEmailRegistered) {
        ere.hide();
        eee.hide();
        pe.hide();
        $("#slb").click();
    } else {
        ere.show();
        eee.hide();
        pe.hide();
    }
}