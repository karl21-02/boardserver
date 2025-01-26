# 게시글 검색 최적화 보고서

## 1. 문제 상황

게시글 검색 기능의 성능 저하로 인해 시스템의 **Requests Per Second (RPS)**가 낮은 상태였습니다. 스트레스 테스트 결과는 다음과 같습니다:

- **기존 RPS:** 648 (500명, 초당 50명 조회)

## 2. 쿼리 성능 분석

### 2.1. 초기 `EXPLAIN` 결과

초기 `EXPLAIN` 명령어를 통해 쿼리의 실행 계획을 분석한 결과는 다음과 같습니다:

```sql
EXPLAIN SELECT 
    `id`,
    `name`,
    `isAdmin`,
    `contents`,
    `createTime`,
    `views`,
    `categoryId`,
    `userId`,
    `fileId`,
    `updateTime`
FROM post
WHERE 1=1
  AND name LIKE '테스트%'
  AND categoryId = 1
ORDER BY createTime DESC;

[ EXPLAIN키워드로 조회 결과 ]
![image](https://github.com/user-attachments/assets/03236191-be44-4ddb-84a8-a47cfd3f4b84)

-------------------------
### 최종 성능 결과

| 항목          | 기존 RPS | 최적화 후 RPS | 향상 비율 |
|---------------|---------|---------------|-----------|
| RPS           | 648     | 876           | +35%      |

INDEX가 1.11로 인덱스로 검색하긴 하지만, 약 300000만 번 중에서 1%정도만 조회한다는 결론 도출
따라서, POST테이블에 인덱스를 추가
![image](https://github.com/user-attachments/assets/fcdd0077-ee65-4d49-b05b-96c72f3aff4d)
위 사진처럼 name을 기준으로 인덱스 추가하면 위 사진처럼 5%로 조회 성능이 향상된 점을 파악

-------------------------

또한, 게시글 검색 부분에서 비동기 통신을 추가

이후 RPS가 기존 스트레스 테스트 500명이 초당 50명씩 조회한 RPS 648에서 같은 조건하에 876으로 향상된 점을 파악
