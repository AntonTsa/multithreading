<h2>Task 1</h2>
I wrote a program that displays the time elapsed since the program was started every second.
The second thread of the same program displays the message 5 seconds have passed every 5 seconds.

<h2>Task 2</h2>
I wrote program that prints a string of numbers from 1 to n to the console, but with some values replaced:

if the number is divisible by 3 - print fizz
if the number is divisible by 5 - print buzz
if the number is divisible by 3 and 5 at the same time - print fizzbuzz
For example, for n = 15, the following result is expected: 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz.

The program should be multithreaded, and work with 4 threads:

Thread A calls fizz() to check if the number is divisible by 3, and if so, add the string fizz to the output queue for thread D.
Thread B calls buzz() to check if the number is divisible by 5, and if so, add the string buzz to the output queue for thread D.
Thread C calls fizzbuzz() to check if the number is divisible by 3 and 5 at the same time, and if so, add the string fizzbuzz to the output queue for thread D.
Thread D calls the number() method to print the next number from the queue, if there is such a number to print.