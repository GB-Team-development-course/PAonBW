package ru.gb.core.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
import ru.gb.core.enums.Currency;;

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
                BigDecimal.valueOf(500L)
        );

        RequestBuilder request = MockMvcRequestBuilders
                .post(URL_COMMON + "/createDebit")
                .header("clientId",1L)
                .content(objectMapper.writeValueAsString(accountRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult requestResult = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        AccountDto objectResponse = objectMapper.readValue(
                requestResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        Assertions.assertEquals(objectResponse.getAccountType(),AccountType.D);
        Assertions.assertEquals(objectResponse.getAccountNumber(),"D000000004");
        Assertions.assertEquals(objectResponse.getAccountStatus(),AccountStatus.ACTIVE);
        Assertions.assertEquals(objectResponse.getCurrency(), Currency.USD);
        Assertions.assertNotNull(objectResponse.getDtCreated());
        Assertions.assertNull(objectResponse.getDtBlocked());
        Assertions.assertNull(objectResponse.getDtClosed());

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
                BigDecimal.valueOf(500L)
        );

        RequestBuilder request = MockMvcRequestBuilders
                .post(URL_COMMON + "/createCredit")
                .header("clientId",2L)
                .content(objectMapper.writeValueAsString(accountRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult requestResult = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        AccountDto objectResponse = objectMapper.readValue(
                requestResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        Assertions.assertEquals(objectResponse.getAccountType(),AccountType.C);
        Assertions.assertEquals(objectResponse.getAccountNumber(),"C000000005");
        Assertions.assertEquals(objectResponse.getAccountStatus(),AccountStatus.ACTIVE);
        Assertions.assertEquals(objectResponse.getCurrency(), Currency.USD);
        Assertions.assertNotNull(objectResponse.getDtCreated());
        Assertions.assertNull(objectResponse.getDtBlocked());
        Assertions.assertNull(objectResponse.getDtClosed());

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
                .put(URL_COMMON + "/blockAccount/" + blockingAccount)
                .header("clientId", 1L);

        MvcResult requestResult = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        AccountDto objectResponse = objectMapper.readValue(
                requestResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        Assertions.assertEquals(objectResponse.getAccountType(),AccountType.C);
        Assertions.assertEquals(objectResponse.getAccountNumber(),"C000000001");
        Assertions.assertEquals(objectResponse.getAccountStatus(),AccountStatus.BLOCKED);
        Assertions.assertEquals(objectResponse.getCurrency(), Currency.USD);
        Assertions.assertNotNull(objectResponse.getDtCreated());
        Assertions.assertNotNull(objectResponse.getDtBlocked());
        Assertions.assertNull(objectResponse.getDtClosed());

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
                .put(URL_COMMON + "/closeAccount/" + closingAccount)
                .header("clientId", 3L);

        MvcResult requestResult = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        AccountDto objectResponse = objectMapper.readValue(
                requestResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        Assertions.assertEquals(objectResponse.getAccountType(),AccountType.D);
        Assertions.assertEquals(objectResponse.getAccountNumber(),"D000000003");
        Assertions.assertEquals(objectResponse.getAccountStatus(),AccountStatus.CLOSED);
        Assertions.assertEquals(objectResponse.getCurrency(), Currency.USD);
        Assertions.assertNotNull(objectResponse.getDtCreated());
        Assertions.assertNull(objectResponse.getDtBlocked());
        Assertions.assertNotNull(objectResponse.getDtClosed());

    }
}
