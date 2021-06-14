let element = document.querySelector("#about-animation");
let notes = ["Welcome to ApnaGurukul", "An ultimate platform for teachers & students", "Have a cheerful & wonderful day"];

const foo = (text, initialTime) => {
    element.innerHTML = "";

    for (let index = 0; index < text.length; index++) {
        setTimeout(() => {
            element.innerHTML = text.slice(0, index + 1);
        }, initialTime + 100 * index);
    }

    let time = initialTime + text.length * 100 + 2000;

    for (let index = 0; index < text.length; index++) {
        setTimeout(() => {
            element.innerHTML = text.slice(0, text.length - index - 1) + "|";
        }, time + 50 * index);
    }

    return time + text.length * 50;
};

let ttime;

const f = () => {
    let ftime = 0;
    for (let index = 0; index < notes.length; index++) {
        ftime = foo(notes[index], ftime);
    }
    return ftime;
};

ttime = f();

setInterval(f, ttime);