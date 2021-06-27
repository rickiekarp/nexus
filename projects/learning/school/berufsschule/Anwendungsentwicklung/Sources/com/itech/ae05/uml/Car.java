package com.itech.ae05.uml;

/**
 * In the case of composition, the Engine is completely encapsulated by the Car.
 * There is no way for the outside world to get a reference to the Engine.
 * The Engine lives and dies with the car. With aggregation, the Car also performs
 * its functions through an Engine, but the Engine is not always an internal part of
 * the Car. Engines may be swapped, or even completely removed. Not only that,
 * but the outside world can still have a reference to the Engine, and tinker with it
 * regardless of whether it's in the Car.
 */
class CarA {
    private final Engine engine;

    //Composition
    CarA(EngineSpecs specs) {
        engine = new Engine(specs);
    }

    void move() {
        engine.work();
    }
}

final class CarB {
    private Engine engine;

    //Aggregation
    void setEngine(Engine engine) {
        this.engine = engine;
    }

    void move() {
        if (engine != null)
            engine.work();
    }
}