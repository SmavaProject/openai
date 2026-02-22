package com.aiapplication.openai.overrides;

import com.aiapplication.openai.tools.TimeTools;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.model.tool.DefaultToolExecutionEligibilityPredicate;
import org.springframework.stereotype.Component;

@Component
public class CustomToolExecutionEligibilityPredicate extends DefaultToolExecutionEligibilityPredicate {

    private static final Logger logger = LoggerFactory.getLogger(CustomToolExecutionEligibilityPredicate.class);


    @Override
    public boolean test(ChatOptions promptOptions, ChatResponse chatResponse) {
    /*
        DefaultToolCallingManager orchestrates tool calls
    */
        logger.info(String.format("chatResponse.hasToolCalls() : %s", chatResponse.hasToolCalls() ));
        return super.test(promptOptions, chatResponse);
    }
}
