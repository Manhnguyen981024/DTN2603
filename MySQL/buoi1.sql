-- Department
CREATE TABLE department (
    department_id   INT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100)
);

-- Position
CREATE TABLE `position` (
    position_id   INT PRIMARY KEY,
    position_name ENUM('DEV', 'TEST', 'SCRUM_MASTER', 'PM')
);

-- Account
CREATE TABLE `account` (
    account_id    INT AUTO_INCREMENT PRIMARY KEY,
    email         VARCHAR(100) UNIQUE,
    username      VARCHAR(100) UNIQUE,
    fullname      VARCHAR(100),
    department_id INT,
    position_id   INT,
    create_date   DATE,

    CONSTRAINT fk_account_department
        FOREIGN KEY (department_id)
        REFERENCES department(department_id),

    CONSTRAINT fk_account_position
        FOREIGN KEY (position_id)
        REFERENCES `position`(position_id)
);

-- Group
CREATE TABLE `group` (
    group_id    INT AUTO_INCREMENT PRIMARY KEY,
    group_name  VARCHAR(100),
    creator_id  INT,
    create_date DATE,

    CONSTRAINT fk_group_account
        FOREIGN KEY (creator_id)
        REFERENCES `account`(account_id)
);

-- Group Account
CREATE TABLE group_account (
    group_id   INT,
    account_id INT,
    join_date  DATE,

    PRIMARY KEY (group_id, account_id),

    CONSTRAINT fk_group_account_group
        FOREIGN KEY (group_id)
        REFERENCES `group`(group_id),

    CONSTRAINT fk_group_account_account
        FOREIGN KEY (account_id)
        REFERENCES `account`(account_id)
);

-- Type Question
CREATE TABLE type_question (
    type_id   INT AUTO_INCREMENT PRIMARY KEY,
    type_name ENUM('ESSAY', 'MULTIPLE_CHOICE')
);

-- Category Question
CREATE TABLE category_question (
    category_id   INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100)
);

-- Question
CREATE TABLE question (
    question_id INT AUTO_INCREMENT PRIMARY KEY,
    content     VARCHAR(100),
    category_id INT,
    type_id     INT,
    creator_id  INT,
    create_date DATE,

    CONSTRAINT fk_question_category
        FOREIGN KEY (category_id)
        REFERENCES category_question(category_id),

    CONSTRAINT fk_question_type
        FOREIGN KEY (type_id)
        REFERENCES type_question(type_id),

    CONSTRAINT fk_question_account
        FOREIGN KEY (creator_id)
        REFERENCES `account`(account_id)
);

-- Answer
CREATE TABLE answer (
    answer_id   INT AUTO_INCREMENT PRIMARY KEY,
    content     VARCHAR(100),
    question_id INT,
    is_correct  BOOLEAN,

    CONSTRAINT fk_answer_question
        FOREIGN KEY (question_id)
        REFERENCES question(question_id)
);

-- Exam
CREATE TABLE exam (
    exam_id     INT AUTO_INCREMENT PRIMARY KEY,
    code        INT,
    title       VARCHAR(100),
    category_id INT,
    duration    TIME,
    creator_id  INT,
    create_date DATE,

    CONSTRAINT fk_exam_category
        FOREIGN KEY (category_id)
        REFERENCES category_question(category_id),

    CONSTRAINT fk_exam_account
        FOREIGN KEY (creator_id)
        REFERENCES `account`(account_id)
);

-- Exam Question
CREATE TABLE exam_question (
    exam_id     INT,
    question_id INT,

    PRIMARY KEY (exam_id, question_id),

    CONSTRAINT fk_exam_question_exam
        FOREIGN KEY (exam_id)
        REFERENCES exam(exam_id),

    CONSTRAINT fk_exam_question_question
        FOREIGN KEY (question_id)
        REFERENCES question(question_id)
);
