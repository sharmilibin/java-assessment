package com.assessment.parser;

import com.assessment.CellPhoneUsage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CellPhoneUsageFileProcessor {

    Map<Integer, List<CellPhoneUsage>> processor(String fileName) {
        Map<Integer, List<CellPhoneUsage>> map = new HashMap<>();
        try {
            Scanner ob = new Scanner(new File("./input/" + fileName));
            if (ob.hasNext()) {
                ob.next(); // skipping header line - row 1
            }

            while (ob.hasNext()) {
                String line = ob.next();
                String[] col = line.split(",");
                CellPhoneUsage c = new CellPhoneUsage();
                c.setEmployeeId(Integer.parseInt(col[0]));
                c.setDate(col[0]);
                c.setTotalData(Double.parseDouble(col[0]));
                c.setTotalMinutes(Integer.parseInt(col[0]));

                List<CellPhoneUsage> cellPhoneUsages = map.getOrDefault(c.getEmployeeId(), new ArrayList<>());
                cellPhoneUsages.add(c);
                map.put(c.getEmployeeId(), cellPhoneUsages);
            }

        } catch (FileNotFoundException filenotfound) {
            System.out.println("Message : " + filenotfound.getMessage()); // Handle exception
        }
        return map;
    }
}
