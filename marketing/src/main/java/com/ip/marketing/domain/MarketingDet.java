package com.ip.marketing.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MarketingDet.
 */
@Entity
@Table(name = "marketing_det")
public class MarketingDet implements Serializable {

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
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

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

    public MarketingDet productCODE(String productCODE) {
        this.productCODE = productCODE;
        return this;
    }

    public void setProductCODE(String productCODE) {
        this.productCODE = productCODE;
    }

    public String getName() {
        return name;
    }

    public MarketingDet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public MarketingDet description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
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
        MarketingDet marketingDet = (MarketingDet) o;
        if (marketingDet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketingDet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketingDet{" +
            "id=" + getId() +
            ", productCODE='" + getProductCODE() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
