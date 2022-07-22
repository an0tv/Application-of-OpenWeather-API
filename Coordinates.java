public class Coordinates {
        private String lon, lat, state, country, name;
        Coordinates(String a, String b) {
            this.lon = a.replaceAll("\\s", "");
            this.lat = b.replaceAll("\\s", "");
        }
        Coordinates(String lat , String lon, String country, String state, String name){
            this.lat = lat.replaceAll("\\s", "");
            this.lon = lon.replaceAll("\\s", "");
            this.state = state.replaceAll("\\s", "");
            this.country = country.replaceAll("\\s", "");
            this.name = name.replaceAll("\\s", "");
        }
        public String toString() {
            String temp = country + ", " + state + ", " + name + ", Coordinates:, " + lat + ", " + lon;
            return temp;
        }
        public String toFile(){
            String temp = country + ", " + state + ", " + name + ", " + lat + ", " + lon;
            return temp;
        }
        public String getLon(){
            return lon;
        }
        public String getLat(){
            return lat;
        }
        public String getCity(){
            return state;
        }
        public String getCountry(){
            return country;
        }
        public String getName(){return name;}
}
