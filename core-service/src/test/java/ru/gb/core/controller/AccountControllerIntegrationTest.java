package ru.gb.core.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.core.dto.*;
import ru.gb.core.enums.AccountStatus;
import ru.gb.core.enums.AccountType;
import ru.gb.core.enums.Currency;
import ru.gb.core.response.Response;;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("integration-test")
@DisplayName("AccountController")
public class AccountControllerIntegrationTest {

    private final static String URL_COMMON = "/api/v1/account";
    private final static String INIT_SQL_PATH = "init/account-controller/populate_db.sql";
    private final static String CLEAN_UP_SQL_PATH = "init/account-controller/cleanUp.sql";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Инициализация БД требуемыми данными для прохождения теста
     */
    @BeforeAll
    public static void initDb(@Autowired final DataSource dataSource) {
        try (final Connection connection = dataSource.getConnection()) {
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

    /**
     * Тест: создание дебетового счета
     *
     * @throws Exception
     */
    @Test
    void createDebitAccount() throws Exception {
        CreateAccountRequest accountRequest = new CreateAccountRequest(
                1,
                BigDecimal.valueOf(500L),
                1
        );

        RequestBuilder request = MockMvcRequestBuilders
                .post(URL_COMMON + "/debit")
                .header("username", "John")
                .content(objectMapper.writeValueAsString(accountRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult requestResult = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Response<AccountDto> objectResponse = objectMapper.readValue(
                requestResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        Assertions.assertEquals(objectResponse.getCode(), 200);
        Assertions.assertTrue(objectResponse.isSuccess());
        Assertions.assertEquals(objectResponse.getData().getAccountType(), AccountType.D);
        Assertions.assertEquals(objectResponse.getData().getAccountNumber(), "D000000004");
        Assertions.assertEquals(objectResponse.getData().getAccountStatus(), AccountStatus.ACTIVE);
        Assertions.assertEquals(objectResponse.getData().getCurrency(), Currency.USD);
        Assertions.assertNotNull(objectResponse.getData().getDtCreated());
        Assertions.assertNull(objectResponse.getData().getDtBlocked());
        Assertions.assertNull(objectResponse.getData().getDtClosed());
    }

    /**
     * Тест: создание кредитного счета
     *
     * @throws Exception
     */
    @Test
    void createCreditAccount() throws Exception {
        CreateAccountRequest accountRequest = new CreateAccountRequest(
                1,
                BigDecimal.valueOf(500L),
                1
        );

        RequestBuilder request = MockMvcRequestBuilders
                .post(URL_COMMON + "/credit")
                .header("username", "Jack")
                .content(objectMapper.writeValueAsString(accountRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult requestResult = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Response<AccountDto> objectResponse = objectMapper.readValue(
                requestResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        Assertions.assertEquals(objectResponse.getCode(), 200);
        Assertions.assertTrue(objectResponse.isSuccess());
        Assertions.assertEquals(objectResponse.getData().getAccountType(), AccountType.C);
        Assertions.assertEquals(objectResponse.getData().getAccountNumber(), "C000000005");
        Assertions.assertEquals(objectResponse.getData().getAccountStatus(), AccountStatus.ACTIVE);
        Assertions.assertEquals(objectResponse.getData().getCurrency(), Currency.USD);
        Assertions.assertNotNull(objectResponse.getData().getDtCreated());
        Assertions.assertNull(objectResponse.getData().getDtBlocked());
        Assertions.assertNull(objectResponse.getData().getDtClosed());

    }

    /**
     * Тест: блокировка счета
     *
     * @throws Exception
     */
    @Test
    void blockAccount() throws Exception {

        String blockingAccount = "C000000001";

        RequestBuilder request = MockMvcRequestBuilders
                .put(URL_COMMON + "/block/" + blockingAccount)
                .header("username", "John");

        MvcResult requestResult = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Response<AccountDto> objectResponse = objectMapper.readValue(
                requestResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        Assertions.assertEquals(objectResponse.getCode(), 200);
        Assertions.assertTrue(objectResponse.isSuccess());
        Assertions.assertEquals(objectResponse.getData().getAccountType(), AccountType.C);
        Assertions.assertEquals(objectResponse.getData().getAccountNumber(), "C000000001");
        Assertions.assertEquals(objectResponse.getData().getAccountStatus(), AccountStatus.BLOCKED);
        Assertions.assertEquals(objectResponse.getData().getCurrency(), Currency.USD);
        Assertions.assertNotNull(objectResponse.getData().getDtCreated());
        Assertions.assertNotNull(objectResponse.getData().getDtBlocked());
        Assertions.assertNull(objectResponse.getData().getDtClosed());

    }

    /**
     * Тест: закрытие счета
     *
     * @throws Exception
     */
    @Test
    void closeAccount() throws Exception {

        String closingAccount = "D000000003";

        RequestBuilder request = MockMvcRequestBuilders
                .put(URL_COMMON + "/close/" + closingAccount)
                .header("username", "Bob");

        MvcResult requestResult = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Response<AccountDto> objectResponse = objectMapper.readValue(
                requestResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        Assertions.assertEquals(objectResponse.getCode(), 200);
        Assertions.assertTrue(objectResponse.isSuccess());
        Assertions.assertEquals(objectResponse.getData().getAccountType(), AccountType.D);
        Assertions.assertEquals(objectResponse.getData().getAccountNumber(), "D000000003");
        Assertions.assertEquals(objectResponse.getData().getAccountStatus(), AccountStatus.CLOSED);
        Assertions.assertEquals(objectResponse.getData().getCurrency(), Currency.USD);
        Assertions.assertNotNull(objectResponse.getData().getDtCreated());
        Assertions.assertNull(objectResponse.getData().getDtBlocked());
        Assertions.assertNotNull(objectResponse.getData().getDtClosed());
    }
}
