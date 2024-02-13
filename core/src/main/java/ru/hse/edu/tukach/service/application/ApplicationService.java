package ru.hse.edu.tukach.service.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.edu.tukach.dto.application.ApplicationFromEmailCreationDto;
import ru.hse.edu.tukach.dto.application.ApplicationLiteDto;
import ru.hse.edu.tukach.exception.NotFoundException;
import ru.hse.edu.tukach.exception.constant.ExceptionCode;
import ru.hse.edu.tukach.mapper.application.ApplicationMapper;
import ru.hse.edu.tukach.model.application.Application;
import ru.hse.edu.tukach.model.application.ApplicationStatus;
import ru.hse.edu.tukach.repository.application.ApplicationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    /**
     * Сохранение новой заявки, заполненной на фронте.
     * При вызове с фронта сохраняется сразу вся заполненная заявка
     */
    @Transactional
    public Long save(ApplicationFromEmailCreationDto dto) {
        Application application = applicationMapper.toApplication(dto);
        application.setStatus(ApplicationStatus.NEW);

        return applicationRepository.save(application).getId();
    }

    /**
     * Метод получения заявки по её id. Данный метод используется, когда Telegram пользователь:
     * 1. в процессе заполнения заявки, и нужно получать текущее состояние заявки,
     *    чтобы бот предлагал заполнять только не заполненные поля
     * 2. запрашивает информацию по конкретной отправленной им заявке
     */
    @Transactional(readOnly = true)
    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id)
            .orElseThrow(
                () -> new NotFoundException(ExceptionCode.APPLICATION_NOT_FOUND, "Заявка %d не найдена".formatted(id))
            );
    }

    /**
     * Метод получения всех заявок по Telegram-никнейму пользователя.
     * Данный метод используется, когда Telegram пользователь хочет вывести список всех имеющихся у него заявок.
     * Информация по каждой заявке предоставляется кратко.
     */
    @Transactional(readOnly = true)
    public List<ApplicationLiteDto> getApplicationByInitiatorTg(String initiatorTg) {
        return applicationRepository.findAllByInitiatorTg(initiatorTg);
    }

    /**
     * Метод получения всех заявок по email'у пользователя + id заявки.
     * Данный метод используется, когда пользователь, отправивший заявку с фронта, хочет получить информацию о ней
     */
    @Transactional(readOnly = true)
    public Application getApplicationByInitiatorEmail(Long id, String initiatorEmail) {
        return applicationRepository.findById(id)
            .filter(it -> initiatorEmail.equals(it.getInitiatorEmail()))
            .orElseThrow(
                () -> new NotFoundException(
                    ExceptionCode.APPLICATION_NOT_FOUND,
                    "Заявка %d для пользователя %s не найдена".formatted(id, initiatorEmail)
                )
            );
    }
}
