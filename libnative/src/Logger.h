#pragma once

#include <sstream>
#include <string>

#include "ExporterMacro.h"

typedef void (*LoggerCallback) (const char *level, const char *file, const char *function, int line, const char *message);

#ifdef __cplusplus
extern "C" {
#endif
EXPORTER void loggerInit(LoggerCallback loggerCallback);
#ifdef __cplusplus
}
#endif
void logger(const char *level, const char *file, const char *function, int line, std::string message);

#define ILOG(level, msg) \
{ \
	std::stringstream a; \
	a << msg; \
	logger(level, __FILE__, __FUNCTION__, __LINE__, a.str()); \
}

#define ILOG_TRACE(msg) ILOG("TRACE", msg);
#define ILOG_DEBUG(msg) ILOG("DEBUG", msg);
#define ILOG_INFO(msg) ILOG("INFO", msg);
#define ILOG_WARN(msg) ILOG("WARN", msg);
#define ILOG_ERROR(msg) ILOG("ERROR", msg);


