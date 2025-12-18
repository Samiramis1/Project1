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
