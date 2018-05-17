package com.ip.marketing.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MarketingDet entity.
 */
public class MarketingDetDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 6, max = 6)
    private String productCODE;

    @NotNull
    private String name;

    @NotNull
    private String description;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarketingDetDTO marketingDetDTO = (MarketingDetDTO) o;
        if(marketingDetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketingDetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketingDetDTO{" +
            "id=" + getId() +
            ", productCODE='" + getProductCODE() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
