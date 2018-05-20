package com.ydt.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.ydt.domain.enumeration.Fact;

import com.ydt.domain.enumeration.State;

import com.ydt.domain.enumeration.OrderState;

/**
 * A Coin.
 */
@Entity
@Table(name = "coin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Coin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 认证编号
     */
    @ApiModelProperty(value = "认证编号")
    @Column(name = "cert_no")
    private String certNo;

    /**
     * 类型编号
     */
    @ApiModelProperty(value = "类型编号")
    @Column(name = "type_no")
    private String typeNo;

    /**
     * 年代
     */
    @ApiModelProperty(value = "年代")
    @Column(name = "jhi_year")
    private String year;

    /**
     * 面额
     */
    @ApiModelProperty(value = "面额")
    @Column(name = "denom")
    private Integer denom;

    /**
     * 国别
     */
    @ApiModelProperty(value = "国别")
    @Column(name = "country")
    private String country;

    /**
     * 钱币名称
     */
    @ApiModelProperty(value = "钱币名称")
    @Column(name = "name")
    private String name;

    /**
     * 装盒类型
     */
    @ApiModelProperty(value = "装盒类型")
    @Column(name = "package_type")
    private String packageType;

    /**
     * 评级结果
     */
    @ApiModelProperty(value = "评级结果")
    @Enumerated(EnumType.STRING)
    @Column(name = "grading_result")
    private Fact gradingResult;

    /**
     * 是否上链(换虚拟币)
     */
    @ApiModelProperty(value = "是否上链(换虚拟币)")
    @Enumerated(EnumType.STRING)
    @Column(name = "block_chain_flag")
    private State blockChainFlag;

    /**
     * 预估价格(每枚)
     */
    @ApiModelProperty(value = "预估价格(每枚)")
    @Column(name = "declared_value", precision=10, scale=2)
    private BigDecimal declaredValue;

    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    @Column(name = "handing_fee", precision=10, scale=2)
    private BigDecimal handingFee;

    /**
     * 是否装盒
     */
    @ApiModelProperty(value = "是否装盒")
    @Enumerated(EnumType.STRING)
    @Column(name = "package_flag")
    private State packageFlag;

    /**
     * 钱币状态
     */
    @ApiModelProperty(value = "钱币状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OrderState state;

    /**
     * 正面照片
     */
    @ApiModelProperty(value = "正面照片")
    @Column(name = "front_image")
    private String frontImage;

    /**
     * 反面照片
     */
    @ApiModelProperty(value = "反面照片")
    @Column(name = "back_image")
    private String backImage;

    @ManyToOne
    private Ordr ordr;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertNo() {
        return certNo;
    }

    public Coin certNo(String certNo) {
        this.certNo = certNo;
        return this;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getTypeNo() {
        return typeNo;
    }

    public Coin typeNo(String typeNo) {
        this.typeNo = typeNo;
        return this;
    }

    public void setTypeNo(String typeNo) {
        this.typeNo = typeNo;
    }

    public String getYear() {
        return year;
    }

    public Coin year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getDenom() {
        return denom;
    }

    public Coin denom(Integer denom) {
        this.denom = denom;
        return this;
    }

    public void setDenom(Integer denom) {
        this.denom = denom;
    }

    public String getCountry() {
        return country;
    }

    public Coin country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public Coin name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageType() {
        return packageType;
    }

    public Coin packageType(String packageType) {
        this.packageType = packageType;
        return this;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public Fact getGradingResult() {
        return gradingResult;
    }

    public Coin gradingResult(Fact gradingResult) {
        this.gradingResult = gradingResult;
        return this;
    }

    public void setGradingResult(Fact gradingResult) {
        this.gradingResult = gradingResult;
    }

    public State getBlockChainFlag() {
        return blockChainFlag;
    }

    public Coin blockChainFlag(State blockChainFlag) {
        this.blockChainFlag = blockChainFlag;
        return this;
    }

    public void setBlockChainFlag(State blockChainFlag) {
        this.blockChainFlag = blockChainFlag;
    }

    public BigDecimal getDeclaredValue() {
        return declaredValue;
    }

    public Coin declaredValue(BigDecimal declaredValue) {
        this.declaredValue = declaredValue;
        return this;
    }

    public void setDeclaredValue(BigDecimal declaredValue) {
        this.declaredValue = declaredValue;
    }

    public BigDecimal getHandingFee() {
        return handingFee;
    }

    public Coin handingFee(BigDecimal handingFee) {
        this.handingFee = handingFee;
        return this;
    }

    public void setHandingFee(BigDecimal handingFee) {
        this.handingFee = handingFee;
    }

    public State getPackageFlag() {
        return packageFlag;
    }

    public Coin packageFlag(State packageFlag) {
        this.packageFlag = packageFlag;
        return this;
    }

    public void setPackageFlag(State packageFlag) {
        this.packageFlag = packageFlag;
    }

    public OrderState getState() {
        return state;
    }

    public Coin state(OrderState state) {
        this.state = state;
        return this;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public Coin frontImage(String frontImage) {
        this.frontImage = frontImage;
        return this;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getBackImage() {
        return backImage;
    }

    public Coin backImage(String backImage) {
        this.backImage = backImage;
        return this;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public Ordr getOrdr() {
        return ordr;
    }

    public Coin ordr(Ordr ordr) {
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
        Coin coin = (Coin) o;
        if (coin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Coin{" +
            "id=" + getId() +
            ", certNo='" + getCertNo() + "'" +
            ", typeNo='" + getTypeNo() + "'" +
            ", year='" + getYear() + "'" +
            ", denom=" + getDenom() +
            ", country='" + getCountry() + "'" +
            ", name='" + getName() + "'" +
            ", packageType='" + getPackageType() + "'" +
            ", gradingResult='" + getGradingResult() + "'" +
            ", blockChainFlag='" + getBlockChainFlag() + "'" +
            ", declaredValue=" + getDeclaredValue() +
            ", handingFee=" + getHandingFee() +
            ", packageFlag='" + getPackageFlag() + "'" +
            ", state='" + getState() + "'" +
            ", frontImage='" + getFrontImage() + "'" +
            ", backImage='" + getBackImage() + "'" +
            "}";
    }
}
