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
1)Sign into parse database with the push@student.bcsdny.org account.
  Go to Heroku.com, then mLab add-on
  
2)Go to the \_GlobalConfig collection. Edit the collection (pencil on the right-hand corner)

3)The LunchMenuURL parameter is the URL we want to use for the Lunch Menu (http://bcsdny.org/documents.cfm?id=14.608&subid=630)

4)The WhatDay parameter is a JSON Array of the day codes for each date. The format is the following: "MM/dd:<daycode>" Example: "04/06:E" This designates an E day on April 6th. This format is year independent: it does not regard what the year is.

5)Possible day code formats (to the right of the colon)
["A","B","C","D","E","1","2","3","4","5","ADV","CLB","1HD","2HD"]
If you write a day code that is NOT A-E or 1-5, you must designate the letter of the day as the LAST CHARACTER.
Example: "09/27:ADV4" -- This designates an Advisory Day 4 on September 27th.

6)Special days:Some schedules will arise that do not fit one of the day codes stated above. For example, you may have an assembly. In order to store a special day schedule, you create your OWN PARAMETER that has a variable name that begins with the character "\~". Next, you separate the possible schedules for that day into "tracks." For example, half the school may have a schedule that has them go to the assembly at 9:15 - 9:45, while the other half of the school may have a schedule that has them go to the assembly at 9:50 - 10:20. Another example could be that people have different lunch blocks. When you name a track, begin the value with the character "/." Between track values, list the schedule's courses immediately followed by their corresponding time slots. Here is an example: 
![Screenshot should be here.](https://github.com/DrewGregory/AndroidFLHSApp/blob/master/mLabParseConfig.PNG?raw=true)
In this example, I created a special schedule entitled "Theo." The two tracks are entitled "Track 1" and "Track 2". The schedule for each track is as follows:
![Transcribed schedule should be here](https://github.com/DrewGregory/AndroidFLHSApp/blob/master/Transcribed%20schedule.PNG?raw=true)

Users will be allowed to choose which track in a similar manner with which they can choose their lunch.
Of course, this is a bizarre schedule. This is to show the how flexible your custom schedules can be!
In order to reference this schedule in your WhatDayArray, merely call "\~" + [Name] + [Day Letter]
For example: "10/12:~Theo4" would assign the "Theo" schedule on October 12th, designating the courses as a 4 day. 

7)The purpose of the day letter/number on the end of the schedule is to allow the user's custom schedule names to replace the course names. For example, they may have "Band" on 4 days but "Chorus" on D days. 
