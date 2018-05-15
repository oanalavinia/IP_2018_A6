package com.ip.salesapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Sales.
 */
@Entity
@Table(name = "sales")
public class Sales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 4, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Min(value = 0)
    @Column(name = "price", nullable = false)
    private Integer price;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Sales name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public Sales price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
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
        Sales sales = (Sales) o;
        if (sales.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sales.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sales{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
