DROP TABLE IF EXISTS SPRING_AI_CHAT_MEMORY;

CREATE TABLE SPRING_AI_CHAT_MEMORY (
                                       conversation_id VARCHAR(36) NOT NULL,
                                       content LONGVARCHAR NOT NULL,
                                       type VARCHAR(10) NOT NULL,
                                       timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX SPRING_AI_CHAT_MEMORY_CONV_ID_TS_IDX
    ON SPRING_AI_CHAT_MEMORY(conversation_id, timestamp ASC);

ALTER TABLE SPRING_AI_CHAT_MEMORY
    ADD CONSTRAINT TYPE_CHECK
        CHECK (type IN ('USER', 'ASSISTANT', 'SYSTEM', 'TOOL'));
