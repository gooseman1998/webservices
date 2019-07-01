package nl.hu.v1wac.firstapp.domain;


import nl.hu.v1wac.firstapp.persistence.CountryDaoImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WorldService {
    CountryDaoImpl cdi = new CountryDaoImpl();
    public List<Country> getAllCountries() {
        return cdi.findAll();
    }

    public List<Country> get10LargestPopulations() {
        return cdi.find10LargestPopulations();
    }

    public List<Country> get10LargestSurfaces() {
        return cdi.find10LargestSurfaces();
    }

    public Country getCountryByCode(String code) {
        return cdi.findByCode(code);
    }

    public boolean deleteCountry(Country c){
        try{
            cdi.delete(c);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean saveCountry(Country c){
        try{
            cdi.save(c);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateCountry(Country c){
        try{
            cdi.update(c);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
