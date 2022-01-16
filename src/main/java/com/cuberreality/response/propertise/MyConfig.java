package com.cuberreality.response.propertise;

import com.cuberreality.entity.propertisesearch.*;
import com.cuberreality.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MyConfig {

   @Autowired
   private SearchRepository searchRepository;

   @Bean
   public Map<String, PropertyAreaDetails> myVal(){
//      Map<String, String> map = new HashMap<String, String>();
//      map.put("Sample", "Value");


      List<SearchSchema> searchSchemas = searchRepository.findAll();

      Map<String , PropertyAreaDetails> searchDetailsMap = new HashMap<>();

      for(Country country :searchSchemas.get(0).getCountry()){
         PropertyAreaDetails propertyAreaDetails = new PropertyAreaDetails();
         propertyAreaDetails.setCountry(country.getCountryName());
         for(State state :country.getState()){
            propertyAreaDetails.setState(state.getStateName());

            for(City city :state.getCity()){

               String cityName=city.getCityName();
               List subAreaList= new ArrayList();

               for(SubArea subArea:city.getSubArea()){
                  subAreaList.add(subArea.getSubAreaName());

               }
               propertyAreaDetails.setSubAreList(subAreaList);
               searchDetailsMap.put(cityName,propertyAreaDetails);


            }

         }



      }
      return searchDetailsMap;
   }


}
