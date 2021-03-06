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

//[START spanner_jdbc_batch_transaction]
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

class BatchDmlExample {

  static void batchDml() throws SQLException {
    // TODO(developer): Replace these variables before running the sample.
    String projectId = "my-project";
    String instanceId = "my-instance";
    String databaseId = "my-database";
    batchDml(projectId, instanceId, databaseId);
  }

  static void batchDml(String projectId, String instanceId, String databaseId) throws SQLException {
    String connectionUrl =
        String.format(
            "jdbc:cloudspanner:/projects/%s/instances/%s/databases/%s",
            projectId, instanceId, databaseId);
    try (Connection connection = DriverManager.getConnection(connectionUrl)) {
      connection.setAutoCommit(false);
      try (Statement statement = connection.createStatement()) {
        statement.addBatch(
            "INSERT INTO Singers (SingerId, FirstName, LastName, Revenues)\n"
                + "VALUES (10, 'Marc', 'Richards', 100000)");
        statement.addBatch(
            "INSERT INTO Singers (SingerId, FirstName, LastName, Revenues)\n"
                + "VALUES (11, 'Amirah', 'Finney', 195944.10)");
        statement.addBatch(
            "INSERT INTO Singers (SingerId, FirstName, LastName, Revenues)\n"
                + "VALUES (12, 'Reece', 'Dunn', 10449.90)");
        int[] updateCounts = statement.executeBatch();
        connection.commit();
        System.out.printf("Batch insert counts: %s%n", Arrays.toString(updateCounts));
      }
    }
  }
}
//[END spanner_jdbc_batch_transaction]
