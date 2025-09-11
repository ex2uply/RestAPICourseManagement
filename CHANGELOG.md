# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.3.0] - 2024-01-15

### Added

- Comprehensive test coverage with unit and integration tests
- H2 database support for testing
- TestContainers for integration testing
- JaCoCo code coverage reporting
- SpotBugs static analysis
- Checkstyle code quality checks
- Enhanced .gitignore with comprehensive patterns
- MIT License file
- Docker compose override example file
- Application test properties configuration

### Changed

- Updated POM.xml with proper project metadata
- Fixed Dockerfile to use correct JAR name
- Improved GlobalExceptionHandler to return proper validation errors
- Enhanced CourseRequest DTO with missing validation annotations
- Updated README with correct Dockerfile references

### Fixed

- JAR name mismatch between POM.xml and Dockerfile
- Missing @NotNull validation on durationHours field
- Exception handler not properly returning validation error details
- Incomplete project metadata in POM.xml

### Security

- Added comprehensive security configuration
- Implemented role-based access control
- Added JWT token support (dependencies included)

## [1.2.0] - 2024-01-10

### Added

- Enhanced security and validation
- JWT authentication support
- Comprehensive API documentation with Swagger/OpenAPI

## [1.1.0] - 2024-01-05

### Added

- Search functionality
- Filtering capabilities
- Pagination support

## [1.0.0] - 2024-01-01

### Added

- Initial release with basic CRUD operations
- Course management API
- PostgreSQL database integration
- Spring Boot application structure
