package com.holidu.interview.assignment.controllers;

import com.holidu.interview.assignment.models.AggregateTreeData;
import com.holidu.interview.assignment.services.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/tree")
public class TreeController {

    @Autowired
    private TreeService treeService;

    @GetMapping()
    public ResponseEntity<AggregateTreeData> getTreeAggregatedData(@RequestParam Double x, @RequestParam Double y, @RequestParam Double radius) {
        long startTime = System.nanoTime();
        AggregateTreeData data = treeService.fetchTrees(x, y, radius);
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds: " + timeElapsed);

        return new ResponseEntity<AggregateTreeData>(data, HttpStatus.OK);
    }


}
