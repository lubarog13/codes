CREATE USER 'user'@'localhost' IDENTIFIED BY '1234';
GRANT select ON sea_cruise.* TO 'user'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'user'@'localhost';

CREATE USER 'admin'@'localhost' IDENTIFIED BY '1234';
GRANT select, insert, update ON sea_cruise.* TO 'admin'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'admin'@'localhost';

CREATE USER 'booker'@'localhost' IDENTIFIED BY '1234';
GRANT select ON sea_cruise.* TO 'booker'@'localhost';
GRANT  insert, update ON sea_cruise.conducted_excursions TO 'booker'@'localhost';
GRANT  insert, update ON  sea_cruise.booking TO 'booker'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'booker'@'localhost';

CREATE USER 'motorship_owner'@'localhost' IDENTIFIED BY '1234';
GRANT select ON sea_cruise.* TO 'motorship_owner'@'localhost';
GRANT  insert, update ON sea_cruise.motorship TO 'motorship_owner'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'motorship_owner'@'localhost';

Delimiter //
create trigger trigger_insert_booking
before insert
on booking
for each row
begin
if new.first_cat_solding_tickets > (select tickets_numb_sold_by_first_cat from conducted_excursions where new.conducted_excursions_id_excursion = id_excursions)
then
kill connection_id();
end if;
if new.second_cat_solding_tickets > (select tickets_numb_sold_by_second_cat from conducted_excursions where new.conducted_excursions_id_excursion = id_excursions)
then
kill connection_id();
end if;
if new.third_cat_solding_tickets > (select tickets_numb_sold_by_third_cat from conducted_excursions where new.conducted_excursions_id_excursion = id_excursions)
then
kill connection_id();
end if;
end //
Delimiter ;

Delimiter //
 create trigger insert_cruise_route
 before insert
 on cruise_route
 for each row
 begin
 if new.number_of_days < 0 or new.total_length <= 0 or new.number_of_stops < 0 then
 kill connection_id();
end if;
end //
Delimiter ;
insert into cruise_route values ('Atlantic ocea trip', -12, 0, -5);
insert into cruise_route values ('Atlantic ocea trip', 12, 1000, 5);

Delimiter //
 create trigger update_cruise_route
 before update
 on cruise_route
 for each row
 begin
 if new.number_of_days < 0 or new.total_length <= 0 or new.number_of_stops < 0 then
 kill connection_id();
end if;
end //
Delimiter ;

update cruise_route set total_length = -10;

Delimiter //
 create trigger insert_cond_exc
 before insert
 on conducted_excursions
 for each row
 begin
 if new.tickets_numb_sold_by_first_cat < 0 or new.tickets_numb_sold_by_second_cat < 0 or new.tickets_numb_sold_by_third_cat < 0 or departure_date > current_date() then
 kill connection_id();
end if;
end //
Delimiter ;

Delimiter //
 create trigger update_cond_exc
 before update
 on conducted_excursions
 for each row
 begin
 if new.tickets_numb_sold_by_first_cat < 0 or new.tickets_numb_sold_by_second_cat < 0 or new.tickets_numb_sold_by_third_cat < 0 or departure_date > current_date() then
 kill connection_id();
end if;
end //
Delimiter ;

Delimiter //
 create trigger insert_motorship
 before insert
 on motorship
 for each row
 begin
 if new.`ticket_price_by_first cat` < 0 or new.ticket_price_by_second_cat < 0 or new.ticket_price_by_third_cat < 0 or date_of_issue > current_date() 
 or new.service_life<0 or team_members_number<0 then
 kill connection_id();
end if;
end //
Delimiter ;

Delimiter //
 create trigger update_motorship
 before update
 on motorship
 for each row
 begin
 if new.`ticket_price_by_first cat` < 0 or new.ticket_price_by_second_cat < 0 or new.ticket_price_by_third_cat < 0 or date_of_issue > current_date() 
 or new.service_life<0 or team_members_number<0 then
 kill connection_id();
