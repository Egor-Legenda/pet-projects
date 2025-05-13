package backend.academy.scrapper.service.stackOverflow;

import backend.academy.scrapper.service.stackOverflow.dto.QuestionResponse;

public interface StackOverflowClient {
    QuestionResponse findQuestion(String id);
}
