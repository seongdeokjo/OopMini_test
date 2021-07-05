--데이터 삭제
delete from member;
delete from car;
delete from manager;
delete from rent;

--각 테이블의 전체 컬럼 보기
select * from manager;
select * from car ;
select * from rent;
select * from member;
select * from pay;

--문제점 : 결제를 위해 렌트테이블의 데이터가 입력이되어야 pay를 가져와 잔고에서 돈을 인출 해야한다.
-- 현재 진행 과정 : 대여 -> 렌트 데이터 추가 -> 차량 대여여부 변경 -> 결제 ??? -> 이런 서비스는 없습니다. 
update member
set balancce = balance - pay() where account = '1111111-11111';


--member 데이터 입력
insert into member values
(MEMBER_MEMBERCODE_SEQ.nextval, 'member', '123456', '손흥민', '1111', 'member@naver.com', 'SEOUL', '1111111-11111', 0);

--car 데이터 입력
insert into car values
(CAR_CARCODE_SEQ.nextval,'1111','ford','big',6,2020,'휘발유',0);

--manager 데이터 입력
insert into manager values(MANAGER_MANAGERCODE_SEQ.nextval,'admin','1234');

-- rent table의 데이터가 들어가는 sql문 --> 6.26 : 자동차사이즈를 입력받아 저장된 금액과 날짜를 곱해 결제 금액을 알려주는 sql 작성완료.                                                                                                 
insert into rent values(rent_rentcode_seq.nextval,
                        3 * (select paymoney from pay where carsize = 'small'),
                        3,sysdate+3,(select carcode from car where carnumber = 1111),
                        (select membercode from member where carreg = '3'),
                        1
                        );

--pay 테이블 데이터 입력
insert into pay values(pay_paycode_seq.nextval, 10000,'small');
insert into pay values(pay_paycode_seq.nextval, 20000,'middle');
insert into pay values(pay_paycode_seq.nextval, 30000,'big');






commit;


