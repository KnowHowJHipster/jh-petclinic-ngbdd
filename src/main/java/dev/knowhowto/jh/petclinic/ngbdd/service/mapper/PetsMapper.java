package dev.knowhowto.jh.petclinic.ngbdd.service.mapper;

import org.mapstruct.*;
import dev.knowhowto.jh.petclinic.ngbdd.domain.Owners;
import dev.knowhowto.jh.petclinic.ngbdd.domain.Pets;
import dev.knowhowto.jh.petclinic.ngbdd.domain.Types;
import dev.knowhowto.jh.petclinic.ngbdd.service.dto.OwnersDTO;
import dev.knowhowto.jh.petclinic.ngbdd.service.dto.PetsDTO;
import dev.knowhowto.jh.petclinic.ngbdd.service.dto.TypesDTO;

/**
 * Mapper for the entity {@link Pets} and its DTO {@link PetsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PetsMapper extends EntityMapper<PetsDTO, Pets> {
    @Mapping(target = "type", source = "type", qualifiedByName = "typesId")
    @Mapping(target = "owner", source = "owner", qualifiedByName = "ownersId")
    PetsDTO toDto(Pets s);

    @Named("typesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TypesDTO toDtoTypesId(Types types);

    @Named("ownersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OwnersDTO toDtoOwnersId(Owners owners);
}
