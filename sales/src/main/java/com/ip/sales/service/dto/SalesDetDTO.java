package com.ip.sales.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SalesDet entity.
 */
public class SalesDetDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 6, max = 6)
    private String productCODE;

    @NotNull
    private Double price;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalesDetDTO salesDetDTO = (SalesDetDTO) o;
        if(salesDetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salesDetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalesDetDTO{" +
            "id=" + getId() +
            ", productCODE='" + getProductCODE() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
