package org.incode.module.jira.impl;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import static com.google.common.collect.ImmutableMap.of;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JiraMarshaller {

    private final String jiraProjectKey;
    private final String jiraIssueType;

    public Map<String, Object> buildRequestBody(final String summary, final String description) {
        return of(
                "fields", of(
                        "project", of(
                                "key", jiraProjectKey
                        ),
                        "summary", summary,
                        "description", description,
                        "issuetype", of(
                                "name", jiraIssueType
                        )
                )
        );
    }

    public String readResponseKey(final JsonNode responseNode) {
        return responseNode.get("key").asText();
    }
}
