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
    @Size(min = 6, max = 6)
    private String productCODE;

    @NotNull
    private Integer stock;

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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
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
            ", productCODE='" + getProductCODE() + "'" +
            ", stock=" + getStock() +
            "}";
    }
}
