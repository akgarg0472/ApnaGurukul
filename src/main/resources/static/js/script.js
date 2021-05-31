const validateRole = () => {
    const role = $("#role").val();

    if (role.trim() !== 'default' && role.trim() !== '') {
        $("#role-error").hide();
        return true;
    } else {
        $("#role-error").show();
        return false;
    }
}

const validateName = () => {
    const name = $("#name").val();

    if (name.trim() !== '' && name.trim().length > 0) {
        $("#name-error").hide();
        return true;
    } else {
        $("#name-error").show();
        return false;
    }
}

const validateEmail = () => {
    const emailValidator = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    const email = $("#username").val();

    if (email.trim() !== '' && email.match(emailValidator)) {
        $("#email-error").hide();
        return true;
    } else {
        $("#email-error").show();
        return false;
    }
}

const validatePassword = () => {
    const passwordValidator = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";
    const password = $("#password").val();

    if (password.trim() !== '' && password.trim().length >= 8 && password.match(passwordValidator)) {
        $("#password-error").hide();
        return true;
    } else {
        $("#password-error").show();
        return false;
    }
}

const validatePhone = () => {
    const phoneValidator = /^\d{10}$/;
    const phone = $("#phone").val();

    if (phone.trim() !== '' && phone.trim().length === 10 && phone.match(phoneValidator)) {
        $("#phone-error").hide();
        return true;
    } else {
        $("#phone-error").show();
        return false;
    }
}

const validateAddress = () => {
    const address = $("#address").val();

    if (address.trim() !== '') {
        $("#address-error").hide();
        return true;
    } else {
        $("#address-error").show();
        return false;
    }
}

const validateCity = () => {
    const city = $("#city").val();

    if (city.trim() !== '' && city.trim().length > 3) {
        $("#city-error").hide();
        return true;
    } else {
        $("#city-error").show();
        return false;
    }
}

const validateCountry = () => {
    const country = $("#country").val();

    if (country.trim() !== '' && country.trim() !== '-1') {
        $("#country-error").hide();
        $("#state-country-error").hide();
        return true;
    } else {
        $("#country-error").show();
        return false;
    }
}

const validateState = () => {
    const state = $("#state").val();

    if (state === null) {
        $("#state-country-error").show();
        $("#state-error").hide();
        return false;
    } else if (state.trim() !== '' && state.trim() !== 'Select State') {
        $("#state-error").hide();
        $("#state-country-error").hide();
        return true;
    } else {
        $("#state-error").show();
        $("#state-country-error").hide();
        return false;
    }
}

const validatePincode = () => {
    const pincode = $("#pincode").val();

    if (pincode.trim() !== '' && pincode.trim().length === 6) {
        $("#pincode-error").hide();
        return true;
    } else {
        $("#pincode-error").show();
        return false;
    }
}

const validateUserPersonalInformation = async () => {
    const pib = $("#signup-pib");
    const aib = $("#signup-aib");

    const email = $("#username").val();
    const _csrf = $("input[name=_csrf]").val();
    let isEmailRegistered;

    await $.post("/verify-username", `_csrf=${_csrf}&email=${email}`)
        .done((resp) => {
            isEmailRegistered = resp;
        })
        .fail((resp) => {
            console.log(resp);
        })

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

}

const submitSignupForm = async () => {
    const signupForm = $("#signup-form");
    const formData = signupForm.serialize();

    const isAddressValidated = validateAddress();
    const isCityValidated = validateCity();
    const isCountryValidated = validateCountry();
    const isStateValidated = validateState();
    const isPincodeValidated = validatePincode();

    if (isAddressValidated && isCityValidated && isCountryValidated && isStateValidated && isPincodeValidated) {
        let response;

        await $.post("/register", formData)
            .done((resp) => {
                response = resp;
            })
            .fail((resp) => {
                console.log(resp);
            })

        if (response === 'ERRORS' || response === 'null' || response === 'FAIl') {
            window.location = '/signup';
        }

        if (response === 'SUCCESS') {
            window.location = '/verify-otp';
        }

    } else {
        // there is some error, fix those error and then proceed
        console.log("errors in registration form")
    }
}
