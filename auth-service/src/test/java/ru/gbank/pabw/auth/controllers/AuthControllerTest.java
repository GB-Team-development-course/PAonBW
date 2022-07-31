package ru.gbank.pabw.auth.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@ActiveProfiles("integration-test")
public class AuthControllerTest {

	private final static String INIT_SQL_PATH = "init/auth-controller/populate_db.sql";
	private final static String CLEAN_UP_SQL_PATH = "init/auth-controller/cleanUp.sql";

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Инициализация БД требуемыми данными для прохождения теста
	 */
	@BeforeAll
	public static void initDb(@Autowired final DataSource dataSource) {
		try (final Connection connection = dataSource.getConnection()) {
			ScriptUtils.executeSqlScript(connection, new ClassPathResource(CLEAN_UP_SQL_PATH));
			ScriptUtils.executeSqlScript(connection, new ClassPathResource(INIT_SQL_PATH));
		} catch (final SQLException exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
		}
	}

	/**
	 * Очистка БД после прохождения тестов
	 */
	@AfterAll
	public static void cleanUp(@Autowired final DataSource dataSource) {
		try (final Connection connection = dataSource.getConnection()) {
			ScriptUtils.executeSqlScript(connection, new ClassPathResource(CLEAN_UP_SQL_PATH));
		} catch (final SQLException exception) {
			log.error(ExceptionUtils.getStackTrace(exception));
		}
	}

	@Test
	public void shouldGenerateAuthToken() throws Exception {
		String username = "bob";
		String password = "100";

		String body = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";
		log.info(body);

		MockHttpServletRequestBuilder buider = MockMvcRequestBuilders.post("/auth").content(body).contentType(MediaType.APPLICATION_JSON_VALUE);

		mockMvc.perform(buider).andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.token").hasJsonPath());
	}

	@Test
	public void shouldGenerateError() throws Exception {
		String username = "bob";
		String password = "wrong";

		String body = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";

		MockHttpServletRequestBuilder buider = MockMvcRequestBuilders.post("/auth").content(body).contentType(MediaType.APPLICATION_JSON_VALUE);

		mockMvc.perform(buider).andExpect(status().isUnauthorized()).andDo(print()).andExpect(jsonPath("$.message").value("Incorrect username or password"));
	}
}
