package com.techlearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @Autowired
    BuildProperties buildProperties;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("I am alive !!", HttpStatus.OK);
    }

    @GetMapping(value = "/details", produces = "application/json")
    public ResponseEntity<ApplicationDetails> details() {
        return new ResponseEntity<>(new ApplicationDetails(buildProperties.getVersion(), "Application is up!"), HttpStatus.OK);
    }

    static class ApplicationDetails {
        String response;
        String applicationVersion;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getApplicationVersion() {
            return applicationVersion;
        }

        public void setApplicationVersion(String applicationVersion) {
            this.applicationVersion = applicationVersion;
        }

        public ApplicationDetails(String applicationVersion, String response) {
            this.applicationVersion = applicationVersion;
            this.response = response;
        }
    }

    @Autowired
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }
}
