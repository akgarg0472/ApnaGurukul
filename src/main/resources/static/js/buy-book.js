// noinspection JSUnresolvedFunction,JSUnresolvedVariable,JSUnusedGlobalSymbols

$(document).ready(() => {
    $("#loader-div").hide();
});


const closeAlertDialog = () => {
    let alertDialog = $("#rdr-alrt-nt");
    alertDialog.css("opacity", 0);

    setTimeout(() => {
        alertDialog.hide();
    }, 200);
};


const changePriceRange = (rangeSlider) => {
    const val = $(rangeSlider).val();
    const label = $("#srch-bk-price-rng-lbl");
    label.text(`Book price (Min: 1, Max: ${val})`);
};


const setMoreInfoModalData = (bookname, bookPhotoUrl, bookPublisher, bookAuthor, bookClass, bookPrice, bookCondition,
                              sellerName, sellerCity, sellerState, sellerCountry) => {
    const modalData =
        `<div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="more-info-modal-title">${bookname}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

            <div class="modal-body">
                <div class="mi-mdl-bp-div">
                    <img src="${bookPhotoUrl}" alt="Book picture" id="mi-mdl-bp" class="mi-mdl-bp">
                </div>

                <div class="mi-mdl-bi-div">
                    <span><strong>Book Details:</strong></span>
                    <span>Name: ${bookname}</span>
                    <span>Publisher: ${bookPublisher}</span>
                    <span>Author: ${bookAuthor}</span>
                    <span>Class: ${bookClass}</span>
                    <span>Price: ${bookPrice}</span>
                    <span>Condition: ${bookCondition}</span>

                    <span style="margin-top: 12px;"><strong>Seller Information:</strong></span>
                    <span>Seller Name: ${sellerName}</span>
                    <span>Seller City: ${sellerCity}</span>
                    <span>Seller State: ${sellerState}</span>
                    <span>Seller Country: ${sellerCountry}</span>
                </div>
            </div>

            <div class="text-center my-2">
                <button type="button" class="btn btn-outline-success" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>`;

    $("#moreInfoModal").html(modalData);

    const modal = new bootstrap.Modal(document.getElementById("moreInfoModal"));
    modal.show();
};


const setContactSellerModalData = (id, name, email, phone, city, state, country, pincode) => {
    const modalData =
        `<div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="more-info-modal-title">Contact Seller</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <div class="modal-body">
                    <h6>Please fill out following form</h6>

                    <form class="cntc-slr-form" id="cntc-slr-form" action="#" method="post ">
                        <input type="text" class="form-control mb-1" placeholder="Ad listing id" value="${id}" style="display: none;" id="cs-id">

                        <input type="text " class="form-control mb-1" placeholder="Enter name " id="cs-name" value="${name}">
                        <span class="color-error" id="name-error" style="display: none">Please enter your name</span>
                        
                        <input type="email " class="form-control mb-1 mt-1" placeholder="Enter email-id " id="cs-email" value="${email}" readonly>
                        <span class="color-error" id="email-error" style="display: none">Please enter valid email</span>

                        <input type="number " class="form-control mb-1 mt-1" placeholder="Enter contact number" id="cs-contact" value="${phone}">
                        <span class="color-error" id="phone-error" style="display: none">Please enter valid phone number</span>
                        
                        <input type="text " class="form-control mb-1 mt-1" placeholder="Enter city" id="cs-city" value="${city}">
                        <span class="color-error" id="city-error" style="display: none">Please enter valid city</span>
                        
                        <input type="text " class="form-control mb-1 mt-1" placeholder="Enter state" id="cs-state" value="${state}">
                        <span class="color-error" id="state-error" style="display: none">Please enter valid state</span>

                        <input type="number" class="form-control mb-1 mt-1" placeholder="Enter pincode" id="cs-pincode" value="${pincode}">
                        <span class="color-error" id="pincode-error" style="display: none">Please enter valid pincode</span>
                        
                        <input type="text " class="form-control mb-1 mt-1" placeholder="Enter country" id="cs-country" value="${country}">
                        <span class="color-error" id="country-error" style="display: none">Please enter valid country</span>
                        
                        <textarea class="form-control mt-1" rows="4" placeholder="Enter additional message (if any)" id="cs-add-msg"></textarea>

                        <div class="text-center mt-3 ">
                            <button type="button" class="btn btn-success" id="cs-sbmt-btn" onClick='validateContactSellerForm()'>Submit</button>
                            <button type="reset" class="btn btn-danger">Reset</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>`;

    $("#contactSellerModal").html(modalData);
    const modal = new bootstrap.Modal(document.getElementById("contactSellerModal"));
    swal("One Request", "Please give respect to other side person", "warning").then(() => {
        modal.show();
    })
};


