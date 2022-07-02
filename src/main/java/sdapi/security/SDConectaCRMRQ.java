package sdapi.security;

import lombok.Getter;
import sdapi.entities.CRM;

@Getter
public class SDConectaCRMRQ{

    private final String crm;
    private final String uf;
    private final String specialty;

    public SDConectaCRMRQ(CRM crm){
        this.crm=crm.getCrm();
        this.uf=crm.getUf().getDescription();
        this.specialty=crm.getSpecialty();
    }

}
