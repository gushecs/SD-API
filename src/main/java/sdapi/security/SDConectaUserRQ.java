package sdapi.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sdapi.entities.CRM;
import sdapi.entities.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SDConectaUserRQ {

    private final String email;
    private final String name;
    private final String surname;
    private final String mobile_phone;
    private final List<SDConectaCRMRQ> crms = new ArrayList<>();

    public SDConectaUserRQ(User user){
        this.email = user.getEmail();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.mobile_phone = user.getMobile_phone();
        for (CRM crm:user.getCrms()){
            this.crms.add(new SDConectaCRMRQ(crm));
        }
    }

}