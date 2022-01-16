package com.cuberreality.resource;

import com.cuberreality.request.leads.ReferLeadModel;
import com.cuberreality.request.leads.UpdateLeadModel;
import com.cuberreality.request.leads.UpdateReferLeadModel;
import com.cuberreality.response.BaseResponse;
import com.cuberreality.service.LeadService;
import com.cuberreality.service.ReferLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ReferLeadResource {

    @Autowired
    private LeadService leadService;
    @Autowired
    private ReferLeadService referleadService;


    @RequestMapping(value = "/lead/refer", method = RequestMethod.POST)
    public ResponseEntity<?> referLead(@RequestBody ReferLeadModel referLeadModel) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(referleadService.referLead(referLeadModel), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/lead/refer/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getReferLead(@RequestBody @PathVariable String id) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(referleadService.getReferLead(id), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/lead/refer/reseller/{resellerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getReferLeadByReseller(@RequestBody @PathVariable String resellerId) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(referleadService.getReferLeadByResellerId(resellerId), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/lead/refer/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateReferLead( @PathVariable String id ,@RequestBody UpdateReferLeadModel updateReferLeadModel) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(referleadService.updateReferLead(updateReferLeadModel,id), ""), HttpStatus.OK);
    }


}
