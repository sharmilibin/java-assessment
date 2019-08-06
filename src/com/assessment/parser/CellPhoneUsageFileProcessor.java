package com.assessment.parser;

import com.assessment.CellPhoneUsage;
import com.assessment.CellPhoneUsageWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CellPhoneUsageFileProcessor {


    public CellPhoneUsageWrapper processor(String fileName) {
        Map<Integer, CellPhoneUsage> map = new HashMap<>();
        int totalMins = 0;
        double totalData = 0.0;

        try {
            Scanner ob = new Scanner(new File("./input/" + fileName));
            if (ob.hasNextLine()) {
                ob.nextLine(); // skipping header line - row 1
            }

            while (ob.hasNextLine()) {
                String line = ob.nextLine();
                String[] col = line.split(",");

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                Date parse = sdf.parse(col[1]);
                Calendar cal = Calendar.getInstance();
                cal.setTime(parse);

                totalMins+=Integer.parseInt(col[2]);
                totalData+= Double.parseDouble(col[3]);

                CellPhoneUsage cellPhoneUsage = map.getOrDefault(Integer.parseInt(col[0]), new CellPhoneUsage(new KeyComparator()));

                cellPhoneUsage.setEmployeeId(Integer.parseInt(col[0]));

                Map<String, Integer> minutesMap = cellPhoneUsage.getMinutesMap();
                String key = cal.get(Calendar.YEAR) + " " + theMonth(cal.get(Calendar.MONTH));
                Integer tempMinutes = minutesMap.getOrDefault(key, 0);
                minutesMap.put(key, tempMinutes + Integer.parseInt(col[2]));

                Map<String, Double> dataMap = cellPhoneUsage.getDataMap();
                Double tempData = dataMap.getOrDefault(key, 0.0);
                dataMap.put(key, tempData + Double.parseDouble(col[3]));

                cellPhoneUsage.setMinutesMap(minutesMap);
                cellPhoneUsage.setDataMap(dataMap);

                map.put(cellPhoneUsage.getEmployeeId(), cellPhoneUsage);

            }

        } catch (FileNotFoundException filenotfound) {
            System.out.println("Message : " + filenotfound.getMessage()); // Handle exception
        } catch (ParseException pe) {
            System.out.println("Invalid date");
        }

        CellPhoneUsageWrapper wrapper = new CellPhoneUsageWrapper();
        wrapper.setMap(map);
        wrapper.setTotaldata(totalData);
        wrapper.setTotalMinutes(totalMins);
        return wrapper;
    }

    public static String theMonth(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

    public static Integer theMonth(String month){
        Map<String, Integer> monthName = new HashMap<>();
        monthName.put("January", 0);
        monthName.put("February", 1);
        monthName.put("March", 2);
        monthName.put("April", 3);
        monthName.put("May", 4);
        monthName.put("June", 5);
        monthName.put("July", 6);
        monthName.put("August", 7);
        monthName.put("September", 8);
        monthName.put("October", 9);
        monthName.put("November", 10);
        monthName.put("December", 11);


        return monthName.get(month);
    }

    class KeyComparator implements Comparator<String>{

        @Override
        public int compare(String s1, String s2) {
            int result = s1.split(" ")[0].compareTo(s2.split(" ")[0]);
            if(result!=0){
                return result;
            }
            return theMonth(s1.split(" ")[1]) - theMonth(s2.split(" ")[1]);
        }
    }

}
