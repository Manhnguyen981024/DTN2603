-- Question 1: Tạo store để người dùng nhập vào tên phòng ban và in ra tất cả các
-- account thuộc phòng ban đó.
DELIMITER $$

CREATE PROCEDURE
    sp_print_account_in_dept(IN p_department_name VARCHAR(100))
BEGIN
    SELECT a.*
    FROM account        AS a
        JOIN department AS p
            ON  a.department_id = p.department_id
    WHERE p.department_name     = p_department_name;

END $$

DELIMITER ;

CALL sp_print_account_in_dept('Devops') ;

-- Question 2: Tạo store để in ra số lượng account trong mỗi group.
DELIMITER $$

CREATE PROCEDURE
    sp_accounts_per_groups()
BEGIN
    SELECT g.group_name
        , COUNT(a.account_id) AS so_luong
    FROM `group`              AS g
        LEFT JOIN account     AS a
            ON  g.creator_id = a.account_id
    GROUP BY g.group_id;

END $$

DELIMITER;

CALL sp_accounts_per_groups();

-- Question 3: Tạo store để thống kê mỗi type question có bao nhiêu question được tạo
-- trong tháng hiện tại.
DELIMITER $$

CREATE PROCEDURE
    sp_questions_in_month()
BEGIN
    SELECT tq.type_name
        , COUNT(q.type_id) AS so_luong
    FROM type_question     AS tq
        LEFT JOIN question AS q
            ON  tq.type_id     = q.type_id
    WHERE MONTH(q.create_date) = MONTH(CURRENT_DATE)
    GROUP BY tq.type_id;

END $$

DELIMITER;

CALL sp_questions_in_month();

-- Question 4: Tạo store để trả ra id của type question có nhiều câu hỏi nhất.
--DROP PROCEDURE IF EXISTS
--sp_get_type_question_with_most_questions;

DELIMITER $$

CREATE PROCEDURE
    sp_get_type_question_with_most_questions(OUT p_type_id INT)
BEGIN
    WITH cte_type_question AS
        (
            SELECT tq.type_id
                , COUNT(q.question_id) AS so_luong
            FROM type_question         AS tq
                JOIN question          AS q
                    ON  tq.type_id = q.type_id
            GROUP BY tq.type_id
        )
        , cte_max_type_question AS
        (
            SELECT MAX(a.so_luong) AS so_luong
            FROM cte_type_question AS a
        )
    SELECT tq.type_id
    INTO
        p_type_id
    FROM type_question         AS tq
        JOIN cte_type_question AS ctq
            ON  tq.type_id = ctq.type_id
        JOIN cte_max_type_question AS cmt
            ON  ctq.so_luong = cmt.so_luong;

END $$

DELIMITER;

CALL sp_get_type_question_with_most_questions(@type_id) ;

-- Question 5: Sử dụng store ở question 4 để tìm ra tên của type question
SELECT *
FROM type_question AS tq
WHERE tq.type_id = @type_id;

-- Question 6: Viết 1 store cho phép người dùng nhập vào 1 chuỗi và trả về
-- group có tên chứa chuỗi của người dùng nhập vào hoặc trả về user có username chứa chuỗi của người dùng nhập vào.
DROP PROCEDURE
IF EXISTS sp_get_group_account_name;

DELIMITER $$

CREATE PROCEDURE
    sp_get_group_account_name(IN p_name VARCHAR(100))
BEGIN
    SELECT g.group_name AS found_name
    FROM `group`        AS g
    WHERE g.group_name LIKE CONCAT('%' , p_name , '%')
    
    UNION
    
    SELECT a.username AS found_name
    FROM `account`    AS a
    WHERE a.username LIKE CONCAT('%' , p_name , '%') ;

END $$;

DELIMITER ;

CALL sp_get_group_account_name('group') ;

-- Question 7: Viết 1 store cho phép người dùng nhập vào thông tin fullName, email và trong store sẽ tự động gán:
-- username sẽ giống email nhưng bỏ phần @..mail đi
-- positionID: sẽ có default là developer
-- departmentID: sẽ được cho vào 1 phòng chờ
-- Sau đó in ra kết quả tạo thành công
DROP PROCEDURE IF EXISTS sp_insert_account;

