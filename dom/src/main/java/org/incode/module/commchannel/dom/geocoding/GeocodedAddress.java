package org.incode.module.commchannel.dom.geocoding;

import java.util.List;

public class GeocodedAddress {

    public static boolean isOk(final GeocodedAddress geocodedAddress) {
        return geocodedAddress != null && geocodedAddress.getStatus() == GeocodeApiResponse.Status.OK;
    }

    //region > fields, constructor
    private final GeocodeApiResponse apiResponse;
    private final String apiResponseAsJson;

    public GeocodedAddress(
            final GeocodeApiResponse apiResponse,
            final String apiResponseAsJson) {
        this.apiResponse = apiResponse;
        this.apiResponseAsJson = apiResponseAsJson;
    }

    //endregion

    //region > getApiResponse, getApiResponseAsJson
    /**
     * The results of the call to geocode API, converted into a typesafe data structure.
     */
    public GeocodeApiResponse getApiResponse() {
        return apiResponse;
    }

    /**
     * The raw results of the call to the geocode API, in json format.
     */
    public String getApiResponseAsJson() {
        return apiResponseAsJson;
    }
    //endregion

    //region > getStatus
    /**
     * Whether the API call succeeded.
     */
    public GeocodeApiResponse.Status getStatus() {
        return apiResponse.getStatus();
    }
    //endregion

    //region > getFormattedAddress
    /**
     * The <tt>formatted_address</tt> of the first matching result, or <tt>null</tt> if there were no matching results ({@link #getStatus()} did not return {@link GeocodeApiResponse.Status#OK ok}.
     */
    public String getFormattedAddress() {
        final GeocodeApiResponse.Result firstResult = firstResult(apiResponse);
        return firstResult != null? firstResult.getFormatted_address(): null;
    }
    //endregion

    //region > getPlaceId
    /**
     * The <tt>place_id</tt> of the first matching result, or <tt>null</tt> if there were no matching results ({@link #getStatus()} did not return {@link GeocodeApiResponse.Status#OK ok}.
     */
    public String getPlaceId() {
        final GeocodeApiResponse.Result firstResult = firstResult(apiResponse);
        return firstResult != null? firstResult.getPlace_id(): null;
    }
    //endregion

    //region > getPostalCode
    /**
     * The postal code, if any, of the first matching result, or <tt>null</tt> if there were no matching results ({@link #getStatus()} did not return {@link GeocodeApiResponse.Status#OK ok}.
     */
    public String getPostalCode() {
        return findAddressComponentLongName(apiResponse, GeocodeApiResponse.Result.Type.postal_code);
    }

    //endregion

    //region > getCountry
    /**
     * The country, if any, of the first matching result, or <tt>null</tt> if there were no matching results ({@link #getStatus()} did not return {@link GeocodeApiResponse.Status#OK ok}.
     */
    public String getCountry() {
        return findAddressComponentLongName(apiResponse, GeocodeApiResponse.Result.Type.country);
    }

    //endregion

    //region > getLocation
    /**
     * The <tt>geometry.location</tt> of the first matching result, or <tt>null</tt> if there were no matching results ({@link #getStatus()} did not return {@link GeocodeApiResponse.Status#OK ok}.
     */
    public GeocodeApiResponse.Location getLocation() {
        final GeocodeApiResponse.Result firstResult = firstResult(apiResponse);
        if(firstResult == null) {
            return null;
        }
        final GeocodeApiResponse.Geometry geometry = firstResult.getGeometry();
        if (geometry == null) {
            return null;
        }
        return geometry.getLocation();
    }

    /**
     * String representation of {@link #getLocation()}.
     * @return
     */
    public String getLatLng() {
        final GeocodeApiResponse.Location location = getLocation();
        if(location == null) {
            return null;
        }
        return location.getLat() + "," + location.getLng();
    }

    //endregion

    //region > helpers
    private static GeocodeApiResponse.Result firstResult(final GeocodeApiResponse apiResponse) {
        if (apiResponse.getStatus() != GeocodeApiResponse.Status.OK) {
            return null;
        }
        final List<GeocodeApiResponse.Result> results = apiResponse.getResults();
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    private static String findAddressComponentLongName(
            final GeocodeApiResponse apiResponse,
            final GeocodeApiResponse.Result.Type type) {
        GeocodeApiResponse.Result.AddressComponent addressComponent = findAddressComponent(apiResponse, type);
        return addressComponent != null? addressComponent.getLong_name() : null;
    }

    private static GeocodeApiResponse.Result.AddressComponent findAddressComponent(
            final GeocodeApiResponse apiResponse,
            final GeocodeApiResponse.Result.Type requestedType) {
        final GeocodeApiResponse.Result firstResult = firstResult(apiResponse);
        if(firstResult == null) {
            return null;
        }
        final List<GeocodeApiResponse.Result.AddressComponent> address_components =
                firstResult.getAddress_components();
        for (GeocodeApiResponse.Result.AddressComponent address_component : address_components) {
            final GeocodeApiResponse.Result.Type[] types = address_component.getTypes();
            for (GeocodeApiResponse.Result.Type type : types) {
                if(type == requestedType) {
                    return address_component;
                }
            }
        }
        return null;
    }

    public String getAddressComponents() {
        final GeocodeApiResponse.Result result = firstResult(apiResponse);
        final StringBuilder buf = new StringBuilder();
        for (GeocodeApiResponse.Result.AddressComponent addressComponent : result.getAddress_components()) {
            final GeocodeApiResponse.Result.Type[] types = addressComponent.getTypes();
            final GeocodeApiResponse.Result.Type type = coalesce(types);
            if(type != null) {
                final String long_name = addressComponent.getLong_name();
                buf.append(type.name()).append(": ").append(long_name).append("\n");
            }
        }
        return buf.toString();
    }

    private static <T> T coalesce(T[] elements) {
        if(elements == null) {
            return null;
        }
        for (T element : elements) {
            if(element != null) {
                return element;
            }
        }
        return null;
    }

    //endregion

}
