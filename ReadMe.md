# Breakout

## Description

A project as part of the course FOP (functional object-oriented programming) at the TU Darmstadt.

This is a reimplementation of the game Breakout using LWJGL and a given template.

## Features

* Stick movement
* Ball movement
* Ball collides with other entities (i.e. Border and Stick)
* Pause game on shortcut
* Koop-Multiplayer
* Custom graphics
* Moveable blocks
* Ball speed increases over time
* Particle effects (flames for the stick)
* Pause game
* Multiple maps
* Items (including temporarily ones)
* Sounds
    1. Effects (like item item pickup)
    2. Background music

### Items

* Slower/Faster bal
* Bigger/Smaller stick
* Smash-Ball (ball destroys everything on it's way instantly without bouncing back)
* SlowMo (makes temporarily everything slower including music)
* Additional ball (Adds a new ball to the game)
* Health point (Adds a new health point)

### Sounds

* Background music loop
* Audio clips
    1. Item pickup
    2. Ball hits stick
    3. Ball hits block

## Setup

1. Import the project
2. Include the eea.jar, slick.jar and all jars in log4j/ and lwjgl/jar
3. Set the Breakout class as the main class
4. Add a "-Djava.library.path=ABSOLUTE_PATH_TO_LWGL_NATIVE" as vm startup argument

## Available keyboard shortcuts

| State | Input | Effect |
| --- | ---|---|
| Gameplay | Left Arrow | Moves the stick left |
| Gameplay | Right Arrow | Moves the stick right |
| Gameplay | P | Pauses the game |
| Gameplay | Space | Starts the game on a new game |
| Gameplay | A | Moves left for Player 2 (Multiplayer) |
| Gameplay | D | Moves right for Player 2 (Multiplayer) |

## Credits

### Sounds

* [Background music](https://www.looperman.com/loops/detail/93477/drums-and-rust-by-flsouto-free-170bpm-industrial-drum-loop)
royalty-free
