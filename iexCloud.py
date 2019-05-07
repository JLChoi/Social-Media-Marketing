'''
Python file for handling the IEX Cloud API
'''
from urllib.request import urlopen
import json

iex_base = "https://cloud.iexapis.com/stable/stock/"
iex_base2 = "&symbols="
iex_api_key = "pk_49480859e7124b878222b5774909b72e"
earnings_base = "/earnings?token="
price_base = "/chart/date/"
price_base2 = "?chartByDay=true&token="

def get_earnings(query):
    try:
        url = iex_base + query + earnings_base + iex_api_key + iex_base2 + query
        data = json.load(urlopen(url))
        for earnings in data['earnings']:
            eps = earnings['actualEPS']
            when = earnings['fiscalPeriod']
            year_ago = earnings['yearAgo']
        print("Current period:", when)
        print("Current earnings per share:", eps)
        print("EPS a year ago:", year_ago)
    except:
        print("Query was not a valid stock")

def get_price(company, date1, date2):
    try:
        close_prices = []
        date = ""
        url = ""
        url_head = iex_base + company + price_base
        url_tail = price_base2 + iex_api_key + iex_base2 + company
        start_year = int(date1[0:4])
        start_month = int(date1[4:6])
        start_day = int(date1[6:8])
        end_year = int(date2[0:4])
        end_month = int(date2[4:6])
        end_day = int(date2[6:8])
        upper_bound = 0;
        while start_year <= end_year:
            if start_year == end_year:
                while start_month <= end_month:
                    if start_month == end_month:
                        while start_day <= end_day:
                            if start_day == end_day:
                                url = url_head + date2 + url_tail
                                data = json.load(urlopen(url))
                                if len(data) != 0:
                                    close_prices.append(data[0]['close'])
                                else:
                                    if (end_day - 2) < 10:
                                        date = date2[0:6] + "0" + str(end_day - 2)
                                    else:
                                        date = date2[0:6] + str(end_day - 2)
                                    url = url_head + date + url_tail
                                    data = json.load(urlopen(url))
                                    close_prices.append(data[0]['close'])
                                return close_prices
                            else:
                                if start_month < 10:
                                    if start_day < 10:
                                        date = str(start_year) + "0" + str(start_month) + "0" + str(start_day)
                                    else:
                                        date = str(start_year) + "0" + str(start_month) + str(start_day)
                                else:
                                    if start_day < 10:
                                        date = str(start_year) + str(start_month) + "0" + str(start_day)
                                    else:
                                        date = str(start_year) + str(start_month) + str(start_day)
                                url = url_head + date + url_tail
                                data = json.load(urlopen(url))
                                if len(data) != 0:
                                    close_prices.append(data[0]['close'])
                                else:
                                    filler_day = int(date[6:8])
                                    if (filler_day - 2) < 10:
                                        date = date[0:6] + "0" + str(filler_day - 2)
                                    else:
                                        date = date[0:6] + str(filler_day - 2);
                                    url = url_head + date + url_tail
                                    data = json.load(urlopen(url))
                                    close_prices.append(data[0]['close'])
                                start_day += 1
                    else:
                        if start_month == (1 or 3 or 5 or 7 or 8 or 10 or 12):
                            upper_bound = 31
                        elif start_month == 2:
                            upper_bound = 28
                        else:
                            upper_bound = 30

                        while start_day <= upper_bound:
                            if start_month < 10:
                                if start_day < 10:
                                    date = str(start_year) + "0" + str(start_month) + "0" + str(start_day)
                                else:
                                    date = str(start_year) + "0" + str(start_month) + str(start_day)
                            else:
                                if start_day < 10:
                                    date = str(start_year) + str(start_month) + "0" + str(start_day)
                                else:
                                    date = str(start_year) + str(start_month) + str(start_day)
                            url = url_head + date + url_tail
                            data = json.load(urlopen(url))
                            if len(data) != 0:
                                close_prices.append(data[0]['close'])
                            else:
                                filler_day = int(date[6:8])
                                if (filler_day - 2) < 10:
                                    date = date[0:6] + "0" + str(filler_day - 2)
                                else:
                                    date = date[0:6] + str(filler_day - 2);
                                url = url_head + date + url_tail
                                data = json.load(urlopen(url))
                                close_prices.append(data[0]['close'])
                            start_day += 1
                        start_day = 1
                        start_month += 1
            else:
                while start_month <= 12:
                    if start_month == (1 or 3 or 5 or 7 or 8 or 10 or 12):
                        upper_bound = 31
                    elif start_month == 2:
                        upper_bound = 28
                    else:
                        upper_bound = 30

                    while start_day <= upper_bound:
                        if start_month < 10:
                            if start_day < 10:
                                date = str(start_year) + "0" + str(start_month) + "0" + str(start_day)
                            else:
                                date = str(start_year) + "0" + str(start_month) + str(start_day)
                        else:
                            if start_day < 10:
                                date = str(start_year) + str(start_month) + "0" + str(start_day)
                            else:
                                date = str(start_year) + str(start_month) + str(start_day)
                            url = url_head + date + url_tail
                            data = json.load(urlopen(url))
                            if len(data) != 0:
                                close_prices.append(data[0]['close'])
                            else:
                                filler_day = int(date[6:8])
                                if (filler_day - 2) < 10:
                                    date = date[0:6] + "0" + str(filler_day - 2)
                                else:
                                    date = date[0:6] + str(filler_day - 2);
                                url = url_head + date + url_tail
                                data = json.load(urlopen(url))
                                close_prices.append(data[0]['close'])
                        start_day += 1
                    start_day = 1
                    start_month += 1
                start_month = 1
                start_year += 1
    except:
        print("Invalid company or date")


#print("Adidas", get_price("addyy", "20190402", "20190413"))
print("Nike", get_price("nke", "20190407", "20190413"))
'''print("Dunkin Donuts", get_price("dnkn", "20190402", "20190413"))
print("Wendy's", get_price("wen", "20190402", "20190413"))
print("Disney", get_price("dis", "20190402", "20190413"))
print("Viacom", get_price("viab", "20190402", "20190413"))
'''
