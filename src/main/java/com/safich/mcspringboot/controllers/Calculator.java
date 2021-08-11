package com.safich.mcspringboot.controllers;

import com.safich.mcspringboot.models.DataStorage;
import com.safich.mcspringboot.models.ParameterType;
import com.safich.mcspringboot.models.ValueType;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.poi.ss.formula.functions.Irr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Calculator {
    private final DataStorage storage;
    private final int startYear = 2021;
    private final int reportYear = 2022;

    double capEx;
    double totalRevenue;
    double totalCost;
    double severanceTax;
    double deprecation;
    double genRunCosts;
    double opProfit;
    double intPays;
    double profBefTax;
    double incomeTax;
    double netProfit;
    double cashFlow;
    double discountRate;

    @Autowired
    public Calculator(DataStorage storage) {
        this.storage = storage;
    }

    private void assignValuesFromStorage(int year) {
        capEx = storage.getCapExValue(year);
        totalRevenue = storage.getTotalRevenueValue(year);
        totalCost = storage.getTotalCostValue(year);
        severanceTax = storage.getSeveranceTaxValue(year);
        deprecation = storage.getDeprecationValue(year);
        genRunCosts = storage.getGenRunCostsValue(year);
        opProfit = storage.getOpProfitValue(year);
        intPays = storage.getIntPaysValue(year);
        profBefTax = storage.getProfBefTaxValue(year);
        incomeTax = storage.getIncomeTaxValue(year);
        netProfit = storage.getNetProfitValue(year);
        cashFlow = storage.getCashFlowValue(year);
        discountRate = storage.getDiscountRate();
    }

    //Get normal distribution value
    private double getNormDist(double mean, double standDev) {
        NormalDistribution distribution = new NormalDistribution(mean, standDev);
        return distribution.inverseCumulativeProbability(Math.random());
    }

    //Calculate NPV, IRR and PI values from storage data
    public double calcStorageNpv() {
        int y = startYear;
        double npv = 0;
        for (int i = 0; i < storage.getReportPeriod(); i++) {
            npv += storage.getDiscCashFlowValue(y);
            y++;
        }
        return npv;
    }

    public double calcStorageIrr() {
        double[] array = new double[storage.getReportPeriod()];
        int y = startYear;
        for (int i = 0; i < storage.getReportPeriod(); i++) {
            array[i] = storage.getCashFlowValue(y);
            y++;
        }
        return Irr.irr(array);
    }

    public double calcStoragePi() {
        double sum1 = 0;
        int y = startYear + 2;
        for (int i = 2; i < storage.getReportPeriod(); i++) {
            sum1 += storage.getDiscCashFlowValue(y);
            y++;
        }
        double sum2 = storage.getDiscCashFlowValue(startYear) + storage.getDiscCashFlowValue(startYear + 1);
        return sum1/(-1 * sum2);
    }

    //Calculation methods
    public double[] getSimulation(int year, double mean, double standDev, int testsNum, ParameterType paramType, ValueType valueType) {
        double[] array = new double[testsNum];
        if(valueType.equals(ValueType.NPV)) {
            for (int i = 0; i < testsNum; i++) {
                array[i] = Math.round(getValue(year, mean, standDev, paramType, valueType));
            }
        } else {
            for (int i = 0; i < testsNum; i++) {
                array[i] = getValue(year, mean, standDev, paramType, valueType);
            }
        }
        return array;
    }

    private double getValue(int year, double mean, double standDev, ParameterType paramType, ValueType valueType) {
        assignValuesFromStorage(year);
        double npvValue = 0;
        double irrValue = 0;
        double piValue = 0;
        double resultValue = 0;

        switch (paramType) {
            case OPPROFIT:
                opProfit = getNormDist(mean,standDev);
                cashFlow = opProfit - intPays - incomeTax - capEx;
                npvValue = calcDCFForNPV(year, cashFlow);
                irrValue = calcIRR(year, cashFlow);
                piValue = getSumForPi(year, cashFlow);
                break;
            case TOTALREVENUE:
                totalRevenue = getNormDist(mean, standDev);
                cashFlow = totalRevenue - totalCost - severanceTax - deprecation - genRunCosts - intPays - incomeTax - capEx;
                npvValue = calcDCFForNPV(year, cashFlow);
                irrValue = calcIRR(year, cashFlow);
                piValue = getSumForPi(year, cashFlow);
                break;
            case TOTALCOST:
                totalCost = getNormDist(mean, standDev);
                cashFlow = totalRevenue - totalCost - severanceTax - deprecation - genRunCosts - intPays - incomeTax - capEx;
                npvValue = calcDCFForNPV(year, cashFlow);
                irrValue = calcIRR(year, cashFlow);
                piValue = getSumForPi(year, cashFlow);
                break;
            case PROFBEFTAX:
                profBefTax = getNormDist(mean, standDev);
                cashFlow = profBefTax - incomeTax - capEx;
                npvValue = calcDCFForNPV(year, cashFlow);
                irrValue = calcIRR(year, cashFlow);
                piValue = getSumForPi(year, cashFlow);
                break;
            case NETPROFIT:
                netProfit = getNormDist(mean, standDev);
                cashFlow = netProfit - capEx;
                npvValue = calcDCFForNPV(year, cashFlow);
                irrValue = calcIRR(year, cashFlow);
                piValue = getSumForPi(year, cashFlow);
                break;
        }
        switch (valueType) {
            case NPV: resultValue = npvValue;
            break;
            case IRR: resultValue = irrValue;
            break;
            case PI: resultValue = piValue;
            break;
        }
        return resultValue;
    }

    private double calcDCFForNPV(int year, double cashFlow) {
        double discCashFlow = cashFlow/Math.pow((1 + discountRate),(year - reportYear));
        return calcStorageNpv() - storage.getDiscCashFlowValue(year) + discCashFlow;
    }

    private double calcIRR(int year, double cashFlow) {
        HashMap<Integer, Double> cashFlowMap = storage.getCashFlow();
        cashFlowMap.put(year, cashFlow);
        double[] array = new double[storage.getReportPeriod()];
        int y = startYear;
        for (int i = 0; i < array.length; i++) {
            array[i] = cashFlowMap.get(y);
            y++;
        }
        return Irr.irr(array);
    }

    private double getSumForPi(int year, double cashFlow) {
        double discCashFlow = cashFlow/Math.pow((1 + discountRate),(year - reportYear));
        HashMap<Integer, Double> map = storage.getDiscCashFlow();
        map.put(year, discCashFlow);
        double sum1 = 0;
        int y = startYear + 2;
        for (int i = 2; i < storage.getReportPeriod(); i++) {
            sum1 += map.get(y);
            y++;
        }
        double sum2 = map.get(startYear) + map.get(startYear + 1);
        return sum1/(-1 * sum2);
    }
}
