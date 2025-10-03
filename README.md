# AdaptR

[![Maven Central](https://img.shields.io/maven-central/v/io.github.jlapugot/adaptr.svg)](https://central.sonatype.com/artifact/io.github.jlapugot/adaptr)
[![Build Status](https://github.com/jlapugot/adaptr/workflows/CI/badge.svg)](https://github.com/jlapugot/adaptr/actions)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

AdaptR is a lightweight Java library that dynamically adapts `Map<String, Object>` instances to Java interfaces at runtime using `java.lang.reflect.Proxy`. Say goodbye to boilerplate mapping code!

## Features

- **Zero Boilerplate**: No need to write mapping code or DTOs
- **Dynamic Adaptation**: Uses Java's dynamic proxy for runtime type adaptation
- **Custom Mapping**: Use `@Mapping` annotation to map methods to specific map keys
- **Convention over Configuration**: Automatically derives keys from getter method names
- **Lightweight**: No external dependencies (only reflection API)
- **Type-Safe**: Works with strongly-typed interfaces

## Installation

### Maven

```xml
<dependency>
    <groupId>io.github.jlapugot</groupId>
    <artifactId>adaptr</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Gradle

```gradle
implementation 'io.github.jlapugot:adaptr:0.1.0'
```

## Quick Start

### 1. Define Your Interface

```java
public interface PersonDTO {
    String getName();

    @Mapping("age_in_years")
    Integer getAge();

    Boolean isActive();
}
```

### 2. Create Your Data Map

```java
Map<String, Object> personData = new HashMap<>();
personData.put("name", "John Doe");
personData.put("age_in_years", 30);
personData.put("active", true);
```

### 3. Adapt and Use

```java
PersonDTO person = AdaptR.adapt(personData, PersonDTO.class);

System.out.println(person.getName());    // "John Doe"
System.out.println(person.getAge());     // 30
System.out.println(person.isActive());   // true
```

## How It Works

AdaptR uses Java's `Proxy` API to create dynamic implementations of your interfaces:

1. **Method Name Convention**: Getter methods like `getName()` automatically map to the `name` key in your map
2. **Custom Mapping**: Use `@Mapping("custom_key")` to override the default key derivation
3. **Boolean Getters**: Methods like `isActive()` map to the `active` key
4. **Type Safety**: Your interface defines the contract, ensuring type-safe access

### Key Derivation Rules

| Method Name | Map Key (default) | With `@Mapping` |
|-------------|-------------------|-----------------|
| `getName()` | `name` | Custom key |
| `getFullName()` | `fullName` | Custom key |
| `isActive()` | `active` | Custom key |
| `getAge()` | `age` | `@Mapping("age_in_years")` → `age_in_years` |

## Advanced Usage

### Working with Nested Data

```java
public interface AddressDTO {
    String getStreet();
    String getCity();
}

public interface PersonDTO {
    String getName();

    @Mapping("address")
    Map<String, Object> getAddressData();
}

Map<String, Object> personData = Map.of(
    "name", "Jane Doe",
    "address", Map.of(
        "street", "123 Main St",
        "city", "Springfield"
    )
);

PersonDTO person = AdaptR.adapt(personData, PersonDTO.class);
AddressDTO address = AdaptR.adapt(person.getAddressData(), AddressDTO.class);
```

### Handling Missing Keys

If a key doesn't exist in the map, the method will return `null`:

```java
Map<String, Object> data = new HashMap<>();
data.put("name", "John");

PersonDTO person = AdaptR.adapt(data, PersonDTO.class);
person.getName();  // "John"
person.getAge();   // null (key not in map)
```

## Why AdaptR?

### Comparison with Other Mapping Libraries

| Feature | AdaptR | MapStruct | ModelMapper | Orika |
|---------|--------|-----------|-------------|-------|
| **Setup Complexity** | ✅ Zero config | ⚠️ Annotation processor setup | ✅ Simple | ⚠️ Complex config |
| **Build Time** | ✅ Instant | ❌ Slower (code generation) | ✅ Instant | ✅ Fast |
| **Runtime Performance** | ✅ Fast (direct map lookup) | ✅ Fastest (compile-time) | ⚠️ Slower (reflection) | ⚠️ Slower (reflection) |
| **Learning Curve** | ✅ Minimal | ⚠️ Moderate | ⚠️ Moderate | ❌ Steep |
| **Use Case** | Map → Interface | POJO → POJO | POJO → POJO | POJO → POJO |
| **Dependencies** | ✅ Zero | ⚠️ Annotation processor | ⚠️ Runtime lib | ⚠️ Runtime lib + plugins |
| **Code Generation** | ❌ No | ✅ Yes | ❌ No | ⚠️ Optional |
| **Jar Size** | ✅ ~10 KB | ⚠️ ~200 KB | ⚠️ ~400 KB | ⚠️ ~700 KB |

### When to Choose AdaptR

**Choose AdaptR when you:**
- Work primarily with `Map<String, Object>` data (JSON, config, database results)
- Want zero dependencies and minimal overhead
- Need rapid prototyping without build-time code generation
- Prefer interface-based contracts over POJO classes
- Want type-safe access to dynamic data structures
- Value simplicity over extensive mapping features

**Choose MapStruct when you:**
- Need maximum runtime performance for POJO-to-POJO mapping
- Have complex nested object transformations
- Want compile-time validation of mappings
- Work with large enterprise applications

**Choose ModelMapper when you:**
- Need automatic deep object graph mapping
- Have complex inheritance hierarchies
- Want convention-based automatic mapping
- Can accept reflection-based performance overhead

### Key Advantages

1. **No Code Generation** - Unlike MapStruct, AdaptR works immediately without annotation processors or build plugins. Change your interface, run your code—no rebuild required.

2. **Map-First Design** - While other libraries focus on POJO-to-POJO mapping, AdaptR is specifically designed for Map-to-Interface adaptation, making it perfect for:
   - REST API responses (Jackson/Gson parsed maps)
   - Database query results (JDBC result maps)
   - Configuration files (YAML/JSON parsed maps)
   - Dynamic data structures

3. **Interface-Driven** - Work with lightweight interfaces instead of heavyweight DTOs/POJOs. No need to maintain separate classes with fields, getters, and setters.

4. **Zero Dependencies** - Just one small JAR (~10 KB) with no transitive dependencies. Perfect for microservices, lambdas, or any environment where dependency bloat matters.

5. **Instant Feedback** - No compilation delays. Define your interface, adapt your map, and go. Ideal for rapid development and prototyping.

6. **Type Safety Without Boilerplate** - Get compile-time type checking on your interface methods without writing mapping code or maintaining POJO classes.

## Use Cases

- **REST API Responses**: Adapt JSON-parsed maps to strongly-typed interfaces
- **Database Results**: Convert query results to domain objects
- **Configuration Files**: Map configuration data to typed interfaces
- **Testing**: Create mock objects from test data maps
- **Legacy Code**: Bridge between loosely-typed and strongly-typed code
- **Microservices**: Lightweight alternative to heavy mapping frameworks
- **Serverless Functions**: Minimal cold-start overhead with zero dependencies

## Requirements

- Java 17 or higher
- No external runtime dependencies

## Building from Source

```bash
git clone https://github.com/jlapugot/adaptr.git
cd adaptr
mvn clean install
```

## Running Tests

```bash
mvn test
```

## Contributing

Contributions are welcome! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Support

- **Issues**: [GitHub Issues](https://github.com/jlapugot/adaptr/issues)
- **Discussions**: [GitHub Discussions](https://github.com/jlapugot/adaptr/discussions)

## Roadmap

- [ ] Support for nested object adaptation
- [ ] Type conversion and validation
- [ ] Performance optimizations with caching
- [ ] Support for default interface methods
- [ ] Collection and array support

## Acknowledgments

Built with inspiration from other mapping libraries, but designed to be simpler and more focused on the Map-to-Interface use case.
