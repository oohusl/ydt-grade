package com.ydt.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.ydt.domain.enumeration.State;

/**
 * A OrdrCoin.
 */
@Entity
@Table(name = "ordr_coin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrdrCoin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 钱币名称
     */
    @ApiModelProperty(value = "钱币名称")
    @Column(name = "name")
    private String name;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    @Column(name = "quantity")
    private Integer quantity;

    /**
     * 是否装盒
     */
    @ApiModelProperty(value = "是否装盒")
    @Enumerated(EnumType.STRING)
    @Column(name = "package_flag")
    private State packageFlag;

    /**
     * 预估价格(每枚)
     */
    @ApiModelProperty(value = "预估价格(每枚)")
    @Column(name = "declared_value", precision=10, scale=2)
    private BigDecimal declaredValue;

    @ManyToOne
    private Ordr ordr;

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

    public OrdrCoin name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrdrCoin quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public State getPackageFlag() {
        return packageFlag;
    }

    public OrdrCoin packageFlag(State packageFlag) {
        this.packageFlag = packageFlag;
        return this;
    }

    public void setPackageFlag(State packageFlag) {
        this.packageFlag = packageFlag;
    }

    public BigDecimal getDeclaredValue() {
        return declaredValue;
    }

    public OrdrCoin declaredValue(BigDecimal declaredValue) {
        this.declaredValue = declaredValue;
        return this;
    }

    public void setDeclaredValue(BigDecimal declaredValue) {
        this.declaredValue = declaredValue;
    }

    public Ordr getOrdr() {
        return ordr;
    }

    public OrdrCoin ordr(Ordr ordr) {
        this.ordr = ordr;
        return this;
    }

    public void setOrdr(Ordr ordr) {
        this.ordr = ordr;
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
        OrdrCoin ordrCoin = (OrdrCoin) o;
        if (ordrCoin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordrCoin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrdrCoin{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", packageFlag='" + getPackageFlag() + "'" +
            ", declaredValue=" + getDeclaredValue() +
            "}";
    }
}
