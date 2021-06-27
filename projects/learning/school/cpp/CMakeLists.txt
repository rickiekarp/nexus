cmake_minimum_required (VERSION 3.8)
project (CPP)

#version number
set (CPP_VERSION_MAJOR 1)
set (CPP_VERSION_MINOR 0)

#include the subdirectory containing our libs
add_subdirectory (Chapter1)
include_directories(Chapter1)
#indicate the entry point for the executable
add_executable (CPP Chapter1 main.cpp)

# Indicate which libraries to include during the link process.
target_link_libraries (CPP Chapter1)

install (TARGETS CPP DESTINATION bin)
