$(document).ready(() => {
    $("#cnus_lname_error").hide();
    $("#cnus_email_error").hide();
    $("#cnus_desc_error").hide();
});

const submit = async () => {
    const fname = $("#cnus_fname").val();
    const lname = $("#cnus_lname").val();
    const email = $("#cnus_email").val();
    const desc = $("#cnus_desc").val();
    const emailValidator = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

    if (lname === null || lname === "" || lname.trim() === "") {
        $("#cnus_lname_error").show();
        return;
    } else {
        $("#cnus_lname_error").hide();
    }

    if (
        email === null || email === "" || email.trim() === "" || !emailValidator.test(email)) {
        $("#cnus_email_error").show();
        return;
    } else {
        $("#cnus_email_error").hide();
    }

    if (desc === null || desc === "" || desc.trim() === "") {
        $("#cnus_desc_error").show();
        return;
    } else {
        $("#cnus_desc_error").hide();
    }

    //  all good here, proceed further
    const _csrf = $("input[name=_csrf]").val();

    await $.post("/cont-us-query", `_csrf=${_csrf}&fname=${fname}&lname=${lname}&email=${email}&desc=${desc}`)
        .done((resp) => {
            if (resp === true) {
                alert("Thank you for your query");
                $("#cnus_fname").val("");
                $("#cnus_lname").val("");
                $("#cnus_email").val("");
                $("#cnus_desc").val("");
            } else {
                alert("Error submitting query");
            }
        })
        .fail((resp) => {
            console.log("Server error in submitting query: " + resp);
        });
};
