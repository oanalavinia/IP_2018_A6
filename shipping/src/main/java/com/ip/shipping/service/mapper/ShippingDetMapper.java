package com.ip.shipping.service.mapper;

import com.ip.shipping.domain.*;
import com.ip.shipping.service.dto.ShippingDetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShippingDet and its DTO ShippingDetDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShippingDetMapper extends EntityMapper<ShippingDetDTO, ShippingDet> {



    default ShippingDet fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShippingDet shippingDet = new ShippingDet();
        shippingDet.setId(id);
        return shippingDet;
    }
}
