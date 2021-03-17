DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS likeArticle;
DROP TABLE IF EXISTS likeComment;
DROP TABLE IF EXISTS follow;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS user;


CREATE TABLE user
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    userName     VARCHAR(128) NOT NULL UNIQUE,
    nickname     VARCHAR(128),
    firstName    VARCHAR(128),
    lastName     VARCHAR(128),
    dateOfBirth  DATE,
    email        VARCHAR(128),
    signature    TEXT,
    description  TEXT,
    avatar       VARCHAR(128) NOT NULL,
    passwordHash VARCHAR(1024) NOT NULL,
    salt         VARCHAR(1024) NOT NULL,
    iteration    INT          NOT NULL
);

CREATE TABLE article
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    title     TEXT         NOT NULL,
    content   TEXT,
    time      TIMESTAMP    NOT NULL,
    cover     VARCHAR(128) NOT NULL,
    user      INT          NOT NULL,
    isDeleted BOOLEAN      NOT NULL,
    FOREIGN KEY (user) REFERENCES user (id) ON DELETE CASCADE
);

CREATE TABLE comment
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    content   TEXT      NOT NULL,
    time      TIMESTAMP NOT NULL,
    parent    INT,
    user      INT       NOT NULL,
    article   INT,
    isDeleted BOOLEAN   NOT NULL,
    FOREIGN KEY (parent) REFERENCES comment (id) ON DELETE CASCADE,
    FOREIGN KEY (user) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (article) REFERENCES article (id) ON DELETE CASCADE
);

CREATE TABLE likeArticle
(
    user    INT NOT NULL,
    article INT NOT NULL,
    PRIMARY KEY (user, article),
    FOREIGN KEY (user) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (article) REFERENCES article (id) ON DELETE CASCADE
);

CREATE TABLE likeComment
(
    user    INT NOT NULL,
    comment INT NOT NULL,
    PRIMARY KEY (user, comment),
    FOREIGN KEY (user) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (comment) REFERENCES comment (id) ON DELETE CASCADE
);

CREATE TABLE tag
(
    article INT          NOT NULL,
    tag     VARCHAR(256) NOT NULL,
    PRIMARY KEY (article, tag),
    FOREIGN KEY (article) REFERENCES article (id) ON DELETE CASCADE
);

CREATE TABLE follow
(
    followee INT NOT NULL,
    follower INT NOT NULL,
    PRIMARY KEY (followee, follower),
    FOREIGN KEY (followee) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (follower) REFERENCES user (id) ON DELETE CASCADE
);