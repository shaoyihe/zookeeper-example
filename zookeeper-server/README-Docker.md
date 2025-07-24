# Docker Setup for ZooKeeper Server

This directory contains Docker configuration for running the ZooKeeper server application.

## Files

- `Dockerfile` - Simple Dockerfile that uses pre-built JAR
- `Dockerfile.multistage` - Multi-stage build that compiles from source
- `docker-compose.yml` - Complete setup with ZooKeeper and server application

## Usage

### Option 1: Build and Run with Simple Dockerfile

First, build the application:
```bash
mvn clean package
```

Then build and run the Docker image:
```bash
# Build the Docker image
docker build -t zookeeper-server .

# Run with default settings (requires external ZooKeeper)
docker run -p 8080-8090:8080-8090 zookeeper-server

# Run with custom ZooKeeper connection
docker run -p 8080-8090:8080-8090 \
  -e ZOOKEEPER_CONNECT_STRING=your-zookeeper:2181 \
  zookeeper-server
```

### Option 2: Enhanced Production Dockerfile

```bash
# Build using the enhanced Dockerfile with security improvements
docker build -f Dockerfile.multistage -t zookeeper-server:multistage .

# Run the application
docker run -p 8080-8090:8080-8090 zookeeper-server:multistage
```

### Option 3: Using Docker Compose (Recommended)

This will start both ZooKeeper and the server application:

```bash
# From the project root directory
docker-compose up -d

# View logs
docker-compose logs -f zookeeper-server

# Stop services
docker-compose down
```

## Environment Variables

The following environment variables can be configured:

- `ZOOKEEPER_CONNECT_STRING` - ZooKeeper connection string (default: localhost:2181)
- `ZOOKEEPER_TIMEOUT` - Connection timeout in milliseconds (default: 2000)
- `SERVER_REGISTER_PATH` - ZooKeeper path for server registration (default: /servers/test-me)

## Testing

Once running, you can test the service:

```bash
# Get server information (replace port with actual assigned port)
curl http://localhost:8080/find-me
```

The application uses dynamic ports (server.port=0), so check the container logs to see which port was assigned.

## Notes

- The application requires a running ZooKeeper instance to function properly
- The Docker Compose setup includes a ZooKeeper container for convenience
- The Dockerfile.multistage includes security improvements (non-root user)
- Both Dockerfiles expect the application to be pre-built with `mvn clean package`
- For a fully automated build from source, consider using a CI/CD pipeline with proper network access