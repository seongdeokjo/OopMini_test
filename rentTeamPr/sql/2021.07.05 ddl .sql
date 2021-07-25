
#테이블 삭제
drop table rent;
drop table member;
drop table car;
drop table manager;


#member table 생성
create table member(
membercode numeric[4] constraint member_membercode_pk primary key,
id varchar2(20) constraint member_id_uk unique not null,
pw varchar2(20) not null,
name varchar2(20) not null,
carreg varchar2(14) constraint member_carrg_uk unique not null,
email varchar2(40) not null,
address varchar2(40) not null,
account varchar2(30) not null, --계좌
balance number(6)   --잔고
);

#car 테이블 생성
create table car(
carcode number(4) constraint car_carcode_pk primary key,
carnumber varchar2(20) constraint car_cnum_uk unique not null,
carname varchar2(20) not null,
carsize varchar2(10) not null,
carseat number(2) not null,
caryear number(4) not null,
fuel varchar2(20) not null,
rentck number(1) constraint car_rentck_ck check(rentck between 0 and 1) not null

-- rentck 는 대여현황을 체크하기 위한 컴럼으로 
-- 0 이면 대여가 가능한 상태임을 나타내고 1이면 대여중임을 나타낸다
);

#manager 테이블 생성
create table manager(
managercode number(4) constraint manager_managercode_pk primary key,
mid varchar2(20) not null,
mpw varchar2(20) not null
);

#rent table 생성 
create table rent(
rentcode number(4) constraint rent_rentcode_pk primary key,
pay number(6) not null,
rentperiod number(1) constraint rent_period_ck check(rentperiod between 1 and 3)  not null,
rent_date date default sysdate , --대여 날짜
carcode number constraint rent_carcode_fk REFERENCES car(carcode) on delete cascade not null,
membercode number constraint rent_membercode_fk REFERENCES member(membercode) on delete cascade constraint rent_membercode_uk unique not null,
managercode number constraint rent_managercode_fk REFERENCES manager(managercode) on delete cascade
);


create table pay(
paycode number(4) constraint pay_paycode_pk primary key,
paymoney number(8) constraint pay_paymoney_ck check(paymoney= 10000 or paymoney=20000 or paymoney=30000), --결제 금액을 제약 조건으로 (소,중,대)
carsize varchar2(10) not null
);


commit;