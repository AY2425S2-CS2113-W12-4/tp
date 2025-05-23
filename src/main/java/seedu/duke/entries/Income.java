package seedu.duke.entries;

//@@author limleyhooi

import seedu.duke.exception.MTException;

/**
 * Represents an income with description, amount and date.
 */
public class Income {
    protected String description;
    protected double amount;
    protected String date;

    //@@author limleyhooi
    /**
     * Creates an Income with mandatory description and amount.
     * Uses "no date" if date is empty/null.
     *
     * @param description Income description (cannot be empty)
     * @param amount Income amount (must be positive)
     * @param date Income date (optional)
     */
    public Income(String description, double amount, String date) {
        this.description = description;
        this.amount = amount;
        this.date = (date == null || date.isEmpty()) ? "no date" : date;
    }
    //@@author


    //@@author limleyhooi
    /**
     * @return Income description
     */
    public String getDescription() {
        return this.description;
    }
    //@@author

    //@@author limleyhooi
    /**
     * @return Income amount
     */
    public double getAmount() {
        return this.amount;
    }
    //@@author

    //@@author limleyhooi
    /**
     * @return Income date of entry
     */
    public String getDate() {
        return this.date;
    }
    //@@author

    //@@author limleyhooi
    /**
     * @return Formatted string representation of income
     */
    @Override
    public String toString() {
        return String.format("Income: %s $%.2f [%s]",
                this.getDescription(), this.getAmount(),this.getDate());
    }
    //@@author

    //@@author EdwinTun98
    public static Income parseString(String line) throws MTException {
        if (!line.startsWith("Income: ")) {
            throw new MTException("Invalid income entry format.");
        }

        try {
            String trimmed = line.substring("Income: ".length()).trim();
            int dollarIndex = trimmed.lastIndexOf('$');
            int bracketIndex = trimmed.lastIndexOf('[');

            if (dollarIndex == -1 || bracketIndex == -1) {
                throw new MTException("Missing amount or date in income entry.");
            }

            String description = trimmed.substring(0, dollarIndex).trim();
            String amountStr = trimmed.substring(dollarIndex + 1, bracketIndex).trim();
            String date = trimmed.substring(bracketIndex + 1, trimmed.length() - 1).trim();

            double amount = Double.parseDouble(amountStr);

            return new Income(description, amount, date);
        } catch (Exception e) {
            throw new MTException("Failed to parse income entry: " + e.getMessage());
        }
    }
    //@@author
}
//@@author
