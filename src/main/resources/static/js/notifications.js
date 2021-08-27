$(document).ready(() => {
    const count = $(".notification").length;

    for (let index = 1; index <= count; index++) {
        let color = index % 6;
        const child = $(`.notification:nth-child(${index})`);

        switch (color) {
            case 0:
                child.css("background-color", "rgba(31, 33, 37, 0.75)");
                child.css("color", "#fff");
                child.hover(
                    () => {
                        child.css("box-shadow", "1px 1px 1rem rgba(31, 33, 37, 0.75)");
                    },
                    () => {
                        child.css("box-shadow", "none");
                    }
                );
                break;

            case 1:
                child.css("background-color", "#ff6263");
                child.css("color", "#fff");
                child.hover(
                    () => {
                        child.css("box-shadow", "1px 1px 1rem #ff6263");
                    },
                    () => {
                        child.css("box-shadow", "none");
                    }
                );
                break;

            case 2:
                child.css("background-color", "#03c28c");
                child.css("color", "#fff");
                child.hover(
                    () => {
                        child.css("box-shadow", "1px 1px 1rem #03c28c");
                    },
                    () => {
                        child.css("box-shadow", "none");
                    }
                );
                break;

            case 3:
                child.css("background-color", "rgba(24, 44, 97, 0.7)");
                child.css("color", "#fff");
                child.hover(
                    () => {
                        child.css("box-shadow", "1px 1px 1rem rgba(24, 44, 97, 0.7)");
                    },
                    () => {
                        child.css("box-shadow", "none");
                    }
                );
                break;

            case 4:
                child.css("background-color", "#35b5e3");
                child.css("color", "#fff");
                child.hover(
                    () => {
                        child.css("box-shadow", "1px 1px 1rem #35b5e3");
                    },
                    () => {
                        child.css("box-shadow", "none");
                    }
                );
                break;

            case 5:
                child.css("background-color", "#fea47f");
                child.css("color", "#fff");
                child.hover(
                    () => {
                        child.css("box-shadow", "1px 1px 1rem #fea47f");
                    },
                    () => {
                        child.css("box-shadow", "none");
                    }
                );
                break;

            default:
                break;
        }
    }
});
