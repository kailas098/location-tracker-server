var source = new EventSource("/subscribe");

source.addEventListener("message", function (event) {
const message = Object.assign(new Message(), JSON.parse(event.data));
displayMessage(message);
});

source.addEventListener("location-updated", function (event) {
const location = JSON.parse(event.data);
displayLocation(location);
});

function displayLocation(location) {
const locationContainer = document.getElementById("message-container");
locationContainer.innerHTML =
    "<h4> lati: " +
    location.latitude +
    ", longi: " +
    location.longitude +
    "<h4>";
}

function sendMessage() {
const messageInput = document.getElementById("message-input");
const usernameImput = document.getElementById("username-input");
const message = messageInput.value;
const username = usernameImput.value;

fetch("/send-message", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: { name: username, content: message },
})
    .then((response) => {
    if (!response.ok) {
        throw new Error("Failed to send");
    }
    messageInput.value = "";
    })
    .catch((error) => {
    console.error("error");
    });
}

function getBusLocation() {
    var busId = document.getElementById("id-field").value;

    fetch("/find-bus", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name: "kailas" }),
    })
      .then((response) => {
        if (!response.ok) {
          console.log("response error");
          throw new Error("HTTP-Error: " + response.status);
        }

        return response.json();
      })
      .then((data) => {
        console.log(data.latitude);
    }).catch((error)=>{
        console.error("error");
    });
  }

class Message {
constructor(name, content) {
    this.name = name;
    this.content = content;
}
}
class BusLocation {
constructor(bus_id, latitude, longitude) {
    this.bus_id = bus_id;
    this.latitude = latitude;
    this.longitude = longitude;
}
}