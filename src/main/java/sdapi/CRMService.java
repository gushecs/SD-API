package sdapi;

import org.springframework.stereotype.Service;

@Service
public record CRMService(CRMRepository crmRepository) {

    public CRM register(CRMRegisterRQ registerRQ) {
        CRM crm = new CRM();
        crm.setCrm(registerRQ.getCrm());
        crm.setSpecialty(registerRQ.getSpecialty());
        crm.setUf(UF.toEnum(registerRQ.getUf()));
        return crmRepository.save(crm);}

}
