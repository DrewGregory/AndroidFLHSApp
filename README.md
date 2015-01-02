AndroidFLHSApp Directory Organization
==============
This is the source code for the unpackaged Android App.
In the app folder...
The res directory (resources) contains the XML and really all other media for the APK besides the java class files.
Values, Layouts, and Menus are all stored there and written in XML.
The res--dpi folders are for storing all the drawables (PNG's, JPG's, GIF's). These are all the pictures!
The java folder stores all the class files. These are the actual programs in the app. 
The main package is com.flhs. This package contains another package, com.flhs.utils. This package has all the utility classes
that were were made for the classes in com.flhs.
The com.flhs classes are the Activities. Each activity is a seperate section of the app. To learn about activities.
To learn more about how this all works, go to the Android Developer Website and start learning: http://developer.android.com/training/index.html
How to use Parse Config... (remember, this affects ALL USERS!!!)
================================================
Sign into parse with the push@student.bcsdny.org account.
Go to "Core", then "Config"
The LunchMenuURL Parameter is the URL we want to use for the Lunch Menu (http://bcsdny.org/documents.cfm?id=14.608&subid=630)
The ScheduleType Parameter is either "Normal", "One Hour Delay", "Two Hour Delay", and "Special Day". Special Day will use the SpecialDayCourses and SpecialDayTimes Parmaters to print the schedule content (below). All else will show "No School Today:" and whatever you put as the ScheduleType Parameter.
The SpecialDayCourses Paramter is a JSON Array that must have the brackets ([]) surrounding all content, quotation marks surrounding every item (""), and a comma after every item (including quotation marks) except for the very last item. Don't worry: Just write Course 1-8 and it will replace that name with whatever the user set in their CourseSelectorActivity as long as you wrote "Course" (capital "C"), " " (space), and a number between 1 - 8. ex: ["Course 4", "Course 5", "Course 6"]. They must be in sequential order.
The SpecialDayTimes Parameter is also a JSON Array and must follow the same specifications as the Special Day Courses Parameter ([] "" ,) but stores the times for each corresponding course. It should be the same length as the Special Day Courses Parameter (same number of items seperated by commas), and it should be written with the opening time (i.e. "7:40") separated by a dash surrounded by a space on eacn side (" - ") with the closing time (i.e. "8:35"). Ex: ["7:40 - 8:35", "8:40 - 9:35", "9:40 - 10:35"]
The WhatDay Parameter is also a JSON Array and again follows the same specifications as both the SpecialDayCourses and SpecialDayTimes Parameters ([] "" ,) and stores the planned day in our rotating cycle for the date. The convention for each item is "mm/dd:D" where "mm" is two-digit month (i.e. "01" being January, "10" being October), "dd" is two-digit date (i.e. "01" being the 1st, "20" being the 20th) and "D" being the day in our cycle. Possible options for the day are: "A", "B", "C", "1", "2", "3", "4," "5, "Adv E", "Adv 5", "Collab E", and "Collab 5". Ex: "01/23:Adv E" (The Advisory E Day Schedule will be printed on January 23rd).

