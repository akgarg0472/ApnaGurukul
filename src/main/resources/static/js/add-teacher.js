// noinspection DuplicatedCode,JSUnresolvedFunction,JSUnresolvedVariable
// noinspection JSUnresolvedFunction

const name = $("#name");
const email = $("#email");
const experience = $("#experience");
const subject = $("#subject");
const mode = $("#mode");
const city = $("#city");
const state = $("#state");
const country = $("#country");
const pincode = $("#pincode");

$(document).ready(() => {
    $(".error").hide();
});

const validateAddTeacherForm = () => {
    $(".error").hide();
    const emailValidator = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

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
        !emailValidator.test(email.val().trim())
    ) {
        $("#eml-err").show();
        return false;
    }

    if (experience === null || experience.val().trim() === "") {
        $("#exp-err").show();
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

const addTeacher = () => {
    const validationResult = validateAddTeacherForm();
    if (validationResult) {
        const loader = $("#loader-div");
        loader.show();
        
        $.post('/user/add-teacher', `_csrf=${$("input[name=_csrf]").val()}&name=${name.val().trim()}&experience=${experience.val().trim()}&email=${email.val().trim()}&subject=${subject.val().trim()}&mode=${mode.val().trim()}&city=${city.val().trim()}&state=${state.val().trim()}&country=${country.val().trim()}&pincode=${pincode.val().trim()}`)
            .done((resp) => {
                loader.hide();

                if (resp.status === 201) {
                    swal('Success', resp.message, 'success');
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
