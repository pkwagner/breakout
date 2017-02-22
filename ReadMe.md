# Breakout

## Description

A project as part of the course FOP (functional object-oriented programming) at the TU Darmstadt.

This is a reimplementation of the game Breakout using LWJGL and a given template.

## Features

* Stick movement
* Ball movement
* Ball collides with other entities (i.e. Border and Stick)
* Pause game on escape

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
| Gameplay | Escape | Pauses the game |