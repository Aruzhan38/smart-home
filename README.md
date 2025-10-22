🏠 Smart Home Automation System

A Java project implementing Decorator and Facade design patterns

📘 Overview

Smart Home Automation is a console-based Java application that simulates a smart home hub.
It allows users to control devices (lights, thermostats, music systems, cameras, etc.),
activate predefined modes like Night, Party, and LeavingHome,
and even manage all devices in a specific room using simple JSON commands.

This project demonstrates clean, modular Java architecture with design patterns:

🧩 Decorator — adds extra capabilities to devices (voice control, remote access, energy saving).
🏗️ Facade — simplifies complex subsystems (device loading, mode activation, room management) into a unified interface.

🧠 Key Features - Feature	Description
💡 Device Control	- Send commands to individual devices via JSON (e.g., turn on/off).
🎛️ Decorator Pattern - Dynamically add features like VoiceControl, RemoteAccess, or EnergySaving.
🏠 Room Management -	Group devices by rooms (livingroom, bedroom, kitchen, etc.) and control them together.
🎉 Modes -	Predefined environment modes: Night, Party, LeavingHome.
🏭 Factory Pattern -	Different brands (Philips, Xiaomi, etc.) are created via factories.
🌐 JSON Configuration	- Devices and modes are loaded from devices.json and modes.json.
🎯 Clean Architecture -	Separate packages for core, device, decorator, factory, app, and config.