const validateName = (name) => {
    if (name === null || name.trim() === '' || name.trim().length < 3) {
        $("#name-error").show();
        return false;
    } else {
        $("#name-error").hide();
        return true;
    }
};


const validateEmail = (email) => {
    const emailValidator =
        /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    if (email.trim() !== "" && email.match(emailValidator)) {
        $("#email-error").hide();
        return true;
    } else {
        $("#email-error").show();
        return false;
    }
};


const validatePhone = (phone) => {
    const phoneValidator = /^\d{10}$/;

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


const validateCity = (city) => {
    if (city.trim() !== "" && city.trim().length > 3) {
        $("#city-error").hide();
        return true;
    } else {
        $("#city-error").show();
        return false;
    }
};


const validateState = (state) => {
    if (state === null || state.trim() === '' || state.trim().length < 3) {
        $("#state-error").show();
        return false;
    } else {
        $("#state-error").hide();
        return true;
    }
};


const validatePincode = (pincode) => {
    const pincodeValidator = /^\d{6}$/;

    if (pincode !== null && pincode.trim() !== "" && pincode.trim().length === 6 && pincode.match(pincodeValidator)) {
        $("#pincode-error").hide();
        return true;
    } else {
        $("#pincode-error").show();
        return false;
    }
};


const validateCountry = (country) => {
    if (country.trim() !== "" && country.trim() !== "-1") {
        $("#country-error").hide();
        return true;
    } else {
        $("#country-error").show();
        return false;
    }
};


const validateContactSellerForm = () => {
    $('.color-error').hide();

    const id = $("#cs-id").val();
    const name = $('#cs-name').val();
    const email = $("#cs-email").val();
    const contact = $("#cs-contact").val();
    const city = $("#cs-city").val();
    const state = $("#cs-state").val();
    const pincode = $("#cs-pincode").val();
    const country = $("#cs-country").val();
    const message = $("#cs-add-msg").val();

    if (!validateName(name)) {
        return;
    }

    if (!validateEmail(email)) {
        return;
    }

    if (!validatePhone(contact)) {
        return;
    }

    if (!validateCity(city)) {
        return;
    }

    if (!validateState(state)) {
        return;
    }

    if (!validatePincode(pincode)) {
        return;
    }

    if (!validateCountry(country)) {
        return;
    }

    // everything verified and validated, now show loader that something is under progress
    const _csrf = $("input[name=_csrf]").val();
    const formData = `&id=${id}&name=${name}&email=${email}&phone=${contact}&city=${city}&state=${state}&pincode=${pincode}&country=${country}&message=${message}`;
    const url = `/process-buy-book-req`;
    const loader = $("#loader-div");

    loader.show();
    $('#contactSellerModal').modal('hide');

    $.post(url, `_csrf=${_csrf}${formData}`)
        .done((resp) => {
            loader.hide();
            if (resp.severity === 'high') {
                swal("Error", resp.message, "error");
            } else {
                swal("Congratulations", "Request sent to seller successfully", "success");
            }
        })
        .fail(() => {
            loader.hide();
            swal('Error', 'Something wrong happen, please try again later', 'info')
        });
}