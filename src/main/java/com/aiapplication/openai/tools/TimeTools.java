package com.aiapplication.openai.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;

@Component
public class TimeTools {

    private static final Logger logger = LoggerFactory.getLogger(TimeTools.class);

    /**
     DefaultToolCallingManager - is the class which invokes and manages tools and their inputs
     **/

    //description helps the LLM to understand when to invoke the tool
    @Tool(name="getCurrentLocalTime", description = "Get the current time in the users timezone")
    String getCurrentLocalTime() {
        logger.info("Returning current local time");
        return LocalTime.now().toString();
    }

    @Tool(name = "getCurrentTime",
            description = "Get the current time in the specified time zone")
    public String getCurrentTime(@ToolParam(
            description = "Value representing the time zone") String timeZone) {
        logger.info("Returning the current time in the timezone {}", timeZone);
        return LocalTime.now(ZoneId.of(timeZone)).toString();
    }
}
