package com.ydt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.ydt.domain.enumeration.OrderState;

/**
 * A Ordr.
 */
@Entity
@Table(name = "ordr")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ordr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 收件人
     */
    @ApiModelProperty(value = "收件人")
    @Column(name = "receiver")
    private String receiver;

    /**
     * 收件人-电话
     */
    @ApiModelProperty(value = "收件人-电话")
    @Column(name = "receiver_tel")
    private String receiverTel;

    /**
     * 收件人-地址
     */
    @ApiModelProperty(value = "收件人-地址")
    @Column(name = "receiver_addr")
    private String receiverAddr;

    /**
     * 回寄方式(0 自取 1 快递)
     */
    @ApiModelProperty(value = "回寄方式(0 自取 1 快递)")
    @Column(name = "delivery_type")
    private Integer deliveryType;

    /**
     * 快递-保费
     */
    @ApiModelProperty(value = "快递-保费")
    @Column(name = "insured_price", precision=10, scale=2)
    private BigDecimal insuredPrice;

    /**
     * 总数量
     */
    @ApiModelProperty(value = "总数量")
    @Column(name = "total_quantity")
    private Integer totalQuantity;

    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    @Column(name = "handing_fee", precision=10, scale=2)
    private BigDecimal handingFee;

    /**
     * 提交日期
     */
    @ApiModelProperty(value = "提交日期")
    @Column(name = "create_date")
    private Instant createDate;

    /**
     * 收到日期
     */
    @ApiModelProperty(value = "收到日期")
    @Column(name = "receive_date")
    private Instant receiveDate;

    /**
     * 收到-快递单号
     */
    @ApiModelProperty(value = "收到-快递单号")
    @Column(name = "receive_no")
    private String receiveNo;

    /**
     * 回寄日期
     */
    @ApiModelProperty(value = "回寄日期")
    @Column(name = "back_date")
    private Instant backDate;

    /**
     * 回寄-快递单号
     */
    @ApiModelProperty(value = "回寄-快递单号")
    @Column(name = "back_no")
    private String backNo;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OrderState state;

    @OneToMany(mappedBy = "ordr")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrdrCoin> ordrCoins = new HashSet<>();

    @OneToMany(mappedBy = "ordr")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Coin> coins = new HashSet<>();

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public Ordr receiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public Ordr receiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
        return this;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getReceiverAddr() {
        return receiverAddr;
    }

    public Ordr receiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr;
        return this;
    }

    public void setReceiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public Ordr deliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public BigDecimal getInsuredPrice() {
        return insuredPrice;
    }

    public Ordr insuredPrice(BigDecimal insuredPrice) {
        this.insuredPrice = insuredPrice;
        return this;
    }

    public void setInsuredPrice(BigDecimal insuredPrice) {
        this.insuredPrice = insuredPrice;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public Ordr totalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
        return this;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getHandingFee() {
        return handingFee;
    }

    public Ordr handingFee(BigDecimal handingFee) {
        this.handingFee = handingFee;
        return this;
    }

    public void setHandingFee(BigDecimal handingFee) {
        this.handingFee = handingFee;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Ordr createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getReceiveDate() {
        return receiveDate;
    }

    public Ordr receiveDate(Instant receiveDate) {
        this.receiveDate = receiveDate;
        return this;
    }

    public void setReceiveDate(Instant receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getReceiveNo() {
        return receiveNo;
    }

    public Ordr receiveNo(String receiveNo) {
        this.receiveNo = receiveNo;
        return this;
    }

    public void setReceiveNo(String receiveNo) {
        this.receiveNo = receiveNo;
    }

    public Instant getBackDate() {
        return backDate;
    }

    public Ordr backDate(Instant backDate) {
        this.backDate = backDate;
        return this;
    }

    public void setBackDate(Instant backDate) {
        this.backDate = backDate;
    }

    public String getBackNo() {
        return backNo;
    }

    public Ordr backNo(String backNo) {
        this.backNo = backNo;
        return this;
    }

    public void setBackNo(String backNo) {
        this.backNo = backNo;
    }

    public OrderState getState() {
        return state;
    }

    public Ordr state(OrderState state) {
        this.state = state;
        return this;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Set<OrdrCoin> getOrdrCoins() {
        return ordrCoins;
    }

    public Ordr ordrCoins(Set<OrdrCoin> ordrCoins) {
        this.ordrCoins = ordrCoins;
        return this;
    }

    public Ordr addOrdrCoins(OrdrCoin ordrCoin) {
        this.ordrCoins.add(ordrCoin);
        ordrCoin.setOrdr(this);
        return this;
    }

    public Ordr removeOrdrCoins(OrdrCoin ordrCoin) {
        this.ordrCoins.remove(ordrCoin);
        ordrCoin.setOrdr(null);
        return this;
    }

    public void setOrdrCoins(Set<OrdrCoin> ordrCoins) {
        this.ordrCoins = ordrCoins;
    }

    public Set<Coin> getCoins() {
        return coins;
    }

    public Ordr coins(Set<Coin> coins) {
        this.coins = coins;
        return this;
    }

    public Ordr addCoins(Coin coin) {
        this.coins.add(coin);
        coin.setOrdr(this);
        return this;
    }

    public Ordr removeCoins(Coin coin) {
        this.coins.remove(coin);
        coin.setOrdr(null);
        return this;
    }

    public void setCoins(Set<Coin> coins) {
        this.coins = coins;
    }

    public User getUser() {
        return user;
    }

    public Ordr user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Ordr ordr = (Ordr) o;
        if (ordr.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordr.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ordr{" +
            "id=" + getId() +
            ", receiver='" + getReceiver() + "'" +
            ", receiverTel='" + getReceiverTel() + "'" +
            ", receiverAddr='" + getReceiverAddr() + "'" +
            ", deliveryType=" + getDeliveryType() +
            ", insuredPrice=" + getInsuredPrice() +
            ", totalQuantity=" + getTotalQuantity() +
            ", handingFee=" + getHandingFee() +
            ", createDate='" + getCreateDate() + "'" +
            ", receiveDate='" + getReceiveDate() + "'" +
            ", receiveNo='" + getReceiveNo() + "'" +
            ", backDate='" + getBackDate() + "'" +
            ", backNo='" + getBackNo() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
