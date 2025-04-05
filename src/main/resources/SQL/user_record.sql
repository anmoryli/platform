use medicine;

drop table if exists user_image_recognition;
create table user_image_recognition(
    record_id int primary key auto_increment,
    user_id int,
    image_name varchar(100),
    image_path varchar(100),
    image_result varchar(100),
    timestamp datetime default now(),
    foreign key (user_id) references user_info(id)
);

drop table if exists user_ai_conversation;
create table user_ai_conversation(
    conversation_id int primary key auto_increment,
    user_id int,
    question varchar(100),
    answer varchar(100),
    timestamp datetime default now(),
    foreign key (user_id) references user_info(id)
);

drop table if exists user_knowledge_search;
create table user_knowledge_search(
    search_id int primary key auto_increment,
    user_id int,
    search_query varchar(100),
    search_result varchar(100),
    timestamp datetime default now(),
    foreign key (user_id) references user_info(id)
);