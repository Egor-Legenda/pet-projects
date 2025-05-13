CREATE INDEX idx_users_chat_id ON users(chat_id);
CREATE INDEX idx_links_user_id ON links(user_id);
CREATE INDEX idx_links_url ON links(url);
CREATE INDEX idx_links_user_url ON links(user_id, url);
CREATE INDEX idx_links_filters_link_id ON links_filters(link_id);
CREATE INDEX idx_links_filters_filter_id ON links_filters(filter_id);
CREATE INDEX idx_links_tags_link_id ON links_tags(link_id);
CREATE INDEX idx_links_tags_tag_id ON links_tags(tag_id);
