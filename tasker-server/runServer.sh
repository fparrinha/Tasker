#!/bin/bash


sigint_handler() {
    echo "Exiting..."
    sleep 1
    exit
}



# Main Commands to be executed on the new shell
sourceCommand="export PATH='$HOME/.sdkman/candidates/java/current/bin:$HOME/.sdkman/candidates/maven/current/bin:$PATH'"
compileCommand="mvn clean package"
runCommand="cd target; java -jar TaskerServer-1.0-SNAPSHOT.jar"

# KILL Shell on SIG_INT
trapCommand="$(declare -f sigint_handler); trap sigint_handler SIGINT"


gnome-terminal -- sh -c "bash -c '${trapCommand}; ${sourceCommand}; ${compileCommand}; ${runCommand}; exec bash'"

