package com.assessment;

import com.assessment.parser.CellPhoneFileProcessor;
import com.assessment.parser.CellPhoneUsageFileProcessor;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ReportGenerator {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the file name of first file :");
        String cellPhoneFile = scanner.next();
        System.out.print("Enter the file name of second file :");
        String cellPhoneUsageFile = scanner.next();


        Pair<Map<Integer, Employee>, Map<String, List<Integer>> > pair = new CellPhoneFileProcessor().processor(cellPhoneFile);
        CellPhoneUsageWrapper wrapper = new CellPhoneUsageFileProcessor().processor(cellPhoneUsageFile);

        try {
            new ReportGenerator().generate(pair, wrapper);
        }
        catch (Exception e){
            System.out.println("Invalid data");
        }

    }


    void generate(Pair<Map<Integer, Employee>, Map<String, List<Integer>>> pair, CellPhoneUsageWrapper wrapper) throws ParseException, IOException {

        Map<Integer,Employee> employeeMap = pair.a;
        Map<String, List<Integer>> cellPhoneMap = pair.b;
        Map<Integer, CellPhoneUsage> cellPhoneUsages = wrapper.getMap();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date parse = null;
        try {
            parse = sdf.parse("18/08/2012");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(parse);


        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[]{"Cell Phone Usage Report"});
        dataLines.add(new String[]{"Report Run Date", String.valueOf(new Date())});
        dataLines.add(new String[]{"Number of Phones", String.valueOf(wrapper.getMap().size())});
        dataLines.add(new String[]{"Number of models", String.valueOf(cellPhoneMap.size())});
        dataLines.add(new String[]{"Total Minutes", String.valueOf(wrapper.getTotalMinutes())});
        dataLines.add(new String[]{"Total Data", String.valueOf(wrapper.getTotaldata())});
        dataLines.add(new String[]{"Average Minutes", String.valueOf(wrapper.getTotalMinutes() / wrapper.getMap().size())});
        dataLines.add(new String[]{"Average Data", String.valueOf(wrapper.getTotaldata() / wrapper.getMap().size())});
        // empty line
        dataLines.add(new String[]{""});

        dataLines.add(new String[]{"Cell Phone Usage per company"});
        for (Map.Entry<String, List<Integer>> entry : cellPhoneMap.entrySet()) {
            List<Integer> empList = entry.getValue();
            dataLines.add(new String[]{"Cell Phone", entry.getKey()});

            for(Integer empId: empList) {
                dataLines.add(new String[]{"Employee Id", "Employee Name", "Model", "Purchase Date"});
                Employee employee = employeeMap.get(empId);
                dataLines.add(new String[]{String.valueOf(employee.getEmployeeId()), employee.getEmployeeName(), employee.getModel(), employee.getPurchaseDate()});
                dataLines.add(new String[]{"Minutes and Data Usage per month"});
                dataLines.add(new String[]{"Month & Year", "Minutes", "Data"});
                CellPhoneUsage cellPhoneUsage = cellPhoneUsages.get(empId);

                for (Map.Entry<String, Integer> monthUsage : cellPhoneUsage.getMinutesMap().entrySet()) {

                    dataLines.add(new String[]{monthUsage.getKey(), String.valueOf(monthUsage.getValue()), String.valueOf(cellPhoneUsage.getDataMap().get(monthUsage.getKey()))});
                }
                // empty line
                dataLines.add(new String[]{""});

            }

        }
        generateCSV(dataLines);


    }

    public static void generateCSV(List<String[]> dataLines) throws IOException {
        File csvOutputFile = new File("./output/CellPhoneReport.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(ReportGenerator::convertToCSV)
                    .forEach(pw::println);
        }
        System.out.println("Report Generated.. Check in output folder");

    }
    private static String convertToCSV(String[] data) {
        return String.join(",", data);
    }

}
