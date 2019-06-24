INSERT INTO me_user( user_id, first_name, last_name, password, school_class, school_number, date_of_registration)
VALUES ( 0, 'Фамилия', 'Имя', 'Пароль', 0, 0, current_date );

INSERT INTO me_theme( theme_id, theme_title, brief_theoretical_information )
VALUES (0,  'Название_темы', 'Описание');

INSERT INTO me_subtheme( subtheme_id, subtheme_title, theme_id )
VALUES (0, 'Название_подтемы', 0);

INSERT INTO me_task( task_id, subtheme_id, description, answer )
VALUES (0,  0, 'Описание_задания', 'Ответ' );