add_library (Chapter1
  Speaker.h
  Speaker.cpp)

install (TARGETS Chapter1 DESTINATION bin)
install (FILES Speaker.h DESTINATION include)
