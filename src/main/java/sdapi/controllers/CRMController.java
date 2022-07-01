package sdapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sdapi.entities.CRM;
import sdapi.entities.CRMRQ;
import sdapi.services.CRMService;

import javax.validation.Valid;

@Controller
@RequestMapping("/sdapi/crm")
public record CRMController(CRMService crmService) {

    @PostMapping
    public ResponseEntity<CRM> register(@Valid @RequestBody CRMRQ registerRQ) {return ResponseEntity.ok(crmService.register(registerRQ));}

}
