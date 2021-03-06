/*
 * Copyright 2021 Google LLC
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

package secretmanager;

// [START secretmanager_list_secret_versions_with_filter]
import com.google.cloud.secretmanager.v1.ListSecretVersionsRequest;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient.ListSecretVersionsPagedResponse;
import com.google.cloud.secretmanager.v1.SecretName;
import java.io.IOException;

public class ListSecretVersionsWithFilter {

  public static void listSecretVersions() throws IOException {
    // TODO(developer): Replace these variables before running the sample.
    String projectId = "your-project-id";
    String secretId = "your-secret-id";
    // Follow https://cloud.google.com/secret-manager/docs/filtering
    // for filter syntax and examples.
    String filter = "create_time>2021-01-01T00:00:00Z";
    listSecretVersions(projectId, secretId, filter);
  }

  // List all secret versions for a secret.
  public static void listSecretVersions(String projectId, String secretId, String filter)
      throws IOException {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
      // Build the parent name.
      SecretName secretName = SecretName.of(projectId, secretId);

      // Get filtered versions.
      ListSecretVersionsRequest request =
          ListSecretVersionsRequest.newBuilder()
              .setParent(secretName.toString())
              .setFilter(filter)
              .build();

      ListSecretVersionsPagedResponse pagedResponse = client.listSecretVersions(request);

      // List all versions and their state.
      pagedResponse
          .iterateAll()
          .forEach(
              version -> {
                System.out.printf("Secret version %s, %s\n", version.getName(), version.getState());
              });
    }
  }
}
// [END secretmanager_list_secret_versions_with_filter]
