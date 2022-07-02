package sdapi.entities;

import lombok.Getter;
import java.util.List;

@Getter
public class UserRS {
    private final String email;
    private final String name;
    private final String surname;
    private final String mobile_phone;
    private final String authorization_status;
    private final List<CRM> crms;

    public UserRS(User user){
        this.email=user.getEmail();
        this.name=user.getName();
        this.surname=user.getSurname();
        this.mobile_phone=user.getMobile_phone();
        this.authorization_status=user.getAuthorization_status();
        this.crms=user.getCrms();
    }
}
