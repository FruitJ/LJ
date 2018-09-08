-- 创建员工数据库(staffInfoManageDB)
CREATE DATABASE IF NOT EXISTS staffInfoManageDB CHARACTER SET utf8mb4;

-- 创建地区表(location)
CREATE TABLE IF NOT EXISTS location(
location_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
location_city NVARCHAR(20) NOT NULL,
location_country NVARCHAR(20) NOT NULL,
location_province NVARCHAR(20) NOT NULL
);
-- 创建部门表(department)
CREATE TABLE IF NOT EXISTS department(
department_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
department_name NVARCHAR(20) NOT NULL
);
ALTER TABLE department ADD COLUMN profession_id INT NOT NULL;
-- 为department_id字段绑定主键约束与自增
ALTER TABLE department MODIFY COLUMN department_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL;

ALTER TABLE department ADD COLUMN location_id INT NOT NULL;

-- 创建工种表
CREATE TABLE IF NOT EXISTS profession(
profession_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
profession_name NVARCHAR(20) NOT NULL
);


-- 创建员工信息表(staff)
CREATE TABLE IF NOT EXISTS staff(
staff_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
staff_name NVARCHAR(20) NOT NULL,
staff_age INT NOT NULL,
department_id INT NOT NULL,
sex CHAR(3) NOT NULL,
phone NVARCHAR(20) NOT NULL,
hobby NVARCHAR(20) NOT NULL
);
-- 向四张表添加数据

INSERT INTO department(department_name, profession_id, location_id)
VALUES('水利', 5, 2);
INSERT INTO department(department_name, profession_id, location_id)
VALUES('电力', 3, 3);
INSERT INTO department(department_name, profession_id, location_id)
VALUES('宣传', 9, 5);
INSERT INTO department(department_name, profession_id, location_id)
VALUES('策划', 8, 6);
INSERT INTO department(department_name, profession_id, location_id)
VALUES('IT', 3, 5);
INSERT INTO department(department_name, profession_id, location_id)
VALUES('食品', 5, 3);
INSERT INTO department(department_name, profession_id, location_id)
VALUES('药物', 6, 2);
INSERT INTO department(department_name, profession_id, location_id)
VALUES('IT', 2, 1);
INSERT INTO department(department_name, profession_id, location_id)
VALUES('策划', 1, 8);
INSERT INTO department(department_name, profession_id, location_id)
VALUES('电力', 4, 7);

-- 截断department表
TRUNCATE TABLE department;

-- 为工种表(profession)添加数据
INSERT INTO profession(profession_name) VALUES('water');
INSERT INTO profession(profession_name) VALUES('electric');
INSERT INTO profession(profession_name) VALUES('propaganda');
INSERT INTO profession(profession_name) VALUES('Internet');
INSERT INTO profession(profession_name) VALUES('medicine');
INSERT INTO profession(profession_name) VALUES('Plan');
INSERT INTO profession(profession_name) VALUES('Internet');
INSERT INTO profession(profession_name) VALUES('medicine');
INSERT INTO profession(profession_name) VALUES('propaganda');
INSERT INTO profession(profession_name) VALUES('electric');


-- 为location表添加数据
INSERT INTO location(location_city, location_country, location_province)
VALUES('承德' ,'china' ,'河北');
INSERT INTO location(location_city, location_country, location_province)
VALUES('南京' ,'china' ,'河北');
INSERT INTO location(location_city, location_country, location_province)
VALUES('uion' ,'us' ,'firstPro');
INSERT INTO location(location_city, location_country, location_province)
VALUES('verion' ,'un' ,'secondPro');
INSERT INTO location(location_city, location_country, location_province)
VALUES('厦门' ,'china' ,'福建');
INSERT INTO location(location_city, location_country, location_province)
VALUES('洛阳' ,'china' ,'河南');
INSERT INTO location(location_city, location_country, location_province)
VALUES('sina' ,'us' ,'xinlang');
INSERT INTO location(location_city, location_country, location_province)
VALUES('king' ,'us' ,'tencet');
INSERT INTO location(location_city, location_country, location_province)
VALUES('呼和浩特' ,'china' ,'内蒙古');
INSERT INTO location(location_city, location_country, location_province)
VALUES('沈阳' ,'china' ,'辽宁');

-- 截断staff表
INSERT INTO staff(staff_name, staff_age, department_id, sex, phone, hobby, salary)
VALUES('杨过','22','2','男','15732408538','喂雕', 2000);
INSERT INTO staff(staff_name, staff_age, department_id, sex, phone, hobby, salary)
VALUES('郭靖','19','1','男','15732408537','看电视', 3500);
INSERT INTO staff(staff_name, staff_age, department_id, sex, phone, hobby, salary)
VALUES('赵敏','32','5','女','15732408536','逛街', 4000);
INSERT INTO staff(staff_name, staff_age, department_id, sex, phone, hobby, salary)
VALUES('小龙女','16','2','女','15732408535','走路', 1500);
INSERT INTO staff(staff_name, staff_age, department_id, sex, phone, hobby, salary)
VALUES('黄蓉','21','1','女','15732408534','浇花', 3000);
INSERT INTO staff(staff_name, staff_age, department_id, sex, phone, hobby, salary)
VALUES('温家宝','22','3','男','15732408531','看新闻', 2500);
INSERT INTO staff(staff_name, staff_age, department_id, sex, phone, hobby, salary)
VALUES('刘少奇','16','5','男','15732408528','看风景', 3400);
INSERT INTO staff(staff_name, staff_age, department_id, sex, phone, hobby, salary)
VALUES('彭丽媛','21','5','女','15732408526','看书', 1000);
INSERT INTO staff(staff_name, staff_age, department_id, sex, phone, hobby, salary)
VALUES('范冰冰','26','1','女','15732408527','出差', 1500);
INSERT INTO staff(staff_name, staff_age, department_id, sex, phone, hobby, salary)
VALUES('张无忌','21','3','男','15732408539','练舞', 4500);

SELECT * FROM staff WHERE staff_name = 'XXY';