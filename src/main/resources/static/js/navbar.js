$(".rnb-hamburger").on("click", () => {
    $("#nav-links").animate({
        height: "toggle",
    });

    if ($("#profile-nav-ulusb").css("display") === "none") {
        $("#profile-nav-ulusb").css("display", "flex");
    } else {
        $("#profile-nav-ulusb").css("display", "none");
    }

    if ($("#nav-ulusb").css("display") === "none") {
        $("#nav-ulusb").css("display", "flex");
    } else {
        $("#nav-ulusb").css("display", "none");
    }

    if ($(".nbr-art").css("display") === "none") {
        $(".nbr-art").css("display", "flex");
        $(".nb-btnr-link ").css("display", "flex");
    } else {
        $(".nbr-art").css("display", "none");
        $(".nb-btnr-link ").css("display", "none");
    }
});

$(window).resize(() => {
    if ($(window).width() > 768) {
        $("#nav-links").css("display", "flex");
        $("#nav-ulusb").css("display", "block");
        $(".nbr-art").css("display", "none");
        $(".nb-btnr-link ").css("display", "none");
        $("#profile-nav-ulusb").css("display", "block");
        $("#profile-dropdown-content").css("display", "none");
        $("#profile-dropdown-content").css("position", "absolute!important");
    } else {
        $("#nav-links").css("display", "none");
        $("#nav-ulusb").css("display", "none");
        $("#profile-dropdown-content").css("display", "none");
        $("#profile-nav-ulusb").css("display", "none");
    }
});

const toggleNavbarServicesDropDown = () => {
    $("#dropdown-content").animate({
        height: "toggle",
    });

    if ($("#dropdown-content").height() < 1) {
        $("#nb-crdn").removeClass("rotate-down");
        $("#nb-crdn").addClass("rotate-upper");
    } else {
        $("#nb-crdn").removeClass("rotate-upper");
        $("#nb-crdn").addClass("rotate-down");
    }
};

const toggleUserProfileDropdown = () => {
    $("#profile-dropdown-content").animate({
        height: "toggle",
    });

    if ($("#profile-dropdown-content").height() < 1) {
        $("#nb-pcrdn").removeClass("rotate-down");
        $("#nb-pcrdn").addClass("rotate-upper");
    } else {
        $("#nb-pcrdn").removeClass("rotate-upper");
        $("#nb-pcrdn").addClass("rotate-down");
    }
};

$("#demo").on("click", () => {
    toggleNavbarServicesDropDown();
});

$("#profile-nav-ulusb").on("click", () => {
    toggleUserProfileDropdown();
});

$(window).on("click", (e) => {
    if (
        e.target.id !== "demo-link" &&
        e.target.id !== "nvbr-dd-link" &&
        e.target.id !== "nb-crdn" &&
        e.target.id !== "demo" &&
        $("#dropdown-content").css("display") === "block"
    ) {
        toggleNavbarServicesDropDown();
    }

    if (
        e.target.id !== "nb-up-ic" &&
        e.target.id !== "nb-pcrdn" &&
        e.target.id !== "profile-nav-ulusb" &&
        e.target.id !== "rnb-pnu-sp" &&
        e.target.id !== "nvbr-dd-link" &&
        e.target.id !== "nvbr-dd-link-uname" &&
        e.target.id !== 'rnb-up-div' &&
        $("#profile-dropdown-content").css("display") === "block"
    ) {
        toggleUserProfileDropdown();
    }
});

const logout = (type) => {
    switch (type) {
        case 'student':
            window.location = '/student/logout';
            break;
        case 'admin':
            window.location = '/admin/logout';
            break;
        case 'teacher':
            window.location = '/faculty/logout';
            break;
    }
}
