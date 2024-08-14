import requests
import pandas as pd

# Replace 'your_api_key' with your actual Alpha Vantage API key
API_KEY = 'RKG1OPYHJX3UIZ1Q'
BASE_URL = 'https://www.alphavantage.co/query'

def get_stock_data(symbol):
    """Fetch real-time stock data from Alpha Vantage API."""
    params = {
        'function': 'TIME_SERIES_INTRADAY',
        'symbol': symbol,
        'interval': '1min',
        'apikey': API_KEY
    }
    response = requests.get(BASE_URL, params=params)
    data = response.json()
    if "Time Series (1min)" in data:
        df = pd.DataFrame(data['Time Series (1min)']).transpose()
        df = df.astype(float)
        return df
    else:
        print("Error fetching data for symbol:", symbol)
        return None

class Portfolio:
    def __init__(self):
        self.stocks = {}
    
    def add_stock(self, symbol, shares):
        if symbol in self.stocks:
            self.stocks[symbol] += shares
        else:
            self.stocks[symbol] = shares
        print(f"Added {shares} shares of {symbol} to the portfolio.")
    
    def remove_stock(self, symbol, shares):
        if symbol in self.stocks:
            self.stocks[symbol] -= shares
            if self.stocks[symbol] <= 0:
                del self.stocks[symbol]
            print(f"Removed {shares} shares of {symbol} from the portfolio.")
        else:
            print(f"Stock {symbol} not found in the portfolio.")
    
    def track_performance(self):
        portfolio_value = 0
        print("\nPortfolio Performance:")
        for symbol, shares in self.stocks.items():
            stock_data = get_stock_data(symbol)
            if stock_data is not None:
                latest_price = stock_data['4. close'].iloc[0]
                total_value = latest_price * shares
                portfolio_value += total_value
                print(f"{symbol}: {shares} shares @ ${latest_price:.2f} = ${total_value:.2f}")
        print(f"\nTotal Portfolio Value: ${portfolio_value:.2f}")

def main():
    portfolio = Portfolio()
    
    while True:
        print("\nStock Portfolio Tracker")
        print("1. Add Stock")
        print("2. Remove Stock")
        print("3. Track Performance")
        print("4. Exit")
        choice = input("Choose an option: ")
        
        if choice == '1':
            symbol = input("Enter the stock symbol (e.g., AAPL): ").upper()
            shares = int(input("Enter the number of shares: "))
            portfolio.add_stock(symbol, shares)
        
        elif choice == '2':
            symbol = input("Enter the stock symbol (e.g., AAPL): ").upper()
            shares = int(input("Enter the number of shares to remove: "))
            portfolio.remove_stock(symbol, shares)
        
        elif choice == '3':
            portfolio.track_performance()
        
        elif choice == '4':
            print("Exiting the portfolio tracker.")
            break
        
        else:
            print("Invalid option. Please try again.")

if __name__ == "__main__":
    main()
