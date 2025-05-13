package backend.academy.scrapper.repository.jdbc;

import backend.academy.common.dto.response.LinkResponse;
import backend.academy.scrapper.DatabaseConfig;
import backend.academy.scrapper.repository.custom.CustomRepository;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@SuppressFBWarnings({"ODR_OPEN_DATABASE_RESOURCE", "OBL_UNSATISFIED_OBLIGATION"})
public class JdbcRepository implements CustomRepository {
    private final Logger log = LoggerFactory.getLogger(JdbcRepository.class);
    private final DatabaseConfig databaseConfig;

    @Autowired
    public JdbcRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    databaseConfig.url(), databaseConfig.username(), databaseConfig.password());
        } catch (SQLException e) {
            log.error("Error while connecting to database", e);
            return null;
        }
    }

    private Long getUserId(Long chatId, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM users WHERE chat_id = ?");
        statement.setLong(1, chatId);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next() ? resultSet.getLong("id") : null;
    }

    @Override
    public List<LinkResponse> getLinks(Long chatId) {
        List<LinkResponse> links = new ArrayList<>();
        try (Connection connection = getConnection()) {
            if (connection == null) return links;

            Long userId = getUserId(chatId, connection);
            if (userId == null) return links;

            PreparedStatement statement =
                    connection.prepareStatement("SELECT id, url, created_at, update_time FROM links WHERE user_id = ?");
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long linkId = resultSet.getLong("id");
                LinkResponse link = new LinkResponse(
                        linkId,
                        resultSet.getString("url"),
                        getTags(linkId, connection),
                        getFilters(linkId, connection));
                link.setCreated(resultSet.getTimestamp("created_at").toInstant().atZone(java.time.ZoneOffset.UTC));
                link.setUpdated(
                        resultSet.getTimestamp("update_time").toInstant().atZone(java.time.ZoneOffset.UTC));
                links.add(link);
            }
        } catch (SQLException e) {
            log.error("Error while getting links", e);
        }
        return links;
    }

    @Override
    public void addLink(Long chatId, LinkResponse linkResponse) {
        try (Connection connection = getConnection()) {
            if (connection == null) return;

            Long userId = getUserId(chatId, connection);
            if (userId == null) return;

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO links (user_id, url) VALUES (?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, userId);
            statement.setString(2, linkResponse.getUrl());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long linkId = resultSet.getLong(1);
                linkResponse.setId(linkId);
                saveTags(linkId, linkResponse.getTags(), connection);
                saveFilters(linkId, linkResponse.getFilters(), connection);
            }
        } catch (SQLException e) {
            log.error("Error while adding link", e);
        }
    }

    @Override
    public void deleteLink(Long chatId, LinkResponse linkResponse) {
        try (Connection connection = getConnection()) {
            if (connection == null) return;

            Long userId = getUserId(chatId, connection);
            if (userId == null) return;

            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM links WHERE user_id = ? AND url = ?");
            statement.setLong(1, userId);
            statement.setString(2, linkResponse.getUrl());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while deleting link", e);
        }
    }

    @Override
    public void deleteAllLinks(Long chatId) {
        try (Connection connection = getConnection()) {
            if (connection == null) return;

            Long userId = getUserId(chatId, connection);
            if (userId == null) return;

            PreparedStatement statement = connection.prepareStatement("DELETE FROM links WHERE user_id = ?");
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while deleting all links", e);
        }
    }

    @Override
    public void updateLink(Long chatId, LinkResponse linkResponse) {
        try (Connection connection = getConnection()) {
            if (connection == null) return;

            Long userId = getUserId(chatId, connection);
            if (userId == null) return;
            if (linkResponse.getUpdated() != null) {
                PreparedStatement statement =
                        connection.prepareStatement("UPDATE links SET update_time =  ? WHERE user_id = ? AND url = ?");

                statement.setTimestamp(
                        1, java.sql.Timestamp.from(linkResponse.getUpdated().toInstant()));
                statement.setLong(2, userId);
                statement.setString(3, linkResponse.getUrl());
                statement.executeUpdate();
            }
            Long linkId = getLinksId(chatId, linkResponse.getUrl());
            saveTags(linkId, linkResponse.getTags(), connection);
            saveFilters(linkId, linkResponse.getFilters(), connection);
        } catch (SQLException e) {
            log.error("Error while updating link", e);
        }
    }

    @Override
    public boolean checkLink(Long chatId, String link) {
        try {
            Connection connection = getConnection();
            if (connection == null) return false;

            Long userId = getUserId(chatId, connection);
            if (userId == null) return false;

            PreparedStatement statement =
                    connection.prepareStatement("SELECT id FROM links WHERE user_id = ? AND url = ?");
            statement.setLong(1, userId);
            statement.setString(2, link);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Error while checking link", e);
        }

        return false;
    }

    @Override
    public boolean chatExists(Long chatId) {
        try {
            Connection connection = getConnection();
            if (connection == null) return false;

            PreparedStatement statement = connection.prepareStatement("SELECT id FROM users WHERE chat_id = ?");
            statement.setLong(1, chatId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Error while checking chat", e);
        }
        return false;
    }

    @Override
    public void registerChat(Long chatId) {
        try {
            Connection connection = getConnection();
            if (connection == null) return;

            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO users (chat_id) VALUES (?) ON CONFLICT DO NOTHING");
            statement.setLong(1, chatId);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while registering chat", e);
        }
    }

    @Override
    public void deleteChat(Long chatId) {
        try {
            Connection connection = getConnection();
            if (connection == null) return;

            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE chat_id = ?");
            statement.setLong(1, chatId);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while deleting chat", e);
        }
    }

    private List<String> getTags(Long linkId, Connection connection) throws SQLException {
        List<String> tags = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT name FROM tags JOIN links_tags ON tags.id = links_tags.tag_id WHERE link_id = ?");
        statement.setLong(1, linkId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            tags.add(resultSet.getString("name"));
        }
        return tags;
    }

    private List<String> getFilters(Long linkId, Connection connection) throws SQLException {
        List<String> filters = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT name FROM filters JOIN links_filters ON filters.id = links_filters.filter_id WHERE link_id = ?");
        statement.setLong(1, linkId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            filters.add(resultSet.getString("name"));
        }
        return filters;
    }

    private void saveTags(Long linkId, List<String> tags, Connection connection) throws SQLException {
        for (String tag : tags) {

            PreparedStatement tagStatement = connection.prepareStatement(
                    "INSERT INTO tags (name) VALUES (?) ON CONFLICT (name) DO NOTHING RETURNING id");
            tagStatement.setString(1, tag);
            ResultSet tagResult = tagStatement.executeQuery();

            Long tagId = null;
            if (tagResult.next()) {
                tagId = tagResult.getLong("id");
            } else {

                PreparedStatement getTagId = connection.prepareStatement("SELECT id FROM tags WHERE name = ?");
                getTagId.setString(1, tag);
                ResultSet existingTagResult = getTagId.executeQuery();
                if (existingTagResult.next()) {
                    tagId = existingTagResult.getLong("id");
                }
            }

            if (tagId != null) {
                PreparedStatement linkTagStatement = connection.prepareStatement(
                        "INSERT INTO links_tags (link_id, tag_id) VALUES (?, ?) ON CONFLICT DO NOTHING");
                linkTagStatement.setLong(1, linkId);
                linkTagStatement.setLong(2, tagId);
                linkTagStatement.executeUpdate();
            }
        }
    }

    private void saveFilters(Long linkId, List<String> filters, Connection connection) throws SQLException {
        for (String filter : filters) {
            PreparedStatement filterStatement = connection.prepareStatement(
                    "INSERT INTO filters (name) VALUES (?) ON CONFLICT (name) DO NOTHING RETURNING id");
            filterStatement.setString(1, filter);
            ResultSet filterResult = filterStatement.executeQuery();

            Long filterId = null;
            if (filterResult.next()) {
                filterId = filterResult.getLong("id");
            } else {
                PreparedStatement getFilterId = connection.prepareStatement("SELECT id FROM filters WHERE name = ?");
                getFilterId.setString(1, filter);
                ResultSet existingFilterResult = getFilterId.executeQuery();
                if (existingFilterResult.next()) {
                    filterId = existingFilterResult.getLong("id");
                }
            }

            if (filterId != null) {
                PreparedStatement linkFilterStatement = connection.prepareStatement(
                        "INSERT INTO links_filters (link_id, filter_id) VALUES (?, ?) ON CONFLICT DO NOTHING");
                linkFilterStatement.setLong(1, linkId);
                linkFilterStatement.setLong(2, filterId);
                linkFilterStatement.executeUpdate();
            }
        }
    }

    public Long getLinksId(Long chatId, String url) {
        try (Connection connection = getConnection()) {
            if (connection == null) return null;

            Long userId = getUserId(chatId, connection);
            if (userId == null) return null;

            PreparedStatement statement =
                    connection.prepareStatement("SELECT id FROM links WHERE user_id = ? AND url = ?");
            statement.setLong(1, userId);
            statement.setString(2, url);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
        } catch (SQLException e) {
            log.error("Error while getting link id", e);
        }
        return null;
    }
}
