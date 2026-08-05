-- Question 1: Viết lệnh để lấy ra danh sách nhân viên và thông tin phòng ban của họ
SELECT acc.*
    , dep.department_name
FROM account             AS acc
    LEFT JOIN department AS dep
        ON     acc.department_id = dep.department_id ;

-- Question 2: Viết lệnh để lấy ra thông tin các account được tạo sau ngày 20/12/2010
SELECT acc.*
FROM account AS acc
WHERE acc.create_date > '2010-12-20' ;

-- Question 3: Viết lệnh để lấy ra tất cả các developer
SELECT acc.*
    , pos.position_name
FROM account             AS acc
    LEFT JOIN `position` AS pos
        ON     acc.position_id = pos.position_id
WHERE pos.position_name        = 'DEV' ;

--Question 4: Viết lệnh để lấy ra danh sách các phòng ban có >3 nhân viên
SELECT dep.department_name
    , COUNT(acc.department_id) AS so_luong
FROM account                   AS acc
    RIGHT JOIN department      AS dep
        ON     acc.department_id = dep.department_id
GROUP BY dep.department_id
HAVING COUNT(acc.department_id) > 3 ;

--Question 5: Viết lệnh để lấy ra danh sách câu hỏi được sử dụng trong đề thi nhiều nhất
SELECT qu.content
    , COUNT(exq.question_id)     AS  so_luong
FROM question                    AS  qu
    JOIN exam_questionAS             exq
        ON     qu.question_id = exq.question_id
GROUP BY exq.question_id
HAVING so_luong =(
        SELECT MAX(total)
        FROM(
                SELECT COUNT(1)    AS total
                FROM exam_question AS exq
                GROUP BY question_id
            ) AS t
    ) ;

--Question 6: Thông kê mỗi category Question được sử dụng trong bao nhiêu Question
SELECT cq.category_name
    , COUNT(1)                AS so_luong
FROM questionAS                  q
    JOIN category_question    AS cq
        ON     cq.category_id = q.category_id
GROUP BY q.category_id;

--Question 7: Thống kê mỗi Question được sử dụng trong bao nhiêu Exam
SELECT rs.contentASquestion
    , COUNT(rs.question_id) AS so_luong
FROM(
        SELECT exq.exam_id
            , q.content
            , q.question_id
        FROM questionAS                   q
            JOIN test.exam_question    AS exq
                ON      q.question_id = exq.question_id
        GROUP BY exq.exam_id
            , exq.question_id
    ) AS rs
GROUP BY rs.question_id ;

--Question 8: Lấy ra Question có nhiều câu trả lời nhất
SELECT q.content         AS question
    , COUNT(a.answer_id) AS cnt
FROM test.answer         AS a
    JOIN test.question   AS q
        ON     a.question_id = q.question_id
GROUP BY a.question_id
HAVING cnt =(
        SELECT MAX(rs.cnt)
        FROM(
                SELECT q.question_id
                    , COUNT(q.answer_id)    AS cnt
                FROM answerAS                  q
                GROUP BY q.question_id
            ) AS rs
    ) ;

--Question 9: Thống kê số lượng account trong mỗi group
SELECT g.group_name
    , COUNT(account_id)  AS so_luong_account
FROM test.group_account  AS ga
    JOIN `group`         AS g
        ON    g.group_id = ga.group_id
GROUP BY ga.group_id ;


--Question 10: Tìm chức vụ có ít người nhất
SELECT    d.department_name
    , SUM(CASE WHEN p.position_name = 'DEV'     THEN 1 ELSE 0 END)           AS DEV
    , SUM(CASE WHEN p.position_name = 'TEST'    THEN 1 ELSE 0 END)           AS TEST
    , SUM(CASE WHEN p.position_name = 'SCRUM_MASTER'   THEN 1 ELSE 0 END)    AS SCRUM_MASTER
    , SUM(CASE WHEN p.position_name = 'PM'      THEN 1 ELSE 0 END)           AS PM
FROM department                                                                 d
    LEFT JOIN account                                                        AS a
        ON   d.department_id = a.department_id
    LEFT JOIN position p
        ON   a.position_id = p.position_id
GROUP BY    d.department_id
    , d.department_name;

--Question 12: Lấy thông tin chi tiết của câu hỏi bao gồm: thông tin cơ bản của question,
--  loại câu hỏi, ai là người tạo ra câu hỏi, câu trả lời là gì, …
SELECT q.question_id
    , q.content
    , cq.category_name
    , t.type_name
    , a.fullname
    , ans.content
    , ans.is_correct
    , q.create_date
FROM question                   AS q
    JOIN category_question      AS cq
        ON  cq.category_id = q.category_id
    JOIN type_question AS t
        ON  t.type_id = q.type_id
    JOIN account AS a
        ON  a.account_id = q.creator_id
    JOIN answer AS ans
        ON   q.question_id = ans.question_id
ORDER BY q.question_id ;

--Question 13: Lấy ra số lượng câu hỏi của mỗi loại tự luận hay trắc nghiệm
--Question 14: Lấy ra group không có account nào
--Question 15: Lấy ra group không có account nào
--Question 16: Lấy ra question không có answer nào.
