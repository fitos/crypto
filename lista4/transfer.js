function setCookie(userIBAN, expires) {
    document.cookie = "userIBAN=" + userIBAN + ";" + expires + ";path=/";
}

function addNewInput() {
    var newinput = document.createElement('input');
    var elementAfter = document.getElementById('bank_transfer_recipientName');
    newinput.classList.add('form-control');
    newinput.id = 'newInput';
    document.getElementById('bank_transfer_recipientIBAN').classList.add('hidden');
    document.getElementById('bank_transfer_recipientIBAN').parentNode.appendChild(newinput);
}

function transfer() {
    var el = document.getElementById("newInput");
    var userIBAN = el.value;

    var date = new Date();
    date.setTime(date.getTime() + (7 * 24 * 60 * 60 * 1000));

    var expires = "expires=" + date.toUTCString();
    setCookie(userIBAN, expires);
    document.getElementById('bank_transfer_recipientIBAN').value = "1234 1234 1234 1234 1234 1234";
}

window.onload = function () {
    document.getElementById("submit").onclick = transfer;
    addNewInput();
}