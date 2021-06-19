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
#представление выводит пролученые от экскурсии деньги
create view profit as select motorship_name, registration_number, sum(`ticket_price_by_first cat`*tickets_numb_sold_by_first_cat + ticket_price_by_second_cat*tickets_numb_sold_by_second_cat 
+ ticket_price_by_third_cat*tickets_numb_sold_by_third_cat) as moneys from conducted_excursions right join motorship on motorship_name = motorship_motorship_name group by motorship_name order by motorship_name;

select * from profit;
drop view not_redeemed;
#представление не выкупленных билетов
create view not_redeemed as select cruise_route_cruise_name, departure_date, first_cat_solding_tickets - tickets_numb_sold_by_first_cat  as first_class, 
second_cat_solding_tickets - tickets_numb_sold_by_second_cat  as second_class, third_cat_solding_tickets - tickets_numb_sold_by_third_cat  as third_class from 
conducted_excursions join booking on conducted_excursions_id_excursions = id_excursions order by cruise_route_cruise_name;

select * from not_redeemed;
drop function avg_money_count_on_day;
#возвращает среднюю зарплату на корабле за день рейса
Delimiter //
create function avg_money_count_on_day( motorship_nam varchar(45), percent int, st_date date) returns float
not deterministic
 READS SQL DATA
 begin
 declare prib float;
 select sum((`ticket_price_by_first cat`*tickets_numb_sold_by_first_cat + ticket_price_by_second_cat*tickets_numb_sold_by_second_cat 
+ ticket_price_by_third_cat*tickets_numb_sold_by_third_cat)*percent*0.01)/team_members_number / number_of_days  into prib from motorship join conducted_excursions on motorship_name = motorship_motorship_name 
inner join cruise_route on cruise_route_cruise_name = cruise_name
where departure_date = st_date and motorship_name = motorship_nam;
return prib;
end //
Delimiter ;

select format(avg_money_count_on_day('Titanic', 5, '2021-01-08'), 2);

select motorship_motorship_name, registration_number from conducted_excursions join motorship on  motorship_name = motorship_motorship_name
inner join cruise_route on cruise_route_cruise_name = cruise_name where date_add(departure_date, interval number_of_days DAY) = '2021-03-17';

select cruise_route_cruise_name, sum(tickets_numb_sold_by_first_cat) as first_cat_count, `ticket_price_by_first cat` as first_cat_price,
sum(tickets_numb_sold_by_second_cat) as second_cat_count, ticket_price_by_second_cat as second_cat_price,
sum(tickets_numb_sold_by_third_cat) as third_cat_count, ticket_price_by_third_cat as third_cat_price from conducted_excursions join
motorship on motorship_name = motorship_motorship_name group by cruise_route_cruise_name order by cruise_route_cruise_name;

select motorship_motorship_name, sum(day_money_count(motorship_motorship_name, 100, departure_date)) from conducted_excursions where departure_date between '2021-01-01' and '2021-04-01' group by motorship_motorship_name;

select cruise_route.*, sum(tickets_numb_sold_by_first_cat+tickets_numb_sold_by_second_cat+tickets_numb_sold_by_third_cat) from cruise_route join conducted_excursions
on cruise_route_cruise_name = cruise_name group by cruise_route_cruise_name having  sum(tickets_numb_sold_by_first_cat+tickets_numb_sold_by_second_cat+tickets_numb_sold_by_third_cat) >= all (select
sum(tickets_numb_sold_by_first_cat+tickets_numb_sold_by_second_cat+tickets_numb_sold_by_third_cat) from conducted_excursions group by cruise_route_cruise_name);

select motorship.*,  sum(tickets_numb_sold_by_first_cat+tickets_numb_sold_by_second_cat+tickets_numb_sold_by_third_cat) from motorship join conducted_excursions
on motorship_name = motorship_motorship_name group by motorship_motorship_name having count(id_excursions) >= all(select count(id_excursions) from
conducted_excursions group by motorship_motorship_name);

#выводит информацию о круизе и недополученной прибыли
select not_redeemed.cruise_route_cruise_name, not_redeemed.departure_date, first_class*`ticket_price_by_first cat`, second_class*ticket_price_by_second_cat, third_class*ticket_price_by_third_cat from not_redeemed
join conducted_excursions on not_redeemed.cruise_route_cruise_name = conducted_excursions.cruise_route_cruise_name and not_redeemed.departure_date = conducted_excursions.departure_date join motorship on motorship_name = motorship_motorship_name order by not_redeemed.departure_date;

#вывести информацию о маршруте с максимальной среденей зарплатой за 2021 год
select cruise_route_cruise_name, departure_date, motorship_motorship_name, format(max(avg_money_count_on_day(motorship_motorship_name, 5, departure_date)), 2) from conducted_excursions 
where departure_date between '2021-01-01' and '2021-12-31';

#вывести информацию о самом старом корабле
select * from motorship where date_of_issue <= all (select date_of_issue from motorship);

#вывести информацию о первой в 2021 году экскурсии
select lower(cruise_route_cruise_name), motorship_motorship_name, registration_number, departure_date  from conducted_excursions join motorship on
motorship_name = motorship_motorship_name where departure_date <= all (select departure_date from conducted_excursions where departure_date >= '2021-01-01') and departure_date >= '2021-01-01';

#вывести название маршрута и количество забронированных билетов у маршрутов, длина которых более 10000 или название включает в себя атлантику
select cruise_name, sum(first_cat_solding_tickets+second_cat_solding_tickets+third_cat_solding_tickets) from booking join conducted_excursions on conducted_excursions_id_excursions = id_excursions
join cruise_route on cruise_route_cruise_name = cruise_name where total_length >10000 or lower(cruise_name) like '%atlantic%' group by cruise_route_cruise_name;