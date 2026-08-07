-- Question 1: Tạo view có chứa danh sách nhân viên thuộc phòng ban sale
CREATE VIEW v_account_sale AS
WITH cte_sales_department  AS
    (
        SELECT d.department_id
        FROM department AS d
        WHERE d.department_name = 'Sales'
    )
SELECT a.*
FROM account                  AS a
    JOIN cte_sales_department AS b
        ON  a.department_id = b.department_id ;

-- subquery
SELECT a.*
FROM account AS a
WHERE a.department_id =(
        SELECT d.department_id
        FROM department AS d
        WHERE d.department_name = 'Sales'
    ) ;

-- Question 2: Tạo view có chứa thông tin các account tham gia vào nhiều group nhất
CREATE VIEW v_account_join_most_group AS
WITH cte_account_in_group             AS
    (
        SELECT ga.account_id
            , COUNT(ga.group_id) AS so_luong
        FROM group_account       AS ga
        GROUP BY ga.account_id
    )
    , cte_max_group AS
    (
        SELECT MAX(so_luong)      AS so_luong
        FROM cte_account_in_group AS c
    )
SELECT a.*
    , ca.so_luong             AS tong_so_luong_group
FROM account                  AS a
    JOIN cte_account_in_group AS ca
        ON  a.account_id = ca.account_id
    JOIN cte_max_group AS cmg
        ON  ca.so_luong = cmg.so_luong;

-- subquery
SELECT a.*
    , COUNT(ga.group_id) AS tong_so_luong_group
FROM account             AS a
    JOIN group_account   AS ga
        ON  a.account_id = ga.account_id
GROUP BY a.account_id
HAVING tong_so_luong_group =(
        SELECT MAX(so_luong)
        FROM(
                SELECT COUNT(1) AS so_luong
                FROM group_account
                GROUP BY account_id
            ) AS t
    ) ;

-- Question 3: Tạo view có chứa câu hỏi có những content quá dài (content quá 300 từ
-- được coi là quá dài) và xóa nó đi
CREATE VIEW v_long_question AS
SELECT *
FROM question
WHERE LENGTH(content) > 300;

WITH long_question AS
    (
        SELECT question_id
        FROM question
        WHERE LENGTH(content) > 300
    )
DELETE
FROM question
WHERE question_id IN(
        SELECT question_id
        FROM long_question
    ) ;

-- Question 4: Tạo view có chứa danh sách các phòng ban có nhiều nhân viên nhất
CREATE VIEW v_department_accout AS
WITH cte_department_account     AS
    (
        SELECT department_id
            , COUNT(1) AS so_luong
        FROM account   AS a
        GROUP BY a.department_id
    )
    , cte_max_account AS
    (
        SELECT MAX(so_luong) AS so_luong
        FROM cte_department_account
    )
SELECT p.*
    , cd.so_luong
FROM department                 AS p
    JOIN cte_department_account AS cd
        ON  p.department_id = cd.department_id
    JOIN cte_max_account AS cma
        ON  cd.so_luong = cma.so_luong;

-- sub-query
SELECT d.*
    , COUNT(1)   AS so_luong
FROM department  AS d
    JOIN account AS a
        ON  d.department_id = a.department_id
GROUP BY d.department_id
HAVING so_luong =(
        SELECT MAX(t.so_luong) AS so_luong
        FROM(
                SELECT department_id
                    , COUNT(1) AS so_luong
                FROM account
                GROUP BY department_id
            ) AS t
    ) ;

-- Question 5: Tạo view có chứa tất các các câu hỏi do user họ Nguyễn tạo.
CREATE VIEW v_question_account AS
WITH cte_account               AS
    (
        SELECT *
        FROM account AS a
        WHERE a.fullname LIKE 'Nguyễn%'
    )
SELECT q.*
FROM question        AS q
    JOIN cte_account AS ca
        ON  q.creator_id = ca.account_id;

-- sub-query
SELECT q.*
FROM question AS q
WHERE q.creator_id IN(
        SELECT a.account_id
        FROM account AS a
        WHERE a.fullname LIKE 'Nguyễn%'
    )
