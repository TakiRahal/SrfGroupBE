
-- Add all authorities
INSERT INTO sg_authority (name)
SELECT 'ROLE_SUPER_ADMIN'
WHERE NOT EXISTS
    (SELECT name
     FROM sg_authority
     WHERE name = 'ROLE_SUPER_ADMIN');

INSERT INTO sg_authority (name)
SELECT 'ROLE_ADMIN'
WHERE NOT EXISTS
    (SELECT name
     FROM sg_authority
     WHERE name = 'ROLE_ADMIN');

INSERT INTO sg_authority (name)
SELECT 'ROLE_USER'
WHERE NOT EXISTS
    (SELECT name
     FROM sg_authority
     WHERE name = 'ROLE_USER');




-- Add super admin user

INSERT INTO sg_user (id,first_name,last_name,username,email,activated_account,image_url,activation_key,reset_key,phone,source_register,password,lang_key,link_profile_facebook,blocked_by_admin)
SELECT '1','Srf','Group','srfgroup.contact@gmail.com','srfgroup.contact@gmail.com','true','image','123456789','123456789','0021624158860','WebBrowser','$2a$10$4Ba5qhmFQ14vhIwrYXNDA.Wvs/3zAkwt.u19Ceqg9hHyTn/1SOBri','en','https://www.facebook.com/profile.php?id=100054409273167', 'false'
WHERE NOT EXISTS
    (SELECT email
     FROM sg_user
     WHERE email = 'srfgroup.contact@gmail.com');



-- Add user_authority

INSERT INTO user_authority (user_id,authority_name)
SELECT '1','ROLE_SUPER_ADMIN'
WHERE NOT EXISTS
    (SELECT *
     FROM user_authority
     WHERE user_id = '1' AND authority_name='ROLE_SUPER_ADMIN');