package ru.pokrov.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.pokrov.auth.controllers.UserController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUnauthorised() throws Exception {
        mockMvc.perform(get("/api/user"))
            .andDo(print())
            .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void testInvalidSignUp() throws Exception {
        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"abc\", \"password\":\"123\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("must be a well-formed email address"))
        ;
        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"abc123@abc.com\", \"password\":\"\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("size must be between 1 and 64"))
        ;
    }

//    @Test
//    public void testValidSignUp() throws Exception {
//        mockMvc.perform(post("/api/signup")
//                .contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"zxcv@abc.com\", \"password\":\"123\"}"))
//                .andDo(print())
//                .andExpect(status().isOk())
//        ;
//    }

    @Test
    public void testAlreadyExistsSignUp() throws Exception {
        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"zxcv@abc.com\", \"password\":\"123\"}"))
                .andDo(print())
                .andExpect(status().isGone())
        ;
    }

    @Test
    public void testValidLogin() throws Exception {
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"zxcv@abc.com\", \"password\":\"123\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
        ;
    }

    @Test
    public void testInvalidLogin() throws Exception {
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"zxcv@abc.com\", \"password\":\"abc\"}"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"zxcv@abc.co\", \"password\":\"123\"}"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"zxcv@abc.co\", \"password\":\"abc\"}"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }
}
