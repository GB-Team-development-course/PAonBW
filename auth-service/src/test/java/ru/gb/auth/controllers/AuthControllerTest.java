package ru.gb.auth.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.auth.dto.JwtRequest;
import ru.gb.auth.dto.JwtResponse;
import ru.gb.auth.exceptions.AuthError;
import ru.gb.auth.services.UserService;
import ru.gb.auth.utils.JwtTokenUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Slf4j
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGenerateAuthToken() throws Exception {
        String username = "bob";
        String password = "100";

        String body = "{\"username\":\"" + username + "\", \"password\":\""+  password + "\"}";
        log.info(body);

        MockHttpServletRequestBuilder buider = MockMvcRequestBuilders.post("/auth")
                .content(body).contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(buider).andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.token").hasJsonPath());
    }

    @Test
    public void shouldGenerateError() throws Exception {
        String username = "bob";
        String password = "wrong";

        String body = "{\"username\":\"" + username + "\", \"password\":\""+  password + "\"}";
        log.info(body);

        MockHttpServletRequestBuilder buider = MockMvcRequestBuilders.post("/auth")
                .content(body).contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(buider).andExpect(status().isUnauthorized()).andDo(print())
                .andExpect(jsonPath("$.message").value("Incorrect username or password"));
    }
}
