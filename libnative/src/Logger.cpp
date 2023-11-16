#include <iostream>

#include "Logger.h"

LoggerCallback loggerCallback;

void loggerInit(LoggerCallback loggerCallback_) {
	loggerCallback = loggerCallback_;
}

void logger(const char *level, const char *file, const char * function, int line, std::string message){
	if (loggerCallback != NULL) {
		loggerCallback(level, file, function, line, message.c_str());
	} else {
		std::cout << file << " (" << line << ") " << message << std::flush << std::endl;
	}
}

