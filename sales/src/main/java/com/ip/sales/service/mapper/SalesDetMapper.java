package com.ip.sales.service.mapper;

import com.ip.sales.domain.*;
import com.ip.sales.service.dto.SalesDetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SalesDet and its DTO SalesDetDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SalesDetMapper extends EntityMapper<SalesDetDTO, SalesDet> {



    default SalesDet fromId(Long id) {
        if (id == null) {
            return null;
        }
        SalesDet salesDet = new SalesDet();
        salesDet.setId(id);
        return salesDet;
    }
}
