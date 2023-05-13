
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

function handleOnInput(e)  {
    e.value = e.value.replace(/[^A-Za-z]/ig, '')
}

$('#problemOption_sort1').ready(function () {
    $('#problemOption_sort1').prop("checked", true);
    $('#problemOption_order1').prop("checked", true);
    $('#problemOption_category1').prop("checked", true);
    $('#problemOption_level1').prop("checked", true);
    $('#problemOption_lang1').prop("checked", true);
    $('#problemOption_etc1').prop("checked", true);
})





