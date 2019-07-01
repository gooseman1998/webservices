window.onload = function(){
    initPage();
    loadCountries();
};
function initPage(){
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "https://ipapi.co/json", false ); // false for synchronous request
    xmlHttp.send( null );
    var json = JSON.parse(xmlHttp.responseText);
    document.getElementById("lCode").innerText = json.country;
    document.getElementById("land").innerText = json.country_name;
    document.getElementById("regioo").innerText = json.region;
    document.getElementById("stad").innerText = json.city;
    document.getElementById("stad2").innerText = json.city;
    document.getElementById("post").innerText = json.postal;
    document.getElementById("lat").innerText = json.latitude;
    document.getElementById("long").innerText = json.longitude;
    document.getElementById("ip").innerText = json.ip;
    // document.getElementById("Stad").onclick = function () {
    //     initPage();
    // }
    getWheather(json);
}

function getWheather(json){
    // console.log(localStorage.getItem(json.country_name));
    if(localStorage.getItem(json.country_name)=== null){
        let date = new Date();
        let url = `https://api.openweathermap.org/data/2.5/weather?lat=${json.latitude}&lon=${json.longitude}&appid=5a0c9fe48ebd7415f3c6a10135f7b60c`;
        let xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "GET", url, false ); // false for synchronous request
        xmlHttp.send( null );
        var data = JSON.parse(xmlHttp.responseText);
        data["time"] = date.getTime();
        localStorage.setItem(json.country_name, JSON.stringify(data));
        // console.log("setting and remote");
    }else{

        let localdata = JSON.parse(localStorage.getItem(json.country_name));
        let date = new Date();
        times = date.getTime();
        if((times - localdata.time) > 600000){
            let date = new Date();
            let url = `https://api.openweathermap.org/data/2.5/weather?lat=${json.latitude}&lon=${json.longitude}&appid=5a0c9fe48ebd7415f3c6a10135f7b60c`;
            let xmlHttp = new XMLHttpRequest();
            xmlHttp.open( "GET", url, false ); // false for synchronous request
            xmlHttp.send( null );
            var data = JSON.parse(xmlHttp.responseText);
            data["time"] = date.getTime();
            localStorage.setItem(json.country_name, JSON.stringify(data));
        }else{
            var data = localdata;
        }
    }

    console.log(data);
    document.getElementById("temp").innerText = data.main.temp;
    document.getElementById("lv").innerText = data.main.humidity;
    document.getElementById("ws").innerText = data.wind.speed;
    document.getElementById("wr").innerText = data.wind.deg;
    document.getElementById("zop").innerText = formatWeatherTime(data.sys.sunrise);
    document.getElementById("zon").innerText = formatWeatherTime(data.sys.sunset);
}

function appendchild(html){
    let selector = document.getElementById("vakanties");
    // console.log(html);
    selector.innerHTML  = selector.innerHTML +  html;
    var table = document.getElementById("vakanties");
    var trList = table.getElementsByTagName("tr");
    for (let i = 0;i < trList.length;i++){
        trList[i].onclick = function(e){
            let object  = {};


            if(e.srcElement.nodeName != "BUTTON"){


                object.longitude    =  trList[i].getAttribute("data-long");
                object.country_name =  trList[i].getAttribute("data-country");
                object.latitude     = trList[i].getAttribute("data-lat");
                getWheather(object);
            }
        };
    }

}

function loadCountries(){
    let selector = document.getElementById("vakanties");
    // console.log(html);
    selector.innerHTML  = "";
    fetch("/restservices/countries")
        .then(response => response.json())
        .then(data => {
        for (let i of data){
        // console.log(i);
        let html = `
                    <tr id="${i.Code}" data-country="${i.Name}" data-long="${i.Longtitude}" data-lat="${i.Latitude}">
                        <td>${i.Name}</td>
                        <td>${i.Capital}</td>
                        <td>${i.Region}</td>
                        <td>${i.Surface}</td>
                        <td>${i.Population}</td>
                        <td><button onclick="Edit('${i.Code.trim()}')">Edit</button><button onclick="remove('${i.Code.trim()}')">remove</button></td>
                    </tr>`;
        // appendchild(html);
        appendchild(html);
    }
})
}

