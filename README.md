# 신예리의 개발전기 Develography

> 커뮤니티 앱 프로젝트

![develography_cover](https://user-images.githubusercontent.com/68595933/192332560-a99407ab-1ac5-4e55-a3ca-c01edcf795cc.PNG)

## 시작

- 블로그를 활용한 커뮤니티 앱 프로젝트입니다.

---

## 개발

### 기간

- 22.08.02.~22.00.00.

### 목표

- 코틀린으로 주요 기능을 구현해 봅니다.
- Jetpack과 Firebase를 사용해 봅니다.

### 사용

- Kotlin
- Jetpack
- Firebase

---

## 기능

### 1. 인증(Firebase Auth)

![auth](https://user-images.githubusercontent.com/68595933/192332941-1b4bc082-be0e-4f18-babd-97f2d810662d.PNG)

- 1.1 로그인/로그아웃
  - 로그인
  - 비회원로그인
  - 로그아웃
  
```javascript
// LoginActivity.kt

        // 로그인 버튼 클릭하면
        binding.loginBtn.setOnClickListener {

            // 로그인 조건 확인
            var emailCheck = false
            var pwCheck = false

            // 이메일, 비밀번호
            val emailTxt = binding.email.text.toString()
            val pwTxt = binding.pw.text.toString()

            // 이메일 정규식
            val emailPattern = Patterns.EMAIL_ADDRESS

            // 이메일 검사
            if(emailTxt.isEmpty()) {
                emailCheck = false
                binding.emailArea.error = "이메일주소를 입력하세요"
            } else if(emailPattern.matcher(emailTxt).matches()) {
                emailCheck = true
                binding.emailArea.error = null
            } else {
                emailCheck = false
                binding.emailArea.error = "올바른 이메일이 아닙니다"
            }

            // 비밀번호 검사
            if(pwTxt.isEmpty()) {
                pwCheck = false
                binding.pwArea.error = "비밀번호를 입력하세요"
            } else if (pwTxt.length<6) {
                pwCheck = false
                binding.pwArea.error = "최소 6자 이상 입력하세요"
            } else if (pwTxt.length>20) {
                pwCheck = false
                binding.pwArea.error = "20자 이하로 입력하세요"
            } else {
                pwCheck = true
                binding.pwArea.error = null
            }

            // 로그인 조건을 모두 만족하면
            if(emailCheck and pwCheck) {

                // 로그인
                auth.signInWithEmailAndPassword(emailTxt, pwTxt)
                    .addOnCompleteListener(this) { task ->

                        // 성공하면
                        if (task.isSuccessful) {

                            // 명시적 인텐트 -> 다른 액티비티 호출
                            val intent = Intent(this, MainActivity::class.java)

                            // 메인 액티비티 시작
                            startActivity(intent)

                            // 로그인 액티비티 종료
                            finish()

                        // 실패하면
                        } else {

                            // 메시지 띄움
                            Toast.makeText(this, "이메일과 비밀번호를 다시 확인하세요", Toast.LENGTH_LONG).show()

                        }

                    }

            // 조건 불만족하면
            } else {

                // 메시지 띄움
                Toast.makeText(this, "이메일과 비밀번호를 다시 확인하세요", Toast.LENGTH_LONG).show()

            }

        }
```
```javascript
// IntroActivity.kt

        // 비회원 버튼 클릭하면
        binding.guestBtn.setOnClickListener {

            // 익명으로 로그인
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->

                    // 성공하면
                    if (task.isSuccessful) {

                        // 메인 액티비티 시작
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        // 인트로 액티비티 종료
                        finish()

                    // 실패하면
                    } else {

                        // 토스트 메시지 띄움
                        Toast.makeText(this, "비회원 로그인에 실패했습니다", Toast.LENGTH_LONG).show()

                    }

                }

        }
```
```javascript
// MainActivity.kt

        // 로그아웃 버튼
        alertDialog.findViewById<ConstraintLayout>(R.id.logout)?.setOnClickListener {

            // 로그아웃 실행
            Firebase.auth.signOut()

            // 로그아웃 확인 메시지지
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(this, IntroActivity::class.java)

            // 인트로 액티비티 시작
            startActivity(intent)

            // 메인 액티비티 종료
            finish()

        }
```

- 1.2 회원가입
  - 회원가입

```javascript
// JoinActivity.kt

        // 회원가입 버튼 클릭하면
        binding.joinBtn.setOnClickListener {

            // 회원가입 조건 확인
            var emailCheck = false
            var pwCheck = false
            var pw2Check = false

            // 이메일, 비밀번호, 비밀번호 확인
            val emailTxt = binding.email.text.toString()
            val pwTxt = binding.pw.text.toString()
            val pw2Txt = binding.pw2.text.toString()

            // 이메일 정규식
            val emailPattern = Patterns.EMAIL_ADDRESS

            // 이메일 검사
            if(emailTxt.isEmpty()) {
                emailCheck = false
                binding.emailArea.error = "이메일주소를 입력하세요"
            } else if(emailPattern.matcher(emailTxt).matches()) {
                emailCheck = true
                binding.emailArea.error = null
            } else {
                emailCheck = false
                binding.emailArea.error = "올바른 이메일이 아닙니다"
            }

            // 비밀번호 검사
            if(pwTxt.isEmpty()) {
                pwCheck = false
                binding.pwArea.error = "비밀번호를 입력하세요"
            } else if (pwTxt.length<6) {
                pwCheck = false
                binding.pwArea.error = "최소 6자 이상 입력하세요"
            } else if (pwTxt.length>20) {
                pwCheck = false
                binding.pwArea.error = "20자 이하로 입력하세요"
            } else {
                pwCheck = true
                binding.pwArea.error = null
            }

            // 공란 검사
            if(emailTxt.isEmpty() || pwTxt.isEmpty() || pw2Txt.isEmpty()) {
                emailCheck = false
                pwCheck = false
                pw2Check = false
            }

            // 비밀번호 일치 검사
            if(pwTxt == pw2Txt && pwTxt.isNotEmpty() && pw2Txt.isNotEmpty()) {
                pw2Check = true
                binding.pw2Area.error = null
            } else if(pwTxt == pw2Txt && pwTxt.isEmpty() && pw2Txt.isEmpty()) {
                pw2Check = false
                binding.pw2Area.error = "비밀번호를 한 번 더 입력하세요"
            } else {
                pw2Check = false
                binding.pw2Area.error = "비밀번호가 일치하지 않습니다"
            }

            // 회원가입 조건을 모두 만족하면
            if (emailCheck and pwCheck and pw2Check) {

                // 회원가입
                auth.createUserWithEmailAndPassword(emailTxt, pwTxt)
                    .addOnCompleteListener(this) { task ->

                        // 성공하면
                        if (task.isSuccessful) {

                            // 명시적 인텐트 -> 다른 액티비티 호출
                            val intent = Intent(this, MainActivity::class.java)

                            // 메인 액티비티 시작
                            startActivity(intent)

                            // 조인 액티비티 종료
                            finish()

                        // 조건 만족해도
                        } else {

                            // 가입 불가능한 경우가 있음
                            Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_LONG).show()

                        }

                    }

            // 조건을 만족하지 못하면
            } else {

                // 가입 불가
                Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_LONG).show()

            }

        }
```

### 웹뷰

![webview](https://user-images.githubusercontent.com/68595933/192333063-c18a7ff9-32bf-449b-bdfe-225eb456ec2a.PNG)

- web view
  - 블로그
  - 깃허브

```javascript
// 블로그 코드
```
```javascript
// 깃허브 코드
```

- 썸네일
  - 블로그

```javascript
// 블로그 코드
```

### 게시판

![board_write](https://user-images.githubusercontent.com/68595933/192333230-07034857-d16b-4eb3-9ba7-42b56f2bd293.PNG)
![board_comment](https://user-images.githubusercontent.com/68595933/192333287-c0932989-994e-466d-a798-fb3c88cbc156.PNG)

- 글 CRUD
- 댓글 CRUD

```javascript
// 글 CRUD
```
```javascript
// 글 CRUD
```

### 북마크

![bookmark](https://user-images.githubusercontent.com/68595933/192333390-f8367e6e-3c21-409d-9e69-03e35992a9ac.PNG)

- 북마크 추가
- 북마크 삭제

```javascript
// 북마크 추가/삭제
```

---

## 피드백

### 문제점

1. ~함.

### 개선점

1. ~할 것.

---

## 저작권

이 프로젝트는 MIT 라이센스에 따라 라이센스가 부여됩니다. 자세한 내용은 [LICENSE.md](LICENSE.md) 파일을 참조하십시오.

---

## 레퍼런스

- [참고 강의 바로가기][참고]

<!-- 링크 -->

[참고]: https://www.inflearn.com/course/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%BD%94%ED%8B%80%EB%A6%B0-%EC%BB%A4%EB%AE%A4%EB%8B%88%ED%8B%B0%EC%95%B1/dashboard
