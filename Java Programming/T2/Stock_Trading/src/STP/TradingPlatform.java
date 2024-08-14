package STP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Class to simulate market data
class MarketData {
    private HashMap<String, Double> stockPrices;

    public MarketData() {
        stockPrices = new HashMap<>();
        stockPrices.put("APPLE", 231.19);
        stockPrices.put("GOOGLE", 191.75);
        stockPrices.put("AMAZON", 198.70);
        stockPrices.put("MICROSOFT", 461.32);
        stockPrices.put("FLIPKART", 500.32);
    }

    public double getPrice(String symbol) {
        return stockPrices.getOrDefault(symbol, 0.0);
    }

    public void updatePrice(String symbol, double newPrice) {
        stockPrices.put(symbol, newPrice);
    }

    public void printMarketData() {
        System.out.println("Current Market Prices:");
        for (String symbol : stockPrices.keySet()) {
            System.out.println(symbol + ": $" + stockPrices.get(symbol));
        }
    }
}

// Class to represent a stock
class Stock {
    private String symbol;
    private int quantity;
    private double averagePrice;

    public Stock(String symbol, int quantity, double averagePrice) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void addQuantity(int quantity, double price) {
        double totalCost = this.quantity * this.averagePrice + quantity * price;
        this.quantity += quantity;
        this.averagePrice = totalCost / this.quantity;
    }

    public void removeQuantity(int quantity) {
        this.quantity -= quantity;
    }
}

// Class to manage the user's portfolio
class Portfolio {
    private ArrayList<Stock> stocks;
    private double cashBalance;

    public Portfolio(double initialCash) {
        stocks = new ArrayList<>();
        cashBalance = initialCash;
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public void buyStock(String symbol, int quantity, double price) {
        for (Stock stock : stocks) {
            if (stock.getSymbol().equals(symbol)) {
                stock.addQuantity(quantity, price);
                cashBalance -= quantity * price;
                return;
            }
        }
        stocks.add(new Stock(symbol, quantity, price));
        cashBalance -= quantity * price;
    }

    public void sellStock(String symbol, int quantity, double price) {
        Stock toRemove = null;
        for (Stock stock : stocks) {
            if (stock.getSymbol().equals(symbol)) {
                if (stock.getQuantity() < quantity) {
                    System.out.println("Not enough stock to sell.");
                    return;
                } else {
                    stock.removeQuantity(quantity);
                    cashBalance += quantity * price;
                    if (stock.getQuantity() == 0) {
                        toRemove = stock;
                    }
                    break;
                }
            }
        }
        if (toRemove != null) {
            stocks.remove(toRemove);
        }
    }

    public void printPortfolio() {
        System.out.println("Portfolio:");
        for (Stock stock : stocks) {
            System.out.println(stock.getSymbol() + ": " + stock.getQuantity() + " shares at an average price of $" + stock.getAveragePrice());
        }
        System.out.println("Cash Balance: $" + cashBalance);
    }

    public double getTotalValue(MarketData marketData) {
        double totalValue = cashBalance;
        for (Stock stock : stocks) {
            totalValue += stock.getQuantity() * marketData.getPrice(stock.getSymbol());
        }
        return totalValue;
    }
}

// Main class to simulate the trading platform

public class TradingPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MarketData marketData = new MarketData();
        Portfolio portfolio = new Portfolio(10000000.00); // Initial cash balance of $1,00,00,000

        while (true) {
            System.out.println("\nStock Trading Platform\n");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Portfolio Value");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    marketData.printMarketData();
                    break;
                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.next();
                    System.out.print("Enter quantity to buy: ");
                    int buyQuantity = scanner.nextInt();
                    double buyPrice = marketData.getPrice(buySymbol);
                    if (buyPrice > 0 && portfolio.getCashBalance() >= buyQuantity * buyPrice) {
                        portfolio.buyStock(buySymbol, buyQuantity, buyPrice);
                        System.out.println("Bought " + buyQuantity + " shares of " + buySymbol + " at $" + buyPrice + " each.");
                    } else {
                        System.out.println("Not enough cash or invalid stock symbol.");
                    }
                    break;
                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.next();
                    System.out.print("Enter quantity to sell: ");
                    int sellQuantity = scanner.nextInt();
                    double sellPrice = marketData.getPrice(sellSymbol);
                    if (sellPrice > 0) {
                        portfolio.sellStock(sellSymbol, sellQuantity, sellPrice);
                        System.out.println("Sold " + sellQuantity + " shares of " + sellSymbol + " at $" + sellPrice + " each.");
                    } else {
                        System.out.println("Invalid stock symbol.");
                    }
                    break;
                case 4:
                    portfolio.printPortfolio();
                    break;
                case 5:
                    double totalValue = portfolio.getTotalValue(marketData);
                    System.out.println("Total portfolio value: $" + totalValue);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}