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
 if new.ticket_price_by_firstcat < 0 or new.ticket_price_by_second_cat < 0 or new.ticket_price_by_third_cat < 0 or date_of_issue > current_date() 
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
 if new.ticket_price_by_firstcat < 0 or new.ticket_price_by_second_cat < 0 or new.ticket_price_by_third_cat < 0 or date_of_issue > current_date() 
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