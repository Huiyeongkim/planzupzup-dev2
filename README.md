# Planzupzup - Dev2

**Planzupzup**은 여행 일정을 효율적으로 계획하고 관리할 수 있는 웹 서비스입니다.  
본 저장소는 기존 Dev1에서 개선된 **두 번째 개발 버전(Dev2)** 입니다.

## 🔧 주요 변경 사항 (Dev1 → Dev2)
- 백엔드 1인 개발 체제로 전환
- 자체 로그인 제거 → **카카오 소셜 로그인** 도입
- **Redis 캐시** 기능 제거
- **댓글 기능** 추가

## 👩‍💻 백엔드 (1인 개발) 기능

- **김희영** ([GitHub 프로필](https://github.com/Huiyeongkim))
  - 카카오 소셜 로그인 구현
  - 게시글 및 댓글 기능 개발
  - 여행 일정 관리 기능 구현
  - 좋아요 기능
  - 이미지 업로드 및 AWS S3 연동
  - EC2를 이용한 서버 배포

## 🛠 기술 스택

- Java, Spring Boot
- MySQL, JPA
- AWS EC2, S3

## 📎 관련 링크

- [Dev1 저장소 보기](https://github.com/Huiyeongkim/planzupzup-dev1)
