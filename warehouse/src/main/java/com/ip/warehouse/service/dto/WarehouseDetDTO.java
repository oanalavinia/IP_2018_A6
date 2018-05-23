package com.ip.warehouse.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the WarehouseDet entity.
 */
public class WarehouseDetDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer stock;

    @NotNull
    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
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

        WarehouseDetDTO warehouseDetDTO = (WarehouseDetDTO) o;
        if(warehouseDetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), warehouseDetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WarehouseDetDTO{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", productId=" + getProductId() +
            "}";
    }
}