end if;
end //
Delimiter ;

Delimiter //
 create trigger delete_motorship
 before delete
 on motorship
 for each row
 begin
 delete from conducted_excursions where motorship_motorship_name = old.motorship_name;
end //
Delimiter ;

Delimiter //
 create trigger delete_cruise
 before delete
 on cruise_route
 for each row
 begin
 delete from conducted_excursions where cruise_route_cruise_name = old.cruise_name;
end //
Delimiter ;

Delimiter //
 create trigger delete_cond
 before delete
 on conducted_excursions
 for each row
 begin
 delete from booking where conducted_excursions_id_excursions = old.id_excursions;
end //
Delimiter ;
drop table money;
drop procedure money_count;
Delimiter //
create procedure money_count( percent int, st_date date, end_date date)
begin
create temporary table money (motorship_name varchar(45), cruise_date datetime, cost float);
insert into money select motorship_name, departure_date, (`ticket_price_by_first cat`*tickets_numb_sold_by_first_cat + ticket_price_by_second_cat*tickets_numb_sold_by_second_cat 
+ ticket_price_by_third_cat*tickets_numb_sold_by_third_cat)*percent*0.01 from motorship join conducted_excursions on motorship_name = motorship_motorship_name 
where departure_date between st_date and end_date;
select * from money;
drop table money;
end //
Delimiter ;
call money_count (5, '2020-12-01', '2021-02-01');

Delimiter //
create function day_money_count( motorship_nam varchar(45), percent int, st_date date) returns float
not deterministic
 READS SQL DATA
 begin
 declare prib float;
 select sum((`ticket_price_by_first cat`*tickets_numb_sold_by_first_cat + ticket_price_by_second_cat*tickets_numb_sold_by_second_cat 
+ ticket_price_by_third_cat*tickets_numb_sold_by_third_cat)*percent*0.01) into prib from motorship join conducted_excursions on motorship_name = motorship_motorship_name 
where departure_date = st_date and motorship_name = motorship_nam;
return prib;
end //
Delimiter ;

select day_money_count('Titanic', 5, '2021-01-08');
drop view profit;

create view profit as select motorship_name, registration_number, sum(`ticket_price_by_first cat`*tickets_numb_sold_by_first_cat + ticket_price_by_second_cat*tickets_numb_sold_by_second_cat 
+ ticket_price_by_third_cat*tickets_numb_sold_by_third_cat) as moneys from conducted_excursions right join motorship on motorship_name = motorship_motorship_name group by motorship_name order by motorship_name;

select * from profit;
drop view not_redeemed;

create view not_redeemed as select cruise_route_cruise_name, departure_date, first_cat_solding_tickets - tickets_numb_sold_by_first_cat  as first_class, 
second_cat_solding_tickets - tickets_numb_sold_by_second_cat  as second_class, third_cat_solding_tickets - tickets_numb_sold_by_third_cat  as third_class from 
conducted_excursions join booking on conducted_excursions_id_excursions = id_excursions order by cruise_route_cruise_name;

select * from not_redeemed;
drop function avg_money_count_on_day;

Delimiter //
create function avg_money_count_on_day( motorship_nam varchar(45), percent int, st_date date) returns float
not deterministic
 READS SQL DATA
 begin
 declare prib float;
 select format(sum((`ticket_price_by_first cat`*tickets_numb_sold_by_first_cat + ticket_price_by_second_cat*tickets_numb_sold_by_second_cat 
+ ticket_price_by_third_cat*tickets_numb_sold_by_third_cat)*percent*0.01)/team_members_number / number_of_days, 2)  into prib from motorship join conducted_excursions on motorship_name = motorship_motorship_name 
inner join cruise_route on cruise_route_cruise_name = cruise_name
where departure_date = st_date and motorship_name = motorship_nam;
return prib;
end //
Delimiter ;
select avg_money_count_on_day('Titanic', 5, '2021-01-08');