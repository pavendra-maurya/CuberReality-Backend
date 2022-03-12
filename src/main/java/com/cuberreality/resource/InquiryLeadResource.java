package com.cuberreality.resource;

import com.cuberreality.request.leads.InquiryLeadModel;
import com.cuberreality.request.leads.UpdateInquiryLeadModel;
import com.cuberreality.response.BaseResponse;
import com.cuberreality.service.InquiryLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class InquiryLeadResource {

    @Autowired
    private InquiryLeadService inquiryLeadService;


    @RequestMapping(value = "/lead/inquiry", method = RequestMethod.POST)
    public ResponseEntity<?> inquiryLead(@RequestBody InquiryLeadModel inquiryLeadModel) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(inquiryLeadService.inquiryLead(inquiryLeadModel), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/lead/inquiry/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getInquiryLead(@RequestBody @PathVariable String id) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(inquiryLeadService.getInquiryLead(id), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/lead/inquiry/reseller/{resellerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getInquiryLeadByReseller(@RequestBody @PathVariable String resellerId) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(inquiryLeadService.getInquiryLeadByResellerId(resellerId), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/lead/inquiry/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateInquiryLead( @PathVariable String id ,@RequestBody UpdateInquiryLeadModel updateInquiryLeadModel) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(inquiryLeadService.updateInquiryLead(updateInquiryLeadModel,id), ""), HttpStatus.OK);
    }


}
