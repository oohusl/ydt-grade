package com.ydt.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A FeeRule.
 */
@Entity
@Table(name = "fee_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FeeRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 等级
     */
    @ApiModelProperty(value = "等级")
    @Column(name = "name")
    private String name;

    /**
     * 最低价 <=
     */
    @ApiModelProperty(value = "最低价 <=")
    @Column(name = "min_value")
    private Integer minValue;

    /**
     * 最高价 >
     */
    @ApiModelProperty(value = "最高价 >")
    @Column(name = "max_value")
    private Integer maxValue;

    /**
     * eg. 市场估值的1%, 0表示固定收费
     */
    @ApiModelProperty(value = "eg. 市场估值的1%, 0表示固定收费")
    @Column(name = "rate", precision=10, scale=2)
    private BigDecimal rate;

    /**
     * 固定金额
     */
    @ApiModelProperty(value = "固定金额")
    @Column(name = "fixed")
    private Integer fixed;

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

    public FeeRule name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public FeeRule minValue(Integer minValue) {
        this.minValue = minValue;
        return this;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public FeeRule maxValue(Integer maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public FeeRule rate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getFixed() {
        return fixed;
    }

    public FeeRule fixed(Integer fixed) {
        this.fixed = fixed;
        return this;
    }

    public void setFixed(Integer fixed) {
        this.fixed = fixed;
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
        FeeRule feeRule = (FeeRule) o;
        if (feeRule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feeRule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeeRule{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", minValue=" + getMinValue() +
            ", maxValue=" + getMaxValue() +
            ", rate=" + getRate() +
            ", fixed=" + getFixed() +
            "}";
    }
}
