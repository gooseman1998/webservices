package nl.hu.v1wac.firstapp.webservices;

import nl.hu.v1wac.firstapp.domain.Country;
import nl.hu.v1wac.firstapp.domain.WorldService;

import javax.annotation.security.RolesAllowed;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Path("countries")
public class WorldResource {
    WorldService ws = new WorldService();
    @GET
    public String getAllCountries() {
        String output ="";
        List<Country> countries = new ArrayList<>();
        countries = ws.getAllCountries();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Country country : countries) {
            JsonObjectBuilder countriesJson = Json.createObjectBuilder();
            countriesJson.add("Code",country.getCode());
            countriesJson.add("name", country.getName());
            countriesJson.add("Name",country.getName());
            countriesJson.add("Capital",country.getCapital());
            countriesJson.add("Population",country.getPopulation());
            countriesJson.add("Surface",country.getSurface());
            countriesJson.add("Continent",country.getContinent());
            countriesJson.add("Government",country.getGovernment());
            countriesJson.add("Iso3",country.getIso3());
            countriesJson.add("Latitude",country.getLatitude());
            countriesJson.add("Longtitude",country.getLongitude());
            countriesJson.add("Region",country.getRegion());

            jab.add(countriesJson);
        }
        output = jab.build().toString();
       return output;
    }
    @GET
    @Path("{code}")
    public String getCountryByCode(@PathParam("code") String code) {
        String output = "";
        Country country = ws.getCountryByCode(code);
        if (code == null) {
            throw new WebApplicationException("No such order!");
        }

        JsonObjectBuilder countryJson= Json.createObjectBuilder();
        countryJson.add("Name",country.getName());
        countryJson.add( "Code", country.getCode());
        countryJson.add( "Capital", country.getCapital());
        countryJson.add( "Continent", country.getContinent());
        countryJson.add( "Government", country.getGovernment());
        countryJson.add( "Region", country.getRegion());
        countryJson.add( "Population", country.getPopulation());
        countryJson.add( "Longitude", country.getLongitude());

        output = countryJson.build().toString();

        return output;
    }

    @GET
    @Path("largestsurfaces")
    public String getLargestCountries() {
        String output ="";
        List<Country> countries = new ArrayList<>();
        countries = ws.get10LargestSurfaces();
        JsonObjectBuilder countriesJson = Json.createObjectBuilder();
        for (Country country : countries) {
            output += country.getName() + "<br>";

        }
        return output;
    }

    @GET
    @Path("largestpopulations")
    public String getLargestPopulations() {
        String output ="";
        List<Country> countries = new ArrayList<>();
        countries = ws.get10LargestPopulations() ;
        for (Country country : countries) {
            output += country.getName() + "<br>";
        }
        return output;
    }

    @DELETE
//    @RolesAllowed("user")
    @Path("{id}")
    public boolean deleteCountry(@PathParam("id") String id){
        id = id.toUpperCase();
        Country c = ws.getCountryByCode(id);
        return ws.deleteCountry(c);
    }


    @PUT
//    @RolesAllowed("user")
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public boolean updateCountry(@PathParam("id") String id,
                                 @FormParam("country") String country,
                                 @FormParam("capital") String captital,
                                 @FormParam("population") int population,
                                 @FormParam("region") String region,
                                 @FormParam("surface") double surface){
        System.out.println(captital+country+region+population+surface);
        id = id.toUpperCase();
        Country c = ws.getCountryByCode(id);
        c.setName(country);
        c.setCapital(captital);
        c.setPopulation(population);
        c.setRegion(region);
        c.setSurface(surface);
        return ws.updateCountry(c);
    }

    @POST
//    @RolesAllowed("user")
    @Path("save")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public boolean saveCountry(@FormParam("code") String id,
                               @FormParam("country") String country,
                               @FormParam("capital") String captital,
                               @FormParam("population") int population,
                               @FormParam("region") String region,
                               @FormParam("surface") double surface) {
        try {
            System.out.println(captital + country + region + population + surface);
            id = id.toUpperCase();
            Country c = new Country();
            c.setCode(id);
            c.setName(country);
            c.setCapital(captital);
            c.setPopulation(population);
            c.setRegion(region);
            c.setSurface(surface);
            return ws.saveCountry(c);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
