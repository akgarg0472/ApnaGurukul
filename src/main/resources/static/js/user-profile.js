const showSidemenu = () => {
    $("#hmbg-mnicn").hide("fast");
    $("#cross-mnicn").show("fast");
    $("#sidebar").show("fast");
    $("#cross-mnicn").css("position", "fixed");
    $("#cross-mnicn").css("top", "4.5rem");
};

const hideSidemenu = () => {
    $("#cross-mnicn").hide("fast");
    $("#sidebar").hide("fast");
    $("#hmbg-mnicn").show("fast");
};

$(window).resize(() => {
    if ($(window).width() > 449) {
        $("#sidebar").css("display", "flex");
        $("#resp-icons").hide();
    } else {
        $("#sidebar").css("display", "none");
        $("#resp-icons").css("display", "flex");
        $("#hamburger").show("fast");
    }
});

$(document).ready(() => {
    const currentTime = new Date();
    const currentOffset = currentTime.getTimezoneOffset();
    const ISTOffset = 330;

    const ISTTime = new Date(
        currentTime.getTime() + (ISTOffset + currentOffset) * 60000
    );

    const hoursIST = ISTTime.getHours();

    if (hoursIST > 4 && hoursIST < 12) {
        $("#wgmsg").html("Good Morning");
    } else if (hoursIST >= 12 && hoursIST < 17) {
        $("#wgmsg").html("Good Afternoon");
    } else if (hoursIST >= 17 && hoursIST < 21) {
        $("#wgmsg").html("Good Evening");
    } else if (hoursIST >= 21 || hoursIST <= 4) {
        $("#wgmsg").html("Good Night");
    } else {
        $("#wgmsg").html("Have a nice day");
    }
});

const logout = () => {
    swal({
        text: "Are you sure want to logout?",
        icon: "warning",
        buttons: ["No", "Yes"],
        dangerMode: true,
    })
        .then((willlogout) => {
            if (willlogout) {
                window.location.replace('/user/logout');
            } else {
                // todo nothing
            }
        });
}