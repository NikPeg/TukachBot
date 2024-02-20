package ru.hse.edu.tukach.mapper.application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hse.edu.tukach.config.MappersConfig;
import ru.hse.edu.tukach.dto.application.ApplicationFromEmailCreationDto;
import ru.hse.edu.tukach.dto.application.ApplicationFromTelegramCreationDto;
import ru.hse.edu.tukach.model.application.Application;

@Mapper(config = MappersConfig.class)
public interface ApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "lastModifiedDateTime", ignore = true)
    @Mapping(target = "initiatorTg", ignore = true)
//    @Mapping(target = "lastFilledField", ignore = true)
    @Mapping(target = "reviewerLogin", ignore = true)
    @Mapping(target = "reviewerResponse", ignore = true)
    Application toApplication(ApplicationFromEmailCreationDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "lastModifiedDateTime", ignore = true)
    @Mapping(target = "initiatorEmail", ignore = true)
//    @Mapping(target = "lastFilledField", ignore = true)
    @Mapping(target = "reviewerLogin", ignore = true)
    @Mapping(target = "reviewerResponse", ignore = true)
    Application toApplication(ApplicationFromTelegramCreationDto dto);
}
