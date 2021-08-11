package com.safich.mcspringboot.controllers;

import com.safich.mcspringboot.models.DataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

@Controller
@RequestMapping("/")
public class FirstController {
    private final DocReader docReader;
    private final DataStorage dataStorage;
    private final Calculator calculator;

    @Autowired
    public FirstController(DocReader docReader, DataStorage dataStorage, Calculator calculator) {
        this.docReader = docReader;
        this.dataStorage = dataStorage;
        this.calculator = calculator;
    }

    @GetMapping("/")
    public String index() {
        return "first/index";
    }

    @GetMapping("/fileData")
    public String showFileData(Model model) {
        model.addAttribute("capEx", getRoundedMapValues(dataStorage.getCapEx()));
        model.addAttribute("totalRevenue", getRoundedMapValues(dataStorage.getTotalRevenue()));
        model.addAttribute("totalCost", getRoundedMapValues(dataStorage.getTotalCost()));
        model.addAttribute("severanceTax", getRoundedMapValues(dataStorage.getSeveranceTax()));
        model.addAttribute("deprecation", getRoundedMapValues(dataStorage.getDeprecation()));
        model.addAttribute("genRunCosts", getRoundedMapValues(dataStorage.getGenRunCosts()));
        model.addAttribute("opProfit", getRoundedMapValues(dataStorage.getOpProfit()));
        model.addAttribute("intPays", getRoundedMapValues(dataStorage.getIntPays()));
        model.addAttribute("profBefTax", getRoundedMapValues(dataStorage.getProfBefTax()));
        model.addAttribute("incomeTax", getRoundedMapValues(dataStorage.getIncomeTax()));
        model.addAttribute("netProfit", getRoundedMapValues(dataStorage.getNetProfit()));
        model.addAttribute("cashFlow", getRoundedMapValues(dataStorage.getCashFlow()));
        model.addAttribute("discCashFlow", getRoundedMapValues(dataStorage.getDiscCashFlow()));
        model.addAttribute("npv", Math.round(calculator.calcStorageNpv()));
        model.addAttribute("irr", String.format("%.2f", calculator.calcStorageIrr()));
        model.addAttribute("pi", String.format("%.2f", calculator.calcStoragePi()));
        return "first/fileData";
    }

    @PostMapping("/")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Path tempDir = Files.createTempDirectory("tempDir");
            File newFile = File.createTempFile(tempDir.toString(), ".xlsx");
            if (newFile.exists() && newFile.isFile()) {
                if (newFile.delete()) {
                    System.out.println("Previous data-file deleted");
                } else {
                    System.out.println("New data-file created");
                }
            }
            file.transferTo(newFile);
            docReader.readData(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "errors/uploadingError";
        }
        return "first/downloadResult";
    }

    private HashMap<Integer, Long> getRoundedMapValues(HashMap<Integer, Double> map) {
        int startYear = 2021;
        int endYear = 2038;
        HashMap<Integer, Long> roundedMap = new HashMap<>();
        for (int i = startYear; i < endYear; i++) {
            roundedMap.put(i, Math.round(map.get(i)));
        }
        return roundedMap;
    }
}
