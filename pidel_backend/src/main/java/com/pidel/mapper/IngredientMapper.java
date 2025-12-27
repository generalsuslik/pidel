package com.pidel.mapper;

import com.pidel.dto.IngredientDto;
import com.pidel.entity.Ingredient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientDto toDto(Ingredient ingredient);
    List<Ingredient> toEntityList(List<IngredientDto> ingredients);
}
