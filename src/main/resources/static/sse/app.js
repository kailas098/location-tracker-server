var clientID = generateClientId();
var source = new EventSource("/subscribe/" + clientID);
source.addEventListener("location-updated", function (event) {
  const locationAndRoutePackage = Object.assign(new LocationAndRoutePackage(), JSON.parse(event.data));

  displayLocation(locationAndRoutePackage.busLocation);
  updateLocation(locationAndRoutePackage.busLocation);
});


var latitude = 0.0;
var longitude = 0.0;
var map = L.map("map").setView([latitude, longitude], 16);
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
  attribution: "Bus Tracking System",
}).addTo(map);
document.getElementsByClassName('leaflet-control-attribution')[0].style.display = 'none';
var busMarker = L.icon({
  iconUrl: "images/bus.png",
  iconSize: [32, 32],
  iconAnchor: [16, 32],
  popupAnchor: [0, -32],
});
var marker = L.marker([latitude, longitude], { icon: busMarker }).addTo(map);


function generateClientId() {
  let stringSet = "abcdefghijklmnopqrstuvwxyz";
  let res = "";
  let min = 0;
  let max = 25;

  for (let i = 0; i < 2; i++) {
    res += stringSet[Math.floor(Math.random() * (max - min + 1)) + min];
    res += Math.floor(Math.random() * 9) + 1
  }
  return res;
}

function displayLocation(location) {
  const locationContainer = document.getElementById("message-container");
  locationContainer.innerHTML =
    "<h4> lati: " +
    location.latitude +
    "<br>longi: " +
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

  fetch("/find-bus/" + clientID + "/" + busId)
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
      console.error("bus not with 'id: " + document.getElementById("id-field").value + "' not found");
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

class LocationAndRoutePackage {
  constructor(busLocation, route) {
    this.busLocation = busLocation;
    this.route = route;
  }
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