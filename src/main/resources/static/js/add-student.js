const name = $("#name");
const email = $("#email");
const _class = $("#_class");
const subject = $("#subject");
const mode = $("#mode");
const village = $("#village");
const city = $("#city");
const state = $("#state");
const country = $("#country");
const pincode = $("#pincode");

$(document).ready(() => {
    $(".error").hide();
});

const validateAddStudentForm = () => {
    $(".error").hide();
    const emailValidator =
        /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

    if (
        name === null ||
        name.val().trim() === "" ||
        name.val().trim().length < 3
    ) {
        $("#nm-err").show();
        return false;
    }

    if (
        email === null ||
        email.val().trim() === "" ||
        !emailValidator.test(email.val().trim())
    ) {
        $("#eml-err").show();
        return false;
    }

    if (_class === null || _class.val().trim() === "") {
        $("#class-err").show();
        return false;
    }

    if (subject === null || subject.val().trim() === "") {
        $("#sub-err").show();
        return false;
    }

    if (mode === null || mode.val().trim() === "null") {
        $("#mode-err").show();
        return false;
    }

    if (city === null || city.val().trim() === "") {
        $("#ct-err").show();
        return false;
    }

    if (state === null || state.val().trim() === "") {
        $("#st-err").show();
        return false;
    }

    if (country === null || country.val().trim() === "") {
        $("#cntry-err").show();
        return false;
    }

    if (
        pincode === null ||
        pincode.val().trim() === "" ||
        pincode.val().trim().length !== 6
    ) {
        $("#pc-err").show();
        return false;
    }

    return true;
};

const cancel = () => {
    window.location.href = "/user/dashboard";
}

const addStudent = () => {
    const validationResult = validateAddStudentForm();
    if (validationResult) {
        const loader = $("#loader-div");
        loader.show();

        $.post('/user/add-student', `_csrf=${$("input[name=_csrf]").val()}&name=${name.val().trim()}&currentClass=${_class.val().trim()}&email=${email.val().trim()}&subjects=${subject.val().trim()}&mode=${mode.val().trim()}&village=${village.val().trim()}&city=${city.val().trim()}&state=${state.val().trim()}&country=${country.val().trim()}&pincode=${pincode.val().trim()}`)
            .done((resp) => {
                loader.hide();

                if (resp.status === 201) {
                    swal('Success', resp.message, 'success');
                    name.val('');
                    email.val('');
                    _class.val('');
                    subject.val('');
                    mode.val('null');
                    village.val('');
                    city.val('');
                    state.val('');
                    country.val('');
                    pincode.val('');
                } else {
                    swal('Error', resp.message, 'warning');
                }
            })
            .fail((err) => {
                loader.hide();
                swal('Error', err.responseJSON.error.toString(), 'error');
            })
    }
};
