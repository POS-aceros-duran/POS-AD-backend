package com.aceros_duran.pos.mapper;

import com.aceros_duran.pos.dto.request.GenderRequest;
import com.aceros_duran.pos.dto.response.GenderResponse;
import com.aceros_duran.pos.model.GenderModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenderMapper implements BaseMapper<GenderResponse, GenderRequest, GenderModel> {

    @Override
    public GenderModel toEntity(GenderRequest dto) {
        return GenderModel.builder()
                .idGender(dto.getIdGender())
                .gender(dto.getGender())
                .build();
    }

    @Override
    public GenderResponse toDto(GenderModel entity) {
        return GenderResponse.builder()
                .idGender(entity.getIdGender())
                .gender(entity.getGender())
                .build();
    }

    @Override
    public List<GenderResponse> toDtos(List<GenderModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(GenderRequest request, GenderModel entity) {
        entity.setGender(request.getGender());
    }
}
