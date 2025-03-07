# Price Formatter Service

## Project Description

**Price Formatter Service** is a simple web service built with Spring Boot and Java 17 that parses and formats an input string representing a price. The service exposes two REST API endpoints:

1. **GET /api/currencies** – Returns a list of available currencies (minimum required: ILS and USD).
2. **POST /api/formatprice** – Accepts a payload with a number as a string and a currency as a string. The input price is given in cents (e.g., "100" means 1 unit including a 19% tax). The API response returns the following values:
    - **value**: The price as a number (e.g., "100" → 1, "101" → 1.01, "110" → 1.10). The number should be without trailing zeros if there are no cents.
    - **formattedWithCurrency**: The formatted price with the currency symbol as a string (e.g., "100" → "1$", "101" → "1.01$", "110" → "1.10$", "1000000" → "1,000,000$"). For prices over 1000, commas should be used to separate thousands, and a dot indicates cents. In case of ILS, the currency symbol must appear on the left (e.g., "1000000" → "₪1,000,000").
    - **formattedWithoutCurrency**: The formatted price without the currency symbol (e.g., "100" → "1", "101" → "1.01", "110" → "1.10", "1000000" → "1,000,000"). The formatting rules for thousands and cents are the same as above.
    - **netPrice**: The price without tax.
    - **vatAmount**: The VAT amount such that `netPrice + vatAmount = full price`.

> **Note:** The service does not include a UI. All API responses are returned in JSON.

## Technical Details

- **Language:** Java 17
- **Framework:** Spring Boot
- **Build Tool:** Gradle
- **Data Format:** JSON

## API Endpoints

### GET /api/currencies

Returns a list of available currencies.

**Example Response:**

```json
["ILS", "USD"]
 ```

### POST /api/formatprice
Accepts a JSON payload with the following fields:

amount: A string representing the price in cents (e.g., "100" means 1.00 unit).
currency: A string representing the currency (e.g., "ILS" or "USD").
Example Request:
```json
{
  "amount": "1000000",
  "currency": "ILS"
}
```
**Example Response:**
```json
{
  "value": 10000,
  "formattedWithCurrency": "₪10,000",
  "formattedWithoutCurrency": "10,000",
  "netPrice": 8403.36,
  "vatAmount": 1596.64
}
```
- value: The full price as a number (formatted without trailing zeros).
-  formattedWithCurrency: The full price formatted with the appropriate currency symbol.
-  formattedWithoutCurrency: The price formatted without the currency symbol.
-  netPrice: The price without tax.
-  vatAmount: The VAT amount (so that netPrice + vatAmount equals the full price).