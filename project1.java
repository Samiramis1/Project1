import java.io.*;
import java.util.*;

public class project1 {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };
    static int[][][] profitData = new int[MONTHS][DAYS][COMMS];

    public static void loadData() {
        profitData = new int[MONTHS][DAYS][COMMS];
        for (int m = 0; m < MONTHS; m++) {
            String monthName = months[m];
            String fileName = "Data_Files/" + monthName + ".txt";
            File file = new File(fileName);
            if (!file.exists()) {
                continue;
            }
            Scanner sc;
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                continue; // skip if the month file has a problem
            }
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.length() == 0) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    //bad line
                    continue;
                }
                String dayStr = parts[0].trim();
                String commStr = parts[1].trim();
                String profitStr = parts[2].trim();
                //validate dayStr, only digits
                boolean validDay = true;
                if (dayStr.length() == 0) {
                    validDay = false;
                } else {
                    for (int i = 0; i < dayStr.length(); i++) {
                        char ch = dayStr.charAt(i);
                        if (ch < '0' || ch > '9') {
                            validDay = false;
                            break;
                        }
                    }
                }
                if (!validDay) {
                    continue;
                }
                //validate profitStr, signed integer
                boolean validProfit = true;
                if (profitStr.length() == 0) {
                    validProfit = false;
                } else {
                    int startIdx = 0;
                    char first = profitStr.charAt(0);
                    if (first == '+' || first == '-') {
                        if (profitStr.length() == 1) {
                            validProfit = false;
                        } else {
                            startIdx = 1;
                        }
                    }
                    if (validProfit) {
                        for (int i = startIdx; i < profitStr.length(); i++) {
                            char ch = profitStr.charAt(i);
                            if (ch < '0' || ch > '9') {
                                validProfit = false;
                                break;
                            }
                        }
                    }
                }
                if (!validProfit) {
                    continue;
                }
                int day = Integer.parseInt(dayStr);
                int profit = Integer.parseInt(profitStr);

                int commIndex = -1;
                for (int i = 0; i < COMMS; i++) {
                    if (commodities[i].equals(commStr)) {
                        commIndex = i;
                        break;
                    }
                }
                if (commIndex == -1) {
                    continue; // unknown commodity
                }
                if (day < 1 || day > DAYS) {
                    continue;
                }
                profitData[m][day - 1][commIndex] = profit;
            }
            sc.close();
        }
    }
}