insert into ad_breaks values (default, '2021-05-12', 4.5 , 4, 1, 20);
insert into ad_breaks values (default, '2021-05-12', 4.5 , 4, 6, 10);
insert into ad_breaks values (default, '2021-05-12', 4.5 , 10, 1, 10);

alter table advertisers alter column discount set default 0;

CREATE USER 'anouther'@'localhost' IDENTIFIED BY '1234';
GRANT select ON ad_breaks.* TO 'anouther'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'anouther'@'localhost';

drop view ad_info;

create view ad_info as select idad_breaks, ad_breaks_date, ad.minuts_count, broadcast_name, company_name,
cost-cost*discount/100 as cost, product from ad, ad_breaks, advertisers, broadcasts
 where idad = ad_idad and idadvertisers = advertisers_idadvertisers and broadcasts_idbroadcasts=idbroadcasts order by idad_breaks;
 
 select * from ad_info;
 
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