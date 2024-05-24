package tasker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import tasker.api.utils.Shell;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class TaskerServer {

    public static final int SLEEP_ERROR_STATUS = -1;
    public static final int NO_CONSOLE_ERROR = -2;

    @Value("${spring.datasource.url}")
    String dbUrl;

    public static void main(String[] args) {
        SpringApplication.run(TaskerServer.class);
    }

    public static void QuitServer(int status) {
        Shell.getInstance().printFine("The server is closing...");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Shell.getInstance().printError(e.getMessage());
            System.exit(SLEEP_ERROR_STATUS);
        }

        System.exit(status);
    }

    @Bean
    public DataSource configureDb() {

        // Get db credentials
        Console console = System.console();
        if (console == null) {
            Shell.getInstance().printError("No console available");
            QuitServer(NO_CONSOLE_ERROR);
            return null;
        }
        Shell.getInstance().printLineInput("Enter Database Username: ");
        String username = console.readLine();
        Shell.getInstance().printLineInput("Enter Database Username: ");
        String password = String.valueOf(console.readPassword());

        return DataSourceBuilder.create()
                .url(dbUrl)
                .username(username)
                .password(password)
                .build();
    }
}