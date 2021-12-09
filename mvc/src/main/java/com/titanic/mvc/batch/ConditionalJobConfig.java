package com.titanic.mvc.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Configuration;

// TODO(jack.comeback) : Add Conditional or general batch job boilerplate
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ConditionalJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

}
