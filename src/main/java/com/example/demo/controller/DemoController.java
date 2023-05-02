package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/test")
@Log4j2
@RequiredArgsConstructor
public class DemoController {
  @Value("${secret.test}")
  private String envTest;

  @GetMapping("/{name}")
  public ResponseEntity<String> test(@PathVariable String name) {
    getKubeSecretData();
    return new ResponseEntity<>("test ==> " + name + ", envTest ==> " + envTest, HttpStatus.OK);
  }

  private void getKubeSecretData() {
    File mountFolder = new File("/etc/user-pass-secret");

    File[] files = mountFolder.listFiles();
    if (ObjectUtils.isEmpty(files) || files.length == 0) {
      System.out.println("there is no files");
      return;
    }

    for (File file : files) {
      try {
        String content = new String(FileCopyUtils.copyToByteArray(file));
        System.out.println("file content ==> " + content);
      } catch (IOException e) {
        System.out.println("file cannot read ==> " + e.toString());
      }
    }
  }
}
