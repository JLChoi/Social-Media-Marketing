# Social-Media-Marketing

Project for NETS-150 by Kunaal Chaudhari, Jonathan Choi, and Tiffany Moi

The following provides background about the program files, setup, operation, and interpretation.
For our analysis, please see Analysis.pdf.

Setup (Python 3):
Install the following packages

	- numpy
	- matplotlib
	- oauth2
	- tweepy

Operation:

	1. Run dataViz file
	2. Type company name as appears in list
	3. Type start date and end date such that start date is before end date
	   both fall between April 2, 2019 and April 20, 2019 and format is 
	   YYYYMMDD
	4. View resulting graphs. Top graph displays share price and Twitter activity
	   over the time period. Bottom graph displays percent change in share price and 
	   Twitter activity over time period.
	5. View Granger Causality results. Test iterates through possible lags. For each lag
	   p-values for likelihood of influence are given for different tests.


Resources:

	- matplotlib documentation
	- statsmodels documentation
	- tweety API
	- iex API
