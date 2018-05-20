package com.ydt.service.mapper;

import com.ydt.domain.Ordr;
import com.ydt.service.dto.OrdrDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrdrMapper {

    OrdrMapper INSTANCE = Mappers.getMapper( OrdrMapper.class );

    Ordr toEntity(OrdrDTO ordrDTO);
}
