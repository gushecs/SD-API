package sdapi.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sdapi.SpringSecurityWebTestConfig;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = SpringSecurityWebTestConfig.class)
@AutoConfigureMockMvc
class JWTAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void tryToLoginWithNoCredentials() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/sdapi/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")).andExpect(status().isUnauthorized());
    }

    @Test
    void tryToLoginWithInvalidCredentials() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/sdapi/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"invalid@login.com\",\"password\":\"password\"}"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void tryToLoginWithValidCredentialsAndUserNotRegisteredAtSDConecta() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/sdapi/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"maria@bethania.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sd_conecta_user.access_token").isEmpty());

    }

    @Test
    void tryToLoginWithValidCredentialsAndUserRegisteredAtSDConecta() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/sdapi/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"usuario.teste-1@email.com\",\"password\":\"hard_password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sd_conecta_user.access_token").isNotEmpty());

    }

}