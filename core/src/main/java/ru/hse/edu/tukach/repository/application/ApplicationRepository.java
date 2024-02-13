package ru.hse.edu.tukach.repository.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.edu.tukach.dto.application.ApplicationLiteDto;
import ru.hse.edu.tukach.model.application.Application;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<ApplicationLiteDto> findAllByInitiatorTg(String initiatorTg);
}
