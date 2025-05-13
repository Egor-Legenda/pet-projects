package backend.academy.scrapper.service;

import backend.academy.common.dto.response.LinkResponse;
import backend.academy.scrapper.repository.custom.CustomRepository;
import backend.academy.scrapper.service.github.GithubClient;
import backend.academy.scrapper.service.github.ResourceData;
import backend.academy.scrapper.service.stackOverflow.StackOverflowClientImpl;
import backend.academy.scrapper.service.stackOverflow.dto.QuestionResponse;
import backend.academy.scrapper.storage.UserLinkStorage;
import java.time.ZonedDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateMonitoringService {

    private static final Logger log = LoggerFactory.getLogger(UpdateMonitoringService.class.getName());

    @Autowired
    private StackOverflowClientImpl stackOverflowClient;

    @Autowired
    private UserLinkStorage userLinkStorage;

    @Autowired
    private GithubClient githubClient;

    @Autowired
    private CustomRepository customRepository;

    public UpdateMonitoringService() {}

    public String monitorUpdates(Long userId) {

        List<LinkResponse> links = customRepository.getLinks(userId);
        for (LinkResponse link : links) {
            if (githubClient.checkLink(link.getUrl())) {
                ResourceData newData = githubClient.findData(link.getUrl());
                if (!newData.getCreateTime().equals(link.getUpdated())) {
                    log.atInfo()
                            .setMessage("Time update")
                            .addKeyValue("chat-id", userId)
                            .addKeyValue("link", link.getUrl())
                            .addKeyValue("time", newData.getCreateTime())
                            .log();
                    link.setUpdated(newData.getCreateTime());
                    customRepository.updateLink(userId, link);
                    return "Обновление времени для ссылки: " + link.getUrl() + " время последнего комита "
                            + newData.getCreateTime();
                }
            } else {
                QuestionResponse response = stackOverflowClient.findQuestion(link.getUrl());
                if (!response.getTitle().isEmpty()) {
                    log.atInfo()
                            .setMessage("Question update")
                            .addKeyValue("chat-id", userId)
                            .addKeyValue("link", link.getUrl())
                            .addKeyValue("question", response.getTitle())
                            .log();
                    link.setUpdated(ZonedDateTime.now());
                    customRepository.updateLink(userId, link);
                    return "Вам был задан вопрос: " + response.getTitle() + " к статье " + link.getUrl();
                }
            }
        }

        return null;
    }

    public boolean checkLink(String link) {
        if (githubClient.checkLink(link) || stackOverflowClient.checkStackOverflow(link)) {
            return true;
        }
        return false;
    }
}
