package sdapi.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JWTResponse{

    private final String sdapi_token;
    private final String partner_authorization_status;
    private final String partner_token;
    private final SDConectaUserRS sd_conecta_user;

}
