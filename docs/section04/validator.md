## 스프링 배치 실행 - validator

1. 기본개념
    - Job 실행에 꼭 필요한 파라미터를 검증하는 용도
    - DefaultJobParametersValidator 구현체를 지원하며, 좀 더 복잡한 제약 조건이 있다면 인터페이스를 직접 구현할 수도 있음

2. 구조
    - <img src="../../images/section04/validator.png" alt="validator.png">

<img src="../../images/section04/validator-flow.png" alt="validator-flow.png">