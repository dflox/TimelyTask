FROM eclipse-temurin:24-jre

# Update package list and install only essential packages
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# Create app directory and user
RUN groupadd -r appuser && useradd -r -g appuser appuser
WORKDIR /app
RUN chown appuser:appuser /app

# Copy the assembly JAR
COPY target/scala-3.7.1/timelytask-v0.0.2.jar timelytask.jar

# Copy TUI-only startup configuration
COPY <<EOF /app/startUpConfig.yaml
uiInstances:
  - uis:
      - uiType: tui
    startView: calendar
EOF

# Switch to non-root user
USER appuser

# Set JVM options for container environment
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Run the application
CMD java $JAVA_OPTS -jar timelytask.jar



