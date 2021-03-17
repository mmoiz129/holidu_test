package com.holidu.interview.assignment.controllers;


import com.holidu.interview.assignment.App;
import com.holidu.interview.assignment.services.TreeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestTreeController {

    @Autowired
    private TreeService treeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFetchTrees() throws Exception{
        String params = "?x=1040172.259&y=219009.169&radius=500";
        mockMvc.perform( MockMvcRequestBuilders
                .get("/api/tree" + params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchTreesWithoutParam() throws Exception{
        mockMvc.perform( MockMvcRequestBuilders
                .get("/api/tree")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }


}