function Edit(id){
    childeren = document.getElementById(id).childNodes;
    document.getElementById("modal").style = "display:block";
    let array = ["country" ,"capital" ,"region" ,"surface" ,"population"];
    let counter = 1;
    let id2 = document.getElementById("code").value = id;
    console.log(id2);

    for (let i of childeren){
        if(counter < 12){
            if(i.nodeName == "TD"){
                // console.log(i);
                let identifier = (counter / 2) - 1;
                document.getElementById(array[identifier]).value = i.innerText;
            }
        }
        counter ++;
    }


}

function update() {
    const element = document.getElementById("updateform");
    const formData      = new FormData(element);
    let id = document.getElementById("code");
    console.log(id.value);


    const encodeData    = new URLSearchParams(formData.entries());

    fetch("/restservices/countries/update/" + id.value, {
        method: 'PUT',
        body: encodeData
    }).then(data =>hidden(data,"modal"))
        .then(()=> loadCountries())
}

function save() {
    const element = document.getElementById("insertform");
    const formData      = new FormData(element);
    const encodeData    = new URLSearchParams(formData);

    fetch("/restservices/countries/save", {
        method: 'POST',
        body: encodeData
    }).then(data => hidden(data,"modal2"))
        .then(()=> loadCountries())
}


function remove(id){
    console.log(id);
    fetch( "/restservices/countries/" + id, {
        method: 'DELETE'
    }).then(() => {
        document.getElementById(id).remove();
    }).catch(err => {
        console.error(err)
    })
}

function loadWeatherData(lat, lon, city, country) {
    let headertext = document.getElementById("stad2");
    if(city != "") {
        headertext.innerText = "Het weer in " + city;
    }else{
        headertext.innerText = "Het weer in "+ country;
    }
    var fetchstring = 'https://api.openweathermap.org/data/2.5/weather?lat='+lat+'&lon='+lon+'&units=metric&appid='+weatherkey.toString();
    var today=new Date();
    console.log(today);
    var checkdate = today.setMinutes(today.getMinutes()-10);
    console.log(checkdate);
    var storageDate = localStorage.getItem("countryRetrieve"+lat);
    if(storageDate<=checkdate || storageDate == undefined) {
        fetch(fetchstring).then(function (response) {
            return response.json();
        }).then(function (weatherData) {
            console.log("Response "+ weatherData);
            localStorage.setItem(lat, JSON.stringify(weatherData));
            localStorage.setItem("countryRetrieve"+lat, today);
            insertWeatherData(weatherData);
        });
    } else {
        insertWeatherData(JSON.parse(localStorage.getItem(lat)));
    }
}

function insertWeatherData(weatherData) {
    console.log(weatherData);
    let temp = document.getElementById("temp");
    let luchtv = document.getElementById("lv");
    let winds = document.getElementById("ws");
    let windr = document.getElementById("wr");
    let zonop = document.getElementById("zop");
    let zonon = document.getElementById("zon");
    let land = document.getElementById("stad2");

    temp.innerText      = weatherData["main"]["temp"];
    luchtv.innerText    = weatherData["main"]["humidity"];
    winds.innerText     = weatherData["wind"]["speed"];
    windr.innerText     = weatherData["wind"]["deg"];
    land.innerText     = weatherData["sys"]["country"];
    console.log(land);
    console.log(weatherData["sys"]["country"]);

    // zonon.innerText     = weatherData["sys"]["sunrise"];    //needs formatting
    // zonop.innerText     = weatherData["sys"]["sunset"];
    zonop.innerText     = formatWeatherTime( weatherData["sys"]["sunrise"]);
    zonon.innerText     = formatWeatherTime( weatherData["sys"]["sunset"]);
}

function formatWeatherTime(seconds){
    var miliseconds = seconds*1000;
    var datum = new Date(miliseconds);
    var hours = datum.getHours();
    var minutes = datum.getMinutes();
    if(hours<10){
        hours = '0'+hours;
    }
    return hours+':'+minutes;
}







function hidden(data,id){
    console.log(data);
    const element = document.getElementById(id);
    element.style = "display:none";

    // loadCountries();
}