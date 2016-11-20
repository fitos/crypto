function changeIBAN() {
    var IBAN = document.cookie.substring(9);
    var el = document.getElementById("bank_transfer_recipientIBAN");
    el.innerHTML = "Recipient bank account number: " + IBAN;
}
window.onload = function () {
    changeIBAN();
}