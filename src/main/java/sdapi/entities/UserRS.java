package sdapi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class UserRS {
    private final String email;
    private final String name;
    private final String surname;
    private final String mobile_phone;
    private final String authorization_status;
    private final List<CRM> crms = new ArrayList<>();
}
