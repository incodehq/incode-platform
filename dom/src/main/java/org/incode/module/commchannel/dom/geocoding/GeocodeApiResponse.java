package org.incode.module.commchannel.dom.geocoding;

import java.util.List;

public class GeocodeApiResponse {

    public enum Status {
        OK, ZERO_RESULTS, OVER_QUERY_LIMIT, REQUEST_DENIED, INVALID_REQUEST;
    }

    public static class Result {

        public static enum Type {
            street_address,
            route,
            intersection,
            political,
            country,
            administrative_area_level_1,
            administrative_area_level_2,
            administrative_area_level_3,
            colloquial_area,
            locality,
            sublocality,
            neighborhood,
            premise,
            subpremise,
            postal_code,
            natural_feature,
            airport,
            park,
            point_of_interest,
            post_box,
            street_number,
            floor,
            room;
        }

        public static class AddressComponent {

            private String long_name;
            private String short_name;
            private Result.Type[] types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public Result.Type[] getTypes() {
                return types;
            }

            public void setTypes(Result.Type[] types) {
                this.types = types;
            }
        }

        private String formatted_address;
        private List<Result.AddressComponent> address_components;
        private Geometry geometry;
        private String place_id;
        private Result.Type[] types;

        public Result.Type[] getTypes() {
            return types;
        }

        public void setTypes(Result.Type[] types) {
            this.types = types;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public List<Result.AddressComponent> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<Result.AddressComponent> address_components) {
            this.address_components = address_components;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getPlace_id() {
            return place_id;
        }
    }

    public static class Geometry {
        public static enum LocationType {
            ROOFTOP, RANGE_INTERPOLATED, GEOMETRIC_CENTER, APPROXIMATE;
        }

        public static class ViewPort {
            private Location northeast;
            private Location southwest;

            public Location getNortheast() {
                return northeast;
            }

            public void setNortheast(Location northeast) {
                this.northeast = northeast;
            }

            public Location getSouthwest() {
                return southwest;
            }

            public void setSouthwest(Location southwest) {
                this.southwest = southwest;
            }
        }

        private Location location;
        private Geometry.LocationType location_type;
        private Geometry.ViewPort viewport;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Geometry.LocationType getLocation_type() {
            return location_type;
        }

        public void setLocation_type(Geometry.LocationType location_type) {
            this.location_type = location_type;
        }

        public Geometry.ViewPort getViewport() {
            return viewport;
        }

        public void setViewport(Geometry.ViewPort viewport) {
            this.viewport = viewport;
        }

    }

    public static class Location {
        private double lat;
        private double lng;

        public Location() {
        }

        public Location(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    private Status status;
    private List<Result> results;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
