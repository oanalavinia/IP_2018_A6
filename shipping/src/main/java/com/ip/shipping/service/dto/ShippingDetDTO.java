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
    @Size(min = 6, max = 6)
    private String productCODE;

    @NotNull
    private Double shipCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCODE() {
        return productCODE;
    }

    public void setProductCODE(String productCODE) {
        this.productCODE = productCODE;
    }

    public Double getShipCost() {
        return shipCost;
    }

    public void setShipCost(Double shipCost) {
        this.shipCost = shipCost;
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
            ", productCODE='" + getProductCODE() + "'" +
            ", shipCost=" + getShipCost() +
            "}";
    }
}
