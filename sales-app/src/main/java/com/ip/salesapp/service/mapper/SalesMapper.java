package com.ip.salesapp.service.mapper;

import com.ip.salesapp.domain.*;
import com.ip.salesapp.service.dto.SalesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sales and its DTO SalesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SalesMapper extends EntityMapper<SalesDTO, Sales> {



    default Sales fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sales sales = new Sales();
        sales.setId(id);
        return sales;
    }
}
