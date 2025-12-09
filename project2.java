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
