DROP TABLE IF EXISTS me_user CASCADE;
DROP TABLE IF EXISTS me_input_user;
DROP TABLE IF EXISTS me_theme CASCADE;
DROP TABLE IF EXISTS me_subtheme CASCADE;
DROP TABLE IF EXISTS me_task;
DROP TABLE IF EXISTS me_user_theme;

CREATE TABLE me_user
(
    user_id serial,
    first_name  varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    password text NOT NULL,
    school_class integer NOT NULL,
    school_number integer NOT NULL,
    date_of_registration date,
    PRIMARY KEY (user_id)  
);
INSERT INTO me_user( first_name, last_name, password, school_class, school_number, date_of_registration)
VALUES ( 'Роман', 'Артемьев', 'rgb', 11, 381, current_date ),
       ( 'Иван', 'Иванов', 'cmyk', 10, 234, current_date );
           
CREATE TABLE me_input_user
(
    input_id serial,
    input_date date NOT NULL,
    user_id integer NOT NULL,
    tasks_solved_correctly integer[],
    tasks_solved_incorrectly integer[],
    PRIMARY KEY (input_id),
    FOREIGN KEY (user_id)
    REFERENCES me_user (user_id) 
    ON DELETE CASCADE 
);
INSERT INTO me_input_user( input_date, user_id, tasks_solved_correctly, tasks_solved_incorrectly )
VALUES ( '2019-04-22'::date, 1, '{ 1, 3, 5 }'::integer[], '{ 2 }'::integer[]),
       ( '2019-04-25'::date, 2,  '{ 1, 2 }'::integer[], NULL),
       ( '2019-04-23'::date, 2,  NULL, '{ 1, 2, 3 }'::integer[]);
      
CREATE TABLE me_theme
(
    theme_id serial,
    theme_title  varchar(100) NOT NULL,
    brief_theoretical_information text,
    PRIMARY KEY (theme_id)  
);
INSERT INTO me_theme( theme_title, brief_theoretical_information )
VALUES ( 'Тригонометрия', 'Это шибко сложно'),
       ( 'Прогрессии', 'Ну это ещё ничего...');

CREATE TABLE me_subtheme
(
    subtheme_id serial,
    subtheme_title  varchar(100) NOT NULL,
    theme_id integer NOT NULL,
    PRIMARY KEY (subtheme_id),
    FOREIGN KEY (theme_id)
    REFERENCES me_theme (theme_id) 
    ON DELETE CASCADE 
);
INSERT INTO me_subtheme( subtheme_title, theme_id )
VALUES ( 'Косинусы', 1),
       ( 'Синусы', 1);

CREATE TABLE me_task
(
    task_id serial,
    subtheme_id integer NOT NULL,
    description text NOT NULL,
    answer varchar(100) NOT NULL,
    PRIMARY KEY (task_id),
    FOREIGN KEY (subtheme_id)
    REFERENCES me_subtheme (subtheme_id) 
    ON DELETE CASCADE 
);
INSERT INTO me_task( subtheme_id, description, answer )
VALUES ( 2, 'Чему равен синус угла 90 градусов?', '0' );

CREATE TABLE me_user_theme
(
    user_id integer NOT NULL,
    theme_id integer NOT NULL,
    solve_task integer NOT NULL,
    actual boolean NOT NULL,
    last_solved_task date,
    PRIMARY KEY (user_id, theme_id),  
    FOREIGN KEY (user_id)
    REFERENCES me_user (user_id) 
    ON DELETE CASCADE,
    FOREIGN KEY (theme_id)
    REFERENCES me_theme (theme_id) 
    ON DELETE CASCADE
);
INSERT INTO me_user_theme( user_id, theme_id, solve_task, actual, last_solved_task )
VALUES ( 2, 1, 10, TRUE, null ),
       ( 1, 1, 10, FALSE, null ),
       ( 1, 2, 10, TRUE, null );
