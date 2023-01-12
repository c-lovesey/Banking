# Banking

This is a banking application for use by the teller creted in Java.

To run the application download the code and launch the BankingApplication.java file.
This will show the user a menu with various actions they can take.
they can create an account for a user, create a user, view an account, register a business, create a direct debit and update the balance.




Software Inefficiencies:
In order to save the data csv files were used, originally we had objects which were saved to an array to store the accounts created and hold saved accounts,
we moved away from this as the reading and writing process was causing some errors. Towards the end of the development cycle we had tried to implement a proper
save read and write methods, however due to the time we had left we decided to keep the inefficient way of reading the data.

Missing features.
there is currently no way to add the charges or generate intrest within the Business and ISA accounts.
everything is setup to add the charges, however due to the time limit we were unable to fully implement it.
The intrest is implemented but the task was to create an average balance over a year, this would require us to store the last time the intrest was added,
the number of times the balance was updated along with the days between each update, but due to our method of storing we were unable to add a feature which dynamically
adds columns to rows without redesigning the whole system(a clever work around may have worked by using the \n to find the end of the line and doing calculations with that)
As a result of this the acerage balance we tried to create actuall is the average balance added to the account so this way of generating intrest does not work



Contributors:
Chadrak
Chris
Hassan
