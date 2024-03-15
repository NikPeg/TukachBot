package ru.hse.edu.tukach.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hse.edu.tukach.BaseIntegrationTest;
import ru.hse.edu.tukach.dto.application.ApplicationFromEmailCreationDto;
import ru.hse.edu.tukach.dto.application.ApplicationFromTelegramCreationDto;
import ru.hse.edu.tukach.dto.application.ApplicationLiteDto;
import ru.hse.edu.tukach.dto.application.BaseApplicationCreationDto;
import ru.hse.edu.tukach.model.application.Application;
import ru.hse.edu.tukach.model.application.ApplicationStatus;
import ru.hse.edu.tukach.model.application.ApplicationType;
import ru.hse.edu.tukach.repository.application.ApplicationRepository;
import ru.hse.edu.tukach.service.application.ApplicationService;

import java.util.List;

public class ApplicationServiceTest extends BaseIntegrationTest {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ApplicationService applicationService;

    @Test
    @DisplayName("Сохранение заявки, созданной с фронта (с email-ом)")
    public void save_shouldSaveApplication_whenApplicationCreatedWithEmail() {
        ApplicationFromEmailCreationDto dto = new ApplicationFromEmailCreationDto();
        dto.setInitiatorEmail("email");
        dto.setDescription("description");
        dto.setTopic("topic");
        dto.setType(ApplicationType.VIOLATION);
        dto.setInitiatorFio("Ryan Gosling");

        applicationService.save(dto);

        List<Application> applications = applicationRepository.findAll();
        assertEquals(1, applications.size());
        assertNewApplicationBaseInfo(dto, applications.get(0));
        assertEquals(dto.getInitiatorEmail(), applications.get(0).getInitiatorEmail());
        assertNull(applications.get(0).getInitiatorTg());
    }

    @Test
    @DisplayName("Сохранение заявки, созданной через Telegram-бота")
    public void save_shouldSaveApplication_whenApplicationCreatedViaTelegram() {
        ApplicationFromTelegramCreationDto dto = new ApplicationFromTelegramCreationDto();
        dto.setInitiatorTg("12345");
        dto.setDescription("description");
        dto.setTopic("topic");
        dto.setType(ApplicationType.VIOLATION);
        dto.setInitiatorFio("Ryan Gosling");

        applicationService.save(dto);

        List<Application> applications = applicationRepository.findAll();
        assertEquals(1, applications.size());
        assertNewApplicationBaseInfo(dto, applications.get(0));
        assertEquals(dto.getInitiatorTg(), applications.get(0).getInitiatorTg());
        assertNull(applications.get(0).getInitiatorEmail());
    }

    @Test
    @DisplayName("Получение основной информации по всем заявкам Telegram-пользователя")
    public void getAllApplicationsByInitiatorTg_shouldReturnAllApplicationsForTgUser() {
        String initiatorTg = "initiatorTg";
        Application application1 = createApplication(initiatorTg);
        Application application2 = createApplication(initiatorTg);
        createApplication("notInitiatorTg");

        List<ApplicationLiteDto> dtos = applicationService.getAllApplicationsByInitiatorTg(initiatorTg);

        assertEquals(2, dtos.size());
        assertApplicationLiteDto(dtos.get(0), application1);
        assertApplicationLiteDto(dtos.get(1), application2);
    }

    @Test
    @DisplayName("Получение всей информации по конкретной заявке Telegram-пользователя")
    public void getApplicationByIdAndInitiator_shouldReturnAllApplicationsForTgUser() {
        String initiatorTg = "initiatorTg";
        Application application1 = createApplication(initiatorTg);
        Application application2 = createApplication(initiatorTg);
        createApplication("notInitiatorTg");

        List<ApplicationLiteDto> dtos = applicationService.getAllApplicationsByInitiatorTg(initiatorTg);

        assertEquals(2, dtos.size());
        assertApplicationLiteDto(dtos.get(0), application1);
        assertApplicationLiteDto(dtos.get(1), application2);
    }

    private void assertNewApplicationBaseInfo(BaseApplicationCreationDto dto, Application application) {
        assertNotNull(application.getId());
        assertEquals(ApplicationStatus.NEW, application.getStatus());
        assertEquals(dto.getType(), application.getType());
        assertEquals(dto.getTopic(), application.getTopic());
        assertEquals(dto.getDescription(), application.getDescription());
        assertEquals(dto.getInitiatorFio(), application.getInitiatorFio());
        assertNull(application.getReviewerLogin());
        assertNull(application.getReviewerResponse());
        assertNotNull(application.getCreatedDateTime());
        assertNotNull(application.getLastModifiedDateTime());
    }

    private void assertApplicationLiteDto(ApplicationLiteDto dto, Application application) {
        assertEquals(dto.getId(), application.getId());
        assertEquals(dto.getStatus(), application.getStatus());
        assertEquals(dto.getType(), application.getType());
        assertEquals(dto.getTopic(), application.getTopic());
        assertEquals(dto.getCreatedDateTime(), application.getCreatedDateTime());
    }

    private Application createApplication(String initiatorTg) {
        return applicationRepository.save(
            new Application()
                .setType(ApplicationType.VIOLATION)
                .setDescription("description")
                .setTopic("topic")
                .setType(ApplicationType.VIOLATION)
                .setInitiatorFio("Ryan Gosling")
                .setInitiatorTg(initiatorTg)
                .setStatus(ApplicationStatus.NEW)
        );
    }
}
