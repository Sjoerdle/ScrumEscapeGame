# Scrum Escape Building

A terminal-based dungeon crawler adventure game built with Java, combining RPG elements with educational Scrum methodology questions. Escape from the mysterious Scrum building by solving puzzles, fighting monsters, and demonstrating your knowledge of Agile practices!

![Game Logo](https://img.shields.io/badge/Java-Educational_Game-blue?style=for-the-badge&logo=java)
![Version](https://img.shields.io/badge/Version-1.0-green?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Complete-success?style=for-the-badge)

## Game Overview

**Scrum Escape Building** is an educational dungeon crawler where players navigate through emoji-rich environments, battle monsters by answering Scrum-related questions, and collect items to aid their escape. The game features multiple question types, strategic gameplay elements, and a rich ASCII art presentation.

### Key Features

- **Multi-room dungeon exploration** with emoji-based graphics
- **Monster encounters** requiring Scrum knowledge to defeat
- **Three question types**: Multiple Choice, Open Questions, and Puzzle Matching
- **Inventory system** with health potions and special items
- **Key-door mechanics** for progression
- **Save/Load functionality** for persistent gameplay
- **Joker system** for strategic advantages
- **Custom background music** composed specifically for the game
- **Template Method pattern** for consistent monster encounters

## Technical Architecture

### Design Patterns Implemented

- **Template Method Pattern**: Standardized monster encounter flow
- **Strategy Pattern**: Different question types with unified interface
- **Observer Pattern**: Player state change notifications
- **Factory Pattern**: Dynamic hint provider creation
- **Interface Segregation**: Modular item and usable interfaces
- **Dependency Inversion**: Abstracted hint management system

### Project Structure

```
src/ScrumGame/src/main/java/
├── Monsters/           # Monster types and encounter logic
├── Vragen/            # Question system (Multiple Choice, Open, Puzzle)
├── hints/             # Hint system with factory pattern
├── items/             # Inventory items and usables
├── jokers/            # Special power-up items
├── player/            # Player class with observer pattern
├── rooms/             # Room management and emoji mapping
├── org/game/          # Core game engine and state management
├── ui/                # User interface and console handling
├── audio/             # Sound system integration
└── tests/             # Mock and stub testing classes
```

## Educational Objectives

This project was developed as part of a **first-year Software Engineering** curriculum to demonstrate:

- **Object-Oriented Programming** principles
- **Design Pattern** implementation
- **Clean Code** practices and SOLID principles
- **Team collaboration** and version control
- **Game development** fundamentals
- **Educational content integration**

## Getting Started

### Prerequisites

- **Java 11** or higher
- **Maven** for dependency management
- **Terminal/Console** that supports emoji rendering
- **Audio support** for background music

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/scrum-escape-building.git
   cd scrum-escape-building
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the game**

   **Option A: Using the batch file (Windows)**
    - Edit the `.bat` file in the project root
    - Update the path to match your local user directory
    - Run the batch file to start the game

   **Option B: Using Maven**
   ```bash
   mvn exec:java -Dexec.mainClass="org.game.Main"
   ```

### Alternative IDE Setup

1. Import as Maven project in your IDE
2. Run `Main.java` from `src/main/java/org/game/Main.java`
3. Ensure console supports emoji rendering for best experience

### Important Setup Note

**For Windows users**: The project includes a `.bat` file for easy execution. You must update the user path in this file to match your local system before running the game.

## How to Play

### Basic Controls

- **W, A, S, D**: Movement (Up, Left, Down, Right)
- **1, 2, 3**: Use inventory items
- **I**: Open inventory
- **Q**: Quit game

### Game Elements

| Symbol | Meaning |
|--------|---------|
| Player | Player |
| Monster | Monster |
| Key | Key |
| Door | Door |
| Health | Health Potion |
| Magic | Magic Item |
| Exit | Exit |
| Wall | Wall |

### Gameplay Mechanics

1. **Explore rooms** by moving through the dungeon
2. **Fight monsters** by correctly answering Scrum-related questions
3. **Collect keys** to unlock doors and progress
4. **Manage health** - wrong answers cause damage
5. **Use items strategically** - health potions and monster-skip items
6. **Save progress** automatically when completing rooms

### Question Types

- **Multiple Choice**: Select the correct answer from options
- **Open Questions**: Type the exact answer
- **Puzzle Matching**: Match Scrum terms with their definitions

## Audio Features

The game includes **custom-composed background music** created by team member Benjamin van Teeseling, featuring:

- **Dynamic room-specific soundtracks**
- **Menu background music**
- **Credits music sequence**
- **Seamless audio transitions**

## Testing Framework

The project includes a comprehensive testing system with:

- **Mock objects** for behavior verification
- **Stub implementations** for controlled testing
- **Unit tests** for core game mechanics
- **Integration tests** for system interactions

Run tests with:
```bash
mvn test
```

## Development Team

**Scrum Escape Building** was collaboratively developed by:

- **Stefaan Molenaar**
- **Benjamin van Teeseling**
- **Roderick Schravendeel**
- **Sjoerd Lunshof**

*Developed during first year of Software Engineering studies*

## Learning Outcomes

This project demonstrates proficiency in:

### Programming Fundamentals
- **Java Programming**: Advanced OOP concepts and best practices
- **Object-Oriented Design**: Inheritance, polymorphism, encapsulation, and abstraction
- **Exception Handling**: Robust error management and input validation
- **File I/O Operations**: Resource loading and save/load functionality

### SOLID Principles Implementation
- **Single Responsibility Principle (SRP)**: Each class has one clear purpose
- **Open/Closed Principle (OCP)**: Extensible design for new question types and monsters
- **Liskov Substitution Principle (LSP)**: Proper inheritance hierarchies in monster and question systems
- **Interface Segregation Principle (ISP)**: Focused interfaces like `ItemInfo`, `Usable`, and `HintProvider`
- **Dependency Inversion Principle (DIP)**: Abstract dependencies in hint management system

### Design Patterns
- **Template Method Pattern**: Standardized monster encounter workflow
- **Strategy Pattern**: Interchangeable question types with unified interface
- **Observer Pattern**: Player state change notifications and UI updates
- **Factory Pattern**: Dynamic hint provider creation based on context
- **Command Pattern**: Input handling and action processing

### Software Engineering Practices
- **Clean Code**: Readable, maintainable, and well-documented code
- **Software Architecture**: Layered design with clear separation of concerns
- **Team Development**: Collaborative coding and version control with Git
- **Testing**: Mock objects, stubs, and unit testing implementation
- **Code Organization**: Logical package structure and modular design

### Specialized Skills
- **Game Development**: Interactive entertainment software creation
- **Educational Technology**: Learning through gamification
- **Audio Integration**: Sound system implementation and management
- **Console Programming**: Terminal-based UI with emoji graphics
- **State Management**: Complex game state persistence and loading

## Technical Dependencies

- **JLine**: Terminal handling and console management
- **Java Sound API**: Audio playback system
- **Maven**: Build automation and dependency management
- **JUnit**: Testing framework (if tests are expanded)

## Project Highlights

- **Educational Integration**: Seamlessly combines entertainment with learning
- **Professional Architecture**: Enterprise-level design patterns
- **Rich User Experience**: Emoji graphics and custom audio
- **Robust Error Handling**: Comprehensive input validation
- **Persistent Gameplay**: Save/load functionality
- **Extensible Design**: Easy to add new question types and features

## License

This project was created for educational purposes as part of a Software Engineering curriculum. All rights reserved to the development team.

## Contributing

This project was completed as part of academic coursework. While not actively maintained, it serves as a portfolio piece demonstrating first-year software engineering capabilities.

---

*"Escape the building, master the methodology!"*