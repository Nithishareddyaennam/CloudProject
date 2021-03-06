/*
 * Copyright 2020 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.spanner.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class CreateConnectionWithPropertiesExample {

  static void createConnectionWithProperties() throws SQLException {
    // TODO(developer): Replace these variables before running the sample.
    String projectId = "my-project";
    String instanceId = "my-instance";
    String databaseId = "my-database";
    createConnectionWithProperties(projectId, instanceId, databaseId);
  }

  // Creates a JDBC connection to a Cloud Spanner database using Properties.
  static void createConnectionWithProperties(String projectId, String instanceId, String databaseId)
      throws SQLException {
    Properties properties = new Properties();
    properties.setProperty("readonly", "true");
    properties.setProperty("autocommit", "false");
    try (Connection connection =
        DriverManager.getConnection(
            String.format(
                "jdbc:cloudspanner:/projects/%s/instances/%s/databases/%s",
                projectId, instanceId, databaseId),
            properties)) {
      System.out.printf("Readonly: %b%n", connection.isReadOnly());
      System.out.printf("Autocommit: %b%n", connection.getAutoCommit());
    }
  }
}
