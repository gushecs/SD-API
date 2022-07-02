package sdapi.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SDConectaPartnerRS {

    private String access_token;
    private String scope;
    private String token_type;
    private Long expires_in;

}
