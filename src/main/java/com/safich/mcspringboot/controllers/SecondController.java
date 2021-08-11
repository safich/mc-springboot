package com.safich.mcspringboot.controllers;

import com.safich.mcspringboot.models.ParameterType;
import com.safich.mcspringboot.models.ValueType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class SecondController {
    private final Calculator calculator;

    @Autowired
    public SecondController(Calculator calculator) {
        this.calculator = calculator;
    }

    @PostMapping("getSimulation")
    public String getValue(@RequestParam("parameterType") String stringParameterType, @RequestParam("valueType") String stringValueType,
                           @RequestParam String testsNum, @RequestParam String mean, @RequestParam String standDev, @RequestParam String year) {
        ValueType valueType = null;
        ParameterType parameterType = null;
        double[] simulationResult;

        switch (stringValueType) {
            case "NPV": valueType = ValueType.NPV;
            break;
            case "IRR": valueType = ValueType.IRR;
            break;
            case "PI": valueType = ValueType.PI;
        }

        switch (stringParameterType) {
            case "Total revenue": parameterType = ParameterType.TOTALREVENUE;
            break;
            case "Total cost": parameterType = ParameterType.TOTALCOST;
            break;
            case "Operational profit": parameterType = ParameterType.OPPROFIT;
            break;
            case "Profit before tax": parameterType = ParameterType.PROFBEFTAX;
            break;
            case "Net profit": parameterType = ParameterType.NETPROFIT;
            break;
        }

        simulationResult = callCalculator(year, mean, standDev, testsNum, parameterType, valueType);
        createHistogram(simulationResult);
        return "second/simulationResult";
    }

    private double[] callCalculator(String year, String mean, String standDev, String testsNum, ParameterType parameterType, ValueType valueType) {
        int intYear = Integer.parseInt(year);
        double dMean = Double.parseDouble(mean);
        double dStandDev = Double.parseDouble(standDev);
        int intTestsNum = Integer.parseInt(testsNum);
        double[] array;
        array = calculator.getSimulation(intYear, dMean, dStandDev, intTestsNum, parameterType, valueType);
        return array;
    }

    private void createHistogram(double[] array) {
        HistogramBuilder hb = new HistogramBuilder();
        try {
            hb.build(array);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
