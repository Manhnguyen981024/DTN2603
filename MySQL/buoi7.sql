-- Question 1: Tạo trigger không cho phép người dùng nhập vào Group có ngày tạo
-- trước 1 năm trước
DELIMITER $$

CREATE TRIGGER tg_group_create_date
BEFORE INSERT ON `group`
FOR EACH ROW

BEGIN
	IF NEW.create_date < DATE_SUB(CURDATE(), INTERVAL 1 YEAR) THEN
		SIGNAL SQLSTATE '45000' 
		SET MESSAGE_TEXT = 'CreateDate cannot be older than 1 year';
	END IF;
END $$

DELIMITER ;

insert into `group`(group_name,creator_id, create_date) values ('ABC','1','2021-01-01');

-- Question 2: Tạo trigger Không cho phép người dùng thêm bất kỳ user nào vào
-- department "Sale" nữa, khi thêm thì hiện ra thông báo "Department
-- "Sale" cannot add more user"
DELIMITER $$

CREATE TRIGGER tg_account_sale
BEFORE INSERT ON account
FOR EACH ROW 
BEGIN
	DECLARE v_dept_name VARCHAR(100);

	SELECT d.department_name INTO v_dept_name 
	FROM department as d
	WHERE NEW.department_id = d.department_id;

	IF v_dept_name = 'Sales' THEN
	    SIGNAL SQLSTATE '45000'
	    SET MESSAGE_TEXT = 'Department Sales cannot add more user';
	END IF;
END $$

DELIMITER ;

-- 6
select * from account;
INSERT INTO account (email, username, fullname, department_id, position_id, create_date) 
VALUES ('user22@gmail.com', 'user22', 'Nguyen Van A', 6, 1, '2026-08-03 17:15:02');

-- Question 3: Cấu hình 1 group có nhiều nhất là 5 user
DELIMITER $$

CREATE TRIGGER tg_group_max_user
BEFORE INSERT ON `group_account`
FOR EACH ROW
BEGIN
	DECLARE v_group_cnt INT;
	
	SELECT 	COUNT(1) INTO v_group_cnt
	FROM group_account as ga
	WHERE ga.group_id = NEW.group_id
	GROUP BY ga.group_id;

	IF v_group_cnt >= 5 THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Each group can contain maximum 5 users';
	END IF;
END $$

DELIMITER ;


-- Question 4: Cấu hình 1 bài thi có nhiều nhất là 10 Question
DELIMITER $$

CREATE TRIGGER tg_question_exam
BEFORE INSERT ON exam_question
FOR EACH ROW
BEGIN
	DECLARE v_exam_cnt INT;
	
	SELECT count(1) INTO v_exam_cnt
	FROM exam_question as eq
	WHERE eq.exam_id = NEW.exam_id
	GROUP BY eq.exam_id;
	
	IF v_exam_cnt >=10 THEN 
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'each exam can contain maximum 10 questions';
	END IF;
	
END $$

DELIMITER ;

-- Question 5: Tạo trigger không cho phép người dùng xóa tài khoản có email là
-- admin@gmail.com (đây là tài khoản admin, không cho phép user xóa),
-- còn lại các tài khoản khác thì sẽ cho phép xóa và sẽ xóa tất cả các thông
-- tin liên quan tới user đó
DELIMITER $$

CREATE TRIGGER tg_delete_admin
BEFORE DELETE ON account
FOR EACH ROW
BEGIN 
	IF OLD.email = 'admin@gmail.com' THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'cannot delete admin account';
	ELSE 
		DELETE FROM group_account
		WHERE account_id = OLD.account_id;
	END IF;

END $$

DELIMITER ;

-- Question 6: Không sử dụng cấu hình default cho field DepartmentID của table
-- Account, hãy tạo trigger cho phép người dùng khi tạo account không điền
-- vào departmentID thì sẽ được phân vào phòng ban "waiting Department"
DELIMITER $$

CREATE TRIGGER tg_waiting_department
BEFORE INSERT ON account
FOR EACH ROW
BEGIN
	DECLARE v_id_waiting_dept INT;
	
	IF NEW.department_id IS NULL THEN
		SELECT d.department_id INTO v_id_waiting_dept
		FROM department d 
		WHERE d.department_name = 'waiting Department';
		
		SET NEW.department_id = v_id_waiting_dept;
	END IF;
END $$

DELIMITER ; 

-- Question 7: Cấu hình 1 bài thi chỉ cho phép user tạo tối đa 4 answers cho mỗi question,
-- trong đó có tối đa 2 đáp án đúng.
DELIMITER $$

