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

    const url = window.location.href.split('/');
    if (url.length > 4) {
        const city = url[url.length - 2];
        const state = url[url.length - 1];
        cityInput.value = city;
        stateInput.value = state;
        document.getElementById(
            "show-res-para"
        ).innerHTML = `Showing results for <strong>${city}, ${state}</strong>`;
    }
});

const fetchLocationDetails = async (obj) => {
    if (window.location.href.split('/').length <= 4) {
        const longitude = obj.coords.longitude;
        const latitude = obj.coords.latitude;
        const url = `https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer/reverseGeocode?f=pjson&featureTypes=&location=${longitude},${latitude}`;

        const address = await fetch(url)
            .then((resp) => resp.json())
            .then((resp) => resp.address)
            .catch((err) => console.log(err));

        const state = address.Region;
        const city = address.Subregion;
        const country = address.CountryCode;

        cityInput.value = city;
        stateInput.value = state;
        countryInput.value = country === "IND" ? "India" : "";

        document.getElementById(
            "show-res-para"
        ).innerHTML = `Showing results for <strong>${city}, ${state}</strong>`;

        performSearch();
    }
};

const locationDenied = (obj) => {
    alert(`Please enable location permission!!\nCause: ${obj.message}`);
};

const performSearch = () => {
    if (cityInput === null || cityInput.value.trim() === '') {
        swal('Error', 'Please enter valid city name', 'error');
        return;
    } else if (stateInput === null || stateInput.value.trim() === '') {
        swal('Error', 'Please enter valid state name', 'error');
        return;
    }

    window.location.href = `/find-students/${cityInput.value}/${stateInput.value}`;
};