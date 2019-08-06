package com.assessment.parser;

import com.assessment.Employee;
import com.assessment.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CellPhoneFileProcessor {

    public Pair<Map<Integer, Employee>, Map<String, List<Integer>> > processor(String fileName) {


        Map<Integer,Employee> employeeMap = new HashMap<>();
        Map<String, List<Integer>> cellPhoneMap = new HashMap<>();

        try {
            Scanner ob = new Scanner(new File("./input/" + fileName));
            if(ob.hasNextLine()){
                ob.nextLine(); // skipping header line - row 1
            }

            while (ob.hasNextLine())
            {
                String line = ob.nextLine();
                String[] col= line.split(",");
                Employee e = new Employee();
                e.setEmployeeId(Integer.parseInt(col[0]));
                e.setEmployeeName(col[1]);
                e.setPurchaseDate(col[2]);
                e.setModel(col[3]);

                employeeMap.put(e.getEmployeeId(),e);

                List<Integer> ids = cellPhoneMap.getOrDefault(e.getModel(), new ArrayList<>());
                ids.add(e.getEmployeeId());
                cellPhoneMap.put(e.getModel(), ids);
            }

        }
        catch (FileNotFoundException filenotfound)
        {
            System.out.println("Message : " +filenotfound.getMessage()); // Handle exception
        }
        return new Pair<>(employeeMap, cellPhoneMap);
    }

}
