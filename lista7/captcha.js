$(document).ready(function () {
    document.getElementById("_submit").disabled = true;
});

function recaptchaCallback() {
    $('#_submit').removeAttr('disabled');
};
