package sdapi;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sdapi/crm")
public record CRMController(CRMService crmService) {

    @PostMapping
    public ResponseEntity<CRM> register(@RequestBody CRMRegisterRQ registerRQ) {return ResponseEntity.ok(crmService.register(registerRQ));}

}
