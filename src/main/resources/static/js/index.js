
const boardOptionBtn = document.querySelector(".option-btn");

document.addEventListener("DOMContentLoaded", function() {
    const fadeInElements = document.querySelectorAll(".fade-in");

    fadeInElements.forEach((element, index) => {
        setTimeout(() => {
            element.style.animation = 'fadeInAnimation 2s forwards';
        }, 100 * index);
    });
});

boardOptionBtn.addEventListener('click', function() {

    const optionDropMenu = document.querySelector(".body-option");

    if(optionDropMenu.classList.contains('optionActive')) {
        optionDropMenu.classList.remove('optionActive');

    } else {
        optionDropMenu.classList.add('optionActive');

    }

});

function copyToClipboard() {

    var tempInput = document.createElement("input");
    tempInput.value = document.getElementById("copy-text").innerText;
    document.body.appendChild(tempInput);

    tempInput.select();
    document.execCommand("copy");
    document.body.removeChild(tempInput);

}


function hintModalOpen() {
    var hintBtn = document.querySelector('.modal-schema');

    hintBtn.classList.add('active');

}

function hintModalClose() {
    var hintBtn = document.querySelector('.modal-schema');

    hintBtn.classList.remove('active');

}




