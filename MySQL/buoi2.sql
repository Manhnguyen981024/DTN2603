-- Department
CREATE TABLE department (
    department_id   INT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE
);

-- Position
CREATE TABLE `position` (
    position_id   INT PRIMARY KEY,
    position_name ENUM('DEV', 'TEST', 'SCRUM_MASTER', 'PM') NOT NULL UNIQUE
);

-- Account
CREATE TABLE `account` (
    account_id    INT AUTO_INCREMENT PRIMARY KEY,
    email         VARCHAR(100) NOT NULL UNIQUE,
    username      VARCHAR(100) NOT NULL UNIQUE,
    fullname      VARCHAR(100),
    department_id INT NOT NULL,
    position_id   INT NOT NULL,
    create_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

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
    group_name  VARCHAR(100) NOT NULL UNIQUE,
    creator_id  INT NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_group_account
        FOREIGN KEY (creator_id)
        REFERENCES `account`(account_id)
);

-- Group Account
CREATE TABLE group_account (
    group_id   INT,
    account_id INT,
    join_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

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
    type_name ENUM('ESSAY', 'MULTIPLE_CHOICE') NOT NULL UNIQUE
);

-- Category Question
CREATE TABLE category_question (
    category_id   INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE
);

-- Question
CREATE TABLE question (
    question_id INT AUTO_INCREMENT PRIMARY KEY,
    content     VARCHAR(100) NOT NULL,
    category_id INT NOT NULL,
    type_id     INT NOT NULL,
    creator_id  INT NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

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
    content     VARCHAR(100) NOT NULL,
    question_id INT NOT NULL,
    is_correct  BOOLEAN,

    CONSTRAINT fk_answer_question
        FOREIGN KEY (question_id)
        REFERENCES question(question_id)
);

-- Exam
CREATE TABLE exam (
    exam_id     INT AUTO_INCREMENT PRIMARY KEY,
    code        INT NOT NULL UNIQUE,
    title       VARCHAR(100) NOT NULL,
    category_id INT NOT NULL,
    duration    TIME,
    creator_id  INT NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

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

--Question 3: Chuẩn bị data cho bài 3
-- department
INSERT INTO department (department_name) VALUES
('Engineering'),
('Testing'),
('Human Resource'),
('Accounting'),
('Marketing'),
('Sales'),
('Customer Service'),
('Security'),
('Finance'),
('Management');

-- `position`
INSERT INTO `position` (position_id, position_name) VALUES
(1,'DEV'),
(2,'TEST'),
(3,'SCRUM_MASTER'),
(4,'PM');

--  `account`
INSERT INTO `account`
(email, username, fullname, department_id, position_id) VALUES
('user01@gmail.com','user01','Nguyen Van A',1,1),
('user02@gmail.com','user02','Tran Thi B',2,2),
('user03@gmail.com','user03','Le Van C',3,3),
('user04@gmail.com','user04','Pham Thi D',4,4),
('user05@gmail.com','user05','Hoang Van E',5,1),
('user06@gmail.com','user06','Nguyen Thi F',6,2),
('user07@gmail.com','user07','Tran Van G',7,1),
('user08@gmail.com','user08','Le Thi H',8,2),
('user09@gmail.com','user09','Pham Van I',9,3),
('user10@gmail.com','user10','Hoang Thi K',10,4);

-- `group`
INSERT INTO `group`
(group_name, creator_id) VALUES
('Java Team',1),
('Testing Team',2),
('Scrum Team',3),
('PM Team',4),
('Backend Team',5),
('Frontend Team',6),
('Mobile Team',7),
('DevOps Team',8),
('Security Team',9),
('Management Team',10);
 
-- group_account
INSERT INTO group_account
(group_id, account_id) VALUES
(1,1),
(2,2),
(3,3),
(4,4),
(5,5),
(6,6),
(7,7),
(8,8),
(9,9),
(10,10);

-- type_question
INSERT INTO type_question (type_name) VALUES
('ESSAY'),
('MULTIPLE_CHOICE');

-- category_question
INSERT INTO category_question (category_name) VALUES
('Java'),
('SQL'),
('HTML'),
('CSS'),
('JavaScript'),
('Spring Boot'),
('MySQL'),
('JUnit'),
('Git'),
('Linux');

-- question
INSERT INTO question
(content, category_id, type_id, creator_id)
VALUES
('What is Java?',1,1,1),
('What is JDBC?',1,2,2),
('What is Primary Key?',2,2,3),
('What is Foreign Key?',2,2,4),
('What is HTML?',3,1,5),
('What is CSS?',4,2,6),
('Explain JavaScript.',5,1,7),
('What is Spring Boot?',6,2,8),
('What is Git?',9,1,9),
('What is Linux?',10,2,10);

-- answer
INSERT INTO answer
(content, question_id, is_correct)
VALUES
('Programming Language',1,TRUE),
('Java Database Connectivity',2,TRUE),
('Unique Identifier',3,TRUE),
('Reference another table',4,TRUE),
('Markup Language',5,TRUE),
('Style Sheet Language',6,TRUE),
('Scripting Language',7,TRUE),
('Java Framework',8,TRUE),
('Version Control System',9,TRUE),
('Operating System',10,TRUE);

-- exam
INSERT INTO exam
(code, title, category_id, duration, creator_id)
VALUES
(1001,'Java Basic',1,'01:00:00',1),
(1002,'SQL Basic',2,'01:00:00',2),
(1003,'HTML Test',3,'00:45:00',3),
(1004,'CSS Test',4,'00:45:00',4),
(1005,'JavaScript Test',5,'01:30:00',5),
(1006,'Spring Boot Test',6,'01:30:00',6),
(1007,'MySQL Test',7,'01:00:00',7),
(1008,'JUnit Test',8,'00:45:00',8),
(1009,'Git Test',9,'00:30:00',9),
(1010,'Linux Test',10,'01:00:00',10);

-- exam_question
INSERT INTO exam_question
(exam_id, question_id)
VALUES
(1,1),
(2,2),
(3,3),
(4,4),
(5,5),
(6,6),
(7,7),
(8,8),
(9,9),
(10,10);
