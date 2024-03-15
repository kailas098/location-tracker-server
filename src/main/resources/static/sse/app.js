const urlData = new URLSearchParams(window.location.search);
const clientID = urlData.get("id");
const token = urlData.get("token");
console.log(clientID, token);

fetch("/validate/" + clientID + "/" + token)
  .then((response) => {
    if (!response.ok) {
      alert("Login first");
      window.location.href = "login.html";
    }
  })

var source = new EventSource("/subscribe/" + clientID);
source.addEventListener("location-updated", function (event) {
  const locationAndRoutePackage = Object.assign(
    new LocationAndRoutePackage(),
    JSON.parse(event.data)
  );

  displayLocation(locationAndRoutePackage.busLocation);
  updateLocation(locationAndRoutePackage.busLocation);
});

var latitude = 0.0;
var longitude = 0.0;
var map = L.map("map").setView([latitude, longitude], 16);
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
  attribution: "Bus Tracking System",
}).addTo(map);
document.getElementsByClassName(
  "leaflet-control-attribution"
)[0].style.display = "none";
var busMarker = L.icon({
  iconUrl: "images/bus.png",
  iconSize: [32, 32],
  iconAnchor: [16, 32],
  popupAnchor: [0, -32],
});
var marker = L.marker([latitude, longitude], { icon: busMarker }).addTo(map);
var path = L.polyline([[latitude, longitude]], { color: "red" }).addTo(map);

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
  let busId = document.getElementById("id-field").value;

  fetch("/find-bus/" + clientID + "/" + busId)
    .then((response) => {
      console.log(response);
      if (response.status == 404) {
        throw new Error("Not found");
      }
      if (response.status == 401) {
        throw new Error("Not authorized.");
      }
      return response.json();
    })
    .then((data) => {
      console.log(data);
      updateLocation(data.busLocation);
      displayRoute(data.route);
    })
    .catch((error) => {
      alert(error);
      console.error(
        "bus not with id: " +
        document.getElementById("id-field").value +
        " not found"
      );
    });
}

function displayRoute(route) {
  map.removeLayer(path);
  path = L.polyline(route, { color: "red" }).addTo(map);
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
