CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       chat_id BIGINT UNIQUE NOT NULL
);


CREATE TABLE links (
                       id SERIAL PRIMARY KEY,
                       user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                       url TEXT NOT NULL,
                       update_time TIMESTAMPTZ DEFAULT now(),
                       created_at TIMESTAMPTZ DEFAULT now()
);
CREATE TABLE filters (
                         id SERIAL PRIMARY KEY,
                         name TEXT UNIQUE NOT NULL
);

CREATE TABLE tags (
                      id SERIAL PRIMARY KEY,
                      name TEXT UNIQUE NOT NULL
);

CREATE TABLE links_filters (
                               link_id INT NOT NULL REFERENCES links(id) ON DELETE CASCADE,
                               filter_id INT NOT NULL REFERENCES filters(id) ON DELETE CASCADE,
                               PRIMARY KEY (link_id, filter_id)
);

CREATE TABLE links_tags (
                            link_id INT NOT NULL REFERENCES links(id) ON DELETE CASCADE,
                            tag_id INT NOT NULL REFERENCES tags(id) ON DELETE CASCADE,
                            PRIMARY KEY (link_id, tag_id)
);
