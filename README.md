The Programming Task for OOP

it has 6 Timers which all have slightly different ActionListeners to Start/Stop the Timer.

1 -> has an ActionListener that is used like an interface Overriding the actionPerformed function  
2 -> has an ActionListener which is implemented via a lambda function  
3 -> has an anonymous class as ActionListener  
4 -> has a non-static inner class as ActionListener
5 -> has a class in a seperate file as ActionListener
6 -> has an ActionListener which uses a static class  

they all kind of work the same, however due to weird implementation Timer 6 and Timer 5 dont properly display when they re not started yet and just show 00:00.  
