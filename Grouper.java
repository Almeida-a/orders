package orders;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Groups a list of orders by their products' age
 * Each order can be in multiple groups
 */
public class Grouper {

    /**
     * List of orders to be processed
     */
    private final Order[] orders;
    private final String[] rangesList;

    /**
     *
     * @param orders Orders to be processed by this class
     * @param ranges List of ranges, such as "1-3", "3-6", ..., ">12"
     */
    public Grouper(Order[] orders, String[] ranges) {
        this.orders = orders;
        rangesList = ranges;
    }

    public HashMap<String, Integer> group() {
        assert validRanges(): "Ranges are not valid!";

        Integer[][] rangeValues = extractRanges(rangesList);

        HashMap<String, Integer> countOrders = new HashMap<>();

        // For each range, count orders which have products created within that range
        for (int i = 0; i < rangeValues.length; i++) {
            int finalI = i;  // needs to be effectively final to be included in the filter

            countOrders.put(rangesList[finalI],
                    (int) Arrays.stream(orders).filter(
                            order -> isWithinRange(rangeValues[finalI], order)
                    ).count()
            );
        }

        return countOrders;
    }

    /**
     * @param ranges Range which the products' creation date are evaluated for being within
     * @param order  Order at hand
     * @return If any products at the order that are within the specified range
     */
    private boolean isWithinRange(Integer[] ranges, Order order) {

        LocalDateTime recentOrOnlyThreshold = LocalDateTime.now().minusMonths(ranges[0]);

        if (ranges.length == 1) {
            return order.getProductAges().stream().anyMatch(
                    dateTime -> dateTime.isBefore(recentOrOnlyThreshold)
            );
        }

        LocalDateTime olderThreshold = LocalDateTime.now().minusMonths(ranges[1]);

        return order.getProductAges().stream().anyMatch(
                dateTime -> dateTime.isBefore(recentOrOnlyThreshold) && dateTime.isAfter(olderThreshold)
        );
    }

    /**
     *
     * @return if the list is in valid format as well as semantically correct (not allowing ["1-3", "2-6", ...])
     */
    private boolean validRanges() {

        String ageThreshold = rangesList[rangesList.length - 1];

        Pattern nonLast = Pattern.compile("\\d-\\d"),
                last = Pattern.compile(">\\d");

        if (last.matcher(ageThreshold).matches())
            return false;

        // Ensure all but the last range follows the number-number pattern
        if (Arrays.stream(rangesList).anyMatch(range -> !nonLast.matcher(range).matches()))
            return false;

        Integer[][] rangeValues = extractRanges(rangesList);

        for (int i = 1; i < rangeValues.length; i++) {
            // Ensure the ranges do not overlap, if they do return false
            if (rangeValues[i-1][1] >= rangeValues[i][0])
                return false;
        }

        return true;
    }

    private Integer[][] extractRanges(String[] ranges) {

        return Arrays.stream(ranges).map(this::extractRange).toArray(Integer[][]::new);
    }

    private Integer[] extractRange(String rangeStr) {

        Pattern biggerThanPattern = Pattern.compile(">\\d"),
                rangePattern = Pattern.compile("\\d+-\\d+");

        if (rangePattern.matcher(rangeStr).matches())
            return new Integer[]{
                    Integer.valueOf(rangeStr.split("-")[0]),
                    Integer.valueOf(rangeStr.split("-")[1])
            };
        else if (biggerThanPattern.matcher(rangeStr).matches()) {
            return new Integer[] {
                    Integer.valueOf(rangeStr.split(">")[1])
            };
        }

        throw new IllegalArgumentException("Illegal pattern: "+rangeStr);

    }

}
