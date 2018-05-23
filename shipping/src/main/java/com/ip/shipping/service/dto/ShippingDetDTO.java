package com.ip.shipping.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ShippingDet entity.
 */
public class ShippingDetDTO implements Serializable {

    private Long id;

    @NotNull
    private Double shipCost;

    @NotNull
    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getShipCost() {
        return shipCost;
    }

    public void setShipCost(Double shipCost) {
        this.shipCost = shipCost;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShippingDetDTO shippingDetDTO = (ShippingDetDTO) o;
        if(shippingDetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shippingDetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShippingDetDTO{" +
            "id=" + getId() +
            ", shipCost=" + getShipCost() +
            ", productId=" + getProductId() +
            "}";
    }
}
