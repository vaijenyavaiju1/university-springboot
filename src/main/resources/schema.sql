CREATE TABLE IF NOT EXISTS professor (
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(255),
    department varchar(255)
);

CREATE TABLE IF NOT EXISTS student(
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(255),
    email varchar(255)
);




CREATE TABLE IF NOT EXISTS course (
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(255),
    credits int ,
    professorId int ,
    FOREIGN KEY(professorId) REFERENCES professor(id)
);





CREATE TABLE IF NOT EXISTS course_student(
    courseId int ,
    studentId int ,
    PRIMARY KEY(courseId,studentId),
    FOREIGN KEY(courseId) REFERENCES course(id),
    FOREIGN KEY (studentId) REFERENCES student(id)
);