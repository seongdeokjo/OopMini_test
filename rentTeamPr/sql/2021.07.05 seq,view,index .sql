-- seq , view , index 

--member 시퀀스 생성
CREATE SEQUENCE member_membercode_SEQ
INCREMENT BY 1
START WITH 1;

--car 시퀀스 생성
CREATE SEQUENCE car_carcode_SEQ
INCREMENT BY 1
START WITH 1;

--manager 시퀀스 생성
create sequence manager_managercode_seq;

--rent 시퀀스 생성
CREATE SEQUENCE rent_rentcode_SEQ
INCREMENT BY 1
START WITH 1;

--pay 시퀀스 생성
create sequence pay_paycode_seq
start with 1
increment by 1;

-- member 시퀀스 삭제
drop sequence member_membercode_seq;
-- car 시퀀스 삭제
drop sequence car_carcode_seq;
--rent 시퀀스 삭제
drop sequence rent_rentcode_seq;
--pay 시퀀스 삭제
drop sequence pay_paycode_seq;

--커밋
commit;