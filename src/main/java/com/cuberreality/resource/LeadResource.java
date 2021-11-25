package com.cuberreality.resource;

import com.cuberreality.request.leads.CreateLeadRequest;
import com.cuberreality.request.leads.UpdateLeadRequest;
import com.cuberreality.response.BaseResponse;
import com.cuberreality.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class LeadResource {

    @Autowired
    private LeadService leadService;

    @RequestMapping(value = "/lead", method = RequestMethod.POST)
    public ResponseEntity<?> createLead(@RequestBody CreateLeadRequest createLeadRequest) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(leadService.createLead(createLeadRequest), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/lead/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLead(@PathVariable String id) {
        return new ResponseEntity<>(new BaseResponse<>(leadService.getLead(id), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/leads", method = RequestMethod.GET)
    public ResponseEntity<?> getLeads() {
        return new ResponseEntity<>(new BaseResponse<>(leadService.getLeads(), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/lead", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLead(UpdateLeadRequest updateLeadRequest) {
        return new ResponseEntity<>(new BaseResponse<>(leadService.updateLead(updateLeadRequest), ""), HttpStatus.OK);
    }


}
