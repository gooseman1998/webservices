package nl.hu.v1wac.firstapp.persistence;

import nl.hu.v1wac.firstapp.domain.Country;
import java.util.List;

public interface CountryDao {
    boolean save(Country country);
    List<Country> findAll();
    Country findByCode(String countryCode);
    List<Country> find10LargestPopulations();
    List<Country> find10LargestSurfaces();
    boolean update(Country country);
    boolean delete(Country country);
}
