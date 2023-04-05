package com.task.crypto.recommendation.controllers;

import com.task.crypto.recommendation.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;
}
