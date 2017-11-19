function loadDoc() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("demo").innerHTML =
                this.responseText;
        }
    };
    xhttp.open("GET", "/hello", true);
    xhttp.send();
}

let websocket = new WebSocket("ws://localhost:8080/chat");

websocket.onopen = function (event) {
    console.log("ON_OPEN. Event.data = " + event.data);
};

websocket.onmessage = function(event){
    console.log(event.data);
    document.getElementById("message").innerHTML = event.data;
};

websocket.onclose = function (event) {
    console.log("Disconnected. Event.data - " + event.data);
};

function send() {
    websocket.send(document.getElementById("input").value);
}