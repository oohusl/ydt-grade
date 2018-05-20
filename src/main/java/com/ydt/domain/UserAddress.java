package com.ydt.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserAddress.
 */
@Entity
@Table(name = "user_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 收件人-姓名
     */
    @ApiModelProperty(value = "收件人-姓名")
    @Column(name = "receiver")
    private String receiver;

    /**
     * 收件人-电话
     */
    @ApiModelProperty(value = "收件人-电话")
    @Column(name = "receiver_tel")
    private String receiverTel;

    /**
     * 收件人-城市
     */
    @ApiModelProperty(value = "收件人-城市")
    @Column(name = "receiver_city")
    private String receiverCity;

    /**
     * 收件人-地址
     */
    @ApiModelProperty(value = "收件人-地址")
    @Column(name = "receiver_addr")
    private String receiverAddr;

    /**
     * 是否默认
     */
    @ApiModelProperty(value = "是否默认")
    @Column(name = "selected")
    private Boolean selected;

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

    public UserAddress receiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public UserAddress receiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
        return this;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public UserAddress receiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
        return this;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverAddr() {
        return receiverAddr;
    }

    public UserAddress receiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr;
        return this;
    }

    public void setReceiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr;
    }

    public Boolean isSelected() {
        return selected;
    }

    public UserAddress selected(Boolean selected) {
        this.selected = selected;
        return this;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public User getUser() {
        return user;
    }

    public UserAddress user(User user) {
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
        UserAddress userAddress = (UserAddress) o;
        if (userAddress.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAddress.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAddress{" +
            "id=" + getId() +
            ", receiver='" + getReceiver() + "'" +
            ", receiverTel='" + getReceiverTel() + "'" +
            ", receiverCity='" + getReceiverCity() + "'" +
            ", receiverAddr='" + getReceiverAddr() + "'" +
            ", selected='" + isSelected() + "'" +
            "}";
    }
}
