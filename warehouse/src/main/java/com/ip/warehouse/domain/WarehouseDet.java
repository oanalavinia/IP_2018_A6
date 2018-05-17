package com.ip.warehouse.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A WarehouseDet.
 */
@Entity
@Table(name = "warehouse_det")
public class WarehouseDet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 6, max = 6)
    @Column(name = "product_code", length = 6, nullable = false)
    private String productCODE;

    @NotNull
    @Column(name = "stock", nullable = false)
    private Integer stock;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCODE() {
        return productCODE;
    }

    public WarehouseDet productCODE(String productCODE) {
        this.productCODE = productCODE;
        return this;
    }

    public void setProductCODE(String productCODE) {
        this.productCODE = productCODE;
    }

    public Integer getStock() {
        return stock;
    }

    public WarehouseDet stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WarehouseDet warehouseDet = (WarehouseDet) o;
        if (warehouseDet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), warehouseDet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WarehouseDet{" +
            "id=" + getId() +
            ", productCODE='" + getProductCODE() + "'" +
            ", stock=" + getStock() +
            "}";
    }
}
