package com.ydt.service.dto;

import java.io.Serializable;
import java.util.Set;


public class OrdrDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收件人
     */
    private String receiver;

    /**
     * 收件人-电话
     */
    private String receiverTel;

    /**
     * 收件人-地址
     */
    private String receiverAddr;

    /**
     * 回寄方式(0 自取 1 快递)
     */
    private Integer deliveryType;


    private Set<OrdrCoinDTO> coins;


    public String getReceiver() {
        return receiver;
    }

    public OrdrDTO receiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public OrdrDTO receiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
        return this;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getReceiverAddr() {
        return receiverAddr;
    }

    public OrdrDTO receiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr;
        return this;
    }

    public void setReceiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public OrdrDTO deliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }



    @Override
    public String toString() {
        return "Ordr{" +
            ", receiver='" + getReceiver() + "'" +
            ", receiverTel='" + getReceiverTel() + "'" +
            ", receiverAddr='" + getReceiverAddr() + "'" +
            ", deliveryType=" + getDeliveryType() +
            "}";
    }

    public Set<OrdrCoinDTO> getCoins() {
        return coins;
    }

    public void setCoins(Set<OrdrCoinDTO> coins) {
        this.coins = coins;
    }
}