CREATE TRIGGER tg_answer_limit
BEFORE INSERT ON answer
FOR EACH ROW
BEGIN
	DECLARE v_total_answer INT;
	DECLARE v_correct_total INT;

	SELECT count(1) INTO v_total_answer
	FROM answer as a
	WHERE a.question_id = NEW.question_id
	GROUP BY a.question_id;

	IF v_total_answer >= 4 THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Maximum 4 answers';
	END IF;

	IF NEW.is_correct = TRUE THEN
		SELECT count(1) INTO v_correct_total
		FROM answer as a
		WHERE a.answer_id = NEW.answer_id AND a.is_correct = TRUE;
		
		IF v_correct_total >= 2 THEN 
 			SIGNAL SQLSTATE '45000'
            		SET MESSAGE_TEXT='Maximum 2 correct answers';
		END IF;
	END IF;

END $$

DELIMITER ;

-- Question 8: Viết trigger sửa lại dữ liệu cho đúng:
-- Nếu người dùng nhập vào gender của account là nam, nữ, chưa xác định
-- Thì sẽ đổi lại thành M, F, U cho giống với cấu hình ở database
ALTER TABLE account ADD gender ENUM('M','F','U');

DELIMITER $$

CREATE TRIGGER trg_gender
BEFORE INSERT ON account
FOR EACH ROW
BEGIN
	IF LOWER(NEW.gender) = 'nữ' THEN
		SET NEW.gender = 'F';
	ELSEIF LOWER(NEW.gender) = 'nam' THEN
		SET NEW.gender = 'M';
	ELSEIF LOWER(NEW.gender) = 'chưa xác định' THEN
		SET NEW.gender = 'U';	
	END IF;
END $$

DELIMITER ;


-- Question 9: Viết trigger không cho phép người dùng xóa bài thi mới tạo được 2 ngày 
DELIMITER $$

CREATE TRIGGER trg_delete_exam
BEFORE DELETE ON exam
FOR EACH ROW
BEGIN 
	IF DATEDIFF(CURDATE(), OLD.create_date) < 2 THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Cannot delete exam within 2 days';
	END IF;
END $$

DELIMITER ;
-- Question 10: Viết trigger chỉ cho phép người dùng chỉ được update, delete các
-- question khi question đó chưa nằm trong exam nào
DELIMITER $$

CREATE TRIGGER trg_update_question
BEFORE UPDATE ON question
FOR EACH ROW
BEGIN 
	IF EXISTS (SELECT 1 FROM exam_question WHERE OLD.question_id = question_id) THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT='Question is already used in Exam';
	END IF;
END $$	

CREATE TRIGGER trg_delete_question
BEFORE DELETE ON question
FOR EACH ROW
BEGIN 
	IF EXISTS (SELECT 1 FROM exam_question WHERE OLD.question_id = question_id) THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT='Question is already used in Exam';
	END IF;
END $$	

DELIMITER ;


-- Question 12: Lấy ra thông tin exam trong đó:
-- Duration <= 30 thì sẽ đổi thành giá trị "Short time"
-- 30 < Duration <= 60 thì sẽ đổi thành giá trị "Medium time"
-- Duration > 60 thì sẽ đổi thành giá trị "Long time"
SELECT exam_id
     , title
     , CASE WHEN TIME_TO_SEC(duration)<=1800 THEN 'Short time' 
	    WHEN TIME_TO_SEC(duration)<=3600 THEN 'Medium time'
	    ELSE 'Long time' END AS duration_type 
FROM exam;


-- Question 13: Thống kê số account trong mỗi group và in ra thêm 1 column nữa có tên
-- là the_number_user_amount và mang giá trị được quy định như sau:
-- Nếu số lượng user trong group =< 5 thì sẽ có giá trị là few
-- Nếu số lượng user trong group <= 20 và > 5 thì sẽ có giá trị là normal
-- Nếu số lượng user trong group > 20 thì sẽ có giá trị là higher
WITH group_cnt AS (
	SELECT g.group_id
	     , count(ga.group_id) as cnt
	FROM group_account AS ga
	RIGHT JOIN `group` as g ON g.group_id = ga.group_id
	GROUP BY g.group_id
	ORDER BY cnt
) 
SELECT *
     , CASE WHEN cnt <= 5 THEN 'few'
            WHEN cnt <= 20 THEN 'normal'
            ELSE 'higher' END AS the_number_user_amount
FROM group_cnt 
;
-- Question 14: Thống kê số mỗi phòng ban có bao nhiêu user, nếu phòng ban nào
-- không có user thì sẽ thay đổi giá trị 0 thành "Không có User"
WITH department_cnt AS (
	SELECT d.department_id
	     , COUNT(a.department_id) AS cnt
	FROM department AS d
	LEFT JOIN account AS a ON d.department_id = a.department_id
	GROUP BY d.department_id
	ORDER BY cnt
) 
SELECT department_id
     , CASE WHEN cnt = 0 THEN 'Không có User' ELSE cnt END AS so_luong
FROM department_cnt
;
