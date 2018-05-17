package com.ip.marketing.service.mapper;

import com.ip.marketing.domain.*;
import com.ip.marketing.service.dto.MarketingDetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MarketingDet and its DTO MarketingDetDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MarketingDetMapper extends EntityMapper<MarketingDetDTO, MarketingDet> {



    default MarketingDet fromId(Long id) {
        if (id == null) {
            return null;
        }
        MarketingDet marketingDet = new MarketingDet();
        marketingDet.setId(id);
        return marketingDet;
    }
}
