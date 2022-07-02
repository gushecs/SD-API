package sdapi.services;

import org.springframework.stereotype.Service;
import sdapi.controllers.exceptions.ObjectNotFoundException;
import sdapi.entities.CRM;
import sdapi.entities.CRMRQ;
import sdapi.entities.UF;
import sdapi.repositories.CRMRepository;
import sdapi.repositories.UserRepository;

@Service
public record CRMService(CRMRepository crmRepository, UserRepository userRepository) {

    public CRM register(CRMRQ registerRQ) {
        CRM crm = new CRM();
        crm.setCrm(registerRQ.getCrm());
        crm.setSpecialty(registerRQ.getSpecialty());
        crm.setUf(UF.toEnum(registerRQ.getUf()));
        crm.setUser(userRepository.findById(registerRQ.getUser_id())
                .orElseThrow(() -> new ObjectNotFoundException("Não há usuários para o ID especificado!")));
        return crmRepository.save(crm);}

}
