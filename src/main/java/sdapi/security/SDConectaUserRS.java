package sdapi.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SDConectaUserRS {

    private String access_token;
    private String refresh_token;
    private String authorization_status;
}