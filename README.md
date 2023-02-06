# PowerPlay-season-Code

Below is the error list from the REV IDE I cannot find out
------------------------------------------------------------------------------------------------------------------
Build started at Sun Feb 05 2023 18:10:33 GMT-0600 (Central Standard Time)
powerPlay/Autonomous/RightSideAutonomous.java line 151, column 17: ERROR: missing method body, or declare abstract
powerPlay/Autonomous/RightSideAutonomous.java line 282, column 17: ERROR: missing method body, or declare abstract
powerPlay/Autonomous/RightSideAutonomous.java line 287, column 17: ERROR: missing method body, or declare abstract
powerPlay/Autonomous/RightSideAutonomous.java line 292, column 17: ERROR: missing method body, or declare abstract
powerPlay/Autonomous/RightSideAutonomous.java line 294, column 32: ERROR: cannot find symbol
  symbol:   variable pos
  location: class powerPlay.Autonomous.RightSideAutonomous
powerPlay/Autonomous/RightSideAutonomous.java line 299, column 17: ERROR: missing method body, or declare abstract
powerPlay/Autonomous/RightSideAutonomous.java line 304, column 17: ERROR: missing method body, or declare abstract
powerPlay/Autonomous/RightSideAutonomous.java line 309, column 17: ERROR: missing method body, or declare abstract
powerPlay/Autonomous/RightSideAutonomous.java line 314, column 17: ERROR: missing method body, or declare abstract

Build FAILED!

Build finished in 1.4 seconds
------------------------------------------------------------------------------------------------------------------

methods should do exactly what they say EXEPT:
1. turning methods may not be 45 or 90.
2. strafe may work

reminders:
1. dist in in encoder ticks
2. spd can only be 0 to 1400. (speed range of bot in ticks)
3. write robots actions in place of the // comment.
