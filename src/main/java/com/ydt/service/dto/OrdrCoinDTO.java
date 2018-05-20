package com.ydt.service.dto;

import com.ydt.domain.enumeration.State;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A OrdrCoin.
 */
public class OrdrCoinDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String name;


    private Integer quantity;


    private State packageFlag;


    private BigDecimal declaredValue;


    public String getName() {
        return name;
    }

    public OrdrCoinDTO name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrdrCoinDTO quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public State getPackageFlag() {
        return packageFlag;
    }

    public OrdrCoinDTO packageFlag(State packageFlag) {
        this.packageFlag = packageFlag;
        return this;
    }

    public void setPackageFlag(State packageFlag) {
        this.packageFlag = packageFlag;
    }


    public BigDecimal getDeclaredValue() {
        return declaredValue;
    }

    public OrdrCoinDTO declaredValue(BigDecimal declaredValue) {
        this.declaredValue = declaredValue;
        return this;
    }

    public void setDeclaredValue(BigDecimal declaredValue) {
        this.declaredValue = declaredValue;
    }


    @Override
    public String toString() {
        return "OrdrCoin{" +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", packageFlag='" + getPackageFlag() + "'" +
            ", declaredValue=" + getDeclaredValue() +
            "}";
    }
}
