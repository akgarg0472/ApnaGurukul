const cityInput = document.getElementById("search__form__city");
const stateInput = document.getElementById("search__form__state");
const countryInput = document.getElementById("search__form__country");

$(document).ready(() => {
    if (window.navigator.geolocation) {
        window.navigator.geolocation.getCurrentPosition(
            fetchLocationDetails,
            locationDenied
        );
    } else {
        alert(`This browser is not supported`);
    }
});

const fetchLocationDetails = async (obj) => {
    const longitude = obj.coords.longitude;
    const latitude = obj.coords.latitude;
    const url = `https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer/reverseGeocode?f=pjson&featureTypes=&location=${longitude},${latitude}`;

    const address = await fetch(url)
        .then((resp) => resp.json())
        .then((resp) => resp.address)
        .catch((err) => console.log(err));

    const state = address.Region;
    const city = address.Subregion;
    const postal = address.Postal;
    const country = address.CountryCode;

    cityInput.value = city;
    stateInput.value = state;
    countryInput.value = country === "IND" ? "India" : "";

    document.getElementById(
        "show-res-para"
    ).innerHTML = `Showing results for <strong>${city}, ${state}</strong>`;
};

const locationDenied = (obj) => {
    alert(`Please enable location permission!!\nError message: ${obj.message}`);
};

const performSearch = () => {
    document.getElementById(
        "show-res-para"
    ).innerHTML = `Showing results for <strong>${cityInput.value}, ${stateInput.value}</strong>`;
};
