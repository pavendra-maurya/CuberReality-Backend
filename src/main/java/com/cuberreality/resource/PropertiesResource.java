package com.cuberreality.resource;

import com.cuberreality.request.PropertiesSearchRequest;
import com.cuberreality.request.UpdateLeadRequest;
import com.cuberreality.response.BaseResponse;
import com.cuberreality.service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class PropertiesResource {

    @Autowired
    private PropertiesService propertiesService;


    @RequestMapping(value = "/search/properties", method = RequestMethod.POST)
    public ResponseEntity<?> getPropertiesInSpace(PropertiesSearchRequest propertiesSearchRequest) {
        PropertiesSearchRequest propertiesSearchRequest1 = new PropertiesSearchRequest();
        propertiesSearchRequest1.setCity("Bangalore");
        propertiesSearchRequest1.setCountry("no_data");
        propertiesSearchRequest1.setState("no_data");
        propertiesSearchRequest1.setSubArea("Sarjapur_Road");
        return new ResponseEntity<>(new BaseResponse<>(propertiesService.getPropertiesInSpace(propertiesSearchRequest1), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/property/{id}/{referred_by_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProperty(@PathVariable String id, @PathVariable String referred_by_id) {
        return new ResponseEntity<>(new BaseResponse<>(propertiesService.getProperty(id, referred_by_id), ""), HttpStatus.OK);
    }

}
