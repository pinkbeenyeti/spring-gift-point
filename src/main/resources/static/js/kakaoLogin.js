document.addEventListener('DOMContentLoaded', function() {
    var tokenDisplayElement = document.getElementById('tokenDisplay');

    if (tokenDisplayElement) {
        var token = tokenDisplayElement.innerText;

        if (token) {
            localStorage.setItem('access_token', token);
            console.log('Access Token has been saved to local storage.');
        } else {
            console.error('No token found to save.');
        }
    } else {
        console.error('Element with id "tokenDisplay" not found.');
    }
});