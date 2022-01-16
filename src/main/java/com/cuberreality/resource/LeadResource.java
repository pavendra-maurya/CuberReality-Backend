package com.cuberreality.resource;

import com.cuberreality.request.leads.*;
import com.cuberreality.response.BaseResponse;
import com.cuberreality.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class LeadResource  {

    @Autowired
    private LeadService leadService;

    @RequestMapping(value = "/lead", method = RequestMethod.POST)
    public ResponseEntity<?> createLead(@RequestBody CreateLeadModel createLeadRequest) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(leadService.createLead(createLeadRequest), ""), HttpStatus.OK);
    }

//    @RequestMapping(value = "/lead/refer", method = RequestMethod.POST)
//    public ResponseEntity<?> referLead(@RequestBody ReferLeadModel referLeadModel) throws Exception {
//        return new ResponseEntity<>(new BaseResponse<>(leadService.referLead(referLeadModel), ""), HttpStatus.OK);
//    }

//    @RequestMapping(value = "/lead/refer/{id}", method = RequestMethod.GET)
//    public ResponseEntity<?> getReferLead(@RequestBody @PathVariable String id) throws Exception {
//        return new ResponseEntity<>(new BaseResponse<>(leadService.getReferLead(id), ""), HttpStatus.OK);
//    }

    @RequestMapping(value = "/lead/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLead(@PathVariable String id) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(leadService.getLead(id), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/leads", method = RequestMethod.GET)
    public ResponseEntity<?> getLeads() throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(leadService.getLeads(), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/leads/reseller/{resellerId}/count", method = RequestMethod.GET)
    public ResponseEntity<?> getLeadsCountByReseller(@PathVariable String resellerId) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(leadService.findLeadsCountByReseller(resellerId), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/leads/reseller/{resellerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getLeadsByReseller(@PathVariable String resellerId) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(leadService.findLeadsByReseller(resellerId), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/lead/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLead( @PathVariable String id ,@RequestBody UpdateLeadModel updateLeadRequest) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(leadService.updateLead(updateLeadRequest,id), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/lead/search/", method = RequestMethod.PUT)
    public ResponseEntity<?> searchLead( @RequestBody SearchLeadRequest searchLeadRequest) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(leadService.searchLeads(searchLeadRequest), ""), HttpStatus.OK);
    }


}
