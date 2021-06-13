insert into ad_breaks values (default, '2021-05-12', 4.5 , 4, 1, 20);
insert into ad_breaks values (default, '2021-05-12', 4.5 , 4, 6, 10);
insert into ad_breaks values (default, '2021-05-12', 4.5 , 10, 1, 10);


CREATE USER 'anouther'@'localhost' IDENTIFIED BY '1234';
GRANT select ON ad_breaks.* TO 'anouther'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'anouther'@'localhost';

CREATE USER 'advertiser'@'localhost' IDENTIFIED BY '1234';
GRANT select ON ad_breaks.* TO 'advertiser'@'localhost';
GRANT insert, update ON ad_breaks.ad TO 'advertiser'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'advertiser'@'localhost';

CREATE USER 'channel_owner'@'localhost' IDENTIFIED BY '1234';
GRANT select ON ad_breaks.* TO 'channel_owner'@'localhost';
GRANT insert, update ON ad_breaks.broadcasts TO 'channel_owner'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'channel_owner'@'localhost';

CREATE USER 'admin'@'localhost' IDENTIFIED BY '1234';
GRANT select, insert, update ON ad_breaks.* TO 'admin'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'admin'@'localhost';

drop view ad_info;

create view ad_info as select idad_breaks, ad_breaks_date, ad.minuts_count, broadcast_name, company_name,
cost*ad.minuts_count-cost*ad.minuts_count*discount/100 as cost, product from ad, ad_breaks, advertisers, broadcasts
 where idad = ad_idad and idadvertisers = advertisers_idadvertisers and broadcasts_idbroadcasts=idbroadcasts order by idad_breaks;
 
 create view channel_info as select channel_number, broadcast_name, ad_breaks_date, broadcast_time, product as advertised_product from broadcasts 
 LEFT JOIN ad_breaks on broadcasts_idbroadcasts=idbroadcasts LEFT JOIN ad on ad_idad =idad order by channel_number, broadcast_name;
 drop view channel_info; 
 select * from ad_info;
 select * from channel_info;
 
 /*Delimiter //
 create procedure delete_adverstisers()
	begin
    delete from ad_breaks where ad
 */
 drop procedure minuts_count;
 
 
 Delimiter //
 create procedure minuts_count(prod varchar(45))
 begin
 select sum(ad.minuts_count), broadcast_name from ad join ad_breaks on idad=ad_idad join broadcasts on broadcasts_idbroadcasts=idbroadcasts where ad.product = prod group by broadcasts_idbroadcasts;
 end //
 Delimiter ;
 
 call minuts_count('Pepsi');
 
 drop procedure advertisers_info;
 
 Delimiter //
 create procedure advertisers_info(adversiter varchar(45))
 begin
 select product, format(sum(minuts_count), 2), sum(cost) from ad_info where company_name=adversiter group by product;
 end //
 Delimiter ;
 
 call advertisers_info('Formula reclamy'); 
 
 drop procedure date_info;
 
 Delimiter //
 create procedure date_info(dat date)
 begin
 declare sum_all1 float;
 select sum(cost) into sum_all1 from ad_info where ad_breaks_date=dat;
 create temporary table date_inf(broadcast_name varchar(45), procent float);
 insert into date_inf select broadcast_name, sum(cost)/sum_all1*100 from ad_info where ad_breaks_date = dat group by broadcast_name;
 select * from date_inf;
 drop table date_inf;
 end //
 Delimiter ;
 
 
 call date_info('2021-05-13');
 drop function day_sum;
 
 Delimiter //
 create function day_sum(dat date, br_name varchar(45)) returns float
 not deterministic
 READS SQL DATA
 begin
 declare prib float;
 select sum(cost-cost*discount/100) into prib from ad_breaks join broadcasts on idbroadcasts=broadcasts_idbroadcasts join advertisers on idadvertisers=advertisers_idadvertisers  where broadcast_name=br_name and ad_breaks_date = dat;
 return prib;
 end //
 Delimiter ;
 
 select day_sum('2021-05-13', 'Sled');
 
 drop trigger tr_delete;
 
 Delimiter //
 create trigger tr_delete_ad
 before delete
 on ad
 for each row
 begin
 delete from ad_breaks where ad_idad = old.idad;
 end //
 Delimiter ;
 show triggers;
 
 Delimiter //
 create trigger tr_delete_advertisers
 before delete
 on advertisers
 for each row
 begin
 delete from ad_breaks where advertisers_idadvertisers = old.idadvertisers;
 end //
 Delimiter ;
 
  Delimiter //
 create trigger tr_delete_broadcasts
 before delete
 on broadcasts
 for each row
 begin
 delete from ad_breaks where broadcasts_idbroadcasts = old.idbroadcasts;
 end //
 Delimiter ;
 
 Delimiter //
 create trigger tr_insert_ad_breaks
 before insert
 on ad_breaks
 for each row
 begin
 if  (select minuts_count from ad where idad = new.ad_idad) > new.minuts_count then
 kill connection_id();
 end if;
 if new.ad_breaks_date > current_date() then 
 kill connection_id();
 end if;
 end //
 Delimiter ;
 
 insert into ad_breaks values (default, '2021-06-17', 0.2, 1, 1, 1);
 drop trigger tr_update_ad;
 
 Delimiter //
 create trigger tr_update_adv 
 before update
 on advertisers
 for each row
 begin
 if new.discount > 100 or new.discount < 0 then
 kill connection_id();
 end if;
 end //
 Delimiter ;
 
 update advertisers set discount = 300;
 create index cst on broadcasts(cost);
 create index cnt on ad(minuts_count);
 
