package com.ip.shipping.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ShippingDet.
 */
@Entity
@Table(name = "shipping_det")
public class ShippingDet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ship_cost", nullable = false)
    private Double shipCost;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getShipCost() {
        return shipCost;
    }

    public ShippingDet shipCost(Double shipCost) {
        this.shipCost = shipCost;
        return this;
    }

    public void setShipCost(Double shipCost) {
        this.shipCost = shipCost;
    }

    public Long getProductId() {
        return productId;
    }

    public ShippingDet productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
        ShippingDet shippingDet = (ShippingDet) o;
        if (shippingDet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shippingDet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShippingDet{" +
            "id=" + getId() +
            ", shipCost=" + getShipCost() +
            ", productId=" + getProductId() +
            "}";
    }
}
