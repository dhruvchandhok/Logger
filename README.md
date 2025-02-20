Objective: Develop a basic logging library that can be used by applications to log messages. The library should handle message logging efficiently and reliably, offering basic configuration options.

Key Requirements:
Driver Application should be able to Initialize the Library and log messages to the desired sink.

Logger has the following capabilities-
 - Accepts messages from client(s)
 - A logger would have one or more sinks associated with it. 
 - Supports defined message levels.
 - enriches message with current timestamp while directing message to a sink
 - Logger is initialized with a configuration eg:logger name, sink(s), buffer size.
 - Logger should support both sync and async logging. 
 - For Async logger buffer size would determine the maximum inflight messages.
 - Messages must be ordered. Messages should reach the sink in the order they were sent.
 - Should support writes from multiple-threads.
 - There shouldn’t be any data loss.

Sink:
- There can be various types of sink (file, stdout, database).  
- Sink has a destination.
- For this round you may create STDOUT sink, which would print the message to the console. 
- Sink has an associated log level. Any message with the level lower than the sink level should be discarded.

Message
 - has content which is of type string
 - has a level associated with it
 - You specify message content and level while sending a message


Log Level
DEBUG, INFO, WARN, ERROR, FATAL ; in order of priority. ERROR has higher priority than INFO.

Add test cases to demonstrate sync logging, async logging and concurrent logging requests

Logger configuration (see sample below)
 - Specifies all the details required to use the logger library.
 - Library can accept one or more configuration for an application

Example:
time format
logging level
sink type
Logger type sync/async
details required for sink (eg file location)); this depends on sink type.

Sample Config:
Ts_format: any format
log_level:INFO
logger_type:ASYNC
buffer_size:25
sink_type:STDOUT
 
Sample Output Log Entry
03-01-2024-09-30-00 [INFO] This is a sample log message. 

Sample test case:

Input:
Configuration of the logger is console logging with Info level.
log.info(“Info message”)
log.warn(“Warn message”)
log.debug(“Debug message”)
log.error(“Error message”)

Output:
03-01-2024-09-30-00 [INFO] Info message
03-01-2024-09-30-01 [WARN] Warn message
03-01-2024-09-30-02 [ERROR] Error message
