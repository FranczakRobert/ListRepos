package com.robertfranczak.Task.Utils;

import com.robertfranczak.Task.Exceptions.ApiException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitHubErrorParser {
    static private GitHubErrorParser gitHubErrorParser;
    private GitHubErrorParser() {}

    public static GitHubErrorParser getInstance() {
        if (gitHubErrorParser ==null)
            gitHubErrorParser = new GitHubErrorParser();

        return gitHubErrorParser;
    }

    public ApiException extractStatusAndMessageError(String msg) {
        try {
            Pattern pattern = Pattern.compile("(\\d{3})");
            Matcher matcher = pattern.matcher(msg);
            int statusCode = -1;
            if (matcher.find()) {
                statusCode = Integer.parseInt(matcher.group(1));
            }

            String errorMessage = msg.substring(msg.indexOf("message\":\"") + 10);
            errorMessage = errorMessage.substring(0, errorMessage.indexOf("\""));

            return new ApiException(statusCode,errorMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

