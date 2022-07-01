package sdapi.services;

import org.springframework.stereotype.Service;
import sdapi.entities.CRM;
import sdapi.entities.CRMRQ;
import sdapi.entities.UF;
import sdapi.repositories.CRMRepository;

import java.util.Optional;

@Service
public record CRMService(CRMRepository crmRepository) {

    public CRM register(CRMRQ registerRQ) {
        CRM crm = new CRM();
        crm.setCrm(registerRQ.getCrm());
        crm.setSpecialty(registerRQ.getSpecialty());
        crm.setUf(UF.toEnum(registerRQ.getUf()));
        return crmRepository.save(crm);}

    public void register(CRM crm) {crmRepository.save(crm);}

    public Optional<CRM> findByCrmAndUf(String crm, String uf) {return crmRepository.findByCrmAndUf(crm, uf);}

}
