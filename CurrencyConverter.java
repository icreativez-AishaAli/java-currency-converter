import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class CurrencyConverter {

    // ── Static exchange rates (USD as pivot currency) ─────────────────────────
    // Rates: 1 USD = X <currency>
    private static final Map<String, BigDecimal> RATES = new HashMap<>();

    static {
        RATES.put("USD", new BigDecimal("1.0"));
        RATES.put("EUR", new BigDecimal("0.9235"));
        RATES.put("GBP", new BigDecimal("0.7923"));
        RATES.put("INR", new BigDecimal("83.50"));
        RATES.put("JPY", new BigDecimal("154.72"));
        RATES.put("AUD", new BigDecimal("1.5263"));
        RATES.put("CAD", new BigDecimal("1.3612"));
        RATES.put("CHF", new BigDecimal("0.9012"));
        RATES.put("CNY", new BigDecimal("7.2401"));
        RATES.put("KWD", new BigDecimal("0.3072"));
    }

    // Currency symbols for display
    private static final Map<String, String> SYMBOLS = new HashMap<>();

    static {
        SYMBOLS.put("USD", "$");
        SYMBOLS.put("EUR", "€");
        SYMBOLS.put("GBP", "£");
        SYMBOLS.put("INR", "₹");
        SYMBOLS.put("JPY", "¥");
        SYMBOLS.put("AUD", "A$");
        SYMBOLS.put("CAD", "C$");
        SYMBOLS.put("CHF", "Fr");
        SYMBOLS.put("CNY", "¥");
        SYMBOLS.put("KWD", "KD");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printBanner();

        int choice;
        do {
            printMenu();

            // ── Read menu choice with try-catch (Buffer Trap defense) ─────────
            choice = -1;
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine(); // clear stuck token from buffer
                System.out.println("\n  [ERROR] Invalid input. Please enter a number from the menu.\n");
                continue;
            }

            switch (choice) {
                case 1:
                    performConversion(scanner, "USD", "INR");
                    break;
                case 2:
                    performConversion(scanner, "USD", "EUR");
                    break;
                case 3:
                    performConversion(scanner, "USD", "GBP");
                    break;
                case 4:
                    performConversion(scanner, "EUR", "INR");
                    break;
                case 5:
                    performConversion(scanner, "GBP", "JPY");
                    break;
                case 6:
                    performConversion(scanner, "INR", "USD");
                    break;
                case 7:
                    performCustomConversion(scanner);
                    break;
                case 0:
                    System.out.println("\n  ╔══════════════════════════════════════╗");
                    System.out.println("  ║  Engine shutting down. Goodbye!      ║");
                    System.out.println("  ╚══════════════════════════════════════╝\n");
                    break;
                default:
                    System.out.println("\n  [ERROR] Invalid menu option. Please choose 0–7.\n");
            }

        } while (choice != 0);

        scanner.close();
    }

    
    private static void printBanner() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════════╗");
        System.out.println("  ║      FINANCIAL TRANSLATION ENGINE  v1.0              ║");
        System.out.println("  ║      Java Currency Converter – Project 4             ║");
        System.out.println("  ║      DecodeLabs | Batch 2026                         ║");
        System.out.println("  ╚══════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void printMenu() {
        System.out.println("  ┌──────────────────────────────────────────────────────┐");
        System.out.println("  │                  CONVERSION MENU                     │");
        System.out.println("  ├──────────────────────────────────────────────────────┤");
        System.out.println("  │  1. USD  →  INR  (US Dollar to Indian Rupee)         │");
        System.out.println("  │  2. USD  →  EUR  (US Dollar to Euro)                 │");
        System.out.println("  │  3. USD  →  GBP  (US Dollar to British Pound)        │");
        System.out.println("  │  4. EUR  →  INR  (Euro to Indian Rupee)              │");
        System.out.println("  │  5. GBP  →  JPY  (British Pound to Japanese Yen)     │");
        System.out.println("  │  6. INR  →  USD  (Indian Rupee to US Dollar)         │");
        System.out.println("  │  7. Custom Conversion  (any supported currency)      │");
        System.out.println("  │  0. Exit                                             │");
        System.out.println("  └──────────────────────────────────────────────────────┘");
    }

    // ── Perform a preset conversion ────────────────────────────────────────────
    private static void performConversion(Scanner scanner, String from, String to) {
        System.out.printf("%n  Converting: %s  →  %s%n", from, to);
        System.out.printf("  Enter amount in %s: ", from);

        BigDecimal amount = readAmount(scanner);
        if (amount == null) return;   // validation failed; already printed error

        BigDecimal result = convert(amount, from, to);
        printResult(amount, from, result, to);
    }

    // ── Custom conversion: user picks source & target ─────────────────────────
    private static void performCustomConversion(Scanner scanner) {
        System.out.println("\n  Supported currencies: " + RATES.keySet());

        scanner.nextLine(); // consume leftover newline

        System.out.print("  Enter source currency code (e.g. USD): ");
        String from = scanner.nextLine().trim().toUpperCase();

        System.out.print("  Enter target currency code (e.g. INR): ");
        String to = scanner.nextLine().trim().toUpperCase();

        if (!RATES.containsKey(from)) {
            System.out.printf("%n  [ERROR] Currency '%s' is not supported.%n%n", from);
            return;
        }
        if (!RATES.containsKey(to)) {
            System.out.printf("%n  [ERROR] Currency '%s' is not supported.%n%n", to);
            return;
        }

        System.out.printf("  Enter amount in %s: ", from);
        BigDecimal amount = readAmount(scanner);
        if (amount == null) return;

        BigDecimal result = convert(amount, from, to);
        printResult(amount, from, result, to);
    }

    // ── Core conversion logic: cross-rate via USD pivot ───────────────────────
    private static BigDecimal convert(BigDecimal amount, String from, String to) {
        if (from.equals(to)) return amount;

        BigDecimal rateFrom = RATES.get(from); // 1 USD = rateFrom <from>
        BigDecimal rateTo   = RATES.get(to);   // 1 USD = rateTo   <to>

        // Step 1 – convert source amount to USD (intermediate value)
        // USD value = amount / rateFrom
        BigDecimal usdValue = amount.divide(rateFrom, 10, RoundingMode.HALF_EVEN);

        // Step 2 – convert USD to target currency
        // result = usdValue * rateTo
        BigDecimal result = usdValue.multiply(rateTo);

        // Round to 2 decimal places using Banker's Rounding
        return result.setScale(2, RoundingMode.HALF_EVEN);
    }

    // ── Input reader with validation ───────────────────────────────────────────
    private static BigDecimal readAmount(Scanner scanner) {
        double rawAmount;
        try {
            rawAmount = scanner.nextDouble();
        } catch (java.util.InputMismatchException e) {
            scanner.nextLine(); // clear buffer
            System.out.println("\n  [ERROR] Invalid amount. Please enter a numeric value.\n");
            return null;
        }

        // Security Gate: reject negative amounts
        if (rawAmount < 0) {
            System.out.println("\n  [ERROR] Amount cannot be negative. Please enter a positive value.\n");
            return null;
        }

        // Convert via String constructor for exact BigDecimal representation
        return new BigDecimal(String.valueOf(rawAmount));
    }

    // ── Formatted output (Financial Polish) ───────────────────────────────────
    private static void printResult(BigDecimal amount, String from,
                                    BigDecimal result, String to) {
        String symFrom = SYMBOLS.getOrDefault(from, "");
        String symTo   = SYMBOLS.getOrDefault(to, "");

        System.out.println();
        System.out.println("  ┌──────────────────────────────────────────────────────┐");
        System.out.printf ("  │  %-10s  %s%,.2f %s%n",
                "Amount:", symFrom, amount.doubleValue(), from);
        System.out.printf ("  │  %-10s  %s%,.2f %s%n",
                "Converted:", symTo, result.doubleValue(), to);
        System.out.printf ("  │  %-10s  1 %s = %,.6f %s%n",
                "Rate:", from,
                RATES.get(to).divide(RATES.get(from), 6, RoundingMode.HALF_EVEN).doubleValue(),
                to);
        System.out.println("  └──────────────────────────────────────────────────────┘");
        System.out.println();
    }
}
