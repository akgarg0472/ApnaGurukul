// noinspection JSUnresolvedVariable

$(document).ready(() => {
    hideAllErrors();
})

function hideAllErrors() {
    $("#bk-ttlerr").hide();
    $("#bk-clserr").hide();
    $("#bk-strmerr").hide();
    $("#bk-abierr").hide();
    $("#bk-dntbkmsg").hide();
    $("#bk-operr").hide();
    $("#bk-sperr").hide();
    $("#bk-wrgperr").hide();
    $("#bk-qltyerr").hide();
    $("#bk-bkphterr").hide();
    $("#bk-bkphtszerr").hide();
}

function classSelect() {
    const selected = $("#select-book-class").children("option:selected").val();

    if (selected === 'UG' || selected === 'PG') {
        $("#bid-ip-abi").show();
        $("#select-book-stream").hide();
    } else if (selected === 'eleventh' || selected === 'twelfth') {
        $("#select-book-stream").show();
        $("#bid-ip-abi").hide();
    } else {
        $("#bid-ip-abi").hide();
        $("#select-book-stream").hide();
    }
}

function donateBook(donate) {
    if (donate === true) {
        $("#dnt-bk-opsp-div").hide();
        $("#bk-dntbkmsg").show();
    } else {
        $("#dnt-bk-opsp-div").show();
        $("#bk-dntbkmsg").hide();
    }
}

async function validateBookInformation() {
    const bookTitle = $("#book-title");
    const bookAuthor = $("#book-author").val();
    const bookPublisher = $("#book-publisher").val();
    const bookClass = $("#select-book-class").children("option:selected");
    const bookStream = $("#select-book-stream").children("option:selected");
    const additionalBookInfo = $("#book-add-info");
    let donateBook = $("input[type='radio'][name='donate-book']:checked");
    let originalPrice = $("#book-op");
    let sellingPrice = $("#book-sp");
    const bookQuality = $("#select-book-quality").children("option:selected");
    const bookPhoto = $("#book-photo-file");

    hideAllErrors();

    if (bookTitle == null || bookTitle.val().trim() === '') {
        $("#bk-ttlerr").show();
        return;
    }

    if (bookClass == null || bookClass.val() === 'null') {
        $("#bk-clserr").show();
        return;
    }

    if (bookClass.val() === 'eleventh' || bookClass.val() === 'twelfth') {
        if (bookStream == null || bookStream.val() === 'null') {
            $("#bk-strmerr").show();
            return;
        }
    }

    if (bookClass.val() === 'UG' || bookClass.val() === 'PG') {
        if (additionalBookInfo == null || additionalBookInfo.val().trim() === '') {
            $("#bk-abierr").show();
            return;
        }
    }

    if (donateBook != null && donateBook.val() === 'false') {
        if (originalPrice == null || originalPrice.val().trim() === '') {
            $("#bk-operr").show();
            return;
        }

        if (sellingPrice == null || sellingPrice.val().trim() === '') {
            $("#bk-sperr").show();
            return;
        }

        if (parseInt(sellingPrice.val().trim()) > parseInt(originalPrice.val().trim())) {
            $("#bk-wrgperr").show();
            return;
        }

        originalPrice = originalPrice.val();
        sellingPrice = sellingPrice.val();
    }

    if (donateBook.val() === 'true') {
        originalPrice = '0';
        sellingPrice = '0';
    }

    if (bookQuality == null || bookQuality.val().trim() === 'null') {
        $("#bk-qltyerr").show();
    }

    if (bookPhoto == null || bookPhoto[0].files.length === 0) {
        $("#bk-bkphterr").show();
        return;
    }

    if (bookPhoto[0].files.length === 1 && bookPhoto[0].files[0].size >= 1048576) {
        $("#bk-bkphtszerr").show();
        return;
    }

    const _csrf = $("input[name=_csrf]").val();
    const url = `/process-sell-book`;
    const image = bookPhoto[0].files[0];
    let imageData = await fileToBase64(image);
    imageData = encodeURIComponent(imageData.substring(imageData.indexOf(",") + 1));

    const bookData = `&bookTitle=${bookTitle.val()}&bookAuthor=${bookAuthor}&bookPublisher=${bookPublisher}` +
        `&bookClass=${bookClass.val()}&bookStream=${bookStream.val()}&additionalBookInfo=${additionalBookInfo.val()}` +
        `&donateBook=${donateBook.val() !== 'false'}&originalPrice=${parseInt(originalPrice)}` +
        `&sellingPrice=${parseInt(sellingPrice)}&bookQuality=${bookQuality.val()}&image=${imageData}`;

    $.post(url, `_csrf=${_csrf}${bookData}`)
        .done((resp) => {
            if (resp.severity === 'high') {
                swal("Error", resp.message, "error");
            } else {
                swal({
                    title: "Congratulations",
                    text: "Book successfully listed for selling",
                    type: "success"
                }).then(() => {
                    window.location = '/';
                });
            }
        })
        .fail(() => {
            swal({title: "Error", text: 'Something wrong happen, please try again later', type: "error"}).then(() => {
                window.location = '/sell-book';
            });
        });
}

const fileToBase64 = async (file) =>
    new Promise((resolve, reject) => {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = () => resolve(reader.result)
        reader.onerror = (e) => reject(e)
    })




