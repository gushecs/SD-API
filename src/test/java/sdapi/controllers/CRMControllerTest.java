package sdapi.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sdapi.SpringSecurityWebTestConfig;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = SpringSecurityWebTestConfig.class)
@AutoConfigureMockMvc
class CRMControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void tryToRegisterWithNoCredentials() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/sdapi/crm")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("user@test.com")
    void tryToRegisterWithInvalidCredentials() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/sdapi/crm")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")).andExpect(status().isForbidden());

    }

    @Test
    @WithUserDetails("admin@test.com")
    void tryToRegisterWithInvalidCRM() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/sdapi/crm")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")).andExpect(status().isBadRequest());

    }

    @Test
    @WithUserDetails("admin@test.com")
    void tryToRegisterValidCRM() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/sdapi/crm")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"crm\":\"8765976380323\",\"uf\":\"AC\",\"user_id\":1}")).andExpect(status().isOk());

    }

}
