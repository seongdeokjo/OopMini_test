/* 계좌관리 테이블 생성 */
create table tbl_account(
    ano char(7) PRIMARY key,
    aname VARCHAR(20) not null,
    balance int default 0
);
/* 샘플 데이터 입력*/
insert into tbl_account(ano,aname,balance) 
values ('111-111','오라민',1000);
insert into tbl_account(ano,aname,balance)
values ('222-222','하리보',2000);

/*결과 출력*/
select * from tbl_account;

/*작업 결과 적용*/
commit;