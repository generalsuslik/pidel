package com.pidel.mapper;

import com.pidel.dto.IngredientDto;
import com.pidel.entity.Ingredient;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientDto toDto(Ingredient ingredient);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateIngredientFromDto(IngredientDto dto, @MappingTarget Ingredient entity);

    List<IngredientDto> toDtoList(List<IngredientDto> ingredients);

    List<Ingredient> toEntityList(List<IngredientDto> ingredients);
}
