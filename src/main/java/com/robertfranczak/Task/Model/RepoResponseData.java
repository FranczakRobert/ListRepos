package com.robertfranczak.Task.Model;

import java.util.Map;


public record RepoResponseData (
     String repositoryName,
     String ownerLogin,
     Map<String, String> branchNameAndLastCommitSHA

){}
