# Kafka local setup

## Configuration

In order to run Kafka locally, only a few configuration changes are
needed.

### zookeeper.properties

Set the zookeeper data dir to:
```dataDir=PATH_TO_RFQ_DECORATOR_PROJECT/kafka/data/zookeeper```

### server.properties

Set the Kafka server log files directory:

```log.dirs=PATH_TO_RFQ_DECORATOR_PROJECT/kafka/data/logs```

## Run

Open a command prompt and issue the following commands to run the zookeeper component then leave it running:

    cd PATH_TO_RFQ_DECORATOR_PROJECT/kafka
    bin\windows\zookeeper-server-start.bat config\zookeeper.properties

Open another command prompt, and issue the following commands to run the kafka server:
    
    cd PATH_TO_RFQ_DECORATOR_PROJECT/kafka
    bin\windows\kafka-server-start.bat config\server.properties
    