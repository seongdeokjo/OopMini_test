select * from member;
select * from pay;
select * from rent;



-- 결제에 필요한 클래스? 혹은 메서드 -> 계좌이체 ( 사용자->관리자) / 출금(결제완료시) / 충전 (결제하기위한) /  
--사용자 -> 로그인 -> 대여 -> 자동차 선택 -> 대여기간에 따른 가격 -> 결제 -> 1.돈이 없어 -> 1. 결제 페이지로 보낸다. 2. 즉석 충전 
--                                                                2.돈(충전)이 있어 -> 결제 -> 계좌 (출금) -> 입금(관리자) -> 자동차(대여 상태) / 렌트 데이터 생성 -> 대여 완료 

update member 
set balance = balance - (select pay) where account = '1111111-11111';

commit;