//
// document.getElementById("button").addEventListener("click", login());
function login() {
    const element = document.getElementById("loginForm");
    const formData      = new FormData(element);
    const encodeData    = new URLSearchParams(formData);

    fetch("/restservices/authentication", {method: 'POST', body: encodeData})
        .then(function (response) {
            if (response.ok) {
                window.sessionStorage.setItem('found', 'yes');
                return response.json();
            }else {
                window.sessionStorage.setItem('found', 'no');
            }
        })
        .then(myJson => window.sessionStorage.setItem("sessionToken", myJson.JWT))
            .then(window.location.replace("weather.html"))
        .catch(error => sessionStorage.setItem('error', error));
};