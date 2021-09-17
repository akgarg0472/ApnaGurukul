// noinspection JSUnusedLocalSymbols,DuplicatedCode

$(document).ready(() => {
    $(".error").hide();
});


const showUpdatedImage = (file) => {
    const isFileValid = file.files && file.files[0];
    const extension = file.value
        .substring(file.value.lastIndexOf(".") + 1)
        .toLowerCase();

    const isUpdateValid =
        isFileValid &&
        (extension === "png" || extension === "jpeg" || extension === "jpg");

    if (isUpdateValid) {
        const reader = new FileReader();
        reader.onload = (e) => {
            $("#user-dp").attr("src", e.target.result);
        };
        reader.readAsDataURL(file.files[0]);
    } else {
        if (isFileValid) {
            swal("Warning", "Please select image file", "warning");
        } else {
            swal("Error", "Please select valid image file", "error");
        }
    }
};


const validateInput = () => {
    const email = $("#email");
    const name = $("#name");
    const phone = $("#phone");
    const address = $("#address");
    const city = $("#city");
    const state = $("#state");
    const country = $("#country");
    const pincode = $("#pincode");
    const emailValidator = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    const phoneValidator = /^\d{10}$/;

    if (
        email === null || email.text().toString().trim() === "" || !emailValidator.test(email.text().toString().trim())) {
        $(".error").hide();
        $("#eml-error").show();
        return false;
    }

    if (name === null || name.val().trim() === "") {
        $(".error").hide();
        $("#nm-err").show();
        return false;
    }

    if (phone === null || phone.val().trim() === "" || !phoneValidator.test(phone.val())) {
        $(".error").hide();
        $("#ph-err").show();
        return false;
    }

    if (address === null || address.val().trim() === "") {
        $(".error").hide();
        $("#add-err").show();
        return false;
    }

    if (city === null || city.val().trim() === "") {
        $(".error").hide();
        $("#ct-err").show();
        return false;
    }

    if (state === null || state.val().trim() === "") {
        $(".error").hide();
        $("#st-err").show();
        return false;
    }

    if (country === null || country.val().trim() === "") {
        $(".error").hide();
        $("#cntry-err").show();
        return false;
    }

    if (pincode === null || pincode.val().trim() === "") {
        $(".error").hide();
        $("#pc-err").show();
        return false;
    }

    return true;
};


const validateUpdateForm = async () => {
    const validate = validateInput();

    if (validate) {
        const profilePicture = $("#profile-picture");
        const email = $("#email");
        const name = $("#name");
        const phone = $("#phone");
        const address = $("#address");
        const city = $("#city");
        const state = $("#state");
        const country = $("#country");
        const pincode = $("#pincode");

        const _csrf = $("input[name=_csrf]").val();
        const url = `/user/process-up`;
        const image = profilePicture[0].files[0];
        const loader = $("#loader-div");
        loader.show();

        let imageData = null;
        if (image) {
            imageData = await fileToBase64(image);
            imageData = encodeURIComponent(imageData.substring(imageData.indexOf(",") + 1));
        }

        let profileData = `&email=${email.text().toString()}&name=${name.val()}&phone=${phone.val()}&city=${city.val()}` +
            `&address=${address.val()}&state=${state.val()}&country=${country.val()}&pincode=${pincode.val()}`;

        if (image) {
            profileData += `&picture=${imageData}`;
        }

        $.post(url, `_csrf=${_csrf}${profileData}`)
            .done((resp) => {
                loader.hide();
                swal('Congratulations', 'Your profile has been updated successfully', 'success').then(() => {
                    window.location = '/user/dashboard';
                })
            })
            .fail(() => {
                loader.hide();
                swal('Error', 'Error updating profile, please try again', 'error').then(() => {
                    window.location = '/user/update-profile';
                })
            });
    }
};


const fileToBase64 = async (file) =>
    new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(file);

            reader.onload = () => resolve(reader.result);

            reader.onerror = (e) => reject(e);
        }
    );