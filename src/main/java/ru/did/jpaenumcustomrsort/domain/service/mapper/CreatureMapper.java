package ru.diasoft.micro.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.diasoft.micro.controller.dto.TCandidateSource;
import ru.diasoft.micro.controller.dto.TCandidateSourceType;
import ru.diasoft.micro.domain.model.CandidateSourceDM;
import ru.diasoft.micro.domain.model.CandidateSourceType;

@Mapper(componentModel = "spring")
public interface CandidateSourceMapper {

    @Mapping(target = "sourceType", source = "type.name")
    @Mapping(target = "sourceTypeName", source = "type.title")
    TCandidateSource to(CandidateSourceDM byId);

    @Mapping(target = "sourceType", source = "name")
    TCandidateSourceType toType(CandidateSourceType o);
}
