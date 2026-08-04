--Question 1: Thêm ít nhất 10 record vào mỗi table
-- department
INSERT INTO department (department_name) VALUES
('Engineering'),
('Testing'),
('Human Resource'),
('Accounting'),
('Marketing'),
('Sale'),
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

--Question 2: lấy ra tất cả các phòng ban
SELECT *
FROM department;

--Question 3: lấy ra id của phòng ban "Sale"
SELECT department_id
FROM department
WHERE department_name = 'Sale';

--Question 4: lấy ra thông tin account có full name dài nhất
SELECT *
FROM account
WHERE LENGTH(fullname) = (
    SELECT MAX(LENGTH(fullname))
    FROM account
);

--Question 5: Lấy ra thông tin account có full name dài nhất và thuộc phòng ban có id= 3
SELECT *
FROM account
WHERE department_id = 3
AND LENGTH(fullname) = (
    SELECT MAX(LENGTH(fullname))
    FROM account
    WHERE department_id = 3
);

--Question 6: Lấy ra tên group đã tham gia trước ngày 20/12/2019
SELECT g.group_name
FROM `group` g
   JOIN group_account ga
      ON g.group_id = ga.group_id
WHERE ga.join_date < '2019-12-20';

--Question 7: Lấy ra ID của question có >= 4 câu trả lời
SELECT question_id
FROM answer
GROUP BY question_id
HAVING COUNT(1) >= 4;

--Question 8: Lấy ra các mã đề thi có thời gian thi >= 60 phút và được tạo trước ngày 20/12/2019
SELECT code
FROM exam
WHERE duration >= '01:00:00'
AND create_date < '2019-12-20';

--Question 9: Lấy ra 5 group được tạo gần đây nhất
SELECT *
FROM `group`
ORDER BY create_date DESC
LIMIT 5;

--Question 10: Đếm số nhân viên thuộc department có id = 2
SELECT COUNT(1) AS total_account
FROM account
WHERE department_id = 2;

--Question 11: Lấy ra nhân viên có tên bắt đầu bằng chữ "D" và kết thúc bằng chữ "o"
SELECT *
FROM account
WHERE fullname LIKE 'D%o';

--Question 13: Xóa tất cả các exam được tạo trước ngày 20/12/2019
DELETE
FROM exam
WHERE create_date < '2019-12-20';

--Question 13: Xóa tất cả các question có nội dung bắt đầu bằng từ "câu hỏi"
DELETE
FROM question
WHERE content LIKE 'câu hỏi%';

--Question 14: Update thông tin của account có id = 5 thành tên "Nguyễn Bá Lộc" và email thành loc.nguyenba@vti.com.vn
UPDATE account
SET
    fullname = 'Nguyễn Bá Lộc',
    email = 'loc.nguyenba@vti.com.vn'
WHERE account_id = 5;

--Question 15: update account có id = 5 sẽ thuộc group có id = 4
UPDATE group_account
SET group_id = 4
WHERE account_id = 5;