select product, format(sum(ad.minuts_count), 2) from ad join ad_breaks on ad_idad = idad join broadcasts on broadcasts_idbroadcasts = idbroadcasts group by product, broadcast_name;
 
 select company_name, product, format(sum(ad.minuts_count), 2),format(sum(cost*ad.minuts_count-cost*ad.minuts_count*discount/100), 2) from advertisers join ad_breaks on idadvertisers = advertisers_idadvertisers
 join ad on ad_idad = idad join broadcasts on idbroadcasts = broadcasts_idbroadcasts group by company_name, product order by company_name, product;
 
 select broadcast_name, company_name, format(sum(ad.minuts_count), 2),format(sum(cost*ad.minuts_count-cost*ad.minuts_count*discount/100), 2) from advertisers join ad_breaks on idadvertisers = advertisers_idadvertisers
 join ad on ad_idad = idad join broadcasts on idbroadcasts = broadcasts_idbroadcasts where ad_breaks_date between '2021-03-10' and '2021-06-02' group by broadcast_name, company_name order by broadcast_name, company_name;
 
 select product, broadcast_name, format(sum(ad.minuts_count), 2), format(sum(cost*ad.minuts_count), 2) from ad join 
 ad_breaks on ad_idad = idad join broadcasts on broadcasts_idbroadcasts = idbroadcasts where idad in (select ad_idad from ad_breaks group 
 by ad_idad having count(ad_idad) 
 >= all(select count(ad_idad) from ad_breaks group by ad_idad)) group by broadcast_name order by product, broadcast_name;
 
 select broadcast_name, channel_number, broadcast_time, format(sum(ad.minuts_count), 2), format(sum(cost*ad.minuts_count-cost*ad.minuts_count*discount/100), 2)
 from broadcasts join ad_breaks on idbroadcasts = broadcasts_idbroadcasts join ad on ad_idad = idad join advertisers on idadvertisers = advertisers_idadvertisers 
 where idbroadcasts in (select broadcasts_idbroadcasts from ad_breaks group 
 by broadcasts_idbroadcasts having count(broadcasts_idbroadcasts) 
 >= all(select count(broadcasts_idbroadcasts) from ad_breaks group by broadcasts_idbroadcasts)) group by broadcast_name order by broadcast_name;
 
 select lower(product), broadcast_time, format(sum(ad_info.cost), 2) from ad_info inner join broadcasts on ad_info.broadcast_name = broadcasts.broadcast_name where
 broadcast_time between '08:00:00' and '15:00:00' group by product order by product;
 
 select broadcast_name, ad_breaks_date, format((sum(ad_breaks.minuts_count) - sum(ad.minuts_count)), 2) from ad_breaks join broadcasts on broadcasts_idbroadcasts = idbroadcasts 
 join ad on ad_idad = idad group by broadcasts_idbroadcasts, ad_breaks_date;
 
 select broadcast_name, count(idad_breaks) from ad_info group by broadcast_name having count(idad_breaks) > 
 (select avg(id_count) as cnt from (select count(idad_breaks) as id_count from ad_info group by broadcast_name) as t) order by count(idad_breaks) desc;
 
 select broadcast_name, product, company_name from broadcasts join ad_breaks on idbroadcasts = broadcasts_idbroadcasts join ad on ad_idad = idad join advertisers on idadvertisers = advertisers_idadvertisers
 where DATEDIFF(CURRENT_DATE(), ad_breaks_date) > 25 order by DATEDIFF(CURRENT_DATE(), ad_breaks_date);
 
 select product, format(sum(cost*ad.minuts_count-cost*ad.minuts_count*discount/100), 2) as sum from ad join ad_breaks on ad_idad = idad join broadcasts on broadcasts_idbroadcasts = idbroadcasts join 
 advertisers on idadvertisers = advertisers_idadvertisers group by product having sum(cost*ad.minuts_count-cost*ad.minuts_count*discount/100) <= all (select sum(cost*ad.minuts_count-cost*ad.minuts_count*discount/100) from ad 
 join ad_breaks on ad_idad = idad join broadcasts on broadcasts_idbroadcasts = idbroadcasts join 
 advertisers on idadvertisers = advertisers_idadvertisers group by product);
 
 delete from ad where idad = 5;
 