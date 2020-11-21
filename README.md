# Simple Chat 
Simple chat application with plain text storage for messages written in JAVA. Server should be started before clients. Server application broadcasts messages to other clients via Sockets


### Running two clients and server with maven
```
mvn exec:java -Dexec.mainClass="com.mex.simplechat.main.ChatApplication"  -Dexec.args="server"
mvn exec:java -Dexec.mainClass="com.mex.simplechat.main.ChatApplication"  -Dexec.args="chat /user:User1"
mvn exec:java -Dexec.mainClass="com.mex.simplechat.main.ChatApplication"  -Dexec.args="chat /user:User2"
```

### Running two clients and server with executable jar
```
mvn clean package
java -jar target/SimpleChat-1.0-SNAPSHOT.jar server
java -jar target/SimpleChat-1.0-SNAPSHOT.jar chat /user:User1
java -jar target/SimpleChat-1.0-SNAPSHOT.jar chat /user:User2
```

### Help screen for application
```
Usage: <main class> [COMMAND]
Simple Chat Application, server should be started before the clients
Commands:
  chat    For users connecting to chat application
  server  Starts server for chat application
  help    Displays help information about the specified command
```

### Help screen for server
```
Command Overview
  /help             - This help page
  /exit             - Exit from application
```

### Help screen for client
```
Command Overview
  /help             - This help page
  /exit             - Exit from application
  /send <message>   - Send message for participants
```

## Output
Output will be written in files located in root folder. File names are created associated with user names `message_<USER_NAME>.txt` (chat with User1 name output file will be message_User1.txt). 

### Sample Outputs
message_User1.txt
```
User 'User2' is connected..
outgoing message => User1:  hi there
outgoing message => User1:  this is me
incoming message <= User2: hello
incoming message <= User2: how is it going
outgoing message => User1:  fine thank you
outgoing message => User1:  bye
```
message_User2.txt
```
incoming message <= User1: hi there
incoming message <= User1: this is me
outgoing message => User2:  hello
outgoing message => User2:  how is it going
incoming message <= User1: fine thank you
incoming message <= User1: bye
User 'User1' is gone..
```