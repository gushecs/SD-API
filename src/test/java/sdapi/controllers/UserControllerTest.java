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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = SpringSecurityWebTestConfig.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //FIND ALL TESTS

    @Test
    void tryToFindAllWithNoCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sdapi/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void tryToFindAllWithCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sdapi/users"))
                .andExpect(status().isOk());
    }

    //FIND BY ID TESTS

    @Test
    void tryToFindByIdWithNoCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sdapi/users/2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void tryToFindByIdWithCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sdapi/users/2"))
                .andExpect(status().isOk());
    }

    //REGISTER TESTS

    @Test
    void tryToRegisterWithNoCredentials() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/sdapi/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("user@test.com")
    void tryToRegisterWithInvalidCredentials() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/sdapi/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void tryToRegisterWithInvalidUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/sdapi/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")).andExpect(status().isBadRequest());

    }

    @Test
    @WithUserDetails("admin@test.com")
    void tryToRegisterValidUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/sdapi/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"testing@register.com\",\"password\":\"testPass\",\"name\":\"Testonildo\"}"))
                .andExpect(status().isOk());

    }

    //DELETE TESTS

    @Test
    void tryToDeleteWithNoCredentials() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/sdapi/users/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("user@test.com")
    void tryToDeleteWithInvalidCredentials() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/sdapi/users/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void tryToDeleteWithValidCredentials() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/sdapi/users/1"))
                .andExpect(status().isOk());

    }

    //UPDATE TESTS

    @Test
    void tryToUpdateWithNoCredentials() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/sdapi/users/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("user@test.com")
    void tryToUpdateWithInvalidCredentials() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/sdapi/users/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void tryToUpdateWithInvalidRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/sdapi/users/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")).andExpect(status().isBadRequest());

    }

    @Test
    @WithUserDetails("admin@test.com")
    void tryToUpdateWithValidRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/sdapi/users/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"testing@update.com\",\"password\":\"testPass\",\"name\":\"Testonildo\"}"))
                .andExpect(status().isOk());

    }

    @Test
    @WithUserDetails("admin@test.com")
    void tryToChangeCRMOwner() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/sdapi/users/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"testing@changecrm.com\",\"password\":\"testPass\",\"name\":\"Testonildo\",\"crms\":[{\"crm\":\"123\",\"uf\":\"CE\"}]}"));

        mockMvc.perform(MockMvcRequestBuilders.get("/sdapi/users/4"))
                .andExpect(jsonPath("$.crms").isEmpty());

    }
}