DELIMITER $$

CREATE PROCEDURE
    sp_insert_account(IN p_fullname VARCHAR(100)
                      , IN p_email  VARCHAR(100))
BEGIN
    DECLARE v_dev_id INT;
    DECLARE v_username VARCHAR(100) ;
    
    SELECT p.position_id
    INTO
        v_dev_id
    FROM `position` AS p
    WHERE p.position_name = 'DEV';
    
    SELECT SUBSTRING_INDEX(p_email , '@' , 1)
    INTO
        v_username;
    
    INSERT INTO account
        (
            fullname
            , email
            , username
            , position_id
        )
        VALUES
        (
            p_fullname
            , p_email
            , v_username
            , v_dev_id
        ) ;
    
    SELECT 'Tạo thành công' AS MESSAGE;

END $$
DELIMITER;

CALL sp_insert_account('manhnguyen' , 'manh1@gmail.com') ;


-- Question 8: Viết 1 store cho phép người dùng nhập vào Essay hoặc Multiple-Choice
-- để thống kê câu hỏi essay hoặc multiple-choice nào có content dài nhất
-- drop PROCEDURE sp_get_questions_with_longest_content;

DELIMITER $$

CREATE PROCEDURE
    sp_get_questions_with_longest_content(IN p_type_name VARCHAR(20))
BEGIN
    DECLARE v_type_id INT;
    
    IF p_type_name NOT IN('ESSAY' , 'MULTIPLE_CHOICE') THEN
        SELECT 'Invalid Question Type' AS MESSAGE;
    
    ELSE
        SELECT type_id INTO v_type_id
        FROM type_question
        WHERE type_name = UPPER(p_type_name);
        
        WITH cte_question_length AS
            (
                SELECT MAX(LENGTH(content)) AS chieu_dai_content
                    , q.type_id
                FROM question AS q
                WHERE q.type_id = v_type_id
                GROUP BY q.type_id
            )
        SELECT q.*
            , cq.chieu_dai_content
        FROM question                AS q
            JOIN cte_question_length AS cq
                ON  q.type_id             = cq.type_id
                    AND LENGTH(q.content) = cq.chieu_dai_content ;
    
    END IF;
END $$

DELIMITER;

CALL sp_get_questions_with_longest_content('essay') ;

-- Question 9: Viết 1 store cho phép người dùng xóa exam dựa vào ID
DROP PROCEDURE IF EXISTS sp_delete_exam_by_id;

DELIMITER $$

CREATE PROCEDURE
    sp_delete_exam_by_id(IN p_exam_id                  INT
                         , OUT p_exam_question_deleted INT
                         , OUT p_exam_deleted          INT)
BEGIN
    IF NOT EXISTS(
            SELECT 1
            FROM exam
            WHERE exam_id = p_exam_id
        ) THEN
        SELECT 'Exam id is not exists' AS MESSAGE;
    ELSE
        DELETE
        FROM exam_question
        WHERE exam_id = p_exam_id;

	SET p_exam_question_deleted = ROW_COUNT();
        
        DELETE
        FROM exam
        WHERE exam_id = p_exam_id;
     
     	SET p_exam_deleted = ROW_COUNT();

	SELECT 'Delete successfully.' AS MESSAGE;
    END IF;

END $$

DELIMITER;

CALL sp_delete_exam_by_id(1 , @exam_question_deleted , @exam_deleted) ;
SELECT @exam_question_deleted, @exam_deleted;

-- Question 10: Tìm ra các exam được tạo từ 3 năm trước và xóa các exam đó đi
-- (sử dụng store ở câu 9 để xóa)
-- Sau đó in số lượng record đã remove từ các table liên quan trong khi removing
--DROP PROCEDURE sp_delete_exams_older_than_3_years ;
DELIMITER $$

CREATE PROCEDURE
    sp_delete_exams_older_than_3_years()
