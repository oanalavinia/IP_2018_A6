package com.ip.warehouse.service.mapper;

import com.ip.warehouse.domain.*;
import com.ip.warehouse.service.dto.WarehouseDetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WarehouseDet and its DTO WarehouseDetDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WarehouseDetMapper extends EntityMapper<WarehouseDetDTO, WarehouseDet> {



    default WarehouseDet fromId(Long id) {
        if (id == null) {
            return null;
        }
        WarehouseDet warehouseDet = new WarehouseDet();
        warehouseDet.setId(id);
        return warehouseDet;
    }
}
