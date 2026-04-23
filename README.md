# java-currency-converter

> **Project 4 — DecodeLabs Industrial Training Kit | Batch 2026**
> An enterprise-grade Currency Converter built in Java, demonstrating precision arithmetic, input validation, and clean financial formatting.

---


## Project Overview

This project implements a **menu-driven currency conversion application** in Java. 
It is designed to simulate real-world financial software standards — using `BigDecimal` for precision, 
Banker's Rounding, cross-rate pivot logic, and defensive input handling.

---

## Features

| Feature | Implementation |
|---|---|
| Menu-driven UI | `do-while` loop + `switch` statement |
| Precision arithmetic | `BigDecimal` (never `double`) |
| Banker's Rounding | `RoundingMode.HALF_EVEN` |
| Cross-rate conversion | USD pivot (e.g. GBP → USD → INR) |
| Input validation | `try-catch` for `InputMismatchException` |
| Negative amount guard | Security Gate rejects negative values |
| Financial formatting | `printf` with `%,.2f` — 2 decimal places |
| 10 supported currencies | USD, EUR, GBP, INR, JPY, AUD, CAD, CHF, CNY, KWD |

---

## Supported Currencies

| Code | Currency | Symbol |
|---|---|---|
| USD | US Dollar | $ |
| EUR | Euro | € |
| GBP | British Pound | £ |
| INR | Indian Rupee | ₹ |
| JPY | Japanese Yen | ¥ |
| AUD | Australian Dollar | A$ |
| CAD | Canadian Dollar | C$ |
| CHF | Swiss Franc | Fr |
| CNY | Chinese Yuan | ¥ |
| KWD | Kuwaiti Dinar | KD |

---

## Getting Started

### Prerequisites

- Java JDK 8 or higher

### Run

```bash
# Compile
javac CurrencyConverter.java

# Run
java CurrencyConverter
```

### Sample Output

```
  ╔══════════════════════════════════════════════════════╗
  ║      FINANCIAL TRANSLATION ENGINE  v1.0              ║
  ║      Java Currency Converter – Project 4             ║
  ║      DecodeLabs | Batch 2026                         ║
  ╚══════════════════════════════════════════════════════╝

  ┌──────────────────────────────────────────────────────┐
  │                  CONVERSION MENU                     │
  ├──────────────────────────────────────────────────────┤
  │  1. USD  →  INR  (US Dollar to Indian Rupee)         │
  │  2. USD  →  EUR  (US Dollar to Euro)                 │
  │  3. USD  →  GBP  (US Dollar to British Pound)        │
  │  4. EUR  →  INR  (Euro to Indian Rupee)              │
  │  5. GBP  →  JPY  (British Pound to Japanese Yen)     │
  │  6. INR  →  USD  (Indian Rupee to US Dollar)         │
  │  7. Custom Conversion  (any supported currency)      │
  │  0. Exit                                             │
  └──────────────────────────────────────────────────────┘
```

---

## Key Concepts Demonstrated

- **Variables & Data Types** — `BigDecimal`, `String`, `Map`
- **User Input** — `Scanner` class
- **Control Flow** — `do-while`, `switch`, `if`
- **Exception Handling** — `try-catch`, `InputMismatchException`
- **Arithmetic Operations** — `multiply()`, `divide()`, `setScale()`
- **String Formatting** — `System.out.printf`, `%,.2f`
- **Collections** — `HashMap` for rates and symbols

---

## Project Structure

```
java-currency-converter/
│
├── CurrencyConverter.java    # Main source file
└── README.md                 # This file
```


## License

This project is part of the DecodeLabs Java Programming curriculum. For educational use only.
