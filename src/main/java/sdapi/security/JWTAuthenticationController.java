package sdapi.security;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sdapi.entities.User;
import sdapi.repositories.UserRepository;

@RestController
@CrossOrigin
public record JWTAuthenticationController(JWTUtil jwtTokenUtil, UserRepository userRepository) {

    @PostMapping(value = "/sdapi/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestHeader(value = "Authorization", required = false) String authorization, @RequestBody JWTRequest authenticationRequest) throws Exception {

        User userToAuth = userRepository.findByEmailAndPassword(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        if (userToAuth == null)
            throw new BadCredentialsException("Invalid Credentials!");

        final String token = jwtTokenUtil.generateToken(userToAuth.getEmail());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        SDConectaUserRS sd_conecta_user;

        try {

            sd_conecta_user = userAuth(new SDConectaUserRQ(userToAuth), restTemplate, authorization);

        } catch (Exception e) {
            MultiValueMap<String, String> sdConectaPartnerRQ = new LinkedMultiValueMap<String, String>();
            sdConectaPartnerRQ.add("client_id", System.getenv("SDAPI_CLIENT_ID"));
            sdConectaPartnerRQ.add("client_secret", System.getenv("SDAPI_CLIENT_SECRET"));
            sdConectaPartnerRQ.add("grant_type", "client_credentials");

            HttpHeaders headersPartner = new HttpHeaders();
            if (authorization != null) {
                headersPartner.set("Authorization", authorization);
            }
            headersPartner.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> requestPartner = new HttpEntity<>(sdConectaPartnerRQ, headersPartner);

            SDConectaPartnerRS sdConectaPartnerRS = restTemplate.postForObject(System.getenv("SDAPI_URL") + "oauth2/token", requestPartner, SDConectaPartnerRS.class);
            authorization = sdConectaPartnerRS.getAccess_token();

            sd_conecta_user = userAuth(new SDConectaUserRQ(userToAuth), restTemplate, authorization);

        }

        userToAuth.setAuthorization_status(sd_conecta_user.getAuthorization_status());
        userRepository.save(userToAuth);

        return ResponseEntity.ok(new JWTResponse(token, sd_conecta_user));
    }

    private SDConectaUserRS userAuth(SDConectaUserRQ userRQ, RestTemplate restTemplate, String authorization){
        HttpHeaders headersUser = new HttpHeaders();
        headersUser.set("Authorization", "Bearer "+authorization);
        headersUser.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SDConectaUserRQ> requestUser = new HttpEntity<>(userRQ, headersUser);

        return restTemplate.postForObject(System.getenv("SDAPI_URL")+"api/v2/partners/generate-user-token",requestUser,SDConectaUserRS.class);
    }

}