BEGIN
    	DECLARE done INT DEFAULT FALSE;
	DECLARE v_exam_id INT;
	DECLARE v_exam_question_deleted INT;
	DECLARE v_exam_deleted INT;
	DECLARE v_exam_question_total_deleted INT DEFAULT 0;
	DECLARE v_exam_total_deleted INT DEFAULT 0;

	DECLARE exam_cursor CURSOR FOR
	SELECT exam_id
	FROM exam
	WHERE create_date < DATE_SUB(NOW() , INTERVAL 3 YEAR) ;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET DONE = TRUE;

	OPEN exam_cursor;

	read_loop: LOOP
	    FETCH exam_cursor INTO v_exam_id;
		
	    IF done THEN
		leave read_loop;
	    END IF;

	    CALL sp_delete_exam_by_id(v_exam_id , v_exam_question_deleted , v_exam_deleted) ;

	    SET v_exam_question_total_deleted = v_exam_question_total_deleted + v_exam_question_deleted;
            
            SET v_exam_total_deleted = v_exam_total_deleted + v_exam_deleted;

	END LOOP;

	CLOSE exam_cursor;
	
	SELECT v_exam_question_total_deleted AS so_luong_exam_question_deleted
	    , v_exam_total_deleted           AS so_luong_exam_deleted;
END $$

DELIMITER;

CALL sp_delete_exams_older_than_3_years();

-- Question 11: Viết store cho phép người dùng xóa phòng ban bằng cách
-- người dùng nhập vào tên phòng ban và các account thuộc phòng ban đó sẽ được
-- chuyển về phòng ban default là phòng ban chờ việc
DELIMITER $$

CREATE PROCEDURE
    sp_delete_department_by_name(IN p_department_name VARCHAR(100))
BEGIN
    	DECLARE v_department_id INT;

	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
	    
	    ROLLBACK;
	
	END;


	SELECT department_id INTO v_department_id
	FROM department
	WHERE department_name = p_department_name;

	IF v_department_id IS NULL THEN
	    
	    SELECT 'Department name not found!' AS MESSAGE;
	
	ELSE
	    START TRANSACTION;

	    UPDATE account
	    SET department_id   = NULL
            WHERE department_id = v_department_id;

	    DELETE
	    FROM department
	    WHERE department_id = v_department_id;
    
    	    COMMIT;

	    SELECT 'Delete successfully' AS MESSAGE;
	END IF;
END $$

DELIMITER;

CALL sp_delete_department_by_name('Sale') ;

-- Question 12: Viết store để in ra mỗi tháng có bao nhiêu câu hỏi được tạo trong năm nay
DELIMITER $$

CREATE PROCEDURE
    sp_get_created_question_per_month()
BEGIN
    WITH RECURSIVE months AS
        (
            SELECT 1 AS month_no
            
            UNION ALL
            
            SELECT month_no + 1
            FROM months
            WHERE month_no < 12
        )
    SELECT m.month_no
        , COUNT(q.question_id) AS question_count
    FROM months                AS m
        LEFT JOIN question     AS q
            ON  MONTH(q.create_date)    = m.month_no
                AND YEAR(q.create_date) = YEAR(curdate())
    GROUP BY m.month_no
    ORDER BY m.month_no;
END $$

DELIMITER;

CALL sp_get_created_question_per_month();

-- Question 13: Viết store để in ra mỗi tháng có bao nhiêu câu hỏi được tạo trong 6 tháng gần đây nhất
-- (Nếu tháng nào không có thì sẽ in ra là "không có câu hỏi nào trong tháng")
DELIMITER $$

CREATE PROCEDURE sp_get_question_six_months_recently() 
BEGIN
	WITH recursive cte_nums AS
	    (
	        SELECT 0 AS n
	        
	        UNION ALL
	        
	        SELECT n + 1 AS n
	        FROM cte_nums
	        WHERE n < 5
	    )
	SELECT DATE_FORMAT(DATE_SUB(CURDATE() , INTERVAL n MONTH) , '%Y-%m') AS month_name
	    , count(q.question_id) as so_luong
	FROM cte_nums                                                        AS seq
	    LEFT JOIN question                                               AS q 
	        ON  DATE_FORMAT(q.create_date , '%Y-%m') = DATE_FORMAT(DATE_SUB(CURDATE() , INTERVAL n MONTH) , '%Y-%m')
	GROUP BY month_name;

END $$

DELIMITER;

call sp_get_question_six_months_recently();
