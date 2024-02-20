package ru.hse.edu.tukach.service.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.edu.tukach.dto.application.ApplicationFromEmailCreationDto;
import ru.hse.edu.tukach.dto.application.ApplicationFromTelegramCreationDto;
import ru.hse.edu.tukach.dto.application.ApplicationLiteDto;
import ru.hse.edu.tukach.dto.application.ApplicationSource;
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
     * Сохранение новой заявки, заполненной из Telegram-бота.
     * При вызове с фронта сохраняется сразу вся заполненная заявка
     */
    @Transactional
    public Long save(ApplicationFromTelegramCreationDto dto) {
        Application application = applicationMapper.toApplication(dto);
        application.setStatus(ApplicationStatus.NEW);

        return applicationRepository.save(application).getId();
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
     * Метод получения всех заявок по email'у/Telegram'у пользователя + id заявки.
     * Данный метод используется, когда пользователь, отправивший заявку, хочет получить информацию о ней
     */
    @Transactional(readOnly = true)
    public Application getApplicationByIdAndInitiator(Long id, String initiator, ApplicationSource source) {
        return applicationRepository.findById(id)
            .filter(
                it -> source == ApplicationSource.EMAIL && initiator.equals(it.getInitiatorEmail())
                || source == ApplicationSource.TELEGRAM && initiator.equals(it.getInitiatorTg())
            )
            .orElseThrow(
                () -> new NotFoundException(
                    ExceptionCode.APPLICATION_NOT_FOUND,
                    "Заявка %d для пользователя %s не найдена".formatted(id, initiator)
                )
            );
    }
}
