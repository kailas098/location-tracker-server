var source = new EventSource("/subscribe");

source.addEventListener("message", function (event) {
  const message = Object.assign(new Message(), JSON.parse(event.data));
  displayMessage(message);
});

source.addEventListener("location-updated", function (event) {
  const locationAndRoutePackage = JSON.parse(event.data);
  displayLocation(locationAndRoutePackage.busLocation);
  updateLocation(locationAndRoutePackage.busLocation);
});


var latitude = 0.0;
var longitude = 0.0;

var map = L.map("map").setView([latitude, longitude], 16);

L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
  attribution: "Bus Tracking System",
}).addTo(map);

var busMarker = L.icon({
  iconUrl: "images/bus1.png",
  iconSize: [32, 32],
  iconAnchor: [16, 32],
  popupAnchor: [0, -32],
});

var marker = L.marker([latitude, long̥itude], { icon: busMarker }).addTo(map);


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

  fetch("/find-bus/" + busId)
    .then((response) => {
      if (!response.ok) {
        console.log("response error");
        throw new Error("HTTP-Error: " + response.status);
      }

      return response.json();
    })
    .then((data) => {

      console.log(data);
      updateLocation(data.busLocation);
      displayRoute(data.route);

    }).catch((error) => {
      console.error("error");
    });
}

function displayRoute(route) {
  var path = L.polyline(route, { color: 'red' }).addTo(map);
}

function updateLocation(data) {
  latitude = data.latitude;
  longitude = data.longitude;

  marker.setLatLng([latitude, longitude]);
  map.setView([latitude, longitude], 16);
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