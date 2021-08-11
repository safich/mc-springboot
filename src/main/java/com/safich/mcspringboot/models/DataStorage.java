package com.safich.mcspringboot.models;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class DataStorage {
    private final HashMap<Integer, Double> capEx;
    private final HashMap<Integer, Double> totalRevenue;
    private final HashMap<Integer, Double> totalCost;
    private final HashMap<Integer, Double> severanceTax;
    private final HashMap<Integer, Double> deprecation;
    private final HashMap<Integer, Double> genRunCosts;
    private final HashMap<Integer, Double> opProfit;
    private final HashMap<Integer, Double> intPays;
    private final HashMap<Integer, Double> profBefTax;
    private final HashMap<Integer, Double> incomeTax;
    private final HashMap<Integer, Double> netProfit;
    private final HashMap<Integer, Double> cashFlow;
    private final HashMap<Integer, Double> discCashFlow;
    private double npv;
    private double irr;
    private double pi;

    public DataStorage() {
        capEx = new HashMap<>();
        totalRevenue = new HashMap<>();
        totalCost = new HashMap<>();
        severanceTax = new HashMap<>();
        deprecation = new HashMap<>();
        genRunCosts = new HashMap<>();
        opProfit = new HashMap<>();
        intPays = new HashMap<>();
        profBefTax = new HashMap<>();
        incomeTax = new HashMap<>();
        netProfit = new HashMap<>();
        cashFlow = new HashMap<>();
        discCashFlow = new HashMap<>();
    }

    public HashMap<Integer, Double> getCapEx() {
        return capEx;
    }

    public HashMap<Integer, Double> getTotalRevenue() {
        return totalRevenue;
    }

    public HashMap<Integer, Double> getTotalCost() {
        return totalCost;
    }

    public HashMap<Integer, Double> getSeveranceTax() {
        return severanceTax;
    }

    public HashMap<Integer, Double> getDeprecation() {
        return deprecation;
    }

    public HashMap<Integer, Double> getGenRunCosts() {
        return genRunCosts;
    }

    public HashMap<Integer, Double> getOpProfit() {
        return opProfit;
    }

    public HashMap<Integer, Double> getIntPays() {
        return intPays;
    }

    public HashMap<Integer, Double> getProfBefTax() {
        return profBefTax;
    }

    public HashMap<Integer, Double> getIncomeTax() {
        return incomeTax;
    }

    public HashMap<Integer, Double> getNetProfit() {
        return netProfit;
    }

    public HashMap<Integer, Double> getCashFlow() {
        return cashFlow;
    }

    public HashMap<Integer, Double> getDiscCashFlow() {
        return discCashFlow;
    }

    public void setCapEx(Integer year, Double value) {
        capEx.put(year, value);
    }

    public void setTotalRevenue(Integer year, Double value) {
        totalRevenue.put(year, value);
    }

    public void setTotalCost(Integer year, Double value) {
        totalCost.put(year, value);
    }

    public void setSeveranceTax(Integer year, Double value) {
        severanceTax.put(year, value);
    }

    public void setDeprecation(Integer year, Double value) {
        deprecation.put(year, value);
    }

    public void setGenRunCosts(Integer year, Double value) {
        genRunCosts.put(year, value);
    }

    public void setOpProfit(Integer year, Double value) {
        opProfit.put(year, value);
    }

    public void setIntPays(Integer year, Double value) {
        intPays.put(year, value);
    }

    public void setProfBefTax(Integer year, Double value) {
        profBefTax.put(year, value);
    }

    public void setIncomeTax(Integer year, Double value) {
        incomeTax.put(year, value);
    }

    public void setNetProfit(Integer year, Double value) {
        netProfit.put(year, value);
    }

    public void setCashFlow(Integer year, Double value) {
        cashFlow.put(year, value);
    }

    public void setDiscCashFlow(Integer year, Double value) {
        discCashFlow.put(year, value);
    }

    public void setNpv(double npv) {
        this.npv = npv;
    }

    public void setIrr(double irr) {
        this.irr = irr;
    }

    public void setPi(double pi) {
        this.pi = pi;
    }

    //Get specific values for each index.
    public double getCapExValue(int year) {
        return capEx.get(year);
    }

    public double getTotalRevenueValue(int year) {
        return totalRevenue.get(year);
    }

    public double getTotalCostValue(int year) {
        return totalCost.get(year);
    }

    public double getSeveranceTaxValue(int year) {
        return severanceTax.get(year);
    }

    public double getDeprecationValue(int year) {
        return deprecation.get(year);
    }

    public double getGenRunCostsValue(int year) {
        return genRunCosts.get(year);
    }

    public double getOpProfitValue(int year) {
        return opProfit.get(year);
    }

    public double getIntPaysValue(int year) {
        return intPays.get(year);
    }

    public double getProfBefTaxValue(int year) {
        return profBefTax.get(year);
    }

    public double getIncomeTaxValue(int year) {
        return incomeTax.get(year);
    }

    public double getNetProfitValue(int year) {
        return netProfit.get(year);
    }

    public double getCashFlowValue(int year) {
        return cashFlow.get(year);
    }

    public double getDiscCashFlowValue(int year) {
        return discCashFlow.get(year);
    }

    //Get discount rate and reported period
    public double getDiscountRate() {
        return 0.12;
    }

    public int getReportPeriod() {
        return 17;
    }

    //Get 3 values from document
    public double getNpv() {
        return npv;
    }

    public double getIrr() {
        return irr;
    }

    public double getPi() {
        return pi;
    }
}
