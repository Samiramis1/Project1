int count = 0;
        for (int m = 0; m < MONTHS; m++) {
        for (int d = 0; d < DAYS; d++) {
        if (profitData[m][d][commIndex] > threshold) {
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
    }count++;
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
