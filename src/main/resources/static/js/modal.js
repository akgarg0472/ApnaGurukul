const modal = document.getElementById("modal");

const closeModal = () => {
    modal.style.opacity = 0;
    setTimeout(() => {
        modal.style.display = "none";
    }, 200);
};
