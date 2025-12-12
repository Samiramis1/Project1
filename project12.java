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