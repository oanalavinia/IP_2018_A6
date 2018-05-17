package com.ip.sales.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SalesDet.
 */
@Entity
@Table(name = "sales_det")
public class SalesDet implements Serializable {

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
    @Column(name = "price", nullable = false)
    private Double price;

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

    public SalesDet productCODE(String productCODE) {
        this.productCODE = productCODE;
        return this;
    }

    public void setProductCODE(String productCODE) {
        this.productCODE = productCODE;
    }

    public Double getPrice() {
        return price;
    }

    public SalesDet price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
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
        SalesDet salesDet = (SalesDet) o;
        if (salesDet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salesDet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalesDet{" +
            "id=" + getId() +
            ", productCODE='" + getProductCODE() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
