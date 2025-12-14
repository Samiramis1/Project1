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
