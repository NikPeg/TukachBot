package ru.hse.edu.tukach;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@ActiveProfiles({"test"})
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = "classpath:sql/clean.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class BaseIntegrationTest {

}
