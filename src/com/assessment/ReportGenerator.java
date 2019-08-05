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
        String cellPhoneFile = scanner.next();
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
        Map<Integer, List<CellPhoneUsage>> cellPhoneUsages = wrapper.getMap();

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


        dataLines.add(new String[]{"Cell Phone Usage per company"});
        for ( Map.Entry<String, List<Integer>> entry : cellPhoneMap.entrySet()) {
            List<Integer> empList = entry.getValue();
            dataLines.add(new String[]{"Cell Phone", entry.getKey()});
            dataLines.add(new String[]{"Employee Id", "Employee Name", "Model", "Purchase Date"});

            for(Integer empId: empList) {
                Employee employee = employeeMap.get(empId);
                dataLines.add(new String[]{String.valueOf(employee.getEmployeeId()), employee.getEmployeeName(), employee.getModel(), employee.getPurchaseDate()});
                dataLines.add(new String[]{"Minutes and Data Usage per month"});
                dataLines.add(new String[]{"Month & Year", "Minutes", "Data"});
                List<CellPhoneUsage> list = cellPhoneUsages.get(empId);

                for (CellPhoneUsage cellPhoneUsage : list) {
                    parse = sdf.parse(cellPhoneUsage.getDate());
                    c.setTime(parse);
                    dataLines.add(new String[]{c.get(Calendar.MONTH) + "  " + c.get(Calendar.YEAR), String.valueOf(cellPhoneUsage.getTotalMinutes()), String.valueOf(cellPhoneUsage.getTotalData())});
                }
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

    }
    private static String convertToCSV(String[] data) {
        return String.join(",", data);
    }

}
