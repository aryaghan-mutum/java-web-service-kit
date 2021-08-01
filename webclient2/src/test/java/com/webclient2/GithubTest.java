package com.webclient2;

import com.webclient2.response.employee.github.GithubBranchResponse;
import com.webclient2.service.github.GithubBranchService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

@Slf4j
public class GithubTest {

    private static final String BASE_URL = "https://api.github.com/repos/aryaghan-mutum";

    public static final String getBranchUrl(String repoName) {
        return BASE_URL + String.format("/%s/branches", repoName);
    }

    /**
     * curl   -H "Accept: application/vnd.github.v3+json"   https://api.github.com/repos/aryaghan-mutum/algory/branches
     */
    @Test
    public void testOneBranchInRepo() {
        GithubBranchService branchService = new GithubBranchService();
        GithubBranchResponse[] branchResponse = branchService.getBranchesByRepo(getBranchUrl("java-web-service-kit"));
        Assertions.assertEquals("main", branchResponse[0].getName());
        Assertions.assertFalse(branchResponse[0].getProtected());
    }

    @Test
    public void testMultipleBranchesInRepo() {
        final String repoName = "algory";
        GithubBranchService branchService = new GithubBranchService();
        GithubBranchResponse[] branchResponse = branchService.getBranchesByRepo(getBranchUrl(repoName));

        IntStream.range(0, branchResponse.length).forEach(i -> {
            String branchName = branchResponse[i].getName();
            log.info("Branch Names: " + branchName);
            Assertions.assertNotNull(branchName);
        });
    }

}
