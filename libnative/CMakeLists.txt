cmake_minimum_required(VERSION 3.13)
project(native LANGUAGES "CXX")

set (CMAKE_CXX_STANDARD 11)

file (GLOB SOURCES_ROOT "src/*.cpp")
file (GLOB SOURCES_AUDIO_LINUX_PULSE "src/audio/linux/pulse/*.cpp")
file (GLOB SOURCES_CODEC_OPUS "src/codec/opus/*.cpp")

list(APPEND SOURCES
	${SOURCES_ROOT}
	${SOURCES_AUDIO_LINUX_PULSE}
        ${SOURCES_CODEC_OPUS}
	${SOURCES_NDI}
)

add_library(${PROJECT_NAME} SHARED ${SOURCES})
target_include_directories(${PROJECT_NAME}
        PRIVATE
                include
                src
)

set_target_properties(${PROJECT_NAME} PROPERTIES
        CXX_STANDARD 14
        CXX_STANDARD_REQUIRED ON
        CXX_EXTENSIONS OFF
)

if (LINUX)
        target_link_libraries(${PROJECT_NAME} pulse pulse-simple)
endif()

target_link_libraries(${PROJECT_NAME} 
	pulse 
	pulse-simple
        opus
)


