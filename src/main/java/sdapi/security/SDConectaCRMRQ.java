package sdapi.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sdapi.entities.CRM;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
public class SDConectaCRMRQ implements Serializable {

    private final String crm;
    private final String uf;
    private final String specialty;

    public SDConectaCRMRQ(CRM crm){
        this.crm=crm.getCrm();
        this.uf=crm.getUf().getDescription();
        this.specialty=crm.getSpecialty();
    }

}
