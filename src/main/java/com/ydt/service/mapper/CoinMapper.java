package com.ydt.service.mapper;

import com.ydt.domain.Coin;
import com.ydt.domain.OrdrCoin;
import com.ydt.service.dto.OrdrCoinDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoinMapper {

    CoinMapper INSTANCE = Mappers.getMapper( CoinMapper.class );

    OrdrCoin toEntity(OrdrCoinDTO ordrCoinDTO);

    Coin toCoin(OrdrCoinDTO ordrCoinDTO);
}
