import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
            "July","August","September","October","November","December"
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

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }

        long bestProfit = -99999;
        int bestIndex = 0;

        for (int CommCounter = 0; CommCounter < COMMS; CommCounter++) {
            long sum = 0;
            for (int d = 0; d < DAYS; d++) {
                sum += profitData[month][d][CommCounter];
            }
            if (sum > bestProfit) {
                bestProfit = sum;
                bestIndex = CommCounter;
            }
        }

        return commodities[bestIndex] + " " + bestProfit;
    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS) {
            return -99999;
        }

        int sum = 0;
        int dayIndex = day - 1;
        for (int CommCounter = 0; CommCounter < COMMS; CommCounter++) {
            sum += profitData[month][dayIndex][CommCounter];
        }
        return sum;
    }

    public static int commodityProfitInRange(String commodity, int fromDay, int toDay) {
        int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(commodity)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1 || fromDay < 1 || toDay < 1 || fromDay > toDay || fromDay > DAYS || toDay > DAYS) {
            return -99999;
        }
        int total = 0;
        int fromIndex = fromDay - 1;
        int toIndex = toDay - 1;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = fromIndex; d <= toIndex; d++) {
                total += profitData[m][d][commIndex];
            }
        }
        return total;
    }

    public static int bestDayOfMonth(int month) {
        if (month < 0 || month >= MONTHS) {
            return -1;
        }
        long bestProfit = -99999;
        int bestDay = -1;
        for (int d = 0; d < DAYS; d++) {
            int total = 0;
            for (int CommCounter = 0; CommCounter < COMMS; CommCounter++) {
                total += profitData[month][d][CommCounter];
            }
            if (total > bestProfit) {
                bestProfit = total;
                bestDay = d + 1;
            }
        }
        return bestDay;
    }

    public static String bestMonthForCommodity(String commodity) {
        int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(commodity)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1) {
            return "INVALID_COMMODITY";
        }
        long bestProfit = -99999;
        int bestMonthIndex = 0;
        for (int m = 0; m < MONTHS; m++) {
            long sum = 0;
            for (int d = 0; d < DAYS; d++) {
                sum += profitData[m][d][commIndex];
            }
            if (sum > bestProfit) {
                bestProfit = sum;
                bestMonthIndex = m;
            }
        }

        return months[bestMonthIndex] + " " + bestProfit;
    }

    public static int consecutiveLossDays(String commodity) {
        int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(commodity)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1) {
            return -1;
        }

        int longest = 0;
        int current = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                int value = profitData[m][d][commIndex];
                if (value < 0) {
                    current++;
                    if (current > longest) {
                        longest = current;
                    }
                } else {
                    current = 0;
                }
            }
        }

        return longest;
    }

    public static int daysAboveThreshold(String commodity, int threshold) {
        int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(commodity)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1) {
            return -1;
        }
        int count = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profitData[m][d][commIndex] > threshold) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS) {
            return -99999;
        }
        int prevTotal = 0;
        for (int CommCounter = 0; CommCounter < COMMS; CommCounter++) {
            prevTotal += profitData[month][0][CommCounter];
        }
        int maxSwing = 0;
        for (int d = 1; d < DAYS; d++) {
            int currentTotal = 0;
            for (int CommCounter = 0; CommCounter < COMMS; CommCounter++) {
                currentTotal += profitData[month][d][CommCounter];
            }
            int diff = currentTotal - prevTotal;
            if (diff < 0) {
                diff = -diff;
            }
            if (diff > maxSwing) {
                maxSwing = diff;
            }
            prevTotal = currentTotal;
        }
        return maxSwing;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        int idx1 = -1;
        int idx2 = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(c1)) {
                idx1 = i;
            }
            if (commodities[i].equals(c2)) {
                idx2 = i;
            }
        }
        if (idx1 == -1 || idx2 == -1) {
            return "INVALID_COMMODITY";
        }
        long total1 = 0;
        long total2 = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                total1 += profitData[m][d][idx1];
                total2 += profitData[m][d][idx2];
            }
        }
        if (total1 == total2) {
            return "Equal";
        } else if (total1 > total2) {
            long diff = total1 - total2;
            return c1 + " is better by " + diff;
        } else {
            long diff = total2 - total1;
            return c2 + " is better by " + diff;
        }
    }

    public static String bestWeekOfMonth(int month) {
        if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }
        long bestProfit = -99999;
        int bestWeek = 1;
        for (int week = 1; week <= 4; week++) {
            int startDayIndex = (week - 1) * 7;
            int endDayIndex = startDayIndex + 6;
            long weekTotal = 0;
            for (int d = startDayIndex; d <= endDayIndex; d++) {
                int dayTotal = 0;
                for (int CommCounter = 0; CommCounter < COMMS; CommCounter++) {
                    dayTotal += profitData[month][d][CommCounter];
                }
                weekTotal += dayTotal;
            }
            if (weekTotal > bestProfit) {
                bestProfit = weekTotal;
                bestWeek = week;
            }
        }
        return "Week " + bestWeek + ": " + bestProfit;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}
