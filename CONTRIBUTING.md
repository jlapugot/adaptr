# Contributing to AdaptR

Thank you for your interest in contributing to AdaptR! This document provides guidelines and instructions for contributing.

## Code of Conduct

Be respectful, constructive, and professional in all interactions.

## How to Contribute

### Reporting Bugs

Before creating bug reports, please check existing issues. When creating a bug report, include:

- A clear and descriptive title
- Steps to reproduce the behavior
- Expected behavior vs actual behavior
- Java version and operating system
- Code samples demonstrating the issue

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, include:

- A clear and descriptive title
- Detailed description of the proposed functionality
- Examples of how the feature would be used
- Why this enhancement would be useful

### Pull Requests

1. **Fork the repository** and create your branch from `main`

```bash
git checkout -b feature/your-feature-name
```

2. **Make your changes**
   - Follow the existing code style
   - Write clear, concise commit messages
   - Add tests for new functionality
   - Update documentation as needed

3. **Test your changes**

```bash
mvn clean test
```

4. **Ensure code quality**

```bash
mvn clean verify
```

5. **Submit a pull request**
   - Fill in the pull request template
   - Link any related issues
   - Request review from maintainers

## Development Setup

### Prerequisites

- JDK 17 or higher
- Maven 3.6+
- Git

### Building the Project

```bash
git clone https://github.com/jlapugot/adaptr.git
cd adaptr
mvn clean install
```

### Running Tests

```bash
mvn test
```

### Code Style

- Use 4 spaces for indentation (no tabs)
- Follow standard Java naming conventions
- Keep methods focused and concise
- Add JavaDoc comments for public APIs
- Write descriptive variable and method names

### Writing Tests

- Use JUnit 5 for all tests
- Use AssertJ for assertions
- Test classes should be in the same package as the code they test
- Test method names should clearly describe what they test
- Aim for high code coverage, especially for core functionality

Example test structure:

```java
@Test
void shouldDoSomethingWhenCondition() {
    // Given
    Map<String, Object> data = new HashMap<>();
    data.put("key", "value");

    // When
    Result result = AdaptR.adapt(data, Interface.class);

    // Then
    assertThat(result.getKey()).isEqualTo("value");
}
```

## Project Structure

```
adaptr/
├── src/
│   ├── main/java/com/example/adaptr/
│   │   ├── AdaptR.java              # Main entry point
│   │   ├── AdaptRInvocationHandler.java  # Core logic
│   │   └── Mapping.java             # Annotation
│   └── test/java/com/example/adaptr/
│       └── AdaptRTest.java          # Tests
├── pom.xml
└── README.md
```

## Commit Message Guidelines

- Use the present tense ("Add feature" not "Added feature")
- Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
- Limit the first line to 72 characters or less
- Reference issues and pull requests after the first line

Examples:

```
Add support for nested object adaptation

Implement type conversion for primitive types

Fix NPE when map key is missing

Closes #123
```

## Release Process

Releases are managed by project maintainers:

1. Version is updated in `pom.xml`
2. Release notes are prepared
3. Tag is created: `git tag -a v1.0.0 -m "Release 1.0.0"`
4. GitHub Actions workflow publishes to Maven Central

## Questions?

Feel free to open an issue for any questions about contributing.

## License

By contributing, you agree that your contributions will be licensed under the Apache License 2.0.
