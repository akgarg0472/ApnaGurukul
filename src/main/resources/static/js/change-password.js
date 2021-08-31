$(document).ready(() => {
    $("#upwd-err").hide();
    $("#upwd-err-msg").hide();
});

const updatePassword = () => {
    $("#upwd-err").hide();
    $("#upwd-err-msg").hide();

    const oldPassword = $("#old-pass");
    const newPassword = $("#new-pass");
    const confirmNewPassword = $("#cnew-pass");
    const passwordValidator = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";
    const passwordMessage =
        "Your password must be minimum 8 characters long, contain letters, one special character and numbers, and must not contain spaces";

    if (
        oldPassword === null ||
        oldPassword.val().trim() === "" ||
        !oldPassword.val().trim().match(passwordValidator)
    ) {
        $("#upwd-err").show();
        $("#upwd-err-msg").show();
        $("#upwd-err").text("Invalid old Password");
        $("#upwd-err-msg").text(passwordMessage);
        return;
    }

    if (
        newPassword === null ||
        newPassword.val().trim() === "" ||
        !newPassword.val().trim().match(passwordValidator)
    ) {
        $("#upwd-err").show();
        $("#upwd-err-msg").show();
        $("#upwd-err").text("Invalid new Password");
        $("#upwd-err-msg").text(passwordMessage);
        return;
    }

    if (
        confirmNewPassword === null ||
        confirmNewPassword.val().trim() === "" ||
        !confirmNewPassword.val().trim().match(passwordValidator)
    ) {
        $("#upwd-err").show();
        $("#upwd-err-msg").show();
        $("#upwd-err").text("Invalid confirm new Password");
        $("#upwd-err-msg").text(passwordMessage);
        return;
    }

    if (newPassword.val() !== confirmNewPassword.val()) {
        $("#upwd-err").show();
        $("#upwd-err").text("New passwords didn't match");
        return;
    }

    // all good for client side upto now, now server side task
    const _csrf = $("input[name=_csrf]").val();
    const url = "/user/process-upwd";
    const loader = $("#loader-div");
    loader.show();

    $.post(url, `_csrf=${_csrf}&oldPass=${oldPassword.val()}&newPass=${newPassword.val()}&cNewPass=${confirmNewPassword.val()}`)
        .done((resp) => {
            if (resp.severity === 'none') {
                loader.hide();
                swal('Congratulations', resp.message, "success").then(() => {
                    window.location = '/user/dashboard';
                })
            } else {
                loader.hide();
                swal(`Error`, resp.message, 'error');
            }
        })
        .fail((resp) => {
            console.log(resp);
            swal('Server error', 'Failed to update password. Please try again later', 'error').then(() => {
                window.location = '/user/change-password';
            });
        });
};
